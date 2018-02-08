package com.chenxyz.rmi;

import java.rmi.Remote;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-05
 */
public interface SoaRmi extends Remote {

    /**
     * 方法调用
     * @param param 请求信息
     * @return
     */
    public String invoke(String param);
}
