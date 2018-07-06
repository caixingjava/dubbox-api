package com.cx.dubbox.api.server;

import com.cx.dubbox.api.server.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * <Description> 启动类 <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月06日 <br>
 */
@SpringBootApplication
@Import(Config.class)
public class App {

    /**
     * Description: 启动入口 <br>
     * @param args 参数
     * @author caixing<br>
     * @taskId <br>
     *
     */
    public static void main(String[] args){
        SpringApplication.run(App.class,args);
    }

}
