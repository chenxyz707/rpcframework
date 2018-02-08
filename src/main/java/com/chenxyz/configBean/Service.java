package com.chenxyz.configBean;

import com.chenxyz.registry.BaseRegistryDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Description
 *
 * @author chenlinchao
 * @version 1.0
 * @date 2018-02-05
 * Copyright 青海粮食云项目组
 */
public class Service extends BaseConfigBean implements InitializingBean, ApplicationContextAware {

    private static final long serialVersionUID = 5135724412508782902L;

    private String intf;

    private String ref;

    private String protocol;

    private static ApplicationContext application;

    @Override
    public void afterPropertiesSet() throws Exception {
        BaseRegistryDelegate.registry(ref, application);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Service.application = applicationContext;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public static ApplicationContext getApplication() {
        return application;
    }
}
