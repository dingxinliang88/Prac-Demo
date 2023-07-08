package com.juzi.sensitive.utils.v1;

import java.util.*;

/**
 * 敏感词工具类
 *
 * @author codejuzi
 */
public class SensitiveWordUtil {

    private static Map<String, Object> dictionaryMap = new HashMap<>();

    private static final String END_FLAG = "1";
    private static final String UN_END_FLAG = "0";

    /**
     * 初始化词典
     *
     * @param words 敏感词库
     */
    @SuppressWarnings("unchecked")
    public static void initDictionary(Collection<String> words) {
        if (Objects.isNull(words)) {
            throw new IllegalArgumentException("敏感词库不能为空");
        }
        // 初始长度为word.size()，实际长度小于此值，因为不同的词可能会有相同的首字
        Map<String, Object> iterMap = new HashMap<>(words.size());
        // 遍历过程中当前层次的数据
        Map<String, Object> currentMap;

        // 使用迭代器遍历
        for (String word : words) {
            currentMap = iterMap;
            for (int i = 0; i < word.length(); i++) {
                // 遍历每个字符
                String key = String.valueOf(word.charAt(i));
                // 判断当前字符在当前层是否存在
                Map<String, Object> wordMap = (Map<String, Object>) currentMap.get(key);
                // 不存在，新建，当前层数据指向下一个节点，继续判断是否存在
                if (Objects.isNull(wordMap)) {
                    wordMap = new HashMap<>(2);
                    wordMap.put("isEnd", UN_END_FLAG);
                    currentMap.put(key, wordMap);
                }
                // 存在，将当前层次的数据指向下一个节点
                currentMap = wordMap;
                // 当前字符是最后一个词，设置为结束标置
                if (i == word.length() - 1) {
                    currentMap.put("isEnd", END_FLAG);
                }
            }
        }
        dictionaryMap = iterMap;
    }

    /**
     * 搜索文本中某个文字是否匹配敏感词
     *
     * @param text     文本
     * @param beginIdx 开始下标
     * @return 敏感词长度
     */
    @SuppressWarnings("unchecked")
    private static int checkWord(String text, int beginIdx) {
        if (Objects.isNull(dictionaryMap)) {
            throw new IllegalArgumentException("敏感词库未初始化");
        }
        boolean isEnd = false;
        int wordLen = 0;
        Map<String, Object> curMap = dictionaryMap;
        for (int i = beginIdx; i < text.length(); i++) {
            String key = String.valueOf(text.charAt(i));
            curMap = (Map<String, Object>) curMap.get(key);
            if (Objects.isNull(curMap)) {
                break;
            }
            wordLen++;
            if (END_FLAG.equals(curMap.get("isEnd"))) {
                isEnd = true;
            }
        }
        return isEnd ? wordLen : 0;
    }

    /**
     * 获取匹配的关键词和命中次数
     *
     * @param text 文本
     * @return 关键词 - 命中次数 Map
     */
    public static Map<String, Integer> matchWords(String text) {
        Map<String, Integer> wordMap = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            int wordLen = checkWord(text, i);
            if (wordLen > 0) {
                String word = text.substring(i, i + wordLen);
                // 添加关键词匹配次数
                wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);

                i += wordLen - 1;
            }
        }
        return wordMap;
    }
}
