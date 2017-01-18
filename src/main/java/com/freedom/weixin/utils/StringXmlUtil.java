package com.freedom.weixin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: mihuajun 【kobe96688@126.com】
 * @Date: 1/11/2017 4:31 PM
 */

public class StringXmlUtil {

    //获取标签
    public static ArrayList<String> findTag(StringBuilder source, String tagName){
        Pattern pattern = Pattern.compile("<"+tagName+">((?!</"+tagName+">).)*</"+tagName+">");
        Matcher matcher = pattern.matcher(source);
        ArrayList<String> result = new ArrayList<>();
        while(matcher.find()){
            result.add(matcher.group());
        }
        return result;
    }

    //获取某个标签的内容
    public static String findTagBody(String tag){
        Pattern pattern = Pattern.compile("(?<=>)[^<]*");
        Matcher matcher = pattern.matcher(tag);
        if(matcher.find())return matcher.group();
        return null;
    }

    //获取标签中的属性
    public static String findTagAttr(String tag, String attrName){
        Pattern pattern = Pattern.compile("(?<="+attrName+"=\")[^\"]+");
        Matcher matcher = pattern.matcher(tag);
        ArrayList<String> result = new ArrayList<>();
        if(matcher.find())return matcher.group();
        return null;
    }

    //获取属性中的函数和值
    public static Map<String,String> findAttrFuncValue(String attr){
        Map<String,String> result = new HashMap<>();
        Pattern funcP = Pattern.compile("[^(]*");
        Matcher funcM = funcP.matcher(attr);
        if(funcM.find()) {
            result.put("func",funcM.group());
        }

        Pattern valueP = Pattern.compile("(?<=\\()[^)]*");
        Matcher valueM = valueP.matcher(attr);
        if(valueM.find()){
            result.put("value",valueM.group());
        }
        return result;
    }

}
