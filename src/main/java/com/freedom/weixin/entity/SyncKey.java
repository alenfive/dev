package com.freedom.weixin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @Author: mihuajun 【kobe96688@126.com】
 * @Date: 1/19/2017 4:40 PM
 */

public class SyncKey implements Serializable{
    @JsonProperty(value = "Key")
    private String Key;
    @JsonProperty(value = "Val")
    private Long Val;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public Long getVal() {
        return Val;
    }

    public void setVal(Long val) {
        Val = val;
    }
}
