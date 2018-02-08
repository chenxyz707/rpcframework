package com.chenxyz.registry;

import com.alibaba.fastjson.JSONObject;
import com.chenxyz.configBean.Protocol;
import com.chenxyz.configBean.Registry;
import com.chenxyz.configBean.Service;
import com.chenxyz.redis.RedisApi;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis实现服务注册
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-05
 */
public class RedisRegistry implements BaseRegistry {

    @Override
    public boolean registry(String ref, ApplicationContext application) {

        try {
            Protocol protocol = application.getBean(Protocol.class);
            Map<String, Service> serviceMap = application.getBeansOfType(Service.class);
            Registry registry = application.getBean(Registry.class);

            RedisApi.createJedisPool(registry.getAddress());

            for (Map.Entry<String, Service> entry : serviceMap.entrySet()) {
                if (entry.getValue().getRef().equals(ref)) {
                    JSONObject jo = new JSONObject();
                    jo.put("protocol", JSONObject.toJSONString(protocol));
                    jo.put("service", JSONObject.toJSONString(entry.getValue()));

                    JSONObject ipport = new JSONObject();
                    ipport.put(protocol.getHost()+":"+protocol.getPort(), jo);
                    lpush(ipport, ref);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void lpush(JSONObject ipport, String key) {
        if (RedisApi.exists(key)) {
            Set<String> keys = ipport.keySet();
            String ipportStr = "";
            for (String item : keys) {
                ipportStr = item;
            }

            List<String> registryInfo = RedisApi.lrange(key);
            List<String> newRegistry = new ArrayList<>();

            boolean isold = false;

            for (String node : registryInfo) {
                JSONObject jo = JSONObject.parseObject(node);
                if(jo.containsKey(ipportStr)) {
                    newRegistry.add(ipport.toJSONString());
                    isold = true;
                } else {
                    newRegistry.add(node);
                }
            }

            if (isold) {
                //这里是老机器启动去重
                if (newRegistry.size() > 0) {
                    RedisApi.del(key);
                    String[] newReStr = new String[newRegistry.size()];
                    for (int i=0; i < newRegistry.size(); i++) {
                        newReStr[i] = newRegistry.get(i);
                    }
                    RedisApi.lpush(key, newReStr);
                }
            } else {
                RedisApi.lpush(key, ipport.toJSONString());
            }
        }
    }

    @Override
    public List<String> getRegistry(String id, ApplicationContext application) {

        try {
            Registry registry = application.getBean(Registry.class);
            RedisApi.createJedisPool(registry.getAddress());
            if (RedisApi.exists(id)) {
                //拿key对应的list
                return RedisApi.lrange(id);
            }
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return null;
    }

}
