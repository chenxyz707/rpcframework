package com.chenxyz.spring.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * protocol标签解析器
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-02
 */
public class ProtocolBeanDefinitionParser implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ProtocolBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String name = element.getAttribute("name");
        String host = element.getAttribute("host");
        String port = element.getAttribute("port");
        String contextpath = element.getAttribute("contextpath");

        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("protocol name 不能为空！");
        }
        if (StringUtils.isEmpty(host)) {
            throw new RuntimeException("protocol host 不能为空！");
        }
        if (StringUtils.isEmpty(port)) {
            throw new RuntimeException("protocol port 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("name", name);
        beanDefinition.getPropertyValues().addPropertyValue("host", host);
        beanDefinition.getPropertyValues().addPropertyValue("port", port);
        beanDefinition.getPropertyValues().addPropertyValue("contextpath", contextpath);

        parserContext.getRegistry().registerBeanDefinition("protocol" + host + port, beanDefinition);

        return beanDefinition;
    }
}
