package com.chenxyz.proxy.advice;

import com.chenxyz.cluster.Cluster;
import com.chenxyz.configBean.Reference;
import com.chenxyz.invoke.Invocation;
import com.chenxyz.invoke.Invoke;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-06
 */
public class InvokeInvocationHandler implements InvocationHandler {

    private Invoke invoke;

    private Reference reference;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public InvokeInvocationHandler(Invoke invoke, Reference reference) {
        this.invoke = invoke;
        this.reference = reference;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在这个invoke里面最终要调用多个远程的provider
        logger.info("已经获取到了代理实例");
        Invocation invocation = new Invocation();
        invocation.setMethod(method);
        invocation.setObjs(args);
        invocation.setInvoke(invoke);
        invocation.setReference(reference);

        Cluster cluster = reference.getClusterMap().get(reference.getCluster());
        String result = cluster.invoke(invocation);
        return result;
    }
}
