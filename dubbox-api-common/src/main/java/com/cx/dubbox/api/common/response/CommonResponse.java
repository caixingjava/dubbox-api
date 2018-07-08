package com.cx.dubbox.api.common.response;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Getter
@Setter
public class CommonResponse<T> implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -1362228320961074409L;

    /**
     * 返回的对象泛型
     */
    private T responseObject;

    /**
     * 是否响应
     */
    private boolean isResponse;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 正确的前缀
     */
    private String correctPrefix = "200.";

    /**
     * 无参构造
     */
    public CommonResponse() {

    }

    /**
     * 有参构造
     * @param isResponse
     */
    public CommonResponse(boolean isResponse) {
        this.isResponse = isResponse;
    }

    public CommonResponse(boolean isResponse, T responseObject) {
        this.isResponse = isResponse;
        this.responseObject = responseObject;
    }

    public CommonResponse(Boolean isResponse, String errorCode, String errorMessage) {
        this.isResponse = isResponse;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 是否成功
     * @return
     */
    public boolean isSuccess() {
        if (StringUtils.startsWithAny(errorCode, new String[] { correctPrefix })) {
            return true;
        }
        return errorCode == null;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
