package com.freedom.weixin.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedom.weixin.entity.Concat;
import com.freedom.weixin.entity.ConcatResult;
import com.freedom.weixin.entity.SyncKey;
import com.freedom.weixin.entity.User;
import com.freedom.weixin.utils.StringXmlUtil;
import com.freedom.weixin.utils.UserUtils;
import com.freedom.weixin.utils.WeixinClient;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.freedom.weixin.utils.WeixinClient.httpGet;
import static com.freedom.weixin.utils.WeixinClient.httpGetByte;

/**
 * @Author: mihuajun 【kobe96688@126.com】
 * @Date: 1/17/2017 3:19 PM
 */

@RestController
public class WebController {
    private static final Log log4j = LogFactory.getLog(WebController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/current")
    public User current(HttpSession httpSession){
        User user = UserUtils.getCurrent(httpSession);
        return user;
    }

    @RequestMapping("/queryCode")
    public String  queryCode(HttpSession httpSession,HttpServletResponse response) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        String url = null;

        //uuid
        url = "http://login.wx2.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx2.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_="+getTime();
        WeixinClient.httpGet(user,url);
        String responseBody = WeixinClient.httpGet(user,url);
        Pattern pattern = Pattern.compile("(?<=window.QRLogin.uuid = \")[^\"]+");
        Matcher matcher = pattern.matcher(responseBody);
        if(!matcher.find(0))queryCode(httpSession,response);
        String uuid = matcher.group();
        user.setUuid(uuid);
        log4j.info("get uuid:" + uuid);

        //验证码
        url = "http://login.weixin.qq.com/qrcode/"+user.getUuid();
        return getBase64Img(httpGetByte(user,url));
    }

    @RequestMapping("/listenerScan")
    public String listenerScan(HttpSession httpSession) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        if(user.getUuid() == null)throw new RuntimeException("uuid丢失");
        String url = "http://login.wx2.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=" + user.getUuid() +"&tip=0&r=-2110777326&_="+getTime();
        return httpGet(user,url);
    }

    @RequestMapping("/listenerMsg")
    public String listenerMsg(HttpSession httpSession) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        if(user.getSkey() == null)throw new RuntimeException("skey丢失");
        if(user.getSyncKeys() == null)throw new RuntimeException("SyncKey is empty");
        String url = "http://webpush.wx2.qq.com/cgi-bin/mmwebwx-bin/synccheck";
        String urlParam = "?r="+getTime()+"&skey="+user.getSkey()+"&sid="+user.getWxsid()+"&uin="+user.getWxuin()+"&synckey=";
        for(SyncKey syncKey : user.getSyncKeys()){
            urlParam += (syncKey.getKey()+"_"+syncKey.getVal()+"|");
        }
        url += URLEncoder.encode(urlParam,"UTF-8");
        return httpGet(user,url);
    }

    @RequestMapping("/headImg")
    public void headImg(HttpSession httpSession,HttpServletResponse response,String headImg) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        if(user.getSkey() == null)throw new RuntimeException("skey丢失");
        byte[] rbyte = httpGetByte(user,"http://wx2.qq.com"+ new String(new BASE64Decoder().decodeBuffer(headImg),"UTF-8"));
        if(rbyte.length == 0)return;
        writeStream(response,rbyte);
    }

    @RequestMapping("/logout")
    public void headImg(HttpSession httpSession) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        String url = "http://login.wx.qq.com/logout";
        httpGet(user,url);
        UserUtils.clear(httpSession);
    }

    @RequestMapping("/listenerConfim")
    public String listenerConfim(HttpSession httpSession) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        if(user.getUuid() == null)throw new RuntimeException("uuid丢失");
        String url = "http://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=" + user.getUuid() + "&tip=0&r=-2110777326&_="+getTime();
        String rbody = httpGet(user,url);
        if(rbody.indexOf("https")>0)
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
        Map<String,String> param = new HashMap<>();
        param.put("Uin",user.getWxuin());
        param.put("Sid",user.getWxsid());
        param.put("Skey",user.getSkey());
        Map<String,Object> BaseRequest = new HashMap<>();
        BaseRequest.put("BaseRequest",param);
        StringRequestEntity requestEntity = new StringRequestEntity(objectMapper.writeValueAsString(BaseRequest),"application/json;charset=UTF-8","UTF-8");
        String rbody = WeixinClient.httpPost(user,requestEntity,url);
        return rbody;
    }

    @RequestMapping("/webwxinit")
    public String init(HttpSession httpSession) throws IOException {
        User user = UserUtils.getCurrent(httpSession);
        if(user.getSkey() == null)throw new RuntimeException("skey丢失");
        String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=1860846903&pass_ticket="+user.getPass_ticket();
        Map<String,String> param = new HashMap<>();
        param.put("Uin",user.getWxuin());
        param.put("Sid",user.getWxsid());
        Map<String,Object> BaseRequest = new HashMap<>();
        BaseRequest.put("BaseRequest",param);
        StringRequestEntity requestEntity = new StringRequestEntity(objectMapper.writeValueAsString(BaseRequest),"application/json;charset=UTF-8","UTF-8");
        String rbody = WeixinClient.httpPost(user,requestEntity,url);

        Map<String,Object> rmap = objectMapper.readValue(rbody,Map.class);
        user.setConcat(objectMapper.readValue(objectMapper.writeValueAsString(rmap.get("User")),Concat.class));
        List<SyncKey> syncKeys = objectMapper.readValue(objectMapper.writeValueAsString(((Map) rmap.get("SyncKey")).get("List")), new TypeReference<List<SyncKey>>() {});
        user.setSyncKeys(syncKeys);
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
