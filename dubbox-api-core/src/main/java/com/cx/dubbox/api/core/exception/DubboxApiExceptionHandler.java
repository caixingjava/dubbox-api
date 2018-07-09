package com.cx.dubbox.api.core.exception;

import com.alibaba.fastjson.JSON;
import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import com.cx.dubbox.api.common.utils.CommonUtil;
import com.cx.dubbox.api.core.utils.DubboxApiResponseUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
public class DubboxApiExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        DubboxApiHttpRequest reqBean = (DubboxApiHttpRequest) request
                .getAttribute(CommonUtil.REQ_BEAN_KEY);

        reqBean.setPrintStr(JSON.toJSONString(ex));
        DubboxApiResponseUtils.writeRsp(response, reqBean);
        return null;
    }
}
