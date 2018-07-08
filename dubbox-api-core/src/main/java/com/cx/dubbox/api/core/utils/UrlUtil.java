package com.cx.dubbox.api.core.utils;

import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Properties;

public class UrlUtil {

    private static PropertyPlaceholderHelper propertyPlaceHolder = new PropertyPlaceholderHelper("{", "}");

    public static String dealUrl(String targetUrl, Properties properties) {
        if (properties == null) {
            return targetUrl;
        }

        return propertyPlaceHolder.replacePlaceholders(targetUrl, properties);

    }



}
