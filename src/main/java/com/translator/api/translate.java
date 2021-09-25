package com.translator.api;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class translate {

    private String targetWord;
    private String audio;
    private String pronounce;
    private List<String> type = new ArrayList<String>();
    private List<String> definition = new ArrayList<String>();
    private List<String> example = new ArrayList<String>();
    private List<List<String>> synonyms = new ArrayList<List<String>>();

    /**
     * Translate source word.
     * @param sourceWord the word to be translated
     * @param sourceLang the language of the source word
     * @param targetLang the language of the target word
     */

    public void translateWord(String sourceWord, String sourceLang, String targetLang) {
        translateAPI api = new translateAPI(sourceWord, sourceLang, targetLang);
        JSONObject jsonObject = api.getJsonObject();
        this.targetWord = jsonObject.get("targetWord").toString();
        this.audio = jsonObject.get("audio").toString();
        this.pronounce = jsonObject.get("pronounce").toString();

        JSONArray typeArray = jsonObject.getJSONArray("type");
        if (typeArray.length() > 0) {
            for (int i = 0; i < typeArray.length(); i++) {
                this.type.add(typeArray.get(i).toString());
            }
        }
        JSONArray definitionArray = jsonObject.getJSONArray("definition");
        if (definitionArray.length() > 0) {
            for (int i = 0; i < definitionArray.length(); i++) {
                this.definition.add(definitionArray.get(i).toString());
            }
        }
        JSONArray exampleArray = jsonObject.getJSONArray("example");
        if (exampleArray instanceof JSONArray) {
            if (exampleArray.length() > 0) {
                for (int i = 0; i < exampleArray.length(); i++) {
                    this.example.add(exampleArray.get(i).toString());
                }
            }
        }
        JSONArray synonymsArray = jsonObject.getJSONArray("synonyms");
        if (synonymsArray.length() > 0) {
            for (int i = 0; i < synonymsArray.length(); i++) {
                JSONArray synonymsArray2 = synonymsArray.getJSONArray(i);
                List<String> synonymsList = new ArrayList<>();
                for (int j = 0; j < synonymsArray2.length(); j++) {
                    synonymsList.add(synonymsArray2.get(j).toString());
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
        translate word = new translate();
        word.translateWord("cat", "en", "vi");
        System.out.println(word.getTargetWord());
        System.out.println(word.getAudio());
        System.out.println(word.getPronounce());
        System.out.println(word.getType());
        System.out.println(word.getDefinition());
        System.out.println(word.getExample());
        System.out.println(word.getSynonyms());
    }
}