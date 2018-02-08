package com.chenxyz.loadbalance;

import java.util.List;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public NodeInfo doSelect(List<String> registryInfo) {
        return null;
    }
}
