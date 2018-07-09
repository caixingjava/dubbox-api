package com.cx.dubbox.api.core.interceptors;

import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import com.cx.dubbox.api.common.utils.CommonUtil;
import com.cx.dubbox.api.common.utils.NetworkUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <Description> 该类是所有请求的处理类 <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
@Slf4j
public class DubboxApiServiceParamValidateInterceptor extends AbstractDubboxApiValidateInterceptor {
    @Override
    protected DubboxApiHttpRequest iniDubboxApiHttpRequestBean(HttpServletRequest request) {
        log.info("进入拦截器，开始封装请求参数");
        String requestMethod = request.getMethod();
        DubboxApiHttpRequest bean = new DubboxApiHttpRequest();
        if (requestMethod.equalsIgnoreCase(CommonUtil.REQUEST_METHOD.POST.name())) {
            try {
                parsePostMethod(request, bean);
            } catch (IOException e) {
                log.error("这个请求格式不是application/json的,我处理不了...");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else if (requestMethod.equalsIgnoreCase(CommonUtil.REQUEST_METHOD.GET.name())) {
            parseGetMethod(request, bean);
            String queryString = request.getQueryString();
            bean.setQueryString(queryString);
        }
        bean.setApiUrlParams(extractThdUrlParams(request));
        bean.setLocalAddress(request.getLocalAddr());
        bean.setLocalPort(request.getLocalPort());
        bean.setClientAddress(NetworkUtil.getClientIpAddr(request));
        bean.setRequestHeader(getHeadersInfo(request)); // 获取请求头
        if (request.getContentType() != null)
            bean.getRequestHeader().put("content-type", request.getContentType());
        bean.setOperationType(CommonUtil.API_SERVICE_KEY);
        bean.setRequestMethod(requestMethod);
        if (bean.getSignMethod() == null)
            bean.setSignMethod("MD5");
        if (bean.getFormat() == null)
            bean.setFormat("json");
        return bean;
    }

    private void parseGetMethod(HttpServletRequest request, DubboxApiHttpRequest bean) {

        String requestUrl=request.getRequestURI(); // /test/tapi
        bean.setRequestUrl(requestUrl);
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String mapJson = enums.nextElement();
            String value = (String) request.getParameter(mapJson);

            bean.addServiceGetRequestData(mapJson, value);
        }
    }

    private void parsePostMethod(HttpServletRequest request, DubboxApiHttpRequest bean) throws IOException {


        String requestUrl=request.getRequestURI(); // /test/tapi
        bean.setRequestUrl(requestUrl);


        int len = request.getContentLength();
        ServletInputStream iii = request.getInputStream();
        byte[] buffer = new byte[len];
        iii.read(buffer, 0, len);
        String bodyContent = new String(buffer, "UTF-8");
        bean.setServiceRequestData(bodyContent); // 请求体

    }

    private Map<String, String> extractThdUrlParams(HttpServletRequest request) {
        Map<String, String> urlParams = new HashMap<>();
        Map<String, String[]> orignalUrlParams = request.getParameterMap();
        String key = null;
        String[] values = null;
        if (null != orignalUrlParams) {
            Iterator entries = orignalUrlParams.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                key = (String) entry.getKey();
                values = (String[]) entry.getValue();
                String val = values[0];
                try {
                    val = java.net.URLEncoder.encode(val, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    log.error("exception on prceeding chinese char: " + val + " with " + e.getMessage());
                }

                urlParams.put(key, null != values ? val : "");
            }
        }
        return urlParams;
    }

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

}
