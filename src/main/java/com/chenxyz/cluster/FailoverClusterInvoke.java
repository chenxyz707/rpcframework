package com.chenxyz.cluster;

import com.chenxyz.invoke.Invocation;
import com.chenxyz.invoke.Invoke;

/**
 * 调用节点失败就自动切换到其它集群节点
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public class FailoverClusterInvoke implements Cluster {
    @Override
    public String invoke(Invocation invocation) throws Exception {
        String retries = invocation.getReference().getRetries();
        Integer retryInt = Integer.parseInt(retries);
        for (int i = 0; i < retryInt; i++) {
            try {
                Invoke invoke = invocation.getInvoke();
                String result = invoke.invoke(invocation);
                return result;
            } catch (Exception e) {
                continue;
            }
        }
        throw new RuntimeException("retries " + retries + "全部失败！");
    }
}
