package com.chenxyz.registry;

import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 注册接口
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-05
 */
public interface BaseRegistry {

    public boolean registry(String param, ApplicationContext application);

    public List<String> getRegistry(String id, ApplicationContext application);
}
