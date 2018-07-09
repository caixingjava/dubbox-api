package com.cx.dubbox.api.core.handlers;

import org.apache.commons.chain.Context;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
public interface DubboxApiHandlerExecuteTemplate {

    boolean execute(Context chainContext) throws Exception;

}
