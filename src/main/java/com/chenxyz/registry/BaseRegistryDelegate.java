package com.chenxyz.registry;

import com.chenxyz.configBean.Registry;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-05
 */
public class BaseRegistryDelegate {

    public static void registry(String ref, ApplicationContext application) {
        Registry registry = application.getBean(Registry.class);
        String protocol = registry.getProtocol();
        BaseRegistry registryBean = registry.getRegistryMap().get(protocol);
        registryBean.registry(ref, application);
    }

    public static List<String> getRegistry(String id, ApplicationContext application) {
        Registry registry = application.getBean(Registry.class);
        String protocol = registry.getProtocol();
        BaseRegistry registryBean = registry.getRegistryMap().get(protocol);
        return registryBean.getRegistry(id, application);
    }
}
