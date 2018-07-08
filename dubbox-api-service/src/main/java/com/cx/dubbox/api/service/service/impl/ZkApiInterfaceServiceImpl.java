package com.cx.dubbox.api.service.service.impl;

import com.cx.dubbox.api.common.entity.ApiEntity;
import com.cx.dubbox.api.common.utils.CommonUtil;
import com.cx.dubbox.api.service.service.IapiInterfaceService;
import com.cx.dubbox.api.service.service.IloadBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ZkApiInterfaceServiceImpl implements IapiInterfaceService {

    private static final String REST = "rest";

    private static final String PROVIDERS = "providers";

    private static final String REST_SLASH = REST + "://";
    private static final String SLASH = "/";

    private String rootPath;

    private String zkServers;

    private ZkClient zkClient;

    private IloadBalanceService loadBalancerService;

    @PostConstruct
    public void init() {
        zkClient = new ZkClient(zkServers, 5000);
        if (!rootPath.startsWith(SLASH)) {
            rootPath = SLASH + rootPath;
        }

        if (loadBalancerService == null) {
            loadBalancerService = new RandomLoadBalanceImpl();
        }
        runaway(zkClient, rootPath);
    }

    @Override
    public ApiEntity queryApiInterfaceByApiId(String apiId, String version) {

        return null;
    }

    private void runaway(final ZkClient zkClient, final String path) {
        zkClient.unsubscribeAll();
        ConcurrentHashMap<String, List<String>> newHosts = new ConcurrentHashMap<>();
        //一级节点
        zkClient.subscribeChildChanges(path, (String parentPath, List<String> currentChilds) -> {
            log.info("{}'s child changed, currentChilds:{}", parentPath, currentChilds);
            // 一级节点的子节点发生变化 重新再来
            runaway(zkClient, path);
        });

        //二级节点
        List<String> firstGeneration = zkClient.getChildren(path);
        // /dubbo-online/com.z.test.Testapi
        if (firstGeneration != null && firstGeneration.size() > 0) {
            for (String child : firstGeneration) {
                String firstNextPath = path + "/" + child;
                zkClient.subscribeChildChanges(firstNextPath, (String parentPath, List<String> currentChilds) -> {
                    log.info("{}'s child changed, currentChilds:{}", parentPath, currentChilds);
                    // 2级节点的子节点发生
                    runaway(zkClient, path); // 重新再来
                });

                //三级节点
                List<String> secondGeneration = zkClient.getChildren(firstNextPath);
                // /dubbo-online/com.z.test.Testapi/providers
                if (secondGeneration != null && secondGeneration.size() > 0) {
                    for (String secondChild : secondGeneration) {
                        if (secondChild.startsWith(PROVIDERS)) {
                            String secondNextPath = firstNextPath + "/" + secondChild;
                            zkClient.subscribeChildChanges(secondNextPath, (String parentPath, List<String> currentChilds) -> {
                                log.info("{}'s child changed, currentChilds:{}", parentPath, currentChilds);
                                // 3级节点的子节点发生
                                runaway(zkClient, path); // 重新再来
                            });
                            //四级节点
                            List<String> thirdGeneration = zkClient.getChildren(secondNextPath);
                            // /dubbo-online/com.z.test.Testapi/rest://localhost:8080
                            if (thirdGeneration != null && thirdGeneration.size() > 0) {
                                for (String thirdChild : thirdGeneration) {
                                    if (thirdChild.startsWith(REST)) {
                                        /*
                                         * 样例
                                         * rest://10.148.16.27:8480/demo/
                                         * com.z.m.facade.api.
                                         * DemoFacadeService
                                         */
                                        ServiceProvider sp = new ServiceProvider(thirdChild);
                                        String contextPath = sp.getContextPath();
                                        String host = sp.getHost();
                                        List<String> hostSets = newHosts.get(contextPath);
                                        if (hostSets == null) {
                                            hostSets = new ArrayList<>();
                                            newHosts.put(contextPath, hostSets);
                                        }
                                        hostSets.add(host);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    private static class ServiceProvider {
        private String host;
        private String contextPath;
        private String provider;

        public ServiceProvider(String provider) {
            try {
                this.provider = URLDecoder.decode(provider, CommonUtil.MDF_CHARSET_UTF_8);
            } catch (UnsupportedEncodingException e) {
                log.error("地址解码错误{}", e);
                this.provider = provider;
            }
            parse();
        }

        private void parse() {
            String subString = provider.substring(REST_SLASH.length());

            int indexOfFirstSlash = subString.indexOf(SLASH);

            host = subString.substring(0, indexOfFirstSlash);
            String subSubString = subString.substring(indexOfFirstSlash + 1);
            int indexOfSecondSlash = subSubString.indexOf(SLASH);
            contextPath = subSubString.substring(0, indexOfSecondSlash);
        }

        public String getHost() {
            return host;
        }

        public String getContextPath() {
            return contextPath;
        }

    }

}
