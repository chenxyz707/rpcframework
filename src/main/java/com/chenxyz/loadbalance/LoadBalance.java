package com.chenxyz.loadbalance;

import java.util.List;

/**
 * 负载均衡
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public interface LoadBalance {

    NodeInfo doSelect(List<String> registryInfo);
}
