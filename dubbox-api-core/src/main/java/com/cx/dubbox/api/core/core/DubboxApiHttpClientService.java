package com.cx.dubbox.api.core.core;

import java.util.Map;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
public interface DubboxApiHttpClientService {

    public String doGet(String webUrl,String traceId);
    public String doGet(String webUrl, Map<String,String> paramMap, String traceId);
    public String doHttpsGet(String webUrl,String traceId);

    public String doHttpsGet(String webUrl, Map<String,String> paramMap,String traceId);


    public String doHttpsPost(String url, String content, String contentType,String traceId);

    public String doPost(String url, String reqData, String contentType,String traceId);

}
