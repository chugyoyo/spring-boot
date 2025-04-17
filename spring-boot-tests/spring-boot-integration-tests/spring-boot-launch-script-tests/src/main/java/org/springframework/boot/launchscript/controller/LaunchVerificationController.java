package org.springframework.boot.launchscript.controller;

import org.springframework.aop.support.AopUtils;
import org.springframework.boot.launchscript.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class LaunchVerificationController {

	@Resource
	private UserService userService;

	/**
	 * http://localhost:8080/verifyLaunch
	 *
	 * @return
	 */
	@RequestMapping("/verifyLaunch")
	public String verifyLaunch() {
		return "Launched\n";
	}

	@RequestMapping("/testJdkProxy")
	public String testJdkProxy() {
		userService.createUser("testJdkProxy");
		System.out.println("是否为JDK代理: " + AopUtils.isJdkDynamicProxy(userService));
		System.out.println("是否为CGLIB代理: " + AopUtils.isCglibProxy(userService));
		return "testJdkProxy done";
	}
}
