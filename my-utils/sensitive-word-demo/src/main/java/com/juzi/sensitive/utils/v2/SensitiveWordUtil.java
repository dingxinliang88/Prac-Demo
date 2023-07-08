package com.juzi.sensitive.utils.v2;


import java.util.*;

/**
 * 敏感词工具类（使用 Trie 树）
 *
 * @author codejuzi
 */
public class SensitiveWordUtil {

    private static final TrieNode root = new TrieNode();

    /**
     * 初始化敏感词词典
     *
     * @param words 敏感词库
     */
    public static void initDictionary(Collection<String> words) {
        if (Objects.isNull(words)) {
            throw new IllegalArgumentException("敏感词库不能为空");
        }
        for (String word : words) {
            insertWord(word);
        }
    }

    /**
     * 向 Trie 树中插入敏感词
     *
     * @param word 敏感词
     */
    private static void insertWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.computeIfAbsent(c, k -> new TrieNode());
            node = node.children.get(c);
        }
        node.isEnd = true;
    }

    /**
     * 搜索文本中匹配的敏感词
     *
     * @param text 文本
     * @return 匹配到的敏感词列表
     */
    public static List<String> matchWords(String text) {
        List<String> sensitiveWords = new ArrayList<>();
        int len = text.length();
        for (int i = 0; i < len; i++) {
            int wordLen = checkWord(text, i);
            if (wordLen > 0) {
                String word = text.substring(i, i + wordLen);
                sensitiveWords.add(word);
                i += wordLen - 1;
            }
        }
        return sensitiveWords;
    }

    /**
     * 在指定位置开始，检查文本中是否存在敏感词
     *
     * @param text     文本
     * @param startPos 检查起始位置
     * @return 匹配到的敏感词的长度，未匹配到返回0
     */
    private static int checkWord(String text, int startPos) {
        TrieNode node = root;
        int len = text.length();
        for (int i = startPos; i < len; i++) {
            char c = text.charAt(i);
            node = node.children.get(c);
            if (Objects.isNull(node)) {
                break;
            }
            if (node.isEnd) {
                return i - startPos + 1;
            }
        }
        return 0;
    }

    /**
     * Trie 树节点
     */
    private static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEnd;

        TrieNode() {
            children = new HashMap<>();
            isEnd = false;
        }
    }
}
