## SqlSession下的四大对象
* Excutor 执行器 由它调度 StatementHandler ParameterHandler ResultSetHandler 执行对应的SQL
* StatementHandler  使用JDBC的 statement (PreparedStatement 执行操作)
* ParameterHandler 处理SQL参数 
* ResultSetHandler 处理结果集

### 怎么加入插件呢， 首先要知道我们需要代理他们这些对象
### 以StatementHandler为例

### 这四大对象可以嵌入插件
* 在Configuration对象中四大对象构造之前
 + newExecutor方法里面有 
    executor = (Executor) interceptorChain.pluginAll(executor);
    
 + newParameterHandler 
    parameterHandler = (ParameterHandler) interceptorChain.pluginAll(parameterHandler); 
 
 + newResultSetHandler
    resultSetHandler = (ResultSetHandler) interceptorChain.pluginAll(resultSetHandler);
    
 +  newStatementHandler
        statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler) 
        
  由此可见四大对象在调用之前都会调用pluginAll方法      
  
 * interceptorChain是一个管理Interceptor的链
    在pluginAll方法中它会调用所有拦截器的plugin方法, 
    而plugin方法是一个接口方法需要我们在定义拦截器的时候实现
    在plugin方法中我们可以获取到真实需要代理的对象也就是四大对象
    按道理我们需要自己代理它， 由于代理稍微有点复杂所以mybatis的Plugin类的wrap方法
    给了我们一个默认的实现 采用了JDK的动态代理
    > 里面的大致流程是 通过反射获取拦截器上的Intercepts注解 解析里面的Signature参数注解
    > Signature告诉我们需要拦截哪个对象（Excutor，StatementHandler。。。）的哪个方法
    
    Plugin类实现了InvocationHandler 哪么在Invoke方法的业务中肯定有封装Invocaion的逻辑
    然后调用拦截器的interceptor方法将封装的Invocation方法传递给它
    
    
 **所以由问题可以知道 我们只需要在interceptor方法中写真拦截业务就可以了
    在plugin方法中写一句  return Plugin.wrap(target, this);**
    
   ------------------------
### MetaObject 工具类
MetaObject.forObject() 过时 直接用SystemMetaObejct.forObject(); 包装对象
getValue 获取对象属性 支持OGNL
setValue 设置的对象属性 支持OGNL




   
    
