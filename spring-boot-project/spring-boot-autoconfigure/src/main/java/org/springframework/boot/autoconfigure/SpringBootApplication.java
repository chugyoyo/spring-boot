/*
 * Copyright 2012-2019 the original author or authors.
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

package org.springframework.boot.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.repository.Repository;

/**
 * 标识一个配置类（{@link Configuration}），该类声明一个或多个 {@link Bean @Bean} 方法，
 * 同时触发以下行为：
 * 1. {@link EnableAutoConfiguration 自动配置}
 * 2. {@link ComponentScan 组件扫描}
 *
 * <p>这是一个组合注解，等价于同时声明以下三个注解：
 * {@code @Configuration}、{@code @EnableAutoConfiguration} 和 {@code @ComponentScan}。
 *
 * @author Phillip Webb
 * @author Stephane Nicoll
 * @author Andy Wilkinson
 * @since 1.2.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),  // 排除特定类型过滤器
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) // 排除自动配置类过滤器
})
public @interface SpringBootApplication {

	/**
	 * 排除特定的自动配置类（这些类将永远不会被应用）
	 * @return 要排除的类数组
	 */
	@AliasFor(annotation = EnableAutoConfiguration.class)
	Class<?>[] exclude() default {};

	/**
	 * 排除特定的自动配置类名（这些类将永远不会被应用）
	 * @return 要排除的类全限定名数组
	 * @since 1.3.0
	 */
	@AliasFor(annotation = EnableAutoConfiguration.class)
	String[] excludeName() default {};

	/**
	 * 指定组件扫描的基础包路径（基于字符串）。
	 *
	 * <p>注意：此设置仅作用于 {@link ComponentScan}，对以下扫描无影响：
	 * - JPA {@code @Entity} 实体扫描
	 * - Spring Data {@link Repository} 仓库扫描
	 * 如需配置这些扫描，需分别使用：
	 * {@link org.springframework.boot.autoconfigure.domain.EntityScan @EntityScan} 和
	 * {@code @Enable...Repositories} 注解。
	 *
	 * @return 要扫描的基础包路径数组
	 * @since 1.3.0
	 */
	@AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
	String[] scanBasePackages() default {};

	/**
	 * 指定组件扫描的基础包路径（基于类，类型安全）。
	 *
	 * <p>建议在每个包中创建一个无功能的标记类或接口，专门用于此处引用。
	 *
	 * <p>同 {@link #scanBasePackages}，此设置仅作用于 {@link ComponentScan}，
	 * 不影响 JPA 实体或 Spring Data 仓库的扫描。
	 *
	 * @return 要扫描的基础包中的标记类数组
	 * @since 1.3.0
	 */
	@AliasFor(annotation = ComponentScan.class, attribute = "basePackageClasses")
	Class<?>[] scanBasePackageClasses() default {};

	/**
	 * 指定是否代理 {@link Bean @Bean} 方法以强制执行 Bean 生命周期行为。
	 *
	 * <p>默认值为 {@code true}，启用以下特性：
	 * - 在配置类内部支持 Bean 间的引用
	 * - 外部调用此配置类的 {@code @Bean} 方法时返回共享的单例 Bean 实例
	 *
	 * <p>若不需要上述特性（例如每个 {@code @Bean} 方法都是独立的自包含工厂方法），
	 * 可设为 {@code false} 以禁用 CGLIB 子类生成，此时行为等效于非 {@code @Configuration} 类
	 * 的 "@Bean Lite 模式"。
	 *
	 * <p>禁用后，配置类及其方法允许声明 {@code final}。
	 *
	 * @since 2.2
	 * @return 是否代理 {@code @Bean} 方法
	 */
	@AliasFor(annotation = Configuration.class)
	boolean proxyBeanMethods() default true;
}