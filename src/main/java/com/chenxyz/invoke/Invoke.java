package com.chenxyz.invoke;

/**
 * 远程调用接口
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public interface Invoke {

    public String invoke(Invocation invocation) throws Exception;
}
