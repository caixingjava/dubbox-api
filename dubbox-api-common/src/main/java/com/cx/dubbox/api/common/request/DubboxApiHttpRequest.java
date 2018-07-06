package com.cx.dubbox.api.common.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <Description> 请求通用类 <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月06日 <br>
 */
@Getter
@Setter
public class DubboxApiHttpRequest implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1121547194505824441L;

    private Map<String,String> requestHeader;

    private String requestUrl;

    private String operationType;

    private String clientAddress;

    private String localAddress;

    private int localPort;

    private Map<String,String> apiUrlParams;

    private String serviceRequestData;

    private String requestMethod;

    private Map<String,String> serviceGetRequestData;

    private String queryString;

    private Date requestTime;

    private Date responseTime;

    private Long elapsedTime;

    private String traceId;

    private String printStr;

    private String appId;

    private String appToken;

    private String apiId;

    private String version;

    private String timeStamp;

    private String signMethod;

    private String sign;

    private String deviceToken;

    private String userToken;

    private String format;

    public String getRouteBeanKey() {
        if (this.operationType.equals(CommonCodeConstants.API_SERVICE_KEY)) {
            return CommonCodeConstants.getRouteBeanRedisKey(traceId);
        }

        return CommonCodeConstants.getRouteBeanRedisKey("");
    }

    public void addServiceGetRequestData(String key, String value) {
        if (this.serviceGetRequestData == null) {
            this.serviceGetRequestData = new HashMap<>();
        }
        this.serviceGetRequestData.put(key, value);
    }

}
