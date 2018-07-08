package com.cx.dubbox.api.core.protocol;

import java.util.concurrent.Callable;

public abstract class AbstractTask implements Callable<DubboxApiHttpSessionBean> {
    @Override
    public DubboxApiHttpSessionBean call() throws Exception {
        DubboxApiHttpSessionBean obj = doBussiness();
        return obj;
    }

    public abstract DubboxApiHttpSessionBean doBussiness() throws Exception;

}
