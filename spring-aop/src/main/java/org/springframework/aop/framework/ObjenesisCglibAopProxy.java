/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.framework;

import java.lang.reflect.Constructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.Factory;
import org.springframework.objenesis.SpringObjenesis;
import org.springframework.util.ReflectionUtils;

/**
 * Objenesis-based extension of {@link CglibAopProxy} to create proxy instances
 * without invoking the constructor of the class. Used by default as of Spring 4.
 *
 * @author Oliver Gierke
 * @author Juergen Hoeller
 * @since 4.0
 */
@SuppressWarnings("serial")
class ObjenesisCglibAopProxy extends CglibAopProxy {

	private static final Log logger = LogFactory.getLog(ObjenesisCglibAopProxy.class);

	private static final SpringObjenesis objenesis = new SpringObjenesis();


	/**
	 * Create a new ObjenesisCglibAopProxy for the given AOP configuration.
	 * @param config the AOP configuration as AdvisedSupport object
	 */
	public ObjenesisCglibAopProxy(AdvisedSupport config) {
		super(config);
	}


	@Override
	protected Object createProxyClassAndInstance(Enhancer enhancer, Callback[] callbacks) {
		Class<?> proxyClass = enhancer.createClass(); // 1. 生成代理类字节码并加载到JVM
		Object proxyInstance = null;
		// 2. 优先尝试通过Objenesis库创建实例（绕过构造器）
		if (objenesis.isWorthTrying()) { // 检查是否值得尝试（根据黑名单/白名单配置）
			try { // 2.1 Objenesis的newInstance方法不需要调用构造器
				proxyInstance = objenesis.newInstance(proxyClass, enhancer.getUseCache());
			}
			catch (Throwable ex) { // 2.2 Objenesis创建失败时记录日志（非致命错误，可回退）
				logger.debug("Unable to instantiate proxy using Objenesis, " +
						"falling back to regular proxy construction", ex); // 无法通过Objenesis实例化代理，将回退到常规构造方法创建
			}
		}
		// 3. Objenesis创建失败时的备选方案：反射调用默认构造器
		if (proxyInstance == null) {
			// Regular instantiation via default constructor...
			try { // 3.1 根据是否预定义构造器参数选择构造器
				Constructor<?> ctor = (this.constructorArgs != null ?
						proxyClass.getDeclaredConstructor(this.constructorArgTypes) :
						proxyClass.getDeclaredConstructor());
				ReflectionUtils.makeAccessible(ctor); // 3.2 确保私有构造器可访问
				proxyInstance = (this.constructorArgs != null ?
						ctor.newInstance(this.constructorArgs) : ctor.newInstance()); // 3.3 通过反射创建实例
			}
			catch (Throwable ex) { // 3.4 反射创建失败时抛出致命异常
				throw new AopConfigException("Unable to instantiate proxy using Objenesis, " +
						"and regular proxy instantiation via default constructor fails as well", ex); // Objenesis和常规构造器实例化均失败
			}
		}
		// 4. 将回调链设置到代理实例（CGLIB的Factory接口方法）
		((Factory) proxyInstance).setCallbacks(callbacks);
		return proxyInstance;
	}

}
