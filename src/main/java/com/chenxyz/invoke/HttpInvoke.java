package com.chenxyz.invoke;

import com.alibaba.fastjson.JSONObject;
import com.chenxyz.configBean.Reference;
import com.chenxyz.loadbalance.LoadBalance;
import com.chenxyz.loadbalance.NodeInfo;
import com.chenxyz.rpc.http.HttpRequest;

import java.util.List;

/**
 * Http方式调用
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public class HttpInvoke implements Invoke {
    @Override
    public String invoke(Invocation invocation) {

        List<String> registryInfo = invocation.getReference().getRegistryInfo();
        //负载均衡算法
        Reference reference = invocation.getReference();
        String loadbalance = reference.getLoadbalance();
        LoadBalance loadBalanceBean = reference.getLoadBalanceMap().get(loadbalance);
        NodeInfo nodeInfo = loadBalanceBean.doSelect(registryInfo);

        // 我们调用远程的生产者是传输的json字符串
        // 根据serviceId去生产者的spring容器中获取serviceId对应的实例
        // 根据methodName和methodType获取实例的method对象
        // 然后反射调用method
        JSONObject sendParam = new JSONObject();
        sendParam.put("methodName", invocation.getMethod().getName());
        sendParam.put("methodParams", invocation.getObjs());
        sendParam.put("paramTypes", invocation.getMethod().getParameterTypes());

        String url = "http://" + nodeInfo.getHost() + ":" + nodeInfo.getPort() +
                nodeInfo.getContextpath();
        String result = HttpRequest.sendPost(url, sendParam.toJSONString());
        return result;
    }
}
