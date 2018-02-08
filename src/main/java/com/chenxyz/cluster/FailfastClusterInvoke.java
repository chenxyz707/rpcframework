package com.chenxyz.cluster;

import com.chenxyz.invoke.Invocation;

/**
 * 调用节点异常直接失败
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public class FailfastClusterInvoke implements Cluster {
    @Override
    public String invoke(Invocation invocation) throws Exception {
        return null;
    }
}
