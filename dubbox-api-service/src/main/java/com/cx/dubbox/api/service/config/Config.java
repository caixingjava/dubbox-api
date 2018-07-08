package com.cx.dubbox.api.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * <Description> service包的配置类 <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月06日 <br>
 */
@Import({com.cx.dubbox.api.service.service.defaults.config.Config.class})
@ComponentScan(basePackages = "com.cx.dubbox.api.service.service.impl")
public class Config {
}
