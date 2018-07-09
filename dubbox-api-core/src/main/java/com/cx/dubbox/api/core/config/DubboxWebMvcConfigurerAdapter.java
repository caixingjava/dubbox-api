package com.cx.dubbox.api.core.config;

import com.cx.dubbox.api.core.interceptors.DubboxApiServiceParamValidateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
@Configuration
public class DubboxWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new DubboxApiServiceParamValidateInterceptor()).addPathPatterns("/**");
//                .excludePathPatterns("/hlladmin/login") //登录页
//                .excludePathPatterns("/hlladmin/user/sendEmail") //发送邮箱
//                .excludePathPatterns("/hlladmin/user/register") //用户注册
//                .excludePathPatterns("/hlladmin/user/login"); //用户登录
        super.addInterceptors(registry);

    }

}
