package xuanyang;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by Young on 2018/4/26.
 *
 * @author xuanyang
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class DemoInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 真实对象
        Object target = invocation.getTarget();
        StatementHandler statementHandler = (StatementHandler) target;
        // 绑定
        MetaObject meta = SystemMetaObject.forObject(statementHandler);
        // 分离代理对象链 （由于目标类可能被多个插件拦截， 从而形成多层代理， 通过循环取出来原始的类型）
        // 因为Plugin的wrap 方法最后生成的对象是JDK的代理对象 代理对象都有成员变量 InvocationHandler
        // 他的字段名是h
        while (meta.hasGetter("h")) {
            Object object = meta.getValue("h");
            meta = SystemMetaObject.forObject(object);
        }
        // 获取当前的SQL
        String sql = (String) meta.getValue("delegate.boundSql.sql");
        // TODO: 2018/4/26 处理sql

        // 调用原来对象的方法
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
