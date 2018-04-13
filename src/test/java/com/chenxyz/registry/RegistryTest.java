package com.chenxyz.registry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 注册测试
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-04-10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:redisRegistry.xml")
public class RegistryTest {

    @Test
    public void redisRegistryTest() {

    }
}
