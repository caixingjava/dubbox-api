package com.cx.dubbox.api.core.filters;

import com.cx.dubbox.api.core.utils.SystemConfigParaUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <Description> <br>
 *
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */
public class ControlConcurrentFilter implements Filter {

    private static AtomicInteger times = new AtomicInteger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            if (times.incrementAndGet() > SystemConfigParaUtil.getConcurrentLimit()) {
                response.getWriter().write("please wait a moment...");
                return;
            }
            chain.doFilter(request, response);
        } finally {
            times.decrementAndGet();
        }

    }

    @Override
    public void destroy() {

    }
}
