package com.cx.dubbox.api.service.service.defaults.impl;

import com.cx.dubbox.api.service.service.IvirtualService;
import org.I0Itec.zkclient.ZkClient;

import javax.annotation.PostConstruct;

public class VirtualServiceImpl implements IvirtualService {
    private String rootPath;

    private String zkServers;
    private String context;

    private String ip;
    private String port;

    private static final String GATEWAY = "/provider";

    @PostConstruct
    public void init() {
        if (rootPath == null) {
            throw new RuntimeException("rootPath is null,please set it");
        }
        if (zkServers == null) {
            throw new RuntimeException("zkServers is null,please set it");
        }
        if (!rootPath.startsWith("/")) {
            rootPath = "/" + rootPath;
        }
        regist();
    }

    @Override
    public void regist() {
        ZkClient zkClient;
        zkClient = new ZkClient(zkServers, 5000);

        if (!zkClient.exists(rootPath)) {
            zkClient.createPersistent(rootPath);
        }
        String secondPath = rootPath + GATEWAY;
        if (!zkClient.exists(secondPath)) {
            zkClient.createPersistent(secondPath);
        }

        String thirdpath = secondPath + "/" + ip + ":" + port + "#" + context;
        if (!zkClient.exists(thirdpath)) {
            zkClient.createEphemeral(thirdpath);
        }
        System.out.println("提供的服务节点名称为：" + thirdpath);
    }

}
