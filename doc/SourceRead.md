## SqlSessionFactory的构建过程
* 读取xml 解析xml 生成Configuration
> 1. XMLConfigBuilder 这个类负责解析配置文件 生成Configuration对象 
> 2. SqlSessionFactoryBuilder 生成DefaultSqlSessionFactory

## Mapper 的执行流程
> 1. SqlSession有一个实现类DefaultSqlSession
> 2. sqlSession.getMapper 的实际上调用的是configuration的getMapper
> 3. configuration的getMapper 实际上是调用mapperRegister的getMapper
> 4. mappgerRegister实际上是调用 mapperProxyFactory的newInstance
> 5. mapperProxyFactory的newInstance 通过JDK的动态代理生成了一个对象
> 6. 这个调用的执行逻辑封装在MapperProxy的Invoke方法里面
> 7. 可以看到执行逻辑通过简单的判断之后调用了mapperMethod.execute
> 8. 在execute里面会判断增删改查哪种逻辑 然后调用SqlSession执行方法
> 9. 所以getapper方法并没有新增什么新的功能 而只是同意了原来的接口方法

## SqlSession下的四大对象
* Excutor 执行器 由它调度 StatementHandler ParameterHandler ResultSetHandler 执行对应的SQL
* StatementHandler  使用JDBC的 statement (PreparedStatement 执行操作)
* ParameterHandler 处理SQL参数 
* ResultSetHandler 处理结果集

### Excutor 有三种
* simple
* reuse
* batch
> 在configuration 的newExcutor 可以看到初始化过程
>  创建Excutor的时候会注册插件 interceptorChain.pluginAll(executor);
 
 ### 分析SimplExcutor
 * 查询doQuery
> 1. 首先重Configuration中newStatementHandler
  * 实际上是new了一个RoutingStatementHandler 并不是真正的StatementHandler
  * 它有一个StatementHandler 字段用来保存真实的statementHandler
  * statementHandler 会处理插件
> 2. 调用prepareStatement 生成JDBC statment （会处理参数）
> 3. 执行handler的query 返回结果

### 一条查询SQL的执行流程
```$xslt
Executor 调用StatementHandler 的prepare 编译sql
同时设置参数 然后调用parameterized 启用Parameterhandler设置参数
完成预编译 执行查询 调用ResultSetHandler 封装结果给调用者

```


