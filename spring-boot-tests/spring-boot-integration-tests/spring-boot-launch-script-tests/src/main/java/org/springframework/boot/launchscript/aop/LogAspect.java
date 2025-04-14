package org.springframework.boot.launchscript.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// 切面定义
@Aspect
@Component
public class LogAspect {

	@Before("execution(* org.springframework.boot.launchscript.controller.*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println("Before method: " + joinPoint.getSignature());
	}
}