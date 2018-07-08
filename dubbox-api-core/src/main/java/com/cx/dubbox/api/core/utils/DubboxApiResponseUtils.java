package com.cx.dubbox.api.core.utils;

import com.alibaba.fastjson.JSON;
import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class DubboxApiResponseUtils {

    public static final String CONTENT_TYPE_KEY = "content-type";
    public static final String HEADER_HOST_KEY = "host";
    public static final String HEADER_SERVER_KEY = "server";

    public static void writeRsp(HttpServletResponse response, DubboxApiHttpRequest requestBean) {
        setResponseHeader(response, requestBean.getRequestHeader());
        try {
            PrintWriter writer = response.getWriter();
            String body=requestBean.getPrintStr();
            log.info(String.format("body==%s",body));
            writer.print(requestBean.getPrintStr());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            log.error("Write body to response error, " + e.getMessage());
        }
            log.info(String.format("requestId=%s request end,request=%s", requestBean.getTraceId(), JSON.toJSONString(requestBean)));
    }

    private static void setResponseHeader(HttpServletResponse response, Map<String, String> httpHeader) {
        Iterator<Map.Entry<String, String>> entries = httpHeader.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            if (entry.getKey().equals(CONTENT_TYPE_KEY) || entry.getKey().equals(HEADER_HOST_KEY)) {
                response.addHeader(entry.getKey(), entry.getValue());
            }
        }
        response.setHeader(HEADER_SERVER_KEY, null);
    }


}
