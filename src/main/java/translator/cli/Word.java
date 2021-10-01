package translator.cli;

import java.util.ArrayList;
import java.util.List;

public class Word {
    private String sourceWord;
    private String targetWord;
    private String audio;
    private String pronounce;
    private List<String> type = new ArrayList<String>();
    private List<String> definition = new ArrayList<String>();
    private List<String> example = new ArrayList<String>();
    private List<List<String>> synonyms = new ArrayList<List<String>>();

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

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getDefinition() {
        return definition;
    }

    public void setDefinition(List<String> definition) {
        this.definition = definition;
    }

    public List<String> getExample() {
        return example;
    }

    public void setExample(List<String> example) {
        this.example = example;
    }

    public List<List<String>> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<List<String>> synonyms) {
        this.synonyms = synonyms;
    }
}