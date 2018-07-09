package com.cx.dubbox.api.core.handlers;

import com.cx.dubbox.api.core.protocol.AbstractTask;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
public interface ThreadPoolHandler {

    public Object addTask(AbstractTask task);

}
