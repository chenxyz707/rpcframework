package com.chenxyz.registry;

import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-05
 */
public class RedisRegistry implements BaseRegistry {
    @Override
    public boolean registry(String param, ApplicationContext application) {
        return false;
    }

    @Override
    public List<String> getRegistry(String id, ApplicationContext application) {
        return null;
    }
}
