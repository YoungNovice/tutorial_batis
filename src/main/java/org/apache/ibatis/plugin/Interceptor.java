/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.plugin;

import java.util.Properties;

/* sourceRead
 * 插件约定接口
 */

/**
 * @author Clinton Begin
 */
public interface Interceptor {

  /**
   * 它将直接覆盖拦截对象的原有方法
   * @param invocation Invocation 不是InvocationHandler 通过它可以调用原来对象的方法
   * @return Object
   * @throws Throwable Throwable
   */
  Object intercept(Invocation invocation) throws Throwable;

  /**
   * 它的作用是给拦截对象生成一个代理对象 并返回它
   * 在Mybatis 中有Plugin.wrap方法生成代理对象
   * @param target target是被拦截的对象
   * @return 代理对象
   */
  Object plugin(Object target);

  /**
   * 允许在配置插件的时候配置参数
   * 此方法在插件初始化的时候会调用一次， 可以用它获取参数
   * @param properties Prop
   */
  void setProperties(Properties properties);

}
