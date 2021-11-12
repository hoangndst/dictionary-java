package translator.Models;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Support {

    /**
     * Open link in default browser
     * @param link link to open
     */

    public static void OpenLink(String link) {
        try {
            Desktop.getDesktop().browse(new URL(link).toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Word> BinarySearch(ArrayList<Word> words, String word) {
        ArrayList<Word> result = new ArrayList<>();
        int left = 0;
        int right = words.size() - 1;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (words.get(mid).getSourceWord().startsWith(word)) {
                result.add(words.get(mid));
                left = mid + 1;
            } else if (words.get(mid).getSourceWord().compareTo(word) < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }
}