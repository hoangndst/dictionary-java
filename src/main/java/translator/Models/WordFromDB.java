package translator.Models;

public class WordFromDB extends Word {
    private String time;
    private String info;
    private String targetLang;

    /**
     * Constructor 1.
     * @param time time of the word
     * @param source source of the word
     * @param target target of the word
     * @param info info of the word
     */

    public WordFromDB(String time, String source, String target, String info) {
        super(source, target);
        this.time = time;
        this.info = info;
    }

    /**
     * Constructor 2.
     * @param time time of the word
     * @param source source of the word
     * @param target target of the word
     * @param info info of the word
     * @param audio audio of the word
     * @param targetLang target language of the word
     */

    public WordFromDB(String time, String source, String target, String info, String audio, String targetLang) {
        super(source, target);
        this.setAudio(audio);
        this.time = time;
        this.info = info;
        this.targetLang = targetLang;
    }

    /**
     * Getter for time.
     * @return time
     */

    public String getTime() {
        return time;
    }

    /**
     * Setter for time.
     * @param time time
     */

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Getter for info.
     * @return info
     */

    public String getInfo() {
        return info;
    }

    /**
     * Setter for info.
     * @param info info
     */

    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Getter for target language.
     * @return target language
     */

    public String getTargetLang() {
        return targetLang;
    }

    /**
     * Setter for target language.
     * @param targetLang target language
     */

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }


    // public void setInfo(String type, String pronounce, String definition, String example) {
    //     String result = "";
    //     if (!type.equals("")) {
    //         result += "Type: " + type + ".\n\n";
    //     }
    //     if (!pronounce.equals("")) {
    //         result += "Pronounce: /" + pronounce + "/\n\n";
    //     }
    //     if (!definition.equals("")) {
    //         result += "Definition: " + definition + "\n\n";
    //     }

    // }
}
