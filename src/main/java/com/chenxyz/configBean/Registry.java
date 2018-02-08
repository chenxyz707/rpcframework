package com.chenxyz.configBean;

import com.chenxyz.registry.BaseRegistry;
import com.chenxyz.registry.RedisRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册类
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-02
 */
public class Registry extends BaseConfigBean implements InitializingBean, ApplicationContextAware {


    private static final long serialVersionUID = 6388553342327591904L;

    private String protocol;

    private String address;

    public ApplicationContext application;

    private static Map<String, BaseRegistry> registryMap = new HashMap<>();

    static {
        registryMap.put("redis", new RedisRegistry());
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.application = applicationContext;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static Map<String, BaseRegistry> getRegistryMap() {
        return registryMap;
    }

    public static void setRegistryMap(Map<String, BaseRegistry> registryMap) {
        Registry.registryMap = registryMap;
    }
}
