package com.translator.api;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class translate {
    private String sourceWord = "";
    private String sourceLang = "";
    private String targetLang = "";

    private String targetWord;
    private String audio;
    private String pronounce;
    private List<String> type = new ArrayList<String>();
    private List<String> definition = new ArrayList<String>();
    private List<String> example = new ArrayList<String>();
    private List<List<String>> synonyms = new ArrayList<List<String>>();
    
    /**
     * Constructor 1.
     */

    translate() {
        this.sourceWord = "";
        this.sourceLang = "";
        this.targetLang = "";
    }

    /**
     * Constructor 2.
     * @param sourceWord the source word
     * @param sourceLang the source language
     * @param targetLang the target language
     */

    translate(String sourceWord, String sourceLang, String targetLang) {
        this.sourceWord = sourceWord;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    /**
     * Translate the source word.
     */

    public void translateWord() {
        translateAPI api = new translateAPI(sourceWord, sourceLang, targetLang);
        JSONObject jsonObject = api.getJsonObject();
        this.targetWord = jsonObject.get("targetWord").toString();
        this.audio = jsonObject.get("audio").toString();
        this.pronounce = jsonObject.get("pronounce").toString();

        JSONArray typeArray = jsonObject.getJSONArray("type");
        if (typeArray.length() > 0) {
            for (int i = 0; i < typeArray.length(); i++) {
                this.type.add(typeArray.getString(i));
            }
        }
        JSONArray definitionArray = jsonObject.getJSONArray("definition");
        if (definitionArray.length() > 0) {
            for (int i = 0; i < definitionArray.length(); i++) {
                this.definition.add(definitionArray.getString(i));
            }
        }
        JSONArray exampleArray = jsonObject.getJSONArray("example");
        if (exampleArray.length() > 0) {
            for (int i = 0; i < exampleArray.length(); i++) {
                this.example.add(exampleArray.getString(i));
            }
        }
        JSONArray synonymsArray = jsonObject.getJSONArray("synonyms");
        if (synonymsArray.length() > 0) {
            for (int i = 0; i < synonymsArray.length(); i++) {
                JSONArray synonymsArray2 = synonymsArray.getJSONArray(i);
                List<String> synonymsList = new ArrayList<>();
                for (int j = 0; j < synonymsArray2.length(); j++) {
                    synonymsList.add(synonymsArray2.getString(j));
                }
                this.synonyms.add(synonymsList);
            }
        }
    }

    public List<String> getType() {
        return type;
    }

    public List<String> getDefinition() {
        return definition;
    }

    public List<String> getExample() {
        return example;
    }

    public List<List<String>> getSynonyms() {
        return synonyms;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public String getAudio() {
        return audio;
    }

    public String getPronounce() {
        return pronounce;
    }

    public static void main(String[] args) {
        translate translate = new translate("hello", "en", "vi");
        translate.translateWord();
        System.out.println(translate.getAudio());
    }
    
}
