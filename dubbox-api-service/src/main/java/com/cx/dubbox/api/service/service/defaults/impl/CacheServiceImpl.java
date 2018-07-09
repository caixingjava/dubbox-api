package com.cx.dubbox.api.service.service.defaults.impl;

import com.cx.dubbox.api.service.service.IcacheService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CacheServiceImpl implements IcacheService {

    private Map<String, Object> m = new HashMap<>();

    @Override
    public void put(String key, Object obj) {
        m.put(key, obj);
    }

    @Override
    public Object get(String key) {
        return m.get(key);
    }

    @Override
    public void remove(String key) {
        m.remove(key);
    }
}
