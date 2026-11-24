package org.javaup.ai;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.spec.McpSchema;
import reactor.core.scheduler.Schedulers;

import java.io.PrintStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

/** 通过 Streamable HTTP 直接调用免费的 Parallel Search MCP，不依赖模型或本地 MCP Server。 */
public final class ParallelSearchMcpExample {

    static final String SERVER_URL = "https://search.parallel.ai";
    static final String USER_AGENT = "nexus-agent/parallel-search-mcp-example";

    private ParallelSearchMcpExample() {
    }

    public static void main(String[] args) throws Exception {
        // 先验证参数，避免用法错误也触发网络请求。
        Map<String, Object> arguments = arguments(args);
        try (McpSyncClient client = createClient(SERVER_URL)) {
            execute(client, args[0], arguments, System.out);
        } finally {
            // 独立命令行入口退出时释放 SDK 使用的共享调度器，避免 exec:java 等待后台线程。
            Schedulers.shutdownNow();
        }
    }

    static McpSyncClient createClient(String serverUrl) {
        HttpClientStreamableHttpTransport transport = HttpClientStreamableHttpTransport.builder(serverUrl)
                .endpoint("/mcp")
                .customizeRequest(request -> request.header("User-Agent", USER_AGENT))
                .build();
        return McpClient.sync(transport)
                .clientInfo(new McpSchema.Implementation("nexus-agent-parallel-search-example", "1.0.0"))
                .requestTimeout(Duration.ofSeconds(60))
                .build();
    }

    static Map<String, Object> arguments(String[] args) {
        if (args.length == 1 && "tools".equals(args[0])) {
            return Map.of();
        }
        if (args.length >= 3 && "search".equals(args[0]) && !args[1].isBlank()
                && Arrays.stream(args, 2, args.length).noneMatch(String::isBlank)) {
            return Map.of("objective", args[1], "search_queries", Arrays.asList(args).subList(2, args.length));
        }
        if (args.length >= 2 && "fetch".equals(args[0])) {
            return Map.of("urls", Arrays.asList(args).subList(1, args.length));
        }
        throw new IllegalArgumentException("用法: tools | search \"objective\" \"query\" [\"query\" ...]"
                + " | fetch URL [URL ...]");
    }

    static void execute(McpSyncClient client, String command, Map<String, Object> arguments,
                        PrintStream output) throws Exception {
        client.initialize();
        McpSchema.ListToolsResult tools = client.listTools();
        McpJsonMapper mapper = McpJsonMapper.getDefault();
        if ("tools".equals(command)) {
            output.println(mapper.writeValueAsString(tools));
            return;
        }
        String toolName = "search".equals(command) ? "web_search" : "web_fetch";
        if (tools.tools().stream().noneMatch(tool -> toolName.equals(tool.name()))) {
            throw new IllegalStateException("Server 未提供工具: " + toolName);
        }
        McpSchema.CallToolResult result = client.callTool(McpSchema.CallToolRequest.builder()
                .name(toolName)
                .arguments(arguments)
                .build());
        output.println(mapper.writeValueAsString(result));
        if (Boolean.TRUE.equals(result.isError())) {
            throw new IllegalStateException("MCP 工具调用失败: " + toolName);
        }
    }
}
