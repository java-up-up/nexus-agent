<p align="center">
  <a href="https://javaup.chat/super-agent/overview/project-intro">
    <img src="https://multimedia-javaup.cn/super-agent/structure/super-nexus-agent-banner.png" alt="Super Nexus Agent 企业级 AI 智能体项目" width="100%" />
  </a>
</p>
<p align="center">
  <strong>企业级多编排 Agent 项目</strong><br />
  <span>从文档入库、知识路由、混合检索到证据生成、工具调用和可观测治理的完整闭环</span>
</p>



<p align="center">
  <a href="./LICENSE"><img alt="License" src="https://img.shields.io/badge/license-Apache--2.0-4a9b8f?style=flat-square" /></a>
  <img alt="Java" src="https://img.shields.io/badge/Java-25%2B-df5b46?style=flat-square&logo=openjdk" />
  <img alt="Maven" src="https://img.shields.io/badge/Maven-Multi--Module-cb3b32?style=flat-square&logo=apachemaven&logoColor=white" />
  <img alt="Vue 3" src="https://img.shields.io/badge/Vue-3-42b883?style=flat-square&logo=vuedotjs&logoColor=white" />
  <img alt="Agentic RAG" src="https://img.shields.io/badge/Agentic%20RAG-Enterprise-2d6a8a?style=flat-square" />
  <img alt="ReAct Agent" src="https://img.shields.io/badge/ReAct%20Agent-Tool%20Calling-e87545?style=flat-square" />
  <img alt="Hybrid Search" src="https://img.shields.io/badge/Hybrid%20Search-RRF%20Fusion-7c6ee6?style=flat-square" />
  <img alt="Knowledge Graph" src="https://img.shields.io/badge/Knowledge%20Graph-Neo4j-4581c3?style=flat-square&logo=neo4j&logoColor=white" />
  <img alt="Eval Harness" src="https://img.shields.io/badge/Eval%20Harness-Quality%20Trace-8a63d2?style=flat-square" />
  <img alt="Observability" src="https://img.shields.io/badge/Observability-Tracing-2f855a?style=flat-square" />
  <img alt="MCP" src="https://img.shields.io/badge/MCP-Tool%20Protocol-4f6bed?style=flat-square" />
  <img alt="Skills" src="https://img.shields.io/badge/Skills-Extensible-b56e7a?style=flat-square" />
</p>

<p align="center">
  <a href="https://javaup.chat/super-agent/overview/project-intro"><strong>官网文档</strong></a>
  ·
  <a href="http://super-agent-javaup.chat"><strong>在线体验</strong></a>
  ·
  <a href="https://javaup.chat/super-agent/getting-started/prerequisites"><strong>快速启动</strong></a>
  ·
  <a href="https://javaup.chat/super-agent/overview/core-architecture"><strong>核心架构</strong></a>
</p>

<p align="center">
  <strong>开源不易，如果这个项目对你有帮助，欢迎给 <a href="https://github.com/java-up-up/super-agent">Nexus Agent</a> 点一个 Star。</strong>
</p>

# Nexus Agent 是什么？

**Nexus Agent** 是一个 **企业级** 的 AI 智能体对话平台，覆盖智能对话、文档知识问答、联网搜索、RAG 检索、MCP 工具协议、Skills 能力扩展、Harness 工程化控制、会话记忆管理、等完整能力。

项目从对话入口开始，到意图分析、检索决策、多路知识召回、证据驱动生成，再到会话记忆管理、MCP 外部工具集成和文档治理，**每一个环节都不是简单调个接口就完事的，而是经过深度设计和反复打磨的工程化实现**。

<img src="https://multimedia-javaup.cn/super-agent/screenshot/%E6%96%B0%E7%9A%84%E5%AF%B9%E8%AF%9D.png" width="100%" />

# 快速导航

| 入口 | 链接 | 说明 |
| :--- | :--- | :--- |
| 官网文档 | [Nexus Agent 项目介绍](https://javaup.chat/super-agent/overview/project-intro) | 完整项目介绍和学习路线 |
| 核心架构 | [查看架构设计](https://javaup.chat/super-agent/overview/core-architecture) | 了解对话、检索、工具、文档治理链路 |
| 快速启动 | [准备项目启动条件](https://javaup.chat/super-agent/getting-started/prerequisites) | 本地启动前后端和中间件 |
| MCP 搜索示例 | [Parallel Search MCP](ai-example/ai-example-mcp/ai-example-parallel-search-mcp/README.md) | 无需 API Key 的独立网页搜索与抓取示例 |

# 项目核心亮点

- **ReAct Agent 智能体的完整实现**：不只是能聊天，而是支持联网搜索、工具调用、多步推理、Checkpoint 持久化，真正能自主决策和行动的 Agent。
- **三层执行器体系**：系统不是把所有问题都交给 Agent，而是先做确定性编排，再按场景选择歧义追问、知识问答或开放式 Agent。
- **Neo4j 文档结构图谱**：每份文档在索引构建时生成 Document -> Section -> Item 层级图结构，支持章节定位、邻接遍历和结构化导航。
- **知识路由三级漏斗**：用户提问后先走 Scope -> Topic -> Document 三级排序漏斗，自动锁定最相关的知识范围，再进入检索链路。
- **影子路由质量观测**：用户手动选择文档时，系统在后台静默运行知识路由，对比系统推荐和用户实际选择，用于持续评估和优化路由质量。
- **RAG 前置编排引擎**：路由判定、问题改写、子问题拆分、知识域收缩、歧义澄清，在模型回答前先把检索计划做好。
- **双通道混合检索**：向量检索和关键词检索并行执行，RRF 融合排序，可选外部 Rerank 精排，召回率和精准度兼顾。
- **证据预算控制与无证据短路**：多子问题证据需要严格裁剪；没有找到相关证据时直接告知用户，不让模型凭空编造。
- **Parent-Child 块聚合**：检索粒度用 Child 小块保证命中率，回答阶段聚合到 Parent 大块保证上下文完整性。
- **三种会话记忆策略**：无记忆、滑动窗口、摘要压缩，完整展示生产环境下如何平衡 Token 成本和上下文完整性。
- **MCP 工具协议集成**：Agent 可以动态发现和调用外部工具，不再局限于硬编码 Function Call。
- **Skills 能力扩展体系**：通过 `SKILL.md` 声明式定义技能，支持目录化管理、自动加载、引用脚本和参考资料。
- **文档从上传到可检索的完整链路**：Tika 多格式解析、组合式切块、向量化、向量数据库 + 倒排索引双引擎构建，每一步都有任务日志。
- **组合式切块引擎**：结构切块做主干、递归分块做兜底、语义分块做边界优化、LLM 智能切块处理疑难文档。
- **联网搜索与工具调用保护**：集成 Tavily 搜索，支持工具重试、指数退避、异常兜底，模型调用和工具调用都有 Hook 限制。
- **推荐追问问题生成**：主回答完成后额外生成最多 3 个可继续追问的问题，引导用户深入探索。
- **SSE 流式输出协议**：正文分片实时推送，结束时补发引用来源和推荐问题，支持主动停止生成。

<img src="https://multimedia-javaup.cn/super-agent/structure/super-agent-capacity.png" width="100%" />

前端层负责会话执行和运营控制台，接口与流式层负责请求、SSE、鉴权和中断控制，核心链路串起会话记忆、Query 改写、知识路由、图谱导航、多通道检索和 Prompt 组装。

底层再由 MySQL、PGVector、Elasticsearch、Neo4j、Redis、Kafka、MinIO、Tika 等基础设施共同提供能力。也就是说，项目亮点不是只在某个单独 Agent 里，而是分布在整条 AI 应用链路上。

# 核心架构

Nexus Agent 不是单点能力堆叠，而是围绕 **对话编排中心、三层执行器、知识底座、检索证据、工具扩展、工程化护栏** 这几块协同工作的完整体系：

<img src="https://multimedia-javaup.cn/super-agent/structure/super-agent-architecture-overview.png" width="100%" />

# 系统整体是怎么跑的

用户在输入框里敲了一句话，点了发送。看起来很简单，但在 Nexus Agent 内部，这条消息要经过一条远比你想象中复杂的链路，才能变成一个靠谱的回答。

先看对话执行链路，后面再逐块拆解每个环节的设计细节：

<img src="https://multimedia-javaup.cn/super-agent/structure/super-agent-conversation-execution-chain.png" width="100%" />

核心思路是：**不是让 Agent 自己决定所有事情，而是先用确定性的编排逻辑做好决策，再把执行交给最合适的引擎。** 知识问答走稳定的证据驱动生成，开放式问题才走 Agent 自由探索。

# 为什么需要这个项目？

大多数人学 AI 的方式是跟着教程调一下 API，往向量库里塞点数据，让模型输出一段话——结束了。这顶多算跑通了一个 Demo，一旦追究细节就露馅了。**真正的 AI 应用和调 API 之间，差的不是代码量，是对每个环节的深入理解和工程化设计。**

而此项目就是为了解决这个问题：它覆盖了 **Agent 智能体、RAG 检索增强生成、MCP 工具协议、Skills 能力扩展、会话记忆管理、Harness 工程化控制、文档生命周期治理** 这些 AI 应用层的核心技术，每一块都有完整的设计和代码实现，复杂度对标真实企业级系统。


### 调个 API 不等于会 RAG

很多教程的套路是：调一下 Embedding 接口，往向量数据库里塞点数据，再用大模型生成答案——完事了。这顶多算跑通了一个 Demo。

真正的 RAG 系统要考虑的问题多得多：文档怎么切分效果最好？检索召回率不够怎么办？多路召回怎么融合排序？幻觉怎么控制？这些才是面试官会追问的点。跑通 Demo 和做出能上线的系统之间，差的不是代码量，是对每个环节的深入理解。

### RAG 并不简单

一个能用的 RAG 系统至少涉及问题改写、子问题拆分、知识域收缩、混合检索、父子块聚合、引用来源、无证据短路和会话记忆。每一环都会影响最终效果。

### 只关注模型，忽略工程能力

RAG 项目的核心竞争力不在于你用了多强的模型，而在于工程化能力。同样的模型，检索策略不同、Prompt 设计不同、分块粒度不同，最终效果可以天差地别。

举个例子：用户问 **"打印机墨盒怎么换"**，文档里写的是 **"墨盒更换步骤"**。关键词搜索直接匹配不上，但向量检索能理解它们是一回事。**这背后是 Embedding 模型的选型、向量数据库的调优、检索结果的重排序——每一步都是工程决策**，不是换个更贵的模型就能解决的。

### 用框架套一下不等于企业级

执行器路由、前置编排器五步决策链、双通道检索融合、Parent-Child 块聚合、证据预算控制与无证据短路、组合式切块引擎、摘要压缩记忆、集群级租约互斥、全链路观测追踪。这些构成项目核心竞争力的能力，这些框架并不具备的能力，在此项目中全都有实现。

<img src="https://multimedia-javaup.cn/super-agent/framework/%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE%28jpg%29.jpg" width="100%" />

# 核心设计拆解

- **用户提了一个问题，系统是怎么决定用哪种方式来回答的？** 不是让模型自己选，而是先经过路由判定、问题改写、歧义检测这一整轮编排，最后才决定走哪个执行器。判断顺序是：歧义澄清 > 知识问答 > 开放式 Agent

- **为什么不是所有问题都走 Agent？** 知识问答追求"稳"和"可解释"，让 Agent 自己探索反而容易不可控。只有真正需要联网搜索、多步推理的开放式问题，才适合走 Agent

- **Agent 死循环了怎么办？** 用 ModelCallLimitHook 限制单次运行最多调用模型 8 次，用 ToolCallLimitHook 限制 Tavily 搜索最多调 6 次。单个会话线程累计也有上限，分别是 40 次和 30 次

- **联网搜索调用失败了怎么处理？** ToolRetryInterceptor 做指数退避重试，最多重试 2 次，初始延迟 200ms，最大延迟 1200ms，带随机抖动。如果最终还是失败，ToolErrorInterceptor 做兜底，不让异常直接抛给用户

- **对话状态怎么持久化？** 用 MysqlSaver 把 ReactAgent 的 Checkpoint 存到 MySQL，应用重启后能继续之前的对话

- **并行工具执行怎么做？** ReactAgent 配置了 parallelToolExecution，最多 4 个工具并行执行

<img src="https://multimedia-javaup.cn/super-agent/flow-chart/%E5%9B%BE%E4%B8%89%EF%BC%9AReAct%20Agent%20%E6%89%A7%E8%A1%8C.svg" width="60%" />

# 三层执行器：不是所有问题都该交给 Agent

知识问答追求"稳"和"可解释"——用户问"退款规则是什么"，你需要的是从文档中精准检索证据，然后让模型基于证据生成回答。这种场景让 Agent 自己探索反而容易不可控，可能跑去联网搜索一堆无关内容。

而"今天北京天气怎么样"这种问题，知识库里根本没有答案，必须让 Agent 调用搜索工具去获取实时信息。

**所以 Nexus Agent 设计了三层执行器，按场景精准分流：**

| 执行器             | 触发条件                           | 处理方式                           |
| :----------------- | :--------------------------------- | :--------------------------------- |
| 歧义追问执行器     | 用户问题信息量不足，无法确定意图   | 生成澄清问题，引导用户补充信息     |
| RAG 知识问答执行器 | 问题可以在知识库中找到答案         | 证据驱动生成，引用来源可追溯       |
| ReAct Agent 执行器 | 需要联网搜索、多步推理的开放式问题 | 自主决策 + 工具调用 + 多轮推理循环 |

判断顺序是：**歧义澄清 > 知识问答 > 开放式 Agent**。优先用最稳定的方式回答，只有确实需要自由探索时才启动 Agent。

在知识问答内部，系统还会继续判断问题是不是结构定位类问题。比如"第三章第二节讲了什么"、"上一节内容是什么"，会优先走 Neo4j 图查询；普通语义问答才进入向量 + 关键词的混合检索链路。这样既能处理结构化导航，也能处理开放表达的知识问答。

# 前置编排器到底做了什么

前面说了系统不会把所有问题直接扔给模型，而是先做一轮完整的编排。它在每次对话中做的事情远比你想象的多：

## 路由判定

用户发进来的消息，首先要判断它属于哪种类型。是一个可以在知识库里找到答案的问题？还是需要联网搜索的开放式问题？还是信息不全需要先追问？这个判定不是靠关键词匹配，而是通过模型分析上下文后给出路由方向。

## 问题改写

用户的问题往往不适合直接检索。比如"那它怎么配置？"——"它"指的是什么？得结合前几轮对话才知道。问题改写就是把这些省略的信息补回来，让检索能找到东西。

#### 问题改写效果：

<img src="https://picture-1306508146.cos.ap-beijing.myqcloud.com/super-agent/screenshot/%E8%BD%AE%E6%AC%A1%E8%AF%A6%E6%83%85-%E9%97%AE%E9%A2%98%E6%94%B9%E5%86%99.png" width="100%" />

## 子问题拆分

"退款规则是什么？审批流程怎么走？"这种复合问题，如果直接拿去检索，两个意图互相干扰，效果很差。系统会拆成独立子问题，每个单独走检索链路，最后合并结果。单轮最多拆 4 个子问题，避免过度切碎。

## 意图解析与知识域收缩

拆完子问题后，还要分析每个子问题属于哪个知识域，把检索范围从"全库"缩小到"相关领域"。这一步直接影响检索的精准度和速度。

## 歧义检测

如果用户的问题信息量不够，比如只说"查一下那个"，系统不会硬着头皮去检索，而是先生成澄清问题让用户补充信息。这比返回一个不相关的答案体验好得多。

所有这些步骤完成后，编排器会产出一个 `执行计划`，里面包含执行多种执行编排模式、改写后的问题、拆分后的子问题列表、知识域范围等，交给对应的执行器去执行。

# 检索链路到底有多细

很多人以为检索过程就是"查个向量库 + 让模型回答"，但实际上中间的环节比想象中多得多。下面列的这些问题，在项目中都有对应的设计：

## 问题进来之后怎么处理？

- 用户问"那它怎么配置？"这种省略了主语的追问，直接拿去检索什么都找不到。所以要先做**问题改写**，结合最近几轮历史把指代补全
- 一个问题里问了两件事，比如"退款规则是什么？审批流程怎么走？"，不能一股脑去检索。要**拆成独立子问题**，每个子问题单独走检索链路
- 单轮最多拆 4 个子问题，避免过度切碎

## 检索是怎么做的？

- 不是只用向量检索。用户问一个订单号、一个配置项名，向量检索很可能找不到。所以用了**双通道并行**：向量检索 + 关键词检索
- 两路结果分数量纲完全不同，不能直接比大小。用 **RRF（Reciprocal Rank Fusion）** 按排名倒数法融合
- 向量通道设了最低相似度，关键词通道用相对阈值，低于阈值的弱命中直接过滤掉
- 融合后可以接**外部 Rerank 精排**（支持 SiliconFlow 兼容协议），在较干净的候选集上继续优化排序
- 检索粒度用 Child 小块保证命中率，回答阶段通过 **Parent-Child 聚合**提升到 Parent 大块，保证上下文完整性

## 证据够不够怎么判断？

- 如果最终没有任何有效证据，**直接短路返回**，告诉用户"当前文档中没有检索到足够证据"。不让模型凭空编造，这是防止幻觉最直接的手段
- 如果证据太多，有预算控制：单个子问题字符、全部证据总预算，单个父块最大字符。防止把模型上下文窗口吃满

## 最终怎么组装回答？

- 按子问题边界分别组织证据，注入到 Prompt 里，模型按编号逐一回答
- 要求模型在引用证据时标注来源编号 `[1][2]`
- 答案通过 SSE 实时流式推送，结束时补发引用来源和推荐追问问题

<img src="https://multimedia-javaup.cn/super-agent/flow-chart/%E5%9B%BE%E4%BA%8C%EF%BC%9ARAG%20%E6%A3%80%E7%B4%A2%E9%93%BE%E8%B7%AF.svg" width="50%" />

## 双通道并行检索

用户问一个订单号、一个配置项名，向量检索很可能找不到——语义相似度对精确匹配天然弱势。所以 Super Nexus Agent 用了双通道并行：**向量检索 + 关键词检索**，两路同时出发，互不阻塞。

两路结果的分数量纲完全不同，不能直接比大小。系统用 **RRF（Reciprocal Rank Fusion）** 按排名倒数法融合，向量通道设了最低相似度阈值，关键词通道用相对阈值，低于阈值的弱命中直接过滤。融合后还可以接**外部 Rerank 精排**，在较干净的候选集上继续优化排序。

## 父子块聚合

这是整个检索链路中最精巧的设计之一。检索粒度用 Child 小块保证命中率——小块语义集中，更容易被向量检索命中。但回答阶段如果只用小块，上下文往往不完整。所以系统在命中 Child 块后，自动聚合提升到 Parent 大块，保证回答时有足够的上下文信息。

**检索用小块保精度，回答用大块保完整性。** 这个设计在业界也属于比较前沿的实践。

# 文档从上传到可检索经历了什么

很多项目的文档处理就是"切成固定长度 → 向量化 → 完事"。但实际上不同文档差异很大，一刀切的效果很难达到想要的效果。Super Nexus Agent 的文档处理不是一条孤立的入库流水线，而是 **文档入库 → 组合切块 → 双引擎索引 → Neo4j 图谱 → 三级知识路由 → 混合检索 → 证据生成 → 影子路由观测** 的知识闭环：

<img src="https://multimedia-javaup.cn/super-agent/structure/super-agent-knowledge-closed-loop.png" width="100%" />

这条闭环里，前半段负责把原始文档加工成可检索、可导航、可路由的知识资产；后半段负责在用户提问时先缩小知识范围，再做混合检索和证据生成；最后通过影子路由把"系统推荐"和"用户实际选择"沉淀成质量观测数据，反过来持续优化知识路由。

## 第一步：上传和存储

文件上传到 MinIO 对象存储后，通过 Kafka 异步触发解析任务。Apache Tika 负责处理 PDF、Word、PPT 等多种格式，把五花八门的文档统一转成干净的文本。PDF 里的表格、扫描件、双栏排版——每一个都是坑，Tika 帮你踩过了。

并且可以配置 **知识域编码**、**知识域名称**、**业务分类**、**文档标签** 等元信息，后续检索和分析都能用得上。

<img src="https://multimedia-javaup.cn/super-agent/screenshot/%E6%96%87%E6%A1%A3%E5%88%97%E8%A1%A8.png" width="100%" />

## 第二步：异步解析和策略推荐

解析完成后，系统不是让用户盲选切块算法，而是根据文档类型和内容特征**自动推荐最优的切块策略组合**。用户可以查看推荐结果，也可以手动调整——比如文档质量不太好，可以额外开启 LLM 智能切块。

<img src="https://multimedia-javaup.cn/super-agent/screenshot/%E5%8F%8C%E6%B5%81%E6%B0%B4%E7%BA%BF%E8%B0%83%E6%95%B4.png" width="100%" />

## 第三步：组合式切块引擎

四种切块策略不是四选一，而是各司其职的组合流水线：

| 策略             | 角色 | 什么时候用                           |
| :--------------- | :--- | :----------------------------------- |
| 基于文档结构切块 | 主干 | 按标题/章节/段落切成语义完整的块     |
| 递归分块         | 兜底 | 结构块太大时继续往下裁剪，控制块大小 |
| 语义分块         | 优化 | 在结构切块基础上做边界精修           |
| LLM 智能切块     | 增强 | 处理低质量文档和复杂文档，默认关闭   |

一句话：**结构负责保留文档天然边界，递归负责控制块大小，语义负责优化块边界，大模型负责处理疑难场景。**

<img src="https://multimedia-javaup.cn/super-agent/screenshot/%E9%AA%8C%E8%AF%81%E5%88%86%E5%9D%97%E7%BB%93%E6%9E%9C.png" width="100%" />

## 第四步：向量化与双引擎索引

确认策略后，系统通过 Kafka 异步执行向量化和索引构建。向量写入向量数据库，关键词写入倒排索引数据库，构建双通道检索的基础。每一步都有独立的任务日志，出了问题能精确定位到哪一步失败。

# 会话记忆：Token 成本和上下文完整性的博弈

20 轮对话全塞给模型？Token 成本扛不住。只带最近几轮？可能丢掉关键上下文。这是生产环境下绕不开的问题。

### 项目中设计了三种策略：

- **无记忆**：每轮独立，不携带历史。适合一次性查询
- **滑动窗口**：保留最近 N 轮完整对话。适合短期连续追问
- **摘要压缩**：长期摘要 + 最近原文窗口。这是生产环境最推荐的方案

<img src="https://multimedia-javaup.cn/super-agent/screenshot/%E5%AF%B9%E8%AF%9D%E8%AE%B0%E5%BD%95.png" width="100%" />

### 摘要压缩的具体设计：

- 最近 4 轮原文始终保留，不做压缩
- 更早的历史增量摘要，单次最多推进 6 轮，避免一次处理超长历史
- 最近原文窗口最大 2200 字符，长期摘要最大 1400 字符
- 所有记忆数据持久化到 MySQL，应用重启不丢失

# Skills 能力扩展

Agent 的价值不只是能聊天，更在于能**调用外部工具完成实际任务**。

Skills 是比 MCP 更上层的能力抽象，解决的是"Agent 怎么获得特定领域的专业能力"这个问题：

- **声明式定义**：每个 Skill 通过 `SKILL.md` 配置文件描述能力边界、触发条件、执行逻辑，结构清晰易维护
- **目录化管理**：Skills 按领域组织成目录结构，支持嵌套分类，方便大规模能力管理
- **自动加载机制**：系统启动时自动扫描 Skills 目录，新增 Skill 只需要放入对应目录，零配置生效
- **引用脚本与参考资料**：Skill 可以关联外部脚本（Python、Shell 等）和参考文档，执行时自动加载上下文
- **能力组合**：多个 Skills 可以组合使用，Agent 根据任务需求自动选择最合适的 Skill 组合

Skills 体系让 Agent 的能力边界不再是固定的，而是可以**持续扩展**的。今天加一个"数据分析"Skill，明天加一个"代码审查"Skill，Agent 的能力就跟着增长，核心代码一行不用改。

# 工程质量不是靠嘴说的

说一个项目是企业级，得看实际的工程质量。从几个维度来评估 Nexus Agent：

## 集群安全与并发控制

这是很多开源项目完全忽略的部分，但在生产环境中至关重要：

- **Redis 租约互斥**：`RedisLeaseManager` 实现集群级别的会话锁定，防止同一条消息被多个实例重复处理
- **JVM 级任务注册**：`ChatRuntimeRegistry` 维护进程内的任务注册表，防止同进程重入
- **租约续期**：执行过程中自动续期，防止长对话超时导致锁释放后被其他实例抢占
- **优雅降级**：无论成功还是失败，统一触发清理流程，不会留下孤儿锁

## 全链路可观测

全链路追踪，每个环节的耗时、输入输出、决策结果都有记录。思考过程、检索通道使用情况、证据来源、工具调用记录——全部可视化呈现在管理后台的观测面板中。出了问题不用猜，直接看 Trace 就知道哪一步出了问题。

#### 执行阶段时间线：

<img src="https://multimedia-javaup.cn/super-agent/screenshot/%E8%BD%AE%E6%AC%A1%E8%AF%A6%E6%83%85-%E6%89%A7%E8%A1%8C%E9%98%B6%E6%AE%B5%E6%97%B6%E9%97%B4%E7%BA%BF.png" width="100%" />

# 和其他普通的 Agent 项目有什么区别

市面上大多数 Agent 项目，大致就是跑通一个示例就完事了。Nexus Agent 和这些项目的差距在哪？直接对比一下：

| 对比维度     | 普通 Agent 项目          | Super Nexus Agent                                          |
| :----------- | :----------------------- | :--------------------------------------------------------- |
| 检索方式     | 单路向量检索             | 双通道并行（PGVector + ES）+ RRF 融合 + 可选 Rerank        |
| 问题处理     | 原始问题直接检索         | 改写 + 子问题拆分 + 知识域收缩                             |
| 意图判断     | 无                       | 前置编排器五步决策 + 歧义主动追问                          |
| 执行策略     | 所有问题走同一个模型     | 三层执行器按场景分流（追问 / 知识问答 / Agent）            |
| 会话记忆     | 全量塞给模型或不带       | 无记忆 / 滑动窗口 / 摘要压缩三种策略                       |
| 文档切块     | 固定长度一刀切           | 四种策略组合流水线 + 系统自动推荐                          |
| 文档入库     | 同步处理，无日志         | Kafka 异步流水线 + 分步骤任务日志                          |
| Agent 能力   | 无或仅简单对话           | ReAct 循环 + 联网搜索 + 工具调用 + Checkpoint 持久化       |
| 证据控制     | 无                       | 预算裁剪 + 无证据短路防幻觉                                |
| 检索粒度     | 命中什么用什么           | Parent-Child 块聚合，检索用小块、回答用大块                |
| 流式输出     | 简单 SSE 推文本          | 正文 + 引用来源 + 推荐追问 + 停止生成                      |
| Agent 安全   | 无限制                   | 模型调用次数 Hook + 工具调用次数 Hook + 重试兜底           |
| MCP 协议     | 无或硬编码 Function Call | MCP 标准协议 + 动态发现 + 多工具编排 + 安全沙箱            |
| 能力扩展     | 固定能力，改代码才能加   | Skills 声明式定义 + 自动加载 + 热插拔扩展                  |
| 集群安全     | 单机运行                 | Redis 租约互斥 + JVM 任务注册 + 租约续期                   |
| 可观测性     | 无                       | 全链路 Trace + 可视化观测面板                              |
| 知识路由     | 无，全库检索             | 三级漏斗（Scope → Topic → Document）+ 混合打分自动锁定文档 |
| 文档结构     | 无                       | Neo4j 图数据库构建 Document → Section → Item 层级图谱      |
| 路由质量观测 | 无                       | 影子路由静默对比 + 命中率追踪 + 持续优化闭环               |


## 适合什么人？

- **在校生 / 校招同学**：已经有商城、外卖、博客等常规项目，想用一个 AI 项目拉开简历区分度。
- **1-3 年后端开发者**：日常写业务系统，想转 AI 应用方向，但不想从 Python 技术栈重新开始。
- **3-5 年社招同学**：工程能力不差，但面试被 RAG、Agent、MCP 问住，需要一个能讲深的完整项目。
- **想进入 AI 应用团队的同学**：希望系统理解企业级 Agent 能力，从知识治理到回答生成的完整链路。
