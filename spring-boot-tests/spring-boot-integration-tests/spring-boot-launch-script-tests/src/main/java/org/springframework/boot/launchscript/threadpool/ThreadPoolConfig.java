package org.springframework.boot.launchscript.threadpool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

// 1. 线程池配置类
@Configuration
public class ThreadPoolConfig {
	@Value("${task.pool.core-size:2}")
	private int corePoolSize;

	@Value("${task.pool.max-size:5}")
	private int maxPoolSize;

	@Value("${task.pool.queue-capacity:10}")
	private int queueCapacity;

	@Bean(value = "testThreadPoolTaskExecutor")
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		executor.setThreadNamePrefix("pool-");
		return executor;
	}
}