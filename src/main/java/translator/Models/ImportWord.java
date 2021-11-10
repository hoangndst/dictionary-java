package translator.Models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import translator.api.Translate;


public class ImportWord {
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/main/java/translator/Models/words.txt"));
        ArrayList<String> words = new ArrayList<String>();
        while (s.hasNext()) {
            words.add(s.next());
        }
        s.close();
        System.out.println(words.size());
        Database db = new Database();
        
        for (String w : words) {
            Translate translate = new Translate(w, "", "vi");
            translate.translateWord();
            Word word = translate.getWord();
            db.createTable(word.getSourceWord(), word.getTargetWord(), word.getAudio(), 
            word.getPronounce(), word.getType(), word.getDefinition(), word.getExample(), 
            word.getSynonyms(), word.getTargetLang());
            System.out.println(w);
        }

    }
}
