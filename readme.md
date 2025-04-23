# 项目目录概述

## 根目录
- **mvnw**: Unix 系统的 Maven 包装器脚本。
- **mvnw.cmd**: Windows 系统的 Maven 包装器脚本。
- **pom.xml**: Maven 的项目对象模型文件，包含项目配置和依赖项。
- **src/**: 包含主代码和测试代码的源目录。
- **target/**: 构建输出目录。

## src 目录
- **main/**: 包含主源代码和资源。
- **test/**: 包含测试源代码。

## src/main 目录
- **java/**: 包含 Java 源代码。
- **resources/**: 包含资源文件，如 `application.properties`。

## src/main/java 目录
- **com/example/demo/**: 应用程序的基本包。
  - **DemoApplication.java**: 主应用程序类。
  - **cmd/**: 初始化数据库内容相关的类。
  - **config/**: 鉴权流程配置相关的类。
  - **controller/**: REST 控制器，处理 HTTP 请求，返回 JSON 数据。
  - **dto/**: 数据传输对象，用于某些控制器的请求和响应类型。
  - **entity/**: 表示数据库表的实体类。
  - **repository/**: 数据访问的存储库接口。
  - **service/**: 服务层类，包含业务逻辑。

## src/main/resources 目录
  - **application.properties**: 配置属性文件。


# 运行
## 前置条件
- 安装了 Java 17
- JAVA_HOME 环境变量指向 Java 17 的安装目录
- 安装 MySQL 8.0，创建一个空的 `demo` 数据库
- 把 `src/main/resources/application.properties` 中的 `spring.datasource.username` 和 `spring.datasource.password` 替换为实际的 MySQL 用户名和密码

## 启动
- Linux/Mac
```bash
mvnw spring-boot:run
```

- Windows
```cmd
mvnw.cmd spring-boot:run
```

