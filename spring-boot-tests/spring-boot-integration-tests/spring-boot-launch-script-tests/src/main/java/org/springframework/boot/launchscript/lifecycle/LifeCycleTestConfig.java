//package org.springframework.boot.launchscript.lifecycle;
//
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//// 配置类
//@Configuration
//public class LifeCycleTestConfig {
//
//	@Bean(initMethod = "customInit", destroyMethod = "customDestroy")
//	public OfficialLifecycleBean officialLifecycleBean() {
//		return new OfficialLifecycleBean();
//	}
//
//	// BeanPostProcessor（注意必须声明为static）
//	@Bean
//	public static BeanPostProcessor lifecyclePostProcessor() {
//		return new BeanPostProcessor() {
//			@Override  //12
//			public Object postProcessBeforeInitialization(Object bean, String beanName) {
//				if (bean instanceof OfficialLifecycleBean) {
//					System.out.println("12. BeanPostProcessor.postProcessBeforeInitialization");
//				}
//				return bean;
//			}
//
//			@Override  //16
//			public Object postProcessAfterInitialization(Object bean, String beanName) {
//				if (bean instanceof OfficialLifecycleBean) {
//					System.out.println("16. BeanPostProcessor.postProcessAfterInitialization");
//				}
//				return bean;
//			}
//		};
//	}
//
//	// DestructionAwareBeanPostProcessor（处理销毁前逻辑）
//	@Bean
//	public static DestructionAwareBeanPostProcessor destructionPostProcessor() {
//		return new DestructionAwareBeanPostProcessor() {
//			@Override  //16
//			public void postProcessBeforeDestruction(Object bean, String beanName) {
//				if (bean instanceof OfficialLifecycleBean) {
//					System.out.println("16. DestructionAwareBeanPostProcessor.postProcessBeforeDestruction");
//				}
//			}
//		};
//	}
//}
