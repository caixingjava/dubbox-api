package com.cx.dubbox.api.core.handlers.impl;

import com.alibaba.fastjson.JSON;
import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import com.cx.dubbox.api.common.utils.CommonUtil;
import com.cx.dubbox.api.core.handlers.DubboxApiAcceptHandler;
import com.cx.dubbox.api.core.handlers.DubboxApiHandlerExecuteTemplate;
import com.cx.dubbox.api.core.protocol.DubboxApiContext;
import com.cx.dubbox.api.core.protocol.DubboxApiHttpSessionBean;
import com.cx.dubbox.api.core.utils.DubboxApiResponseUtils;
import com.cx.dubbox.api.service.service.IidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.AsyncContext;
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
@Component
public class AsynDubboxApiAcceptHandlerImpl implements DubboxApiAcceptHandler, ApplicationContextAware {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private IidService idService;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void acceptRequest(HttpServletRequest request, HttpServletResponse response) {
        final AsyncContext context = request.startAsync(request, response);
        taskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpServletRequest asynRequest = (HttpServletRequest) context.getRequest();

                    DubboxApiHttpRequest reqBean = (DubboxApiHttpRequest) asynRequest
                            .getAttribute(CommonUtil.REQ_BEAN_KEY);
                    String traceId = idService.getInnerRequestId();
                    reqBean.setTraceId(traceId);
                    asynRequest.setAttribute(CommonUtil.REQ_BEAN_KEY, reqBean); // 重新设置bean
                    log.info(String.format("requestId=%s request begin,reqeust=%s", traceId,
                            JSON.toJSONString(reqBean)));

                    DubboxApiHttpSessionBean sessionBean = new DubboxApiHttpSessionBean(reqBean);
                    String operationType = sessionBean.getRequest().getOperationType();
                    DubboxApiHandlerExecuteTemplate handlerExecuteTemplate = applicationContext.getBean(operationType,
                            DubboxApiHandlerExecuteTemplate.class);
                    DubboxApiContext openApiContext = new DubboxApiContext();
                    openApiContext.setDubboxApiHttpSessionBean(sessionBean);
                    try {
                        handlerExecuteTemplate.execute(openApiContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 写入响应
                    DubboxApiResponseUtils.writeRsp((HttpServletResponse) context.getResponse(), sessionBean.getRequest());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    context.complete();
                }

            }
        });

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
