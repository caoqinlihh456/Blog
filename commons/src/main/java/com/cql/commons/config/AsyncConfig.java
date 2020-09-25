package com.cql.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author ：lzp
 * @date ：Created in 2020/9/23 13:22
 * @description：全局异步配置类
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${spring.AsyncConfig.corePoolSize:2}")
    private int corePoolSize;
    @Value("${spring.AsyncConfig.maxPoolSize:5}")
    private int maxPoolSize;
    @Value("${spring.AsyncConfig.queueCapacity:100}")
    private int queueCapacity;

    public static final String ASYNC_EXECUTOR_PUBLIC = "asyncExecutor";

    @Bean(name = ASYNC_EXECUTOR_PUBLIC)
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        //复制上下文信息
        executor.setTaskDecorator(new ContextCopyingDecorator());
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        //调度器shutdown被调用时,等待当前被调度的任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //等待时长
        executor.setAwaitTerminationSeconds(60);
        //线程名称前缀
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }
}