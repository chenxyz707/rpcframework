package com.chenxyz.configBean;

import com.chenxyz.cluster.Cluster;
import com.chenxyz.cluster.FailfastClusterInvoke;
import com.chenxyz.cluster.FailoverClusterInvoke;
import com.chenxyz.cluster.FailsafeClusterInvoke;
import com.chenxyz.invoke.HttpInvoke;
import com.chenxyz.invoke.Invoke;
import com.chenxyz.invoke.NettyInvoke;
import com.chenxyz.invoke.RmiInvoke;
import com.chenxyz.loadbalance.LoadBalance;
import com.chenxyz.loadbalance.RandomLoadBalance;
import com.chenxyz.loadbalance.RoundRobinLoadBalance;
import com.chenxyz.proxy.advice.InvokeInvocationHandler;
import com.chenxyz.registry.BaseRegistryDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 引用
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-02
 */
public class Reference extends BaseConfigBean implements FactoryBean, InitializingBean, ApplicationContextAware {

    private static final long serialVersionUID = 3025605481619779602L;

    private String intf;

    private String loadbalance;

    private String protocol;

    private String cluster;

    private String retries;

    public static ApplicationContext application;

    private Invoke invoke;

    private static Map<String, Invoke> invokeMap = new HashMap<>();

    private static Map<String, LoadBalance> loadBalanceMap = new HashMap<>();

    private static Map<String, Cluster> clusterMap = new HashMap<>();

    private static List<String> registryInfo = new ArrayList<>();

    private static final String DEFAULT_PROTOCOL = "http";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    static {
        invokeMap.put("http", new HttpInvoke());
        invokeMap.put("rmi", new RmiInvoke());
        invokeMap.put("netty", new NettyInvoke());

        loadBalanceMap.put("random", new RandomLoadBalance());
        loadBalanceMap.put("roundrob", new RoundRobinLoadBalance());

        clusterMap.put("failover", new FailoverClusterInvoke());
        clusterMap.put("failfast", new FailfastClusterInvoke());
        clusterMap.put("failsafe", new FailsafeClusterInvoke());
    }

    @Override
    public Object getObject() throws Exception {
        logger.info("reference  getObject");
        if (!StringUtils.isEmpty(protocol)) {
            invoke = invokeMap.get(protocol);
        } else {
            Protocol pro = application.getBean(Protocol.class);
            if (pro != null) {
                invoke = invokeMap.get(pro.getName());
            } else {
                invoke = invokeMap.get(DEFAULT_PROTOCOL);
            }
        }
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[] {Class.forName(intf)}, new InvokeInvocationHandler(invoke, this));
    }

    @Override
    public Class<?> getObjectType() {
        try {
            if (!StringUtils.isEmpty(intf)) {
                return Class.forName(intf);
            }
        } catch (ClassNotFoundException e) {
            logger.error("error find object type", e);
        }

        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registryInfo = BaseRegistryDelegate.getRegistry(id, application);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Reference.application = applicationContext;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public Invoke getInvoke() {
        return invoke;
    }

    public void setInvoke(Invoke invoke) {
        this.invoke = invoke;
    }

    public static Map<String, Invoke> getInvokeMap() {
        return invokeMap;
    }

    public static void setInvokeMap(Map<String, Invoke> invokeMap) {
        Reference.invokeMap = invokeMap;
    }

    public static Map<String, LoadBalance> getLoadBalanceMap() {
        return loadBalanceMap;
    }

    public static void setLoadBalanceMap(Map<String, LoadBalance> loadBalanceMap) {
        Reference.loadBalanceMap = loadBalanceMap;
    }

    public static Map<String, Cluster> getClusterMap() {
        return clusterMap;
    }

    public static void setClusterMap(Map<String, Cluster> clusterMap) {
        Reference.clusterMap = clusterMap;
    }

    public static List<String> getRegistryInfo() {
        return registryInfo;
    }

    public static void setRegistryInfo(List<String> registryInfo) {
        Reference.registryInfo = registryInfo;
    }
}
