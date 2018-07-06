package com.cx.dubbox.api.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * <Description> server包的配置类 <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月06日 <br>
 */

@ComponentScan(basePackages = "com.cx.dubbox.api.server")
@Import({com.cx.dubbox.api.common.config.Config.class,
        com.cx.dubbox.api.core.config.Config.class,
        com.cx.dubbox.api.service.config.Config.class})
public class Config {
}
