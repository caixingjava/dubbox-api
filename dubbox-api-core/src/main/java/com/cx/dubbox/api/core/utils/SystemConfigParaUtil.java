package com.cx.dubbox.api.core.utils;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SystemConfigParaUtil implements Serializable {
    private static final long serialVersionUID = -4023127090716506023L;

    public static final String concurrentLimit_Key = "concurrentLimit";

    private static ConcurrentHashMap<String, String> paraContainer = new ConcurrentHashMap<>();

    private SystemConfigParaUtil() {}

    public static Integer getConcurrentLimit() {
        try {
            return Integer.valueOf(paraContainer.get(concurrentLimit_Key));
        } catch (Exception e) {
            return Integer.MAX_VALUE; //不进行并发量限制
        }
    }

    private static final ConcurrentHashMap<String, RateLimiter> container = new ConcurrentHashMap<>();

    private static final String ratelimit_key = "ratelimit_key";

    public static double acquire() {
        return container.get(ratelimit_key) == null ? 0d : container.get(ratelimit_key).acquire();

    }


}
