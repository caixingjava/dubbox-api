package com.cx.dubbox.api.service.service.defaults.impl;

import com.cx.dubbox.api.service.service.IidService;

import java.util.concurrent.atomic.AtomicInteger;


public class IdServiceImpl implements IidService {

    private AtomicInteger ai = new AtomicInteger(1);

    @Override
    public String getInnerRequestId() {
        return String.valueOf(ai.getAndIncrement());
    }
}
