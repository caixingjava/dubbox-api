package com.cx.dubbox.api.core.interceptors;

import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import com.cx.dubbox.api.common.utils.CommonUtil;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
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
public abstract class AbstractDubboxApiValidateInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestMethod = request.getMethod();
        if(!requestMethod.equals(CommonUtil.REQUEST_METHOD.GET.name())&&!requestMethod.equals(CommonUtil.REQUEST_METHOD.POST.name())){
            throw new RuntimeException("请求方法不对，请求方法必须是 GET 或POST");
        }
        DubboxApiHttpRequest requestBean = iniDubboxApiHttpRequestBean(request);
        request.setAttribute(CommonUtil.REQ_BEAN_KEY, requestBean);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    protected abstract DubboxApiHttpRequest iniDubboxApiHttpRequestBean(
            HttpServletRequest request);

}
