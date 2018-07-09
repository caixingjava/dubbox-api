package com.cx.dubbox.api.core.core;

import com.cx.dubbox.api.common.exception.AuthErrorEnum;
import com.cx.dubbox.api.common.exception.DubboxApiException;
import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
@Slf4j
public abstract class AbstractDubboxApiHandler implements Command {

    public final String CONTENT_TYPE_KEY = "content-type";
    public final String CONTENT_TYPE_XML = "application/xml";
    public final String CONTENT_TYPE_JSON = "application/json";
    public final String HEADER_HOST_KEY = "host";
    public final String HEADER_SERVER_KEY = "server";


    public Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    public void validateRequestHeader(DubboxApiHttpRequest routeBean) {
        String contentType = routeBean.getRequestHeader().get(CONTENT_TYPE_KEY);
        if (StringUtils.isBlank(contentType)) {
            throw new DubboxApiException(AuthErrorEnum.CONTENTTYPE.getErrorCode(), AuthErrorEnum.CONTENTTYPE.getErrorMessage());
        }
        if (!contentType.contains(CONTENT_TYPE_JSON) && !contentType.contains(CONTENT_TYPE_XML)) {
            throw new DubboxApiException(AuthErrorEnum.INVALID_CONTENTTYPE.getErrorCode(),
                    AuthErrorEnum.INVALID_CONTENTTYPE.getErrorMessage());
        }
    }

    public abstract boolean doExcuteBiz(Context context);

    @Override
    public boolean execute(Context context) throws Exception {
        return doExcuteBiz(context);
    }
}
