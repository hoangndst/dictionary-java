package com.translator.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    int numsOfWords = scanner.nextInt();
    while (numsOfWords > 0) {
      String wordSource = scanner.nextLine();
      String wordTarget = scanner.nextLine();
      Word newWord = new Word(wordSource.toLowerCase(), wordTarget);
      dictionary.insert(newWord);
      numsOfWords--;
    }
    scanner.close();
  }

  public void insertFromFile() throws IOException {
    String path = "data.txt";
    BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(new FileInputStream(path)));
    String line1 = bufferedReader.readLine();
    String line2 = bufferedReader.readLine();
    while (line1 != null && line2 != null) {
      String wordSource = line1;
      String wordTarget = line2;
      Word newWord = new Word(wordSource.toLowerCase(), wordTarget);
      for (Map.Entry<String, String> e : dictionary.getWords().entrySet()) {
        if (e.getKey() != wordSource) {
          dictionary.insert(newWord);
        }
      }
      line1 = bufferedReader.readLine();
      line2 = bufferedReader.readLine();
    }
    bufferedReader.close();
  }

  public void dictionaryLookup() {
    System.out.println("1. Tra nghia tieng Viet\t\t\t2. Tim tu tieng Anh");
    Scanner scanner = new Scanner(System.in);
    boolean exist = false;
    int choose = scanner.nextInt();
    switch (choose) {
      case 1: {
        System.out.print("Nhap tu tieng Anh can tim: ");
        String findWord = scanner.nextLine();
        for (Map.Entry<String, String> e : dictionary.getWords().entrySet()) {
          if (e.getKey() == findWord) {
            exist = true;
            System.out.println("Nghia cua tu la: " + e.getValue());
          }
        }
        scanner.close();
        if (!exist) {
          System.out.println("Khong tim thay tu!!");
        }
      }
      case 2: {
        System.out.print("Nhap nghia cua tu can tim: ");
        String findWord = scanner.nextLine();
        for (Map.Entry<String, String> e : dictionary.getWords().entrySet()) {
          if (e.getValue().contains(findWord)) {
            exist = true;
            System.out.println("Tu can tim la: " + e.getValue());
            break;
          }
        }
        scanner.close();
        if (!exist) {
          System.out.println("Khong tim thay tu!!");
        }
      }
    }
  }

  public void dictionaryEdit() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Nhap tu tieng Anh can cap nhat: ");
    String editWord = scanner.nextLine();
    boolean exist = false;
    for (Map.Entry<String, String> e : dictionary.getWords().entrySet()) {
      if (e.getKey() == editWord) {
        exist = true;
        System.out.print("Nhap nghia moi cua tu: ");
        String newMeaning = scanner.nextLine();
        e.setValue(newMeaning);
        System.out.println("Da cap nhat tu dien!!");
      }
    }
    scanner.close();
    if (!exist) {
      System.out.println("Khong tim thay tu!!");
    }
  }

  public void dictionaryExportToFile() throws IOException {
    String path = "";
    BufferedWriter bw = new BufferedWriter(new FileWriter(path));
    Date date = java.util.Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    String strDate = dateFormat.format(date);
    bw.write(strDate);
    bw.newLine();
    for (Map.Entry<String, String> e : dictionary.getWords().entrySet()) {
      bw.write(e.getKey());
      bw.newLine();
      bw.write(e.getValue());
    }
    bw.close();
  }
}
