package com.translator.cli;

import java.util.HashMap;

public class Dictionary {
    private HashMap<String, String> words = new HashMap<String, String>();

    public HashMap<String, String> getWords() {
        return words;
    }

    /**
     * Inserts a word into the dictionary.
     * @param word the word to be inserted
     */

    public void insert(Word word) {
        words.put(word.getSourceWord(), word.getTargetWord());
    }

    /**
     * Removes a word from the dictionary.
     * @param sourceWord
     */

    public void removeWord(String sourceWord) {
        words.remove(sourceWord.toLowerCase());
    }
    
    /**
     * Translates a word from the dictionary.
     * @param sourceWord
     * @return the translated word
     */

    public String translate(String sourceWord) {
        return words.get(sourceWord.toLowerCase());
    }
}
