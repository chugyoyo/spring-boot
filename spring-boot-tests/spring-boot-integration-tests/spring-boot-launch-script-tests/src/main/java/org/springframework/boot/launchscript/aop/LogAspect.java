package org.springframework.boot.launchscript.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// 切面定义
@Aspect
@Component
public class LogAspect {

	@After("execution(* org.springframework.boot.launchscript.controller.*.*(..))")
	public void logAfter(JoinPoint joinPoint) {
		System.out.println("After method: " + joinPoint.getSignature());
	}

	@Before("execution(* org.springframework.boot.launchscript.controller.Laun*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println("Before method: " + joinPoint.getSignature());
	}

//	// 切点表达式（仅拦截接口方法）
//	@Pointcut("execution(* org.springframework.boot.launchscript.service.UserService.createUser(..))")
//	public void userServiceMethods() {}
//
//	@Before("userServiceMethods()")
//	public void logBeforeUserServiceMethods(JoinPoint joinPoint) {
//		System.out.println("[JDK代理] 方法调用前: " + joinPoint.getSignature().getName());
//	}
}