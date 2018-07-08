package com.cx.dubbox.api.service.service.defaults.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@ComponentScan(basePackages = "com.cx.dubbox.api.service.service.defaults")
@Profile("default")
public class Config {
}
