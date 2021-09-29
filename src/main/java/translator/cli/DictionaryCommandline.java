package translator.cli;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class DictionaryCommandline {
  private DictionaryManagement dictionaryManagement = new DictionaryManagement();

  public void showAllWords() {
    System.out.println("No\t| " + "English\t\t\t| " + "Vietnamese");
    int x = 0;
    for (Map.Entry<String, String> e : dictionaryManagement.getDictionary().getWords().entrySet()) {
      x++;
      System.out.println(x + "\t| " + e.getKey() + "\t\t\t| " + e.getValue());
    }
    // dictionaryManagement.getDictionary().getWords().entrySet().forEach(e -> {
    // System.out.println(e.getKey() + ": " + e.getValue());
    // });
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
    System.out.println("Nhap tu can tim:");
    Scanner scanner = new Scanner(System.in);
    String searchWord = scanner.nextLine();
    System.out.println("Danh sach cac tu chua \" " + searchWord + "\" la: ");
    for (Map.Entry<String, String> e : dictionaryManagement.getDictionary().getWords().entrySet()) {
      if (e.getValue().contains(searchWord)) {
        System.out.println(e.getValue() + ", ");
      }
    }
    scanner.close();
  }
}
