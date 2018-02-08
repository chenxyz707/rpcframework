package com.chenxyz.configBean;

import com.chenxyz.netty.NettyUtil;
import com.chenxyz.rmi.RmiUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 协议
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-02
 */
public class Protocol extends BaseConfigBean implements InitializingBean, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private static final long serialVersionUID = -8968745500399806597L;

    private String name;

    private String port;

    private String host;

    private String contextpath;

    private static ApplicationContext application;

    public static ApplicationContext getApplication() {
        return application;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (name.equalsIgnoreCase("rmi")) {
            RmiUtil rmi = new RmiUtil();
            rmi.startRmiServer(host, port, "soarmi");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Protocol.application = applicationContext;
    }

    /**
     * 这个事件是在spring启动完成以后触发的
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!ContextRefreshedEvent.class.getName().equals(event.getClass().getName())) {
            return;
        }
        if (!"netty".equalsIgnoreCase(name)) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NettyUtil.startServer(port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContextpath() {
        return contextpath;
    }

    public void setContextpath(String contextpath) {
        this.contextpath = contextpath;
    }
}
