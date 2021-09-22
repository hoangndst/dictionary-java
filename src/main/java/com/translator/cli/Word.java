package com.translator.cli;


public class Word {
    private String sourceWord; 
    private String targetWord;

    /**
     * Constructor 1.
     * @param sourceWord source word
     * @param targetWord target word
     */

    public Word(String sourceWord, String targetWord) {
        this.sourceWord = sourceWord.trim().toLowerCase();
        this.targetWord = targetWord.trim().toLowerCase();
    }

    /**
     * Constructor 2.
     */

    public Word() {
        this.sourceWord = "";
        this.targetWord = "";
    }
    
    public String getSourceWord() {
        return sourceWord;
    }

    public void setSourceWord(String sourceWord) {
        this.sourceWord = sourceWord;
    }

    public String getTargetWord() { 
        return targetWord; 
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }
}
