package com.juzi.sensitive.utils.v2;


import java.util.ArrayList;
import java.util.List;

/**
 * @author codejuzi
 */
public class SensitiveWordUtilTest {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("私人侦探");
        list.add("信用卡提现");
        list.add("广告代理");

        //初始化敏感词库
        SensitiveWordUtil.initDictionary(list);

        long start = System.currentTimeMillis();
        String content = "江户川柯南私人侦探，可以帮你解决：商务调查，要账清债，企业打假，寻人找人，财产调查，私人调查，电话：12345678901";
        //文本中查找是否包含敏感词
        List<String> sensitiveWords = SensitiveWordUtil.matchWords(content);
        if (sensitiveWords.size() > 0) {
            System.out.println(sensitiveWords);
        } else {
            System.out.println("没有找到敏感词");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start); // 0
    }
}