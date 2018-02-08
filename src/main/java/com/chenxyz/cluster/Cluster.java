package com.chenxyz.cluster;

import com.chenxyz.invoke.Invocation;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public interface Cluster {

    String invoke(Invocation invocation) throws Exception;
}
