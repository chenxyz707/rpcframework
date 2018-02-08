package com.chenxyz.cluster;

import com.chenxyz.invoke.Invocation;

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
        return null;
    }
}
