package translator.Models;

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
        this.sourceWord = "null";
        this.targetWord = "null";
    }
    
    /**
     * Get source word.
     * @return source word
     */

    public String getSourceWord() {
        return sourceWord;
    }

    /**
     * Set source word.
     * @param sourceWord source word
     */

    public void setSourceWord(String sourceWord) {
        this.sourceWord = sourceWord;
    }

    /**
     * Get target word.
     * @return target word
     */

    public String getTargetWord() { 
        return targetWord; 
    }

    /**
     * Set target word.
     * @param targetWord target word
     */

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    /**
     * Get audio URL.
     * @return audio URL
     */

    public String getAudio() {
        return audio;
    }

    /**
     * Set audio URL.
     * @param audio audio URL
     */

    public void setAudio(String audio) {
        this.audio = audio;
    }

    /**
     * Get pronounce.
     * @return pronounce
     */

    public String getPronounce() {
        return pronounce;
    }

    /**
     * Set pronounce.
     * @param pronounce pronounce
     */

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    /**
     * Get type.
     * @return type
     */

    public List<String> getType() {
        return type;
    }

    /**
     * Set type.
     * @param type type
     */

    public void setType(List<String> type) {
        this.type = type;
    }

    /**
     * Get definition.
     * @return definition
     */

    public List<String> getDefinition() {
        return definition;
    }

    /**
     * Set definition.
     * @param definition definition
     */

    public void setDefinition(List<String> definition) {
        this.definition = definition;
    }

    /**
     * Get example.
     * @return example
     */

    public List<String> getExample() {
        return example;
    }

    /**
     * Set example.
     * @param example example
     */

    public void setExample(List<String> example) {
        this.example = example;
    }

    /**
     * Get synonyms.
     * @return synonyms
     */

    public List<List<String>> getSynonyms() {
        return synonyms;
    }

    /**
     * Set synonyms.
     * @param synonyms synonyms
     */

    public void setSynonyms(List<List<String>> synonyms) {
        this.synonyms = synonyms;
    }

    public String getString() {
        String result = "";
        try {
            if (!this.type.equals(null)) {
                result += "Type: " + type.get(0) + ".\n\n";
                result += "Pronounce: /" + pronounce + "/\n\n";
                if (!this.definition.get(0).isEmpty()) {
                    result += "Definition: " + definition.get(0) + "\n\n";
                }
                if (!this.example.equals(null)) {
                    String e = example.get(0);
                    if (!e.equals("null")) {
                        result += "Example: " + example.get(0) + ".\n\n";
                    }
                }
                if (!this.synonyms.equals(null)) {
                    String sysString = "";
                    for (int i = 0; i < synonyms.get(0).size(); i++) {
                        if (i == synonyms.get(0).size() - 1) {
                            sysString += synonyms.get(0).get(i) + ".";
                        } else {
                            sysString += synonyms.get(0).get(i) + ", ";
                        }
                    }
                    if (!sysString.equals("")) {
                        result += "Synonyms: " + sysString + "\n";
                    }
                }
            }
        } catch (Exception e) {
            result = "";
        }
        return result;
    }



}