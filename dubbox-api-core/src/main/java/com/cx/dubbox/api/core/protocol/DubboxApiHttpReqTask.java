package com.cx.dubbox.api.core.protocol;

import com.cx.dubbox.api.core.handlers.DubboxApiHandlerExecuteTemplate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DubboxApiHttpReqTask extends AbstractTask {

    private DubboxApiHttpSessionBean httpSessionBean;
    private DubboxApiHandlerExecuteTemplate handlerExecuteTemplate;

    public DubboxApiHttpReqTask(DubboxApiHttpSessionBean httpSessionBean,
                              DubboxApiHandlerExecuteTemplate handlerExecuteTemplate) {
        this.httpSessionBean = httpSessionBean;
        this.handlerExecuteTemplate = handlerExecuteTemplate;
    }

    @Override
    public DubboxApiHttpSessionBean doBussiness() throws Exception {
        return null;
    }
}
