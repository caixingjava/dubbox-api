package com.cx.dubbox.api.core.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
@Getter
@Setter
public class DubboxApiRouteBean implements Serializable {

    private static final long serialVersionUID = -2967347972000466612L;

    private String traceId;
    private String requestUrl;
    private String targetUrl;
    private String apiId;
    private String requestMethod;
    private String version;
    private String timeStamp;
    private Map<String, String> reqHeader;
    private String operationType;
    private String serviceReqData;
    private Map<String, String> serviceGetReqData;
    private Properties thdApiUrlParams;
    private String serviceRsp;

    public void addThdApiUrlParams(String key, String value) {
        if (thdApiUrlParams == null) {
            this.thdApiUrlParams = new Properties();
        }
        this.thdApiUrlParams.put(key, value);
    }

}
