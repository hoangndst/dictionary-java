package translator.Models;

import java.util.ArrayList;
import java.util.List;

public class Word {

    private String time;
    private String sourceWord;
    private String targetWord;
    private String audio;
    private String pronounce;
    private String type;
    private String definition;
    private String example;
    private String synonyms;
    private String targetLang;

    /**
     * Constructor 1.
     * 
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

    /**
     * Constructor 3.
     * 
     * @param sourceWord source word
     * @param targetWord target word
     * @param audio audio
     * @param pronounce pronounce
     * @param type type
     * @param definition definition
     * @param example example
     * @param synonyms synonyms
     */

    public Word(String time, String sourceWord, String targetWord, String audio, String pronounce, String type, String definition, String example, String synonyms, String targetLang) {
        this.time = time;
        this.sourceWord = sourceWord.trim().toLowerCase();
        this.targetWord = targetWord.trim().toLowerCase();
        this.audio = audio;
        this.pronounce = pronounce;
        this.type = type;
        this.definition = definition;
        this.example = example;
        this.synonyms = synonyms;
        this.targetLang = targetLang;
    }


    /**
     * Getter for target language.
     * @return target language
     */
    public String getTargetLang() {
        return targetLang;
    }

    /**
     * Getter for time.
     * @return time
     */

    public String getTime() {
        return time;
    }

    /**
     * Setter for time.
     * @param time time
     */

    public void setTime(String time) {
        this.time = time;
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
     * 
     * @param sourceWord source word
     */

    public void setSourceWord(String sourceWord) {
        this.sourceWord = sourceWord;
    }

    /**
     * Get target word.
     * 
     * @return target word
     */

    public String getTargetWord() {
        return targetWord;
    }

    /**
     * Set target word.
     * 
     * @param targetWord target word
     */

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    /**
     * Get audio URL.
     * 
     * @return audio URL
     */

    public String getAudio() {
        return audio;
    }

    /**
     * Set audio URL.
     * 
     * @param audio audio URL
     */

    public void setAudio(String audio) {
        this.audio = audio;
    }

    /**
     * Get pronounce.
     * 
     * @return pronounce
     */

    public String getPronounce() {
        return pronounce;
    }

    /**
     * Set pronounce.
     * 
     * @param pronounce pronounce
     */

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    /**
     * Get type.
     * 
     * @return type
     */

    public String getType() {
        return type;
    }

    /**
     * Set type.
     * 
     * @param type type
     */

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get definition.
     * 
     * @return definition
     */

    public String getDefinition() {
        return definition;
    }

    /**
     * Set definition.
     * 
     * @param definition definition
     */

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     * Get example.
     * 
     * @return example
     */

    public String getExample() {
        return example;
    }

    /**
     * Set example.
     * 
     * @param example example
     */

    public void setExample(String example) {
        this.example = example;
    }

    /**
     * Get synonyms.
     * 
     * @return synonyms
     */

    public String getSynonyms() {
        return synonyms;
    }

    /**
     * Set synonyms.
     * 
     * @param synonyms synonyms
     */

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    /**
     * String representation of Word.
     * 
     * @return string representation of Word
     */

    public String getString() {
        String result = "";
        try {
            if (!this.getSourceWord().equals("")) {
                if (!this.type.equals("") && !this.type.equals(null)) {
                    result += "Type: " + this.type + ".\n\n";
                } 
                if (!this.pronounce.equals("") && !this.pronounce.equals(null)) {
                    result += "Pronounce: /" + this.pronounce + "/\n\n";
                }
                if (!this.definition.equals("") && !this.definition.equals(null)) {
                    result += "Definition: " + this.definition + "\n\n";
                }
                if (!this.example.equals("") && !this.example.equals(null)) {
                    result += "Example: " + this.example + "\n\n";
                }
                if (!this.synonyms.equals("") && !this.synonyms.equals(null)) {
                    result += "Synonyms: " + this.synonyms + "\n\n";
                }
            }
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

}
