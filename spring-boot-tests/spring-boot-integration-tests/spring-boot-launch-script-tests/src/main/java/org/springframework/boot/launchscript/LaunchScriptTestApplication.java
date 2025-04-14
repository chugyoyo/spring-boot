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

package org.springframework.boot.launchscript;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//@EnableAutoConfiguration // 引入 spring 的构建后，不加这个注解找不到 ServletWebServerFactory，自动配置因为某种原因失效 TODO 有待排查
@SpringBootApplication(scanBasePackageClasses = ScanBasePackagesUtil.class)
public class LaunchScriptTestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(LaunchScriptTestApplication.class, args);
//		context.close(); // 主动触发关闭 （测试生命周期）
	}

}
