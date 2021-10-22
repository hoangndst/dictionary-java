package translator.Models;


public class WordFromDB extends Word {
    private String time;
    private String info;
    private String targetLang;

    public WordFromDB(String time, String source, String target, String info) {
        super(source, target);
        this.time = time;
        this.info = info;
    }

    public WordFromDB(String time, String source, String target, String info, String audio, String targetLang) {
        super(source, target);
        this.setAudio(audio);
        this.time = time;
        this.info = info;
        this.targetLang = targetLang;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }

}
