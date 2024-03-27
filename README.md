此项目是一个 **OJ 系统**，用户可以在此系统中编写代码，提交代码，查看执行结果是否正确。

并集成了**API开放平台**，制作了**SDK**来简化程序员调用API的程序。

共分为四个部分，分别是前端、微服务后端、开放平台SDK、代码沙箱

## 技术栈

**1）前端**

- Node.js 16.17.1
- Vue3
- TypeScript
- Arco Design 组件库
- vscode 在线代码编辑组件
- bytedance/bytemd 在线文档组件
- OpenApi 自动生成调用后端接口的方法
    - https://github.com/ferdikoomen/openapi-typescript-codegen

**2）后端**

- Java 1.8
- Spring Boot 2.6.13
- Spring Boot Starter（SDK开发）
- Spring Cloud Alibaba 2021.0.5.0
- Spring Cloud Gateway
- MySQL 5.7
- Redis 6.0.10
- RabbitMQ 3.8.8
- Nacos 2.2.0
- Seata 1.3.0
- Sentinel Dashboard 1.7.0