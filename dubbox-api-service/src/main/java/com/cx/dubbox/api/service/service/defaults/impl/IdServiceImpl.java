package com.cx.dubbox.api.service.service.defaults.impl;

import com.cx.dubbox.api.service.service.IidService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;


@Service
public class IdServiceImpl implements IidService {

    private AtomicInteger ai = new AtomicInteger(1);

    @Override
    public String getInnerRequestId() {
        return String.valueOf(ai.getAndIncrement());
    }
}
