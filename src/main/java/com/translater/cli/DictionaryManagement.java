package com.translater.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void insertFromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        String wordSource = scanner.nextLine();
        String wordTarget = scanner.nextLine();
        Word newWord = new Word(wordSource.toLowerCase(), wordTarget);
        dictionary.insert(newWord);
        scanner.close();
    }

    public void insertFromFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String wordSource = scanner.nextLine();
            String wordTarget = scanner.nextLine();
            Word newWord = new Word(wordSource.toLowerCase(), wordTarget);
            dictionary.insert(newWord);
        }
        scanner.close();
    }
}
