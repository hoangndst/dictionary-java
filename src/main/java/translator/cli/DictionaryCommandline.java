package translator.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DictionaryCommandline {
  private DictionaryManagement dictionaryManagement = new DictionaryManagement();

  public void showAllWords() {
    System.out.printf("%-8s| %-20s\t| %s\n", "No", "English", "Vietnamese");
    int x = 0;
    for (Map.Entry<String, String> e : dictionaryManagement.getDictionary().getWords().entrySet()) {
      x++;
      System.out.printf("%-8d| %-20s\t| %s\n", x, e.getKey(), e.getValue());
    }
  }

  public void dictionaryBasic() throws IOException {
    dictionaryManagement.insertFromCommandLine();
    showAllWords();
  }

  public void dictionaryAdvanced() throws IOException {
    dictionaryManagement.insertFromFile();
    showAllWords();
    dictionaryManagement.dictionaryLookup();
  }

  public void dictionarySearcher() {
    System.out.print("Nhap tu can tim bat dau bang: ");
    Scanner scanner = new Scanner(System.in);
    String searchWord = scanner.nextLine().trim().toLowerCase();
    List<String> listWords = new ArrayList<>();
    System.out.println("Danh sach cac tu chua \"" + searchWord + "\" la: ");
    for (Map.Entry<String, String> e : dictionaryManagement.getDictionary().getWords().entrySet()) {
      if (e.getKey().length() > searchWord.length()) {
        String temp = e.getKey().substring(0, searchWord.length());
        if (searchWord.equals(temp)) {
          listWords.add(e.getKey());
        }
      }
    }
    if (!listWords.isEmpty()) {
      System.out.print(listWords);
    } else {
      System.out.print("Khong co tu nao bat dau voi \"" + searchWord + "\"!");
    }
  }
}
