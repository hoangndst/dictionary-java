package translator.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class DictionaryManagement {

  private Dictionary dictionary = new Dictionary();

  public Dictionary getDictionary() {
    return dictionary;
  }

  public void insertFromCommandLine() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Nhap so luong tu can them vao tu dien: ");
    int numsOfWords = Integer.parseInt(scanner.nextLine());
    while (numsOfWords > 0) {
      System.out.print("Nhap tu tieng Anh: ");
      String wordSource = scanner.nextLine();
      System.out.print("Nhap nghia cua tu: ");
      String wordTarget = scanner.nextLine();
      Word newWord = new Word(wordSource, wordTarget);
      dictionary.insert(newWord);
      numsOfWords--;
    }
    scanner.close();
  }

  public void insertFromFile() throws IOException {
    String path = new File("").getAbsolutePath() + "\\src\\main\\resources\\data\\dictionaries.txt";
    BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(new FileInputStream(path)));
    String wordSource = bufferedReader.readLine();
    String wordTarget = bufferedReader.readLine();
    while (wordSource != null && wordTarget != null) {
      Word newWord = new Word(wordSource, wordTarget);
      dictionary.insert(newWord);
      wordSource = bufferedReader.readLine();
      wordTarget = bufferedReader.readLine();
    }
  }

  public void dictionaryLookup() {
    System.out.println("1. Tim kiem Anh - Viet\t\t\t2. Tim kiem Viet - Anh");
    Scanner scanner = new Scanner(System.in);
    System.out.print("Nhap lua chon: ");
    int choose = Integer.parseInt(scanner.nextLine());
    switch (choose) {
      case 1: {
        System.out.print("Nhap tu tieng Anh can tim: ");
        String findWord = scanner.nextLine().trim().toLowerCase();
        if (dictionary.getWords().containsKey(findWord)) {
          System.out.println("Nghia cua tu la: " + dictionary.translate(findWord));
        } else {
          System.out.println("Khong tim thay tu!!");
        }
        break;
      }
      case 2: {
        System.out.print("Nhap nghia cua tu can tim: ");
        String findWord = scanner.nextLine().trim().toLowerCase();
        boolean exist = false;
        for (Map.Entry<String, String> e : dictionary.getWords().entrySet()) {
          if (e.getValue().contains(findWord)) {
            exist = true;
            System.out.println("Tu can tim la: " + e.getKey());
            break;
          }
        }
        if (!exist) {
          System.out.println("Khong tim thay tu!!");
        }
        break;
      }
    }
  }

  public void dictionaryEdit() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Nhap tu tieng Anh can chinh sua: ");
    String editWord = scanner.nextLine().trim().toLowerCase();
    if (dictionary.getWords().containsKey(editWord)) {
      System.out.print("Nhap nghia moi cua tu: ");
      String newMeaning = scanner.nextLine();
      dictionary.getWords().replace(editWord, newMeaning);
      System.out.println("Da cap nhat tu dien!!");
    } else {
      System.out.println("Khong tim thay tu!!");
    }
  }

  public void dictionaryRemove() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Nhap tu tieng Anh can xoa: ");
    String removeWord = scanner.nextLine().trim().toLowerCase();
    if (dictionary.getWords().containsKey(removeWord)) {
      dictionary.removeWord(removeWord);
      System.out.println("Da cap nhat tu dien!!");
    } else {
      System.out.println("Khong tim thay tu!!");
    }
  }

  public void dictionaryAdd() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Nhap tu tieng Anh can them: ");
    String addWord = scanner.nextLine().trim().toLowerCase();
    System.out.print("Nhap nghia cua tu: ");
    String meaning = scanner.nextLine().trim().toLowerCase();
    Word newWord = new Word(addWord, meaning);
    dictionary.insert(newWord);
    System.out.println("Da cap nhat tu dien!!");
  }

  public void dictionaryExportToFile() throws IOException {
    String path =
        new File("").getAbsolutePath() + "\\src\\main\\resources\\data\\dictionaryModified.txt";;
    BufferedWriter bw = new BufferedWriter(new FileWriter(path));
    Date date = java.util.Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    String strDate = dateFormat.format(date);
    bw.write("Dictionary was exported to file at: ");
    bw.write(strDate);
    bw.newLine();
    for (Map.Entry<String, String> e : dictionary.getWords().entrySet()) {
      bw.write(e.getKey());
      bw.newLine();
      bw.write(e.getValue());
    }
  }
}