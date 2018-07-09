package com.cx.dubbox.api.service.service.defaults.impl;

import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import com.cx.dubbox.api.service.service.IauthenticationService;
import org.springframework.stereotype.Service;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
@Service
public class AuthenticationServiceImpl implements IauthenticationService {

    /**
     * Description: 鉴定该接口是否对该app鉴权 <br>
     * @author caixing<br>
     * @taskId <br>
     *
     */
    @Override
    public void doAuthOpenApiHttpRequestBean(DubboxApiHttpRequest requestBean) {

    }
}
