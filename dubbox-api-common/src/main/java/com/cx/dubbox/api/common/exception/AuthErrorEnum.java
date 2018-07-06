package com.cx.dubbox.api.common.exception;

import lombok.Getter;

/**
 * <Description> 错误信息的相关编码和提示 <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月06日 <br>
 */
@Getter
public enum AuthErrorEnum {

    /**
     * 服务不可用
     */
    ERROR("1000", "service unavailable"),

    /**
     * 授权类型是必要的
     */
    GRANTTYPE("1001", "grant type is required"),

    /**
     * 应用ID是必要的
     */
    APP_ID("1002", "appid is required"),

    /**
     * 秘钥是必要的
     */
    SECRET("1003", "secret is required"),

    /**
     * 时间戳是必要的
     */
    TIMSTAMP("1004", "timestamp is required"),

    /**
     * sign是必要的
     */
    SIGN("1005", "sign is required"),

    /**
     * 无效的标志
     */
    INVALID_SIGN("1006", "invalid sign"),

    /**
     * 无效的请求
     */
    INVALID_REQUEST("1007", "invalid request"),

    /**
     * 无效的应用ID或者是无效的应用程序令牌
     */
    INVALID_CLIENT("1008", "invalid appId or apptoken"),

    /**
     * 无效的授权
     */
    INVALID_GRANT("1009", "invalid grant"),

    /**
     * 未经授权的应用ID
     */
    UNAUTHORIZED_CLIENT("1010", "unauthorized appId"),

    /**
     * 不支持的授权类型
     */
    UNSUPPORTED_GRANT_TYPE("1011", "unsupported grant type"),

    /**
     * 错误的令牌
     */
    INVALID_TOKEN("1012", "invalid token"),

    /**
     * 验证失败
     */
    ACCESS_DENIED("1013", "access denied"),

    /**
     * api id是必要的
     */
    API_ID("1014", "api id is required"),

    /**
     * 应用令牌是必填的
     */
    ACCESSTOKEN("1015", "app token is required"),

    /**
     * 错误的服务名
     */
    INVALID_SERVICENAME("1016", "invalid service name"),

    /**
     * content type为必填
     */
    CONTENTTYPE("1017", "httprequest header content-type is required"),

    /**
     * content type只允许 application/xml 或者 application/json
     */
    INVALID_CONTENTTYPE("1018", "invalid content-type,just application/xml or application/json"),

    /**
     * 错误的秘钥
     */
    INVALID_SECRET("1019", "invalid secret"),

    /**
     * 当前服务不可见
     */
    UN_VISIBLE_SERVICENAME("1020", "service is not visible"),

    /**
     * 当前应用已被锁定
     */
    LOCK_ITEM_APPID("1021", "current appid is locked"),

    /**
     * 当前服务已被锁定
     */
    LOCK_ITEM_API("1022", "current service is locked"),

    /**
     * 应用不在白名单列表中
     */
    APP_UNDEFIND_WHITE("1023","app undefind in whiteList"),

    /**
     * 在白名单中未找到服务名
     */
    SERVICE_UNDEFIND_WHITE("1024","service name undefind in whiteList"),

    /**
     * 回调url不存在
     */
    NOT_CALLBACKURL("1025","undefind in user's callBackUrl"),

    /**
     * 接口调用频率超出预设范围
     */
    INTERFACE_FREQUENCY("1026", "interface frequency out of limit"),

    /**
     * 应用程序令牌必须存在
     */
    APP_TOKEN("1027", "app token is required");

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误提示信息
     */
    private String errorMessage;

    /**
     * Description: 有参构造 <br>
     * @param errorCode 错误编码
     * @param errorMessage 错误提示信息
     * @author caixing<br>
     * @taskId <br>
     *
     */
    private AuthErrorEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
