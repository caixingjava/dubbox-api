package com.cx.dubbox.api.service.service;

/**
 * <Description> 缓存服务 <br>
 * @author caixing<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年07月09日 <br>
 */

public interface IcacheService {


    public void put(String key,Object obj);

    public Object get(String key);

    public void remove(String key);

}
