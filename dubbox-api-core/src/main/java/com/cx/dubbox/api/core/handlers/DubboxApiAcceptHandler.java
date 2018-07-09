package com.cx.dubbox.api.core.handlers;

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
public interface DubboxApiAcceptHandler {

    void acceptRequest(HttpServletRequest request, HttpServletResponse response);

}
