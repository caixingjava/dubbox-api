package com.cx.dubbox.api.core.handlers.impl;

import com.cx.dubbox.api.core.core.impl.DubboxApiRequestAdapter;
import com.cx.dubbox.api.core.core.impl.DubboxApiRequestHandler;
import com.cx.dubbox.api.core.core.impl.DubboxApiResponseHandler;
import com.cx.dubbox.api.core.handlers.DubboxApiHandlerExecuteTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class DubboxApiServiceHandlerExecuteTemplateImpl implements DubboxApiHandlerExecuteTemplate,Chain {

    private List<Command> commands = new ArrayList<>();

    @Autowired
    private DubboxApiRequestAdapter dubboxApiRequestAdapter;

    @Autowired
    private DubboxApiRequestHandler dubboxApiRequestHandler;

    @Autowired
    private DubboxApiResponseHandler dubboxApiResponseHandler;

    @PostConstruct
    public void init(){
        this.commands.add(dubboxApiRequestAdapter);
        this.commands.add(dubboxApiRequestHandler);
        this.commands.add(dubboxApiResponseHandler);
    }

    @Override
    public void addCommand(Command command) {
        this.commands.add(command);
    }

    @Override
    public boolean execute(Context chainContext) throws Exception {
        log.info("executing all handlers,have a good journey!");
        if (chainContext == null || null == this.commands) {
            throw new IllegalArgumentException();
        }
        Iterator<Command> cmdIterator = commands.iterator();
        Command cmd = null;
        while (cmdIterator.hasNext()) {
            cmd = (Command) cmdIterator.next();
            if (cmd.execute(chainContext)) {
                break; //有一个处理它了，就直接跳出
            }
        }
        return false;
    }
}
