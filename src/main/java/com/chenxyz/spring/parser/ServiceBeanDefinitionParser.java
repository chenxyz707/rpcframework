package com.chenxyz.spring.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * service解析器
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-04-13
 */
public class ServiceBeanDefinitionParser implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ServiceBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String intf = element.getAttribute("interface");
        String ref = element.getAttribute("ref");
        String protocol = element.getAttribute("protocol");

        if (StringUtils.isEmpty(intf)) {
            throw new RuntimeException("service intf 不能为空！");
        }
        if (StringUtils.isEmpty(ref)) {
            throw new RuntimeException("service ref 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("intf", intf);
        beanDefinition.getPropertyValues().addPropertyValue("ref", ref);
        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);

        parserContext.getRegistry().registerBeanDefinition("service" + ref + intf,
                beanDefinition);

        return beanDefinition;
    }
}
