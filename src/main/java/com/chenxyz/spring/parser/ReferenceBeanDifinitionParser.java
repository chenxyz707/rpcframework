package com.chenxyz.spring.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.chenxyz.configBean.Reference;

/**
 * reference标签解析器
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-02
 */
public class ReferenceBeanDifinitionParser implements BeanDefinitionParser {

    private Class beanClass;

    public ReferenceBeanDifinitionParser(Class beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(Reference.class);
        beanDefinition.setLazyInit(false);

        String id = element.getAttribute("id");
        String intf = element.getAttribute("interface");
        String protocol = element.getAttribute("protocol");
        String loadbalance = element.getAttribute("loadbalance");
        String cluster = element.getAttribute("cluster");
        String retries = element.getAttribute("retries");

        if (id == null || "".equals(id)) {
            throw new RuntimeException("Reference id 不能为空！");
        }
        if (intf == null || "".equals(intf)) {
            throw new RuntimeException("Reference interface 不能为空！");
        }
        if (protocol == null || "".equals(protocol)) {
            throw new RuntimeException("Reference protocol 不能为空！");
        }
        if (loadbalance == null || "".equals(loadbalance)) {
            throw new RuntimeException("Reference loadbalance 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("intf", intf);
        beanDefinition.getPropertyValues().addPropertyValue("protocol",
                protocol);
        beanDefinition.getPropertyValues().addPropertyValue("loadbalance",
                loadbalance);
        beanDefinition.getPropertyValues().addPropertyValue("retries", retries);
        beanDefinition.getPropertyValues().addPropertyValue("cluster", cluster);
        parserContext.getRegistry().registerBeanDefinition("Reference" + id,
                beanDefinition);

        return beanDefinition;
    }
}
