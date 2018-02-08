package com.chenxyz.rmi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chenxyz.configBean.Service;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * rmi的实现类，负责rmi的调用，这个是生产者端使用的类
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-05
 */
public class SoaRmiImpl extends UnicastRemoteObject implements SoaRmi {


    private static final long serialVersionUID = 9183604246175365909L;

    protected SoaRmiImpl() throws RemoteException {
        super();
    }

    @Override
    public String invoke(String param) {
        JSONObject requestParam = JSONObject.parseObject(param);
        //要从远程的生产者的spring容器中拿到对应的serviceid实例
        String serviceId = requestParam.getString("serviceId");
        String methodName = requestParam.getString("methodName");
        JSONArray paramTypes = requestParam.getJSONArray("paramTypes");
        //这个对应的方法参数
        JSONArray methodParams = requestParam.getJSONArray("methodParams");
        //这个就是反射的参数
        Object[] objs = null;
        if (methodParams != null) {
            objs = new Object[methodParams.size()];
            int i = 0;
            for (Object o : methodParams) {
                objs[i++] = o;
            }
        }

        //拿到spring的上下文
        ApplicationContext application = Service.getApplication();
        //服务层的实例
        Object serviceBean = application.getBean(serviceId);
        //获取对应的方法，需要考虑方法的重载
        Method method = getMethod(serviceBean, methodName, paramTypes);

        if (method != null) {
            Object result;
            try {
                result = method.invoke(serviceBean, objs);
                return result.toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            return "===no such method===";
        }
        return null;
    }

    /**
     * 根据方法名称和参数类型获取类中对应的方法
     * @param bean
     * @param methodName
     * @param paramTypes
     * @return
     */
    private Method getMethod(Object bean, String methodName, JSONArray paramTypes) {

        Method[] methods = bean.getClass().getMethods();
        List<Method> mtList = new ArrayList<>();

        for(Method method : methods) {
            //把名字和methodName入参相同的方法加入到list中来
            if (methodName.equals(method.getName())) {
                mtList.add(method);
            }
        }

        //如果大小是1就说明相同的方法只有一个
        if (mtList.size() == 1) {
            return mtList.get(0);
        }

        loop : for(Method method : mtList) {
            Class<?>[] types = method.getParameterTypes();
            if (types.length != paramTypes.size()) {
                //如果参数长度不一致就跳过
                continue;
            }
            for (int i = 0; i < types.length; i++) {
                if(!types[i].toString().contains(paramTypes.getString(i))) {
                    //如果参数类型不一致就跳过
                    continue loop;
                }
            }
            return method;
        }
        return null;
    }
}
