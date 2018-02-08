package com.chenxyz.loadbalance;

import com.alibaba.fastjson.JSONObject;
import com.chenxyz.utils.ValueUtils;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * 随机的负载均衡算法
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public NodeInfo doSelect(List<String> registryInfo) {
        Random random = new Random();
        int index = random.nextInt(registryInfo.size());
        String registry = registryInfo.get(index);

        JSONObject registryJo = JSONObject.parseObject(registry);
        Collection values = registryJo.values();
        JSONObject node = new JSONObject();
        for (Object value : values) {
            node = JSONObject.parseObject(value.toString());
        }

        JSONObject protocol = node.getJSONObject("protocol");
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setHost(ValueUtils.getString(protocol, "host"));
        nodeInfo.setPort(ValueUtils.getString(protocol, "port"));
        nodeInfo.setContextpath(ValueUtils.getString(protocol, "contextpath"));
        return nodeInfo;
    }
}
