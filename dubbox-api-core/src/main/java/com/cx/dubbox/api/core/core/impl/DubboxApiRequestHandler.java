package com.cx.dubbox.api.core.core.impl;

import com.cx.dubbox.api.common.entity.ApiEntity;
import com.cx.dubbox.api.common.exception.AuthErrorEnum;
import com.cx.dubbox.api.common.exception.DubboxApiException;
import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import com.cx.dubbox.api.common.utils.CommonUtil;
import com.cx.dubbox.api.core.core.AbstractDubboxApiHandler;
import com.cx.dubbox.api.core.core.DubboxApiHttpClientService;
import com.cx.dubbox.api.core.core.DubboxApiRouteBean;
import com.cx.dubbox.api.core.protocol.DubboxApiContext;
import com.cx.dubbox.api.core.protocol.DubboxApiHttpSessionBean;
import com.cx.dubbox.api.core.utils.UrlUtil;
import com.cx.dubbox.api.service.service.IapiInterfaceService;
import com.cx.dubbox.api.service.service.IcacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class DubboxApiRequestHandler extends AbstractDubboxApiHandler {

    private final int maxReqDataLth = 500;


    @Autowired
    private IapiInterfaceService apiInterfaceService;

    @Autowired
    private DubboxApiHttpClientService apiHttpClientService;

    @Autowired
    private IcacheService cacheService;

    @Override
    public boolean doExcuteBiz(Context context) {
        DubboxApiContext openApiContext = (DubboxApiContext) context;
        DubboxApiHttpSessionBean httpSessionBean = (DubboxApiHttpSessionBean) openApiContext.getDubboxApiHttpSessionBean();
        DubboxApiHttpRequest request = httpSessionBean.getRequest();
        long currentTime = System.currentTimeMillis();
            log.info(String.format("begin run doExecuteBiz,currentTime=%d,httpSessonBean=%s", currentTime,
                    httpSessionBean));
        String routeBeanKey = request.getRouteBeanKey();
        DubboxApiRouteBean routeBean = (DubboxApiRouteBean) cacheService.get(routeBeanKey);

        routeBean.setServiceRsp(doInvokeBackService(routeBean)); // 返回值
            log.info(String.format("end run doExecuteBiz,currentTime=%d,elapase_time=%d milseconds,httpSessonBean=%s",
                    System.currentTimeMillis(), (System.currentTimeMillis() - currentTime) , httpSessionBean));
        return false;
    }

    private String doInvokeBackService(DubboxApiRouteBean bean) {
        log.info("step5...");
        String serviceRspData = null; // 后端服务返回值
        String operationType = bean.getOperationType();
        String requestMethod = bean.getRequestMethod();

        if (operationType.equals(CommonUtil.API_SYSERVICE_KEY)) {

        } else if (CommonUtil.API_GETDATA_KEY.equals(operationType)) {

        } else if (CommonUtil.API_SERVICE_KEY.equals(operationType)) {
            log.info(String.format("{serviceId:%s ,version:%s }", bean.getApiId(), bean.getVersion()));
            ApiEntity apiInfo = apiInterfaceService.queryApiInterfaceByApiId(bean.getApiId(), bean.getVersion());

            if (apiInfo == null) {
                return String.format("this apiId=%s,version=%s has off line,please use another one", bean.getApiId(), bean.getVersion());
            }
            apiInfo.setTargetUrl(bean.getTargetUrl());
            apiInfo.setRequestMethod(bean.getRequestMethod());
            if (CommonUtil.REQUEST_METHOD.GET.name().equalsIgnoreCase(requestMethod)) { // get请求
                String url = apiInfo.getUrl();
                url = UrlUtil.dealUrl(url, bean.getThdApiUrlParams());
                log.info(String.format("{service url:%s} ", url));
                if (url.startsWith(CommonUtil.HTTPS)) {
                    if (bean.getServiceGetReqData() == null) {
                        serviceRspData = apiHttpClientService.doHttpsGet(url,bean.getTraceId());
                    } else {
                        serviceRspData = apiHttpClientService.doHttpsGet(url, bean.getServiceGetReqData(),bean.getTraceId());
                    }
                } else {
                    if (bean.getServiceGetReqData() == null) {
                        serviceRspData = apiHttpClientService.doGet(url,bean.getTraceId());
                    } else {
                        serviceRspData = apiHttpClientService.doGet(url, bean.getServiceGetReqData(),bean.getTraceId());
                    }
                }

            } else if (CommonUtil.REQUEST_METHOD.POST.name().equalsIgnoreCase(requestMethod)) {// post请求
                String url = apiInfo.getUrl();
                log.info(String.format("{service url:%s} ", url));
                String reqData = bean.getServiceReqData(); // 请求的json格式数据参数
                if (StringUtils.isNotBlank(reqData) && reqData.length() > this.maxReqDataLth) {
                    reqData = reqData.substring(0, this.maxReqDataLth - 1);
                }
                log.info(String.format("{serviceId:%s ,reqData:%s }", bean.getApiId(), reqData));

                String contentType = bean.getReqHeader().get(CONTENT_TYPE_KEY);
                if (url.startsWith(CommonUtil.HTTPS)) {
                    serviceRspData = apiHttpClientService.doHttpsPost(url, bean.getServiceReqData(), contentType,bean.getTraceId());
                } else {
                    serviceRspData = apiHttpClientService.doPost(url, bean.getServiceReqData(), contentType,bean.getTraceId());
                }
                if ("timeout".equals(serviceRspData)) {
                    log.error("invoke service: response is null!");
                    throw new DubboxApiException(AuthErrorEnum.ERROR.getErrorCode(), AuthErrorEnum.ERROR.getErrorMessage());
                }
            }
        }
        return serviceRspData;
    }

}
