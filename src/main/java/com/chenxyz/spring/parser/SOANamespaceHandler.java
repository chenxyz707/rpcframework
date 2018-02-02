package com.chenxyz.spring.parser;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.chenxyz.configBean.Protocol;
import com.chenxyz.configBean.Reference;
import com.chenxyz.configBean.Registry;

/**
 * soa标签解析器
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-02
 */
public class SOANamespaceHandler extends NamespaceHandlerSupport {

    public void init() {

        registerBeanDefinitionParser("registry", new RegistryBeanDefinitionParser(Registry.class));
        registerBeanDefinitionParser("reference", new ReferenceBeanDifinitionParser(Reference.class));
        registerBeanDefinitionParser("protocol", new ProtocolBeanDefinitionParser(Protocol.class));
    }
}
