//package org.springframework.boot.launchscript.lifecycle;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanClassLoaderAware;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.beans.factory.BeanNameAware;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.ApplicationEventPublisherAware;
//import org.springframework.context.EmbeddedValueResolverAware;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.MessageSource;
//import org.springframework.context.MessageSourceAware;
//import org.springframework.context.ResourceLoaderAware;
//import org.springframework.context.annotation.Scope;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringValueResolver;
//import org.springframework.web.context.ServletContextAware;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import javax.servlet.ServletContext;
//
////@Component
//public class OfficialLifecycleBean implements
//		BeanNameAware, BeanClassLoaderAware, BeanFactoryAware,
//		EnvironmentAware, EmbeddedValueResolverAware,
//		ResourceLoaderAware, ApplicationEventPublisherAware,
//		MessageSourceAware, ApplicationContextAware, ServletContextAware,
//		InitializingBean, DisposableBean {
//
//	//=============== 初始化阶段 ===============//
//
//	// 1. 实例化
//	public OfficialLifecycleBean() {
//		System.out.println("1. 构造函数执行（实例化）");
//	}
//
//	// 2. 属性赋值（假设有依赖注入）
//	@Autowired
//	public void setDependency(LifecycleDependency lifecycleDependency) {
//		System.out.println("2. @Autowired属性注入");
//	}
//
//	//=============== Aware接口回调 ===============//
//	// 3-12 按官方顺序严格实现
//
//	@Override  //3
//	public void setBeanName(String name) {
//		System.out.println("3. BeanNameAware.setBeanName: " + name);
//	}
//
//	@Override  //4
//	public void setBeanClassLoader(ClassLoader classLoader) {
//		System.out.println("4. BeanClassLoaderAware.setBeanClassLoader");
//	}
//
//	@Override  //5
//	public void setBeanFactory(BeanFactory beanFactory) {
//		System.out.println("5. BeanFactoryAware.setBeanFactory");
//	}
//
//	@Override  //6
//	public void setEnvironment(Environment environment) {
//		System.out.println("6. EnvironmentAware.setEnvironment");
//	}
//
//	@Override  //7
//	public void setEmbeddedValueResolver(StringValueResolver resolver) {
//		System.out.println("7. EmbeddedValueResolverAware.setEmbeddedValueResolver");
//	}
//
//	@Override  //8
//	public void setResourceLoader(ResourceLoader resourceLoader) {
//		System.out.println("8. ResourceLoaderAware.setResourceLoader");
//	}
//
//	@Override  //9
//	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//		System.out.println("9. ApplicationEventPublisherAware.setApplicationEventPublisher");
//	}
//
//	@Override  //10
//	public void setMessageSource(MessageSource messageSource) {
//		System.out.println("10. MessageSourceAware.setMessageSource");
//	}
//
//	@Override  //11
//	public void setApplicationContext(ApplicationContext applicationContext) {
//		System.out.println("11. ApplicationContextAware.setApplicationContext");
//	}
//
//
//	@Override
//	public void setServletContext(ServletContext servletContext) {
//		System.out.println("ServletContextAware.setServletContext");
//	}
//
//	//=============== 初始化扩展点 ===============//
//	@PostConstruct  //13 (BeanPostProcessor.before在12)
//	public void postConstruct() {
//		System.out.println("13. @PostConstruct");
//	}
//
//	@Override  //14
//	public void afterPropertiesSet() {
//		System.out.println("14. InitializingBean.afterPropertiesSet");
//	}
//
//	public void customInit() {
//		System.out.println("15. 自定义init-method");
//	}
//
//	//=============== 销毁阶段 ===============//
//	@PreDestroy  //17 (DestructionAwareBeanPostProcessor在16)
//	public void preDestroy() {
//		System.out.println("17. @PreDestroy");
//	}
//
//	@Override  //18
//	public void destroy() {
//		System.out.println("18. DisposableBean.destroy");
//	}
//
//	public void customDestroy() {
//		System.out.println("19. 自定义destroy-method");
//	}
//}