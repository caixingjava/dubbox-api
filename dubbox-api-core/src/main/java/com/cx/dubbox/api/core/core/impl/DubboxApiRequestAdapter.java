package com.cx.dubbox.api.core.core.impl;

import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import com.cx.dubbox.api.common.response.CommonResponse;
import com.cx.dubbox.api.core.core.AbstractDubboxApiHandler;
import com.cx.dubbox.api.core.core.DubboxApiRouteBean;
import com.cx.dubbox.api.core.protocol.DubboxApiContext;
import com.cx.dubbox.api.core.protocol.DubboxApiHttpSessionBean;
import com.cx.dubbox.api.service.service.IauthenticationService;
import com.cx.dubbox.api.service.service.IcacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

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
public class DubboxApiRequestAdapter extends AbstractDubboxApiHandler {

    @Value("${proj.contextPath}")
    private String contextPath;

    @Autowired
    private IcacheService cacheService;
    @Autowired
    private IauthenticationService authenticationService;


    @Override
    public boolean doExcuteBiz(Context context) {
        DubboxApiContext openApiContext = (DubboxApiContext) context;
        DubboxApiHttpSessionBean httpSessionBean = (DubboxApiHttpSessionBean) openApiContext.getDubboxApiHttpSessionBean();
        DubboxApiHttpRequest request = httpSessionBean.getRequest();
        long currentTime = System.currentTimeMillis();
        log.info(String.format("begin run doExecuteBiz,currentTime=%d,httpSessonBean=%s", currentTime,
                httpSessionBean));

        // 参数校验
        //validateParam(request);
        // 权限校验
        authRequestBean(request);
        initRouteBean(httpSessionBean.getRequest()); // 初始化路由bean
        log.info(String.format(
                "end run doExecuteBiz,currentTime=%d,elapase_time=%d milseconds,httpSessonBean=%s",
                System.currentTimeMillis(), (System.currentTimeMillis() - currentTime), httpSessionBean));

        if (StringUtils.isNotBlank(request.getPrintStr())) {
            return true;
        }
        return false;
    }

    private DubboxApiRouteBean initRouteBean(DubboxApiHttpRequest request) {
        DubboxApiRouteBean routeBean = null;
        log.info("iniApiRouteBean，这一步可以校验token,当然这个根据我们的实际情况去实现");
        log.info("init 路由bean ");
        routeBean = new DubboxApiRouteBean();
        routeBean.setTraceId(request.getTraceId()); // 内部请求id,利于跟踪
        routeBean.setApiId(request.getApiId());// 请求api_id
        routeBean.setVersion(request.getVersion());// api_version
        routeBean.setReqHeader(request.getRequestHeader());// 请求头
        routeBean.setTimeStamp(request.getTimeStamp());// 请求时间

        routeBean.setOperationType(request.getOperationType()); // 请求操作类型
        routeBean.setRequestMethod(request.getRequestMethod());// 请求方法
        routeBean.setServiceReqData(request.getServiceRequestData());// post请求参数
        // routeBean.setQueryString(request.getQueryString());// get请求参数
        routeBean.setServiceGetReqData(request.getServiceGetRequestData()); // get请求参数
        routeBean.setRequestUrl(request.getRequestUrl());
        if (request.getApiUrlParams() != null) {
            for (Map.Entry<String, String> maps : request.getApiUrlParams().entrySet()) {
                routeBean.addThdApiUrlParams(maps.getKey(), maps.getValue());
            }
        }
        setRouteBeanApiId(request, routeBean);//计算apiid
        cacheService.put(request.getRouteBeanKey(), routeBean);
        return routeBean;
    }

    private void setRouteBeanApiId(DubboxApiHttpRequest request, DubboxApiRouteBean routeBean) {
        String requestUrl = request.getRequestUrl();
        if (this.contextPath != null && requestUrl != null && requestUrl.indexOf(contextPath) > 0) {
            String subString = requestUrl.substring(requestUrl.indexOf(contextPath) + contextPath.length() + 1);
            routeBean.setTargetUrl(subString);
            int index = subString.indexOf("/");
            if (index != -1) {
                String apiId = subString.substring(0, index);
                routeBean.setApiId(apiId);
            } else {
                routeBean.setApiId(subString);
            }

        } else {
            // /test
            String subString = requestUrl.substring(1);
            routeBean.setTargetUrl(subString);
            int index = subString.indexOf("/");
            if (index != -1) {
                String apiId = subString.substring(0, index);
                routeBean.setApiId(apiId);
            } else {
                routeBean.setApiId(subString);
            }
        }
    }

    private void setError(String errorCode, String errMsg, DubboxApiHttpRequest requestBean) {
        CommonResponse<String> r = new CommonResponse<String>(false);
        r.setErrorCode(errorCode);
        r.setErrorMessage(errMsg);
        requestBean.setPrintStr(r.toString());
    }

    // step1
    private void authRequestBean(DubboxApiHttpRequest requestBean) {
        // 对于请求信息进行审计
        log.info("authRequestBean权限校验...");
        if (this.authenticationService != null) {
            this.authenticationService.doAuthOpenApiHttpRequestBean(requestBean);
        }
    }

}
