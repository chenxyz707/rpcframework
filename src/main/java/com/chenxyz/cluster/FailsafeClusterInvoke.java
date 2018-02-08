package com.chenxyz.cluster;

import com.chenxyz.invoke.Invocation;

/**
 * 调用节点失败直接忽略
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public class FailsafeClusterInvoke implements Cluster {
    @Override
    public String invoke(Invocation invocation) throws Exception {
        return null;
    }
}
