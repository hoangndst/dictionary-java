package translator.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * author: hoangndst
 */

public class TranslateAPI {
  public static HttpURLConnection connection;
  private String sourceWord;
  private String sourceLang;
  private String targetLang;

  /**
   * Constructor.
   * 
   * @param sourceWord the word to be translated
   * @param sourceLang the language of the source word
   * @param targetLang the language of the target word
   */

  TranslateAPI(String sourceWord, String sourceLang, String targetLang) {
    this.sourceWord = sourceWord;
    this.sourceLang = sourceLang;
    this.targetLang = targetLang;
  }

  /**
   * Make url to call API.
   * 
   * @return the url to call API
   * @throws UnsupportedEncodingException
   */

  private String makeURL() throws UnsupportedEncodingException {
    String url =
        "https://script.google.com/macros/s/AKfycbz_XszfZNwwc3GsvDOXe8nPrT9dZ4MyZCy5ujcly6Ighr8rmTyMOD0jsYjRUTSllz4WMw/exec"
            + "?q=" + URLEncoder.encode(sourceWord, "UTF-8") + "&target=" + targetLang + "&source="
            + sourceLang;
    return url;
  }

  /**
   * Get JSON object from API.
   * 
   * @return the JSON object from API
   */

  public JSONObject getJsonObject() {
    String outString = "";
    BufferedReader reader = null;
    String line;
    StringBuffer response = new StringBuffer();
    try {
      URL url = new URL(makeURL());
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(5000);
      int responseCode = connection.getResponseCode();
      if (responseCode > 299) {
        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
        reader.close();
      } else {
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
        reader.close();
        outString = response.toString();
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      connection.disconnect();
    }
    JSONObject jsonObject = new JSONObject(outString);
    return jsonObject;
  }

  public static void main(String[] args) {
    TranslateAPI translateAPI = new TranslateAPI("hello", "en", "vi");
    JSONObject jsonObject = translateAPI.getJsonObject();
    List<String> example = new ArrayList<String>();
    JSONArray exampleArray = jsonObject.getJSONArray("example");
    if (exampleArray.length() > 0) {
      for (int i = 0; i < exampleArray.length(); i++) {
        example.add(exampleArray.getString(i));
      }
    }
    System.out.println(example.get(0));
  }

}
