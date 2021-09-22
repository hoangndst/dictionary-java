package com.translator.cli;
import java.io.IOException;

public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();

    public void showAllWords() {
        dictionaryManagement.getDictionary().getWords().entrySet().forEach(e -> {
            System.out.println(e.getKey() + ": " + e.getValue());
        });
    }

    public void dictionaryBasic() throws IOException {

        showAllWords();
    }
}
