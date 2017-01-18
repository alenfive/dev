package com.freedom.weixin.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.weixin.entity.ConcatResult;
import com.freedom.weixin.entity.User;
import com.freedom.weixin.utils.StringXmlUtil;
import com.freedom.weixin.utils.UserUtils;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: mihuajun 【kobe96688@126.com】
 * @Date: 1/17/2017 3:19 PM
 */

@RestController
public class WebController {
    private static final Log log4j = LogFactory.getLog(WebController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Header header = new Header("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36");

    @RequestMapping("/current")
    public User current(HttpSession httpSession){
        User user = UserUtils.getCurrent(httpSession);
        return user;
    }

    @RequestMapping("/queryCode")
    public String  queryCode(HttpSession httpSession,HttpServletResponse response) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        GetMethod method = null;
        String url = null;


        //uuid
        url = "http://login.wx2.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx2.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_="+getTime();
        method = new GetMethod(url);
        method.setRequestHeader(header);
        user.getHttpClient().executeMethod(method);
        String responseBody = method.getResponseBodyAsString();
        Pattern pattern = Pattern.compile("(?<=window.QRLogin.uuid = \")[^\"]+");
        Matcher matcher = pattern.matcher(responseBody);
        if(!matcher.find(0))queryCode(httpSession,response);
        String uuid = matcher.group();
        user.setUuid(uuid);
        log4j.info("get uuid:" + uuid);

        //验证码
        url = "http://login.weixin.qq.com/qrcode/"+user.getUuid();
        method = new GetMethod(url);
        method.setRequestHeader(header);
        user.getHttpClient().executeMethod(method);
        return getBase64Img(method.getResponseBody());
    }

    @RequestMapping("/listenerScan")
    public String listenerScan(HttpSession httpSession) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        if(user.getUuid() == null)throw new RuntimeException("uuid丢失");
        String url = "http://login.wx2.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=" + user.getUuid() +"&tip=0&r=-2110777326&_="+getTime();
        return httpGet(user,url);
    }

    @RequestMapping("/listenerConfim")
    public String listenerConfim(HttpSession httpSession) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        if(user.getUuid() == null)throw new RuntimeException("uuid丢失");
        String url = "http://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=" + user.getUuid() + "&tip=0&r=-2110777326&_="+getTime();
        String rbody = httpGet(user,url);
        user.setTicketUrl(rbody.split("\"")[1].replace("https","http"));
        return rbody;
    }

    @RequestMapping("/validateTicket")
    public String validateTicket(HttpSession httpSession) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        if(user.getUuid() == null)throw new RuntimeException("uuid丢失");
        String rbody = httpGet(user,user.getTicketUrl());

        if(rbody.indexOf("pass_ticket")!=-1){
            StringBuilder data = new StringBuilder(rbody);
            user.setSkey(StringXmlUtil.findTagBody(StringXmlUtil.findTag(data,"skey").get(0)));
            user.setPass_ticket(StringXmlUtil.findTagBody(StringXmlUtil.findTag(data,"pass_ticket").get(0)));
            user.setWxsid(StringXmlUtil.findTagBody(StringXmlUtil.findTag(data,"wxsid").get(0)));
            user.setWxuin(StringXmlUtil.findTagBody(StringXmlUtil.findTag(data,"wxuin").get(0)));
        }
        return rbody;
    }

    @RequestMapping("/getChatSet")
    public String getChatSet(HttpSession httpSession) throws IOException {
        String url = "http://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxbatchgetcontact?type=ex&r="+getTime();
        User user = UserUtils.getCurrent(httpSession);
        if(user.getSkey() == null)throw new RuntimeException("skey丢失");
        PostMethod method = new PostMethod(url);
        method.addRequestHeader(header);
        method.addRequestHeader("Content-type","application/json;charset=UTF-8");
        NameValuePair valueUin = new NameValuePair("BaseRequest.Uin",user.getWxuin());
        NameValuePair valueSid = new NameValuePair("BaseRequest.Sid",user.getWxsid());
        NameValuePair valueSkey = new NameValuePair("BaseRequest.Skey",user.getSkey());
        method.setRequestBody(new NameValuePair[]{valueUin,valueSid,valueSkey});
        user.getHttpClient().executeMethod(method);
        String rbody = method.getResponseBodyAsString();
        return rbody;
    }

    @RequestMapping("/getConcat")
    public String getConcat(HttpSession httpSession) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        if(user.getSkey() == null)throw new RuntimeException("skey丢失");
        String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?r="+getTime()+"&seq=0&skey="+user.getSkey()+"&pass_ticket="+user.getPass_ticket();
        String rbody = httpGet(user,url);
        ConcatResult result = objectMapper.readValue(rbody,ConcatResult.class);
        user.setConcats(result.getMemberList());
        return rbody;
    }

    private String httpGet(User user,String url) throws IOException {
        log4j.info(url);
        GetMethod method = new GetMethod(url);
        method.setFollowRedirects(false);
        method.setRequestHeader(header);
        method.addRequestHeader("Content-Type","text/html; charset=UTF-8");
        user.getHttpClient().executeMethod(method);
        String rbody =  new String(method.getResponseBodyAsString().getBytes("ISO-8859-1"),"utf-8");
        log4j.info(rbody);
        return rbody;
    }

    private void writeStream(HttpServletResponse response,byte[] responseBody) throws IOException {
        response.getOutputStream().write(responseBody);
        response.getOutputStream().flush();
    }

    private String getBase64Img(byte[] responseBody) {
        BASE64Encoder encoder = new BASE64Encoder();
        StringBuilder sb = new StringBuilder("data:image/jpeg|png|gif;base64,");
        sb.append(encoder.encode(responseBody));
        return sb.toString();
    }


    private long getTime(){
        return new Date().getTime();
    }
}
