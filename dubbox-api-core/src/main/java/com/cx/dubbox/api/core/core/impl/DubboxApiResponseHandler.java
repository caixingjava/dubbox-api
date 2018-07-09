package com.cx.dubbox.api.core.core.impl;

import com.cx.dubbox.api.common.exception.DubboxApiException;
import com.cx.dubbox.api.common.exception.DubboxApiServiceErrorEnum;
import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import com.cx.dubbox.api.core.core.AbstractDubboxApiHandler;
import com.cx.dubbox.api.core.core.DubboxApiRouteBean;
import com.cx.dubbox.api.core.protocol.DubboxApiContext;
import com.cx.dubbox.api.core.protocol.DubboxApiHttpSessionBean;
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
public class DubboxApiResponseHandler extends AbstractDubboxApiHandler {

    @Autowired
    private IcacheService cacheService;

    @Override
    public boolean doExcuteBiz(Context context) {

        log.info("step2----");
        DubboxApiContext blCtx = (DubboxApiContext) context;
        DubboxApiHttpSessionBean httpSessionBean = (DubboxApiHttpSessionBean) blCtx
                .getDubboxApiHttpSessionBean();
        DubboxApiHttpRequest request = httpSessionBean.getRequest();
        long currentTime = System.currentTimeMillis();
            log.info(String.format(
                    "begin run doExecuteBiz,currentTime=%d,httpSessonBean=%s",
                    currentTime, httpSessionBean));
        String printStr = this.executePrint(request);
        request.setPrintStr(printStr);

            log.info(String
                    .format("end run doExecuteBiz,currentTime=%d,elapase_time=%d milseconds,httpSessonBean=%s",
                            System.currentTimeMillis(),
                            (System.currentTimeMillis() - currentTime),
                            httpSessionBean));

        return false;
    }

    private String executePrint(DubboxApiHttpRequest request) {
        log.info("step3...");
        try {
            return this.getResponseBody(request);
        } catch (Exception e) {
            DubboxApiException ex = null;
            if (e instanceof DubboxApiException) {
                ex = (DubboxApiException) e;
            } else {
                ex = new DubboxApiException(DubboxApiServiceErrorEnum.SYSTEM_BUSY,
                        e.getCause());
            }
            log.error("executePrint error, " + e.getMessage());
            // return XmlUtils.bean2xml((ex.getShortMsg("unknow")));
            return "error";
        } finally {
            // 从redis移除当前routebean
            String routeBeanKey = request.getRouteBeanKey();
            if (StringUtils.isNotBlank(routeBeanKey)) {
                cacheService.remove(routeBeanKey);
            }

			/*
			 * // 设置同步信号unlock redisKey =
			 * request.getUserTokenSyncSingalRedisKey(); if
			 * (StringUtils.isNotBlank(redisKey)) { Cache redisCache =
			 * this.cacheManager.getCache(openApiCacheName);
			 * redisCache.put(request.getUserTokenSyncSingalRedisKey(),
			 * CommonCodeConstants.SyncSingalType.SingalUnLock.getCode()); }
			 */
        }

    }

    private String getResponseBody(DubboxApiHttpRequest bean) {
        log.info("step4....");
        String routeBeanKey = bean.getRouteBeanKey();
        DubboxApiRouteBean routeBean = (DubboxApiRouteBean) cacheService
                .get(routeBeanKey);
        Object body = (Object) routeBean.getServiceRsp();
        if (body instanceof String) {
            return body.toString();
        } else {
            throw new RuntimeException("返回内容格式不对...");
        }

    }


}
