package org.springframework.boot.launchscript.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.concurrent.RejectedExecutionException;

@RestController(value = "/threadTest")
public class TreadTestController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Resource(name = "testThreadPoolTaskExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@GetMapping("/submit")
	public String submitTask(@RequestParam int count) throws Exception {
		Field field = ThreadPoolTaskExecutor.class.getDeclaredField("queueCapacity");
		field.setAccessible(true);
		Object fieldValue = field.get(threadPoolTaskExecutor);
		log.info("threadPoolTaskExecutor corePoolSize={},maxPoolSize={},queueCapacity={}",
				threadPoolTaskExecutor.getCorePoolSize(),
				threadPoolTaskExecutor.getMaxPoolSize(),
				fieldValue);
		for (int i = 1; i <= count; i++) {
			final int taskId = i;
			try {
				threadPoolTaskExecutor.execute(() -> processTask(taskId));
			} catch (RejectedExecutionException e) {
				System.err.println("任务" + taskId + "被拒绝: " + e.getMessage());
			}
		}
		return "已提交" + count + "个任务";
	}

	private void processTask(int taskId) {
		System.out.println(Thread.currentThread().getName() + " 开始处理任务 " + taskId);
		try {
			// 模拟任务处理时间
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println(Thread.currentThread().getName() + " 完成处理任务 " + taskId);
	}
}
