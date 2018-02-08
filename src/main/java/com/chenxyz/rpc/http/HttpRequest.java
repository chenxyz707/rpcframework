package com.chenxyz.rpc.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-08
 */
public class HttpRequest {

    private static Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    /**
     * 发送GET请求
     * @param url   URL路径
     * @param param URL参数，如name1=value1&name2=value2
     * @return
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        String urlString = url + "?" + param;
        try {
            URL realUrl = new URL(urlString);
            URLConnection connection = realUrl.openConnection();
            // 设置请求头
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "keep-alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                logger.info(key + "----------->" + map.get(key));
            }

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException e) {
            logger.error("send get request ["+urlString+"] error", e);
        } catch (IOException e) {
            logger.error("send get request ["+urlString+"] error", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("error close inputstream", e);
                }
            }
        }
        return result;
    }

    /**
     * 发送POST请求
     * @param url   URL路径
     * @param param JSON字符串格式的请求参数
     * @return
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();

            // 设置请求头
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "keep-alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            connection.setDoOutput(true);
            connection.setDoInput(true);

            out = new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.flush();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while((line = in.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException e) {
            logger.error("send post request ["+url+"] error", e);
        } catch (IOException e) {
            logger.error("send post request ["+url+"] error", e);
        } finally {
            if(out != null) {
                out.close();
            }
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
