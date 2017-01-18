package com.freedom.weixin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.httpclient.HttpClient;

import java.util.List;

/**
 * @Author: mihuajun 【kobe96688@126.com】
 * @Date: 1/17/2017 4:44 PM
 */

public class User {
    @JsonIgnore
    private HttpClient httpClient = new HttpClient();
    private String uuid;
    private String ticketUrl;
    private String skey;
    private String wxsid;
    private String wxuin;
    private String pass_ticket;
    private List<Concat> concats;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getWxsid() {
        return wxsid;
    }

    public void setWxsid(String wxsid) {
        this.wxsid = wxsid;
    }

    public String getWxuin() {
        return wxuin;
    }

    public void setWxuin(String wxuin) {
        this.wxuin = wxuin;
    }

    public String getPass_ticket() {
        return pass_ticket;
    }

    public void setPass_ticket(String pass_ticket) {
        this.pass_ticket = pass_ticket;
    }

    public List<Concat> getConcats() {
        return concats;
    }

    public void setConcats(List<Concat> concats) {
        this.concats = concats;
    }
}
