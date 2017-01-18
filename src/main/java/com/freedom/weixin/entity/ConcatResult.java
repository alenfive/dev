package com.freedom.weixin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * @Author: mihuajun 【kobe96688@126.com】
 * @Date: 1/18/2017 6:21 PM
 */

public class ConcatResult {
    @JsonProperty(value = "BaseResponse")
    private Map<String,Object> BaseResponse;
    @JsonProperty(value = "MemberCount")
    private Integer MemberCount;
    @JsonProperty(value = "MemberList")
    private List<Concat> MemberList;
    @JsonProperty(value = "Seq")
    private Integer Seq;

    public Map<String, Object> getBaseResponse() {
        return BaseResponse;
    }

    public void setBaseResponse(Map<String, Object> baseResponse) {
        BaseResponse = baseResponse;
    }

    public Integer getMemberCount() {
        return MemberCount;
    }

    public void setMemberCount(Integer memberCount) {
        MemberCount = memberCount;
    }

    public List<Concat> getMemberList() {
        return MemberList;
    }

    public void setMemberList(List<Concat> memberList) {
        MemberList = memberList;
    }

    public Integer getSeq() {
        return Seq;
    }

    public void setSeq(Integer seq) {
        Seq = seq;
    }
}
