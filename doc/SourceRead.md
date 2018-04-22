## 了解SqlSessionFactory的构建过程
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
