package com.cx.dubbox.api.core.protocol;

import com.cx.dubbox.api.common.request.DubboxApiHttpRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class DubboxApiHttpSessionBean implements Serializable {
    private static final long serialVersionUID = 35412713868168110L;

    private DubboxApiHttpRequest request;

    public DubboxApiHttpSessionBean(DubboxApiHttpRequest reqBean) {
        this.request = reqBean;
    }

    @Override
    public String toString() {
        return "OpenApiHttpSessionBean [request=" + request + "]";
    }


}
