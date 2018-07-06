package com.cx.dubbox.api.common.exception;

import lombok.Getter;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月06日 <br>
 */
@Getter
public enum DubboxApiServiceErrorEnum {

    /**
     * 响应成功
     */
    SYSTEM_SUCCESS("0000", "success"),

    /**
     * 服务器忙
     */
    SYSTEM_BUSY("1028", "server is busy"),

    /**
     * 队列已满
     */
    SYSTEM_QUEUE_DEEPTH("1029", "the queue reached max deepth"),

    /**
     * 输入参数有误
     */
    VALIDATE_PARAM_ERROR("1030", "error of input parameter"),

    /**
     * 远程服务错误
     */
    REMOTE_INVOKE_ERROR("1031", "remote service error"),

    /**
     * 请求参数格式不符合规则
     */
    PARA_NORULE_ERROR("1032", "the request parameter format does not conform to the rules"),

    /**
     * 验证错误
     */
    VALIDATE_ERROR("1033", "error check"),

    /**
     * 数据操作异常
     */
    DATA_OPER_ERROR("1034", "data manipulation exception"),

    /**
     * 业务逻辑异常
     */
    APPLICATION_ERROR("1035", "business logic anomaly"),

    /**
     * 系统业务异常
     */
    APPLICATION_OPER_ERROR("1036", "system service exception"),

    /**
     * 查询结果为空
     */
    DATA_EMPTY_ERROR("1037", "the result of the query is empty");

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
    private DubboxApiServiceErrorEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
