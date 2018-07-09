package com.cx.dubbox.api.server.controller;

import com.cx.dubbox.api.core.handlers.DubboxApiAcceptHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <Description> 所有http链接的入口 <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月06日 <br>
 */
@Slf4j
@Controller
public class GatewayController {


    @Autowired
    private DubboxApiAcceptHandler acceptHandler;

    /**
     * Description: 所有http请求的入口 <br>
     * @author caixing<br>
     * @taskId <br>
     *
     */
    @RequestMapping(value = {"/**",""},method = {RequestMethod.POST,RequestMethod.GET})
    public void service(HttpServletRequest request, HttpServletResponse response){
        log.info("recive a request ... ...");
        this.acceptHandler.acceptRequest(request,response);
    }
}
