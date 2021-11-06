package translator.api;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import translator.Models.Word;

public class Translate {

  private Word word = new Word();
  private String sourceWord;
  private String sourceLang;
  private String targetLang;

  /**
   * Constructor.
   * 
   * @param sourceWord the word to translate.
   * @param sourceLang the source language.
   * @param targetLang the target language.
   */

  public Translate(String sourceWord, String sourceLang, String targetLang) {
    this.sourceWord = sourceWord;
    this.sourceLang = sourceLang;
    this.targetLang = targetLang;
  }

  /**
   * Translate source word.
   */

  public void translateWord() {
    TranslateAPI api = new TranslateAPI(sourceWord, sourceLang, targetLang);
    word.setSourceWord(sourceWord);
    JSONObject jsonObject = api.getJsonObject();
    try {
      word.setTargetWord(jsonObject.get("targetWord").toString());
    } catch (Exception e) {
      word.setTargetWord("Updating");
    }
    try {
      word.setAudio(jsonObject.get("audio").toString());
    } catch (Exception e) {
      word.setAudio("Updating");
    }
    try {
      word.setPronounce(jsonObject.get("pronounce").toString());
    } catch (Exception e) {
      word.setPronounce("none");
    }
    try {
      JSONArray typeArray = jsonObject.getJSONArray("type");
      List<String> type = new ArrayList<String>();
      if (typeArray.length() > 0) {
        for (int i = 0; i < typeArray.length(); i++) {
          type.add(typeArray.get(i).toString());
        }
      }
      word.setType(type);
    } catch (Exception e) {
      word.setType(null);
    }
    try {

      List<String> definition = new ArrayList<String>();
      JSONArray definitionArray = jsonObject.getJSONArray("definition");
      if (definitionArray.length() > 0) {
        for (int i = 0; i < definitionArray.length(); i++) {
          definition.add(definitionArray.get(i).toString());
        }
      }
      word.setDefinition(definition);
    } catch (Exception e) {
      word.setDefinition(null);
    }

    try {
      List<String> example = new ArrayList<String>();
      JSONArray exampleArray = jsonObject.getJSONArray("example");
      if (exampleArray.length() > 0) {
        for (int i = 0; i < exampleArray.length(); i++) {
          example.add(exampleArray.get(i).toString());
        }
      }
      word.setExample(example);
    } catch (Exception e) {
      word.setExample(null);
    }

    try {
      List<List<String>> synonyms = new ArrayList<List<String>>();
      JSONArray synonymsArray = jsonObject.getJSONArray("synonyms");
      if (synonymsArray.length() > 0) {
        for (int i = 0; i < synonymsArray.length(); i++) {
          JSONArray synonymsArray2 = synonymsArray.getJSONArray(i);
          List<String> synonymsList = new ArrayList<String>();
          for (int j = 0; j < synonymsArray2.length(); j++) {
            synonymsList.add(synonymsArray2.get(j).toString());
          }
          synonyms.add(synonymsList);
        }
      }
      word.setSynonyms(synonyms);
    } catch (Exception e) {
      word.setSynonyms(null);
    }
  }

  /**
   * Get the translated word.
   * 
   * @return the translated word
   */
  public Word getWord() {
    return word;
  }

  public static void main(String[] args) {
    Translate word = new Translate("play football", "", "vi");
    word.translateWord();
    Word word1 = word.getWord();
    System.out.println(word1.getSourceWord());
    System.out.println(word1.getTargetWord());
    System.out.println(word1.getAudio());
    System.out.println(word1.getPronounce());
    System.out.println(word1.getType());
    System.out.println(word1.getDefinition());
    System.out.println(word1.getExample());
    System.out.println(word1.getSynonyms());
  }
}
