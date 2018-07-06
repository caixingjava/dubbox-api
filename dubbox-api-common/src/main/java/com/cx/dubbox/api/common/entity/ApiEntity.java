package com.cx.dubbox.api.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <Description> dubbox服务接口的信息 <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月06日 <br>
 */
@Getter
@Setter
public class ApiEntity implements Serializable {

    /**
     * 序列ID
     */
    private static final long serialVersionUID = -5042973550719248189L;

    /**
     * 请求协议（http、https）
     */
    private String protocol;

    /**
     * 内部分配的接口ID
     */
    private String apiId;

    /**
     * 版本号
     */
    private String version;

    /**
     * 服务器地址
     */
    private String hostAddress;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 目标地址
     */
    private String targetUrl;

    /**
     * 请求的方法
     */
    private String requestMethod;

    /**
     * 根据以上属性，组成api地址
     */
    private String url;

    public String getApiUrl(){
        StringBuilder builder = new StringBuilder();
        builder.append("://");
        builder.append(hostAddress);
        if(null != port){
            builder.append(':');
            builder.append(port);
        }
        if(null != targetUrl){
            builder.append('/');
            builder.append(targetUrl);
        } else {
            builder.append('/');
        }
        this.url = builder.toString();
        return url;
    }



}
