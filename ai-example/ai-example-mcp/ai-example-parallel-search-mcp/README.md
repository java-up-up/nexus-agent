# Parallel Search MCP 示例

通过项目已有的 Java MCP SDK 和 Streamable HTTP 连接
[`https://search.parallel.ai/mcp`](https://docs.parallel.ai/integrations/mcp/search-mcp)，
直接发现、调用 `web_search` 和 `web_fetch`。匿名入口免费且有速率限制，无需 Parallel API Key，
也无需模型密钥、本地 MCP Server 或 Nexus Agent 的数据库等基础设施。

这是独立的命令行工具调用示例，不包含模型对话或 ReAct Agent 循环。现有 office MCP 示例和
主应用的 Tavily 配置无需修改。客户端发送 `nexus-agent/parallel-search-mcp-example` User-Agent，
工具请求超时为 60 秒，退出时关闭连接。

## 运行

安装 JDK 17+ 和 Maven，在仓库根目录执行：

```bash
cd ai-example/ai-example-mcp/ai-example-parallel-search-mcp
mvn clean verify

# 查看服务端返回的工具名称、说明和参数 schema
mvn exec:java -Dexec.args='tools'

# 第一个参数为搜索目标，其后为一个或多个查询字符串
mvn exec:java -Dexec.args='search "Find official Java getting started documentation" "Java getting started documentation"'

# 获取一个或多个网页的内容摘录
mvn exec:java -Dexec.args='fetch https://dev.java/learn/'
```

`search` 映射为 `web_search(objective, search_queries)`，`fetch` 映射为 `web_fetch(urls)`。
输出为 MCP 结果 JSON，包含服务端返回的内容和错误标记。工具返回 `isError: true` 或传输失败时，
命令以失败状态退出；遇到速率限制时请稍后再试。此示例不读取环境变量中的 API Key 或保存的凭证，
也不发送 Authorization 请求头。

`mvn verify` 使用本地 HTTP 测试服务验证工具发现、搜索和抓取的分发、请求路径和请求头，
不消耗远程服务额度。
