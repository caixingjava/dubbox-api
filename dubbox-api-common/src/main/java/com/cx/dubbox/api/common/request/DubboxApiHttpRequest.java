package com.cx.dubbox.api.common.request;

import com.alibaba.fastjson.JSON;
import com.cx.dubbox.api.common.utils.CommonUtil;
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

    /**
     * 请求头信息
     */
    private Map<String,String> requestHeader;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 客户端地址
     */
    private String clientAddress;

    /**
     * 本地地址
     */
    private String localAddress;

    /**
     * 本地端口
     */
    private int localPort;

    /**
     * 调用后端api的参数
     */
    private Map<String,String> apiUrlParams;

    /**
     * 服务请求数据
     */
    private String serviceRequestData;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 服务get请求数据
     */
    private Map<String,String> serviceGetRequestData;

    /**
     * 查询的参数
     */
    private String queryString;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 响应时间
     */
    private Date responseTime;

    /**
     * 过期时间
     */
    private Long elapsedTime;

    /**
     * 内部追踪ID
     */
    private String traceId;

    /**
     * 打印的字符串
     */
    private String printStr;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用密钥
     */
    private String appToken;

    /**
     * apiID
     */
    private String apiId;

    /**
     * 版本
     */
    private String version;

    /**
     * 时间戳
     */
    private String timeStamp;

    /**
     * 标记的方法
     */
    private String signMethod;

    /**
     * 标志
     */
    private String sign;

    /**
     * 设备令牌
     */
    private String deviceToken;

    /**
     * 用户令牌
     */
    private String userToken;

    /**
     * 格式
     */
    private String format;


    public String getRouteBeanKey() {
        if (this.operationType.equals(CommonUtil.API_SERVICE_KEY)) {
            return CommonUtil.getRouteBeanRedisKey(traceId);
        }

        return CommonUtil.getRouteBeanRedisKey("");
    }

    public void addServiceGetRequestData(String key, String value) {
        if (this.serviceGetRequestData == null) {
            this.serviceGetRequestData = new HashMap<>();
        }
        this.serviceGetRequestData.put(key, value);
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

}
