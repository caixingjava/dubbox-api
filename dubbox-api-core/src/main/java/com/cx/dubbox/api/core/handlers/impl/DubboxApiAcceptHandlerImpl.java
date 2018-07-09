package com.cx.dubbox.api.core.handlers.impl;

import com.alibaba.fastjson.JSON;
import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import com.cx.dubbox.api.common.utils.CommonUtil;
import com.cx.dubbox.api.core.handlers.DubboxApiAcceptHandler;
import com.cx.dubbox.api.core.handlers.DubboxApiHandlerExecuteTemplate;
import com.cx.dubbox.api.core.handlers.ThreadPoolHandler;
import com.cx.dubbox.api.core.protocol.DubboxApiHttpReqTask;
import com.cx.dubbox.api.core.protocol.DubboxApiHttpSessionBean;
import com.cx.dubbox.api.core.utils.DubboxApiResponseUtils;
import com.cx.dubbox.api.service.service.IidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
@Slf4j
public class DubboxApiAcceptHandlerImpl implements DubboxApiAcceptHandler, ApplicationContextAware {

    private IidService idService;

    private ThreadPoolHandler poolHandler;

    private ApplicationContext applicationContext;


    @Override
    public void acceptRequest(HttpServletRequest request, HttpServletResponse response) {
        DubboxApiHttpRequest reqBean = (DubboxApiHttpRequest) request
                .getAttribute(CommonUtil.REQ_BEAN_KEY);
        String traceId = idService.getInnerRequestId();
        reqBean.setTraceId(traceId);
        request.setAttribute(CommonUtil.REQ_BEAN_KEY, reqBean); // 重新设置bean
        log.info(String.format("requestId=%s request begin,reqeust=%s", traceId, JSON.toJSONString(reqBean)));
        // 将当前请求放入线程池处理，若超过线程池最大处理数则抛出reach queue max deepth 异常
        addTask2Pool(response, new DubboxApiHttpSessionBean(reqBean));
    }

    private void addTask2Pool(HttpServletResponse response, DubboxApiHttpSessionBean sessionBean) {
        long currentTime = System.currentTimeMillis();
        log.debug(String.format("begin deal_sessionbean,current_time=%d,sessionbean=%s ", currentTime,
                sessionBean));
        log.info("added one task to thread pool");
        DubboxApiHttpReqTask task = null;
        String operationType = sessionBean.getRequest().getOperationType();

        DubboxApiHandlerExecuteTemplate handlerExecuteTemplate = applicationContext.getBean(operationType,
                DubboxApiHandlerExecuteTemplate.class);
        task = new DubboxApiHttpReqTask(sessionBean, handlerExecuteTemplate);
        /**
         * 走责任链，将相关的请求处理
         */
        DubboxApiHttpSessionBean tmp = (DubboxApiHttpSessionBean) poolHandler.addTask(task);
        // 写入响应
        DubboxApiResponseUtils.writeRsp(response, tmp.getRequest());
        log.debug(String.format(
                "end deal_sessionbean,current_time=%d,elapase_time=%d milseconds,sessionbean=%s",
                System.currentTimeMillis(), (System.currentTimeMillis() - currentTime), tmp));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
