package com.cx.dubbox.api.core.config;

import com.cx.dubbox.api.core.filters.GateWayFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.servlet.Filter;

/**
 * <Description> core包的配置类 <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月06日 <br>
 */
@ComponentScan(basePackages = "com.cx.dubbox.api.core")
public class Config {

    @Bean
    public ThreadPoolTaskExecutor getPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setKeepAliveSeconds(30000);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(1000);
        return executor;
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(gateWayFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("paramName", "paramValue");
        registrationBean.setName("gateWayFilter");
        return registrationBean;
    }

    @Bean(name = "gateWayFilter")
    public Filter gateWayFilter(){
        return new GateWayFilter();
    }

}
