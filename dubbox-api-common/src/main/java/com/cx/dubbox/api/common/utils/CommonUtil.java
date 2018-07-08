package com.cx.dubbox.api.common.utils;


import java.io.Serializable;

public class CommonUtil implements Serializable {

    private static final long serialVersionUID = 1396910125184138530L;
    public static final String API_SERVICE_KEY = "dubboxapi.service.HandlerExecuteTemplate";
    public static final String API_SYSERVICE_KEY = "dubboxapi.syService";
    public static final String API_GETDATA_KEY = "dubboxapi.getSyData";
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String REQ_BEAN_KEY = "GATE_WAY_BEAN";
    public static final String ROUTE_BEAN_KEY = "BL_OPENAPI_ROUTE_BEAN";
    public static final String CTRL_STR = "_";
    public static final String MDF_CHARSET_UTF_8 = "UTF-8";
    public static final String TRACE_ID = "traceId";
    public static String getRouteBeanRedisKey(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(ROUTE_BEAN_KEY).append(CTRL_STR).append(key);
        return sb.toString();
    }
    public enum REQUEST_METHOD {
        POST, GET
    }
}
