//package org.springframework.boot.launchscript.injection;
//
//import jdk.nashorn.internal.objects.annotations.Getter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//public interface InjectionService {
//}
//
//@Component("injectionServiceA")
//class InjectionServiceA implements InjectionService {
//}
//
//@Component("injectionServiceB")
//class InjectionServiceB implements InjectionService {
//}
//
//@Component
//class InjectionTest {
//////
////	@Autowired(required = false)    // 默认按类型匹配，但存在多个实现类会报错
////	private InjectionService injectionService;
//	@Qualifier(value = "injectionServiceB")
//	@Resource
//	private InjectionService injectionService;
//
////	@Resource      // 默认按名称匹配（字段名或变量名）
////	private InjectionService injectionServiceB; // 匹配 @Component("serviceB")
//
////	private final InjectionService injectionService;
////
////	InjectionTest(InjectionService injectionService) {
////		this.injectionService = injectionService;
////	}
//}
