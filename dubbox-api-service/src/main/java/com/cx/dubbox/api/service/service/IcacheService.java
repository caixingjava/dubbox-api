package com.cx.dubbox.api.service.service;

public interface IcacheService {

    public void put(String key,Object obj);

    public Object get(String key);

    public void remove(String key);

}
