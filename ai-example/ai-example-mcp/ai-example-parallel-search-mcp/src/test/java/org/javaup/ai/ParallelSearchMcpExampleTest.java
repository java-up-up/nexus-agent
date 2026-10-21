package org.javaup.ai;

import com.sun.net.httpserver.HttpServer;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.json.TypeRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParallelSearchMcpExampleTest {

    private final McpJsonMapper mapper = McpJsonMapper.getDefault();
    private final List<Request> requests = new CopyOnWriteArrayList<>();
    private HttpServer server;
    private boolean toolError;

    @BeforeEach
    void startServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/mcp", exchange -> {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            requests.add(new Request(exchange.getRequestURI().getPath(),
                    exchange.getRequestHeaders().getFirst("User-Agent"),
                    exchange.getRequestHeaders().getFirst("Authorization"), body));
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
                return;
            }
            Map<String, Object> message = mapper.readValue(body, new TypeRef<>() { });
            if (!message.containsKey("id")) {
                exchange.sendResponseHeaders(202, -1);
                exchange.close();
                return;
            }
            Object result = switch ((String) message.get("method")) {
                case "initialize" -> Map.of("protocolVersion", "2025-06-18", "capabilities", Map.of("tools", Map.of()),
                        "serverInfo", Map.of("name", "test-search", "version", "1.0.0"));
                case "tools/list" -> Map.of("tools", List.of(tool("web_search"), tool("web_fetch")));
                case "tools/call" -> Map.of("isError", toolError, "content", List.of(Map.of("type", "text",
                        "text", toolError ? "Search unavailable" : "Java documentation: https://dev.java/learn/")));
                default -> throw new IllegalArgumentException("Unexpected method: " + message.get("method"));
            };
            byte[] response = mapper.writeValueAsString(Map.of("jsonrpc", "2.0", "id", message.get("id"),
                    "result", result)).getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });
        server.start();
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
    }

    @Test
    void discoversAndDispatchesSearchAndFetchOverAnonymousHttp() throws Exception {
        assertTrue(run("tools").contains("web_search"));
        assertTrue(run("search", "Find Java documentation", "Java getting started", "Java language guide")
                .contains("https://dev.java/learn/"));
        assertTrue(run("fetch", "https://dev.java/learn/").contains("Java documentation"));

        List<Map<String, Object>> calls = requests.stream().filter(request -> request.body().contains("tools/call"))
                .map(request -> readMessage(request.body())).toList();
        assertEquals(2, calls.size());
        assertEquals(Map.of("name", "web_search", "arguments", Map.of("objective", "Find Java documentation",
                "search_queries", List.of("Java getting started", "Java language guide"))), calls.get(0).get("params"));
        assertEquals(Map.of("name", "web_fetch", "arguments", Map.of("urls", List.of("https://dev.java/learn/"))),
                calls.get(1).get("params"));
        assertFalse(requests.isEmpty());
        for (Request request : requests) {
            assertEquals("/mcp", request.path());
            assertEquals(ParallelSearchMcpExample.USER_AGENT, request.userAgent());
            assertNull(request.authorization());
        }
    }

    @Test
    void propagatesToolErrorsInsteadOfReportingSuccess() {
        toolError = true;
        assertThrows(IllegalStateException.class, () -> run("search", "Find documentation", "Java documentation"));
    }

    @Test
    void rejectsInvalidArgumentsBeforeConnecting() {
        for (String[] args : List.of(new String[0], new String[]{"search"}, new String[]{"fetch"},
                new String[]{"search", "", "Java"}, new String[]{"unknown"})) {
            assertThrows(IllegalArgumentException.class, () -> ParallelSearchMcpExample.arguments(args));
        }
        assertTrue(requests.isEmpty());
    }

    private String run(String... args) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (var client = ParallelSearchMcpExample.createClient("http://127.0.0.1:" + server.getAddress().getPort());
             var stream = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            ParallelSearchMcpExample.execute(client, args[0], ParallelSearchMcpExample.arguments(args), stream);
        }
        return output.toString(StandardCharsets.UTF_8);
    }

    private Map<String, Object> readMessage(String body) {
        try {
            return mapper.readValue(body, new TypeRef<>() { });
        } catch (Exception exception) {
            throw new AssertionError(exception);
        }
    }

    private Map<String, Object> tool(String name) {
        return Map.of("name", name, "description", "Fixture tool", "inputSchema", Map.of("type", "object"));
    }

    private record Request(String path, String userAgent, String authorization, String body) { }
}
