package com.chenxyz.remote.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chenxyz.configBean.Service;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个是SOA框架中给生产者接收请求用的servlet，这个必须是采用http协议才能调得到的
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-08
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject requestParam = httpProcess(req, resp);
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
            Object result = "error";
            try {
                result = method.invoke(serviceBean, objs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            // servlet中的printwriter不需要手动关闭 servlet会自动关闭
            PrintWriter pw = resp.getWriter();
            pw.write(result.toString());
        } else {
            // servlet中的printwriter不需要手动关闭 servlet会自动关闭
            PrintWriter pw = resp.getWriter();
            pw.write("===no such method===");
        }
    }

    public static JSONObject httpProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStream is = req.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

        String line = "";
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        if(sb.length() <= 0) {
            return null;
        }
        return JSONObject.parseObject(sb.toString());
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
