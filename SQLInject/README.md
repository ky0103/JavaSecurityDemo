# Java SQL注入

## JDBC SQL注入

JDBC 原生查询如果没有经过预编译，而是直接进行 SQL 语句的拼接，这时过滤不严格则会产生 SQL 注入问题，比如下面代码就是一个带有 SQL 注入漏洞的 Demo

```java
Class.forName("com.mysql.cj.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/security?characterEncoding=utf8&useSSL=false&serverTimezone=UTC", "root", "123456");
String sql = "select * from users where id = '"+id+"'";
resp.getWriter().write("The SQL statement:" + sql + "\n");
Statement statement = conn.createStatement();
ResultSet resultSet = statement.executeQuery(sql);
```

预编译修复

```java
Class.forName("com.mysql.cj.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/security?characterEncoding=utf8&useSSL=false&serverTimezone=UTC", "root", "123456");
String sql = "select * from users where id = ?";
PreparedStatement preparedStatement = conn.prepareStatement(sql);
preparedStatement.setString(1,id);
ResultSet resultSet = preparedStatement.executeQuery();
```

## Mybatis SQL注入

Mybatis 执行SQL语句也有预编译和直接拼接两种方法，如果直接拼接SQL语句且过滤不严格则会产生SQL注入问题，比如下面一个查询语句，使用了 `${}` 直接拼接值

```xml
<select id="selectUserByName" resultType="com.mybatis.pojo.User">
    SELECT * FROM Users WHERE username = '${username}'
</select>
```

使用 `#{}` 进行预编译则可以有效防御 SQL 注入问题

```xml
<select id="selectUserByName" resultType="com.mybatis.pojo.User">
    SELECT * FROM Users WHERE username = '#{username}'
</select>
```

## LIKE 查询

对于 Like 查询，直接预编译则程序会抛出异常

```xml
<select id="selectUserByLikeName" resultType="com.mybatis.pojo.User">
    SELECT * FROM Users WHERE username like '%#{username}#'
</select>
```

如果开发人员为了防止报错而改为直接取值且没有足够过滤则产生SQL注入问题

```xml
<select id="selectUserByLikeName" resultType="com.mybatis.pojo.User">
    SELECT * FROM Users WHERE username like '%${username}%'
</select>
```

直接预编译会报错，那么就使用 concat 连接预编译，既不会抛出异常也防御了SQL注入

```xml
<select id="selectUserByLikeNameRepair" resultType="com.mybatis.pojo.User">
    SELECT * FROM Users WHERE username like concat('%',#{username},'%')
</select>
```

## IN/ORDER BY 查询

同理，`IN/ORDER BY` 查询直接预编译也会使得程序抛出异常，如果开发人员改为直接拼接的方式构造SQL语句且没有足够过滤则会产生SQL注入

