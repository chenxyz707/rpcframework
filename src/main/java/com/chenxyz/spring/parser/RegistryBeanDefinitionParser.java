package com.chenxyz.spring.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * registry标签解析器
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-02
 */
public class RegistryBeanDefinitionParser implements BeanDefinitionParser {

    private Class<?> beanClass;

    public RegistryBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String protocol = element.getAttribute("protocol");
        String address = element.getAttribute("address");

        if(StringUtils.isEmpty(protocol)) {
            throw new RuntimeException("regitry protocol 不能为空！");
        }
        if(StringUtils.isEmpty(address)) {
            throw new RuntimeException("regitry address 不能为空！");
        }
        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
        beanDefinition.getPropertyValues().addPropertyValue("address", address);

        parserContext.getRegistry().registerBeanDefinition("registry"+address, beanDefinition);

        return beanDefinition;
    }
}
