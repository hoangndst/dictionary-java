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
      word.setPronounce("");
    }
    try {
      JSONArray typeArray = jsonObject.getJSONArray("type");
      if (typeArray.length() > 0) {
        word.setType(typeArray.get(0).toString());
      } else {
        word.setType("");
      }
    } catch (Exception e) {
      word.setType("");
    }
    try {
      JSONArray definitionArray = jsonObject.getJSONArray("definition");
      if (definitionArray.length() > 0) {
        word.setDefinition(definitionArray.get(0).toString());
      } else {
        word.setDefinition("");
      }
    } catch (Exception e) {
      word.setDefinition("");
    }

    try {
      JSONArray exampleArray = jsonObject.getJSONArray("example");
      if (exampleArray.length() > 0) {
        word.setExample(exampleArray.get(0).toString());
      } else {
        word.setExample("");
      }
    } catch (Exception e) {
      word.setExample("");
    }

    try {
      JSONArray synonymsArray = jsonObject.getJSONArray("synonyms");
      if (synonymsArray.length() > 0) {
        JSONArray synonyms = synonymsArray.getJSONArray(0);
        String sysStr = "";
        for (int i = 0; i < synonyms.length(); i++) {
          if (i == synonyms.length() - 1) {
            sysStr += synonyms.get(i).toString() + ".";
          } else {
            sysStr += synonyms.get(i).toString() + ", ";
          }
        }
        word.setSynonyms(sysStr);
      } else {
        word.setSynonyms("");
      }
    } catch (Exception e) {
      word.setSynonyms("");
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
    Translate word = new Translate("play", "", "vi");
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
