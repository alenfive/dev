package com.freedom.weixin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: mihuajun 【kobe96688@126.com】
 * @Date: 1/18/2017 5:31 PM
 */

public class Concat implements Serializable {
    @JsonProperty(value = "Uin")
    private Integer Uin;
    @JsonProperty(value = "UserName")
    private String UserName;
    @JsonProperty(value = "NickName")
    private String NickName;
    @JsonProperty(value = "HeadImgUrl")
    private String HeadImgUrl;
    @JsonProperty(value = "ContactFlag")
    private Integer ContactFlag;
    @JsonProperty(value = "MemberCount")
    private Integer MemberCount;
    @JsonProperty(value = "MemberList")
    private List<Concat> MemberList;
    @JsonProperty(value = "RemarkName")
    private String RemarkName;
    @JsonProperty(value = "HideInputBarFlag")
    private Integer HideInputBarFlag;
    @JsonProperty(value = "Sex")
    private Integer Sex;
    @JsonProperty(value = "Signature")
    private String Signature;
    @JsonProperty(value = "VerifyFlag")
    private Integer VerifyFlag;
    @JsonProperty(value = "OwnerUin")
    private Integer OwnerUin;
    @JsonProperty(value = "PYInitial")
    private String PYInitial;
    @JsonProperty(value = "PYQuanPin")
    private String PYQuanPin;
    @JsonProperty(value = "RemarkPYInitial")
    private String RemarkPYInitial;
    @JsonProperty(value = "RemarkPYQuanPin")
    private String RemarkPYQuanPin;
    @JsonProperty(value = "StarFriend")
    private Integer StarFriend;
    @JsonProperty(value = "AppAccountFlag")
    private Integer AppAccountFlag;
    @JsonProperty(value = "Statues")
    private Integer Statues;
    @JsonProperty(value = "AttrStatus")
    private Long AttrStatus;
    @JsonProperty(value = "Province")
    private String Province;
    @JsonProperty(value = "City")
    private String City;
    @JsonProperty(value = "Alias")
    private String Alias;
    @JsonProperty(value = "SnsFlag")
    private Integer SnsFlag;
    @JsonProperty(value = "UniFriend")
    private Integer UniFriend;
    @JsonProperty(value = "DisplayName")
    private String DisplayName;
    @JsonProperty(value = "ChatRoomId")
    private Integer ChatRoomId;
    @JsonProperty(value = "KeyWord")
    private String KeyWord;
    @JsonProperty(value = "EncryChatRoomId")
    private String EncryChatRoomId;
    @JsonProperty(value = "IsOwner")
    private String IsOwner;

    public Integer getUin() {
        return Uin;
    }

    public void setUin(Integer uin) {
        Uin = uin;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getHeadImgUrl() {
        return HeadImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        HeadImgUrl = headImgUrl;
    }

    public Integer getContactFlag() {
        return ContactFlag;
    }

    public void setContactFlag(Integer contactFlag) {
        ContactFlag = contactFlag;
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

    public String getRemarkName() {
        return RemarkName;
    }

    public void setRemarkName(String remarkName) {
        RemarkName = remarkName;
    }

    public Integer getHideInputBarFlag() {
        return HideInputBarFlag;
    }

    public void setHideInputBarFlag(Integer hideInputBarFlag) {
        HideInputBarFlag = hideInputBarFlag;
    }

    public Integer getSex() {
        return Sex;
    }

    public void setSex(Integer sex) {
        Sex = sex;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public Integer getVerifyFlag() {
        return VerifyFlag;
    }

    public void setVerifyFlag(Integer verifyFlag) {
        VerifyFlag = verifyFlag;
    }

    public Integer getOwnerUin() {
        return OwnerUin;
    }

    public void setOwnerUin(Integer ownerUin) {
        OwnerUin = ownerUin;
    }

    public String getPYInitial() {
        return PYInitial;
    }

    public void setPYInitial(String PYInitial) {
        this.PYInitial = PYInitial;
    }

    public String getPYQuanPin() {
        return PYQuanPin;
    }

    public void setPYQuanPin(String PYQuanPin) {
        this.PYQuanPin = PYQuanPin;
    }

    public String getRemarkPYInitial() {
        return RemarkPYInitial;
    }

    public void setRemarkPYInitial(String remarkPYInitial) {
        RemarkPYInitial = remarkPYInitial;
    }

    public String getRemarkPYQuanPin() {
        return RemarkPYQuanPin;
    }

    public void setRemarkPYQuanPin(String remarkPYQuanPin) {
        RemarkPYQuanPin = remarkPYQuanPin;
    }

    public Integer getStarFriend() {
        return StarFriend;
    }

    public void setStarFriend(Integer starFriend) {
        StarFriend = starFriend;
    }

    public Integer getAppAccountFlag() {
        return AppAccountFlag;
    }

    public void setAppAccountFlag(Integer appAccountFlag) {
        AppAccountFlag = appAccountFlag;
    }

    public Integer getStatues() {
        return Statues;
    }

    public void setStatues(Integer statues) {
        Statues = statues;
    }

    public Long getAttrStatus() {
        return AttrStatus;
    }

    public void setAttrStatus(Long attrStatus) {
        AttrStatus = attrStatus;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public Integer getSnsFlag() {
        return SnsFlag;
    }

    public void setSnsFlag(Integer snsFlag) {
        SnsFlag = snsFlag;
    }

    public Integer getUniFriend() {
        return UniFriend;
    }

    public void setUniFriend(Integer uniFriend) {
        UniFriend = uniFriend;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public Integer getChatRoomId() {
        return ChatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
        ChatRoomId = chatRoomId;
    }

    public String getKeyWord() {
        return KeyWord;
    }

    public void setKeyWord(String keyWord) {
        KeyWord = keyWord;
    }

    public String getEncryChatRoomId() {
        return EncryChatRoomId;
    }

    public void setEncryChatRoomId(String encryChatRoomId) {
        EncryChatRoomId = encryChatRoomId;
    }

    public String getIsOwner() {
        return IsOwner;
    }

    public void setIsOwner(String isOwner) {
        IsOwner = isOwner;
    }
}
