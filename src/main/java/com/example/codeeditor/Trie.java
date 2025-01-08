package com.example.codeeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public Trie(String... words) {
        root = new TrieNode();
        for (String word: words) {
            insert(word);
        }
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char c: word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    public List<String> suggestions(String prefix) {
        TrieNode current = root;
        for (char c: prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return new ArrayList<>();
            }
            current = current.children.get(c);
        }
        return collectAllWords(current, prefix);
    }

    private List<String> collectAllWords(TrieNode node, String prefix) {
        List<String> words = new ArrayList<>();
        if (node.isEndOfWord) {
            words.add(prefix);
        }
        for (Map.Entry<Character, TrieNode> entry: node.children.entrySet()) {
            words.addAll(collectAllWords(entry.getValue(), prefix + entry.getKey()));
        }
        return words;
    }
}
