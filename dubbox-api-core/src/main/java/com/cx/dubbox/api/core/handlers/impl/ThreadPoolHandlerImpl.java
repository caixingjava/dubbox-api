package com.cx.dubbox.api.core.handlers.impl;

import com.cx.dubbox.api.common.exception.DubboxApiException;
import com.cx.dubbox.api.common.exception.DubboxApiServiceErrorEnum;
import com.cx.dubbox.api.core.handlers.ThreadPoolHandler;
import com.cx.dubbox.api.core.protocol.AbstractTask;
import com.cx.dubbox.api.core.protocol.DubboxApiHttpSessionBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
@Slf4j
@Component
public class ThreadPoolHandlerImpl implements ThreadPoolHandler {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public Object addTask(AbstractTask task) {
        FutureTask<DubboxApiHttpSessionBean> tsFutre = new FutureTask<>(task);
        taskExecutor.execute(tsFutre);
        try {
            while (!tsFutre.isDone()) {}
            return tsFutre.get();
        } catch (TaskRejectedException e) {
            log.error("the queue reached max deepth");
            throw new DubboxApiException(DubboxApiServiceErrorEnum.SYSTEM_QUEUE_DEEPTH);
        } catch (Throwable e) {
            Throwable throwable = e.getCause();
            if (throwable instanceof DubboxApiException) {
                throw (DubboxApiException) throwable;
            }
            log.error(String.format("exception happend on executing task with %s", e.getMessage()));
            DubboxApiException ex = new DubboxApiException(DubboxApiServiceErrorEnum.SYSTEM_BUSY, throwable);
            throw ex;
        }
    }
}
