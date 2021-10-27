package translator.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;

public class DictionaryManagement {

  public static void insertFromCommandLine(Dictionary dict) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the number of words adding to dictionary: ");
    int numsOfWords = Integer.parseInt(scanner.nextLine());
    while (numsOfWords > 0) {
      System.out.print("Enter the word adding to dictionary: ");
      String wordSource = scanner.nextLine();
      System.out.print("Enter the meaning: ");
      String wordTarget = scanner.nextLine();
      Word newWord = new Word(wordSource, wordTarget);
      dict.insert(newWord);
      numsOfWords--;
    }
  }

  public static void insertFromFile(Dictionary dict) {
    String path = new File("").getAbsolutePath() + "\\src\\main\\resources\\data\\dictionaries.txt";
    try {
      BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(new FileInputStream(path)));
      String wordSource = bufferedReader.readLine();
      String wordTarget = bufferedReader.readLine();
      while (wordSource != null && wordTarget != null) {
        Word newWord = new Word(wordSource, wordTarget);
        dict.insert(newWord);
        wordSource = bufferedReader.readLine();
        wordTarget = bufferedReader.readLine();
      }
      bufferedReader.close();
    } catch (FileNotFoundException fnfe) {
      System.out.println("The specified file not found" + fnfe);
    } catch (IOException ioe) {
      System.out.println("I/O Exception: " + ioe);
    }
  }

  public static void dictionaryLookup(Dictionary dict) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the word to translate: ");
    String findWord = scanner.nextLine().trim().toLowerCase();
    if (dict.getWords().containsKey(findWord)) {
      System.out.println("The meaning is:" + dict.translate(findWord));
    } else {
      System.out.println("Couldn't find the word in the dictionary!!");
    }
  }

  public static void dictionaryEdit(Dictionary dict) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the word in dictionary to edit: ");
    String editWord = scanner.nextLine().trim().toLowerCase();
    if (dict.getWords().containsKey(editWord)) {
      System.out.print("Enter the new meaning: ");
      String newMeaning = scanner.nextLine();
      dict.getWords().replace(editWord, newMeaning);
      System.out.println("The dictionary has been updated!");
    } else {
      System.out.println("Couldn't find the word in the dictionary!");
    }
  }

  public static void dictionaryRemove(Dictionary dict) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the word to remove from dictionary: ");
    String removeWord = scanner.nextLine().trim().toLowerCase();
    if (dict.getWords().containsKey(removeWord)) {
      dict.removeWord(removeWord);
      System.out.println("The dictionary has been updated!");
    } else {
      System.out.println("Couldn't find the word in the dictionary!");
    }
  }

  public static void dictionaryExportToFile(Dictionary dict) {
    String path =
        new File("").getAbsolutePath() + "/src/main/resources/data/dictionaryModified.txt";
    try {
      File file = new File(path);
      if (!file.exists()) {
        file.createNewFile();
      } else {
        file.delete();
        file.createNewFile();
      }
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      for (Map.Entry<String, String> e : dict.getWords().entrySet()) {
        bw.write(e.getKey());
        bw.newLine();
        bw.write(e.getValue());
        bw.newLine();
      }
      bw.close();

    } catch (IOException ioe) {
      System.out.println("Exception occurred:");
      ioe.printStackTrace();
    }
  }
}
