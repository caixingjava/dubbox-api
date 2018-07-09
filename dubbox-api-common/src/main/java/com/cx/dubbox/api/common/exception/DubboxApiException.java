package com.cx.dubbox.api.common.exception;

/**
 * <Description> dubbox 网关的异常类 <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月06日 <br>
 */
public class DubboxApiException extends RuntimeException {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 3547486748488433182L;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误提示信息
     */
    private String errorMessage;

    /**
     * Description: 无参构造 <br>
     * @author caixing<br>
     * @taskId <br>
     *
     */
    public DubboxApiException(){}

    /**
     * Description: 有参构造 <br>
     * @param errorEnum 错误提示信息
     * @author caixing<br>
     * @taskId <br>
     *
     */
    public DubboxApiException(AuthErrorEnum errorEnum){
        super(errorEnum.getErrorMessage());
        this.errorCode = errorEnum.getErrorCode();
        this.errorMessage = errorEnum.getErrorMessage();
    }

    /**
     * Description: 有参构造 <br>
     * @param errorEnum 错误提示信息
     * @param cause 捕捉到的异常信息
     * @author caixing<br>
     * @taskId <br>
     *
     */
    public DubboxApiException(AuthErrorEnum errorEnum,Throwable cause){
        super(errorEnum.getErrorMessage(),cause);
        this.errorCode = errorEnum.getErrorCode();
        this.errorMessage = errorEnum.getErrorMessage();
    }

    /**
     * Description: 有参构造 <br>
     * @param errorEnum 错误提示信息
     * @author caixing<br>
     * @taskId <br>
     *
     */
    public DubboxApiException(DubboxApiServiceErrorEnum errorEnum){
        super(errorEnum.getErrorMessage());
        this.errorCode = errorEnum.getErrorCode();
        this.errorMessage = errorEnum.getErrorMessage();
    }

    /**
     * Description: 有参构造 <br>
     * @param errorEnum 错误提示信息
     * @param cause 捕捉到的异常信息
     * @author caixing<br>
     * @taskId <br>
     *
     */
    public DubboxApiException(DubboxApiServiceErrorEnum errorEnum,Throwable cause){
        super(errorEnum.getErrorMessage(),cause);
        this.errorCode = errorEnum.getErrorCode();
        this.errorMessage = errorEnum.getErrorMessage();
    }


    /**
     * Description: 有参构造 <br>
     * @param errorMessage 错误提示信息
     * @author caixing<br>
     * @taskId <br>
     *
     */
    public DubboxApiException(String errorMessage){
        super(errorMessage);
    }

    /**
     * Description: 有参构造 <br>
     * @param errorMessage 错误提示信息
     * @param cause 捕捉到的异常
     * @author caixing<br>
     * @taskId <br>
     *
     */
    public DubboxApiException(String errorMessage,Throwable cause){
        super(errorMessage,cause);
    }

    /**
     * Description: 有参构造 <br>
     * @param errorMessage 错误提示信息
     * @param errorCode 错误编码
     * @author caixing<br>
     * @taskId <br>
     *
     */
    public DubboxApiException(String errorCode,String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Description: 有参构造 <br>
     * @param cause 捕捉到的异常信息
     * @author caixing<br>
     * @taskId <br>
     *
     */
    public DubboxApiException(Throwable cause){
        super(cause);
    }

}
