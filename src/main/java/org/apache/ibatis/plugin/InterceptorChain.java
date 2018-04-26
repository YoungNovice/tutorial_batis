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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* sourceRead
 * 调用插件执行的责任链 get 和 add 两个方法就不用管了撒
 * 现在就剩下一个核心方法pluginAll
 */
/**
 * @author Clinton Begin
 */
public class InterceptorChain {

  private final List<Interceptor> interceptors = new ArrayList<Interceptor>();

  /**
   * 调用pluginAll 就是调用某个拦截器的plugin方法
   * 每个拦截器的plugin方法是生成真实对象的代理方法
   * 为什么要代理 因为要拦截真实对象的业务逻辑
   * 在代理业务中我们需要做一件事就是调用拦截器的interceptor方法
   * 然后调用这样就可以将拦截器的业务逻辑添加到执行的对象中去
   * @param target
   * @return
   */
  public Object pluginAll(Object target) {
    /* sourceRead
     * 这个方法的执行逻辑也很简单就是取出每一个插件执行它的plugin方法
     */
    for (Interceptor interceptor : interceptors) {
      target = interceptor.plugin(target);
    }
    return target;
  }

  public void addInterceptor(Interceptor interceptor) {
    interceptors.add(interceptor);
  }
  
  public List<Interceptor> getInterceptors() {
    return Collections.unmodifiableList(interceptors);
  }

}
