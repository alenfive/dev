package com.freedom.weixin.utils;

import com.freedom.weixin.entity.User;
import com.freedom.weixin.web.WebController;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * @Author: mihuajun 【kobe96688@126.com】
 * @Date: 1/19/2017 11:38 AM
 */

public class WeixinClient {

    private static final Log log4j = LogFactory.getLog(WebController.class);
    private static final Header header = new Header("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36");


    public static String httpGet(User user, String url) throws IOException {
        log4j.info(url);
        GetMethod method = new GetMethod(url);
        method.setFollowRedirects(false);
        method.setRequestHeader(header);
        method.addRequestHeader("Content-Type","text/html; charset=UTF-8");
        user.getHttpClient().executeMethod(method);
        String rbody =  new String(method.getResponseBodyAsString().getBytes("ISO-8859-1"),"utf-8");
        method.releaseConnection();
        log4j.info(rbody);
        return rbody;
    }

    public static byte[] httpGetByte(User user, String url) throws IOException {
        log4j.info(url);
        GetMethod method = new GetMethod(url);
        method.setFollowRedirects(false);
        method.setRequestHeader(header);
        method.addRequestHeader("Content-Type", "text/html; charset=UTF-8");
        user.getHttpClient().executeMethod(method);
        byte[] rbyte =  method.getResponseBody();
        method.releaseConnection();
        return rbyte;
    }

    public static String httpPost(User user, RequestEntity param, String url) throws IOException {
        log4j.info(url);
        PostMethod method = new PostMethod(url);
        method.setRequestEntity(param);
        method.setFollowRedirects(false);
        method.setRequestHeader(header);
        method.addRequestHeader("Content-Type","application/json;charset=UTF-8");
        user.getHttpClient().executeMethod(method);
        String rbody =  new String(method.getResponseBodyAsString().getBytes("ISO-8859-1"),"utf-8");
        method.releaseConnection();
        log4j.info(rbody);
        return rbody;
    }

}
