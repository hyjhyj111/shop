#!/bin/bash

# 设置类路径（包含编译输出目录和 JDBC 驱动）
CLASS_PATH="/Users/heyujie/shop/out/production/shop:"/Users/heyujie/IdeaProjects/mysql-connector-j-9.1.0/mysql-connector-j-9.1.0.jar

# 主类（根据 package 声明调整）
MAIN_CLASS="Main"

# 运行 Java 程序
java -cp "$CLASS_PATH" "$MAIN_CLASS"
