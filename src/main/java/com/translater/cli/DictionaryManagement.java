package com.translater.cli;

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

    public void insertFromFile(String path) {
        
    }
}
