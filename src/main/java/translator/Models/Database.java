package translator.Models;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {    

    private String url;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    /**
     * Constructor 1.
     */

    public Database() {
        try {
            this.url = "jdbc:sqlite:src\\main\\resources\\data\\Words.sqlite";
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Constructor 2.
     *
     * @param url
     */
    public Database(String url) {
        this.url = url;
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *  Close the connection.
     */

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert a new word into the database.
     * @param source The source word.
     * @param target The target word.
     * @param audio The audio file.
     * @param pronounce The pronunciation.
     * @param type The type of the word.
     * @param definition The definition of the word.
     * @param example The example of the word.
     * @param synonyms The synonyms of the word.
     * @param targetLang The target language.
     */

    public void createTable(String source, String target, String audio, String pronounce, String type, String definition, String example, String synonyms, String targetLang) {
        boolean tableExists = checkData(source, target);
        source = source.replace("\\s+", "").toLowerCase();
        String time = getTime();
        try {
            if (!tableExists) {
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO bookmark (time, source, target, audio, pronounce, type, definition, example, synonyms, targetLang) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, time);
                pstmt.setString(2, source);
                pstmt.setString(3, target);
                pstmt.setString(4, audio);
                pstmt.setString(5, pronounce);
                pstmt.setString(6, type);
                pstmt.setString(7, definition);
                pstmt.setString(8, example);
                pstmt.setString(9, synonyms);
                pstmt.setString(10, targetLang);
                pstmt.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Delete a bookmark.
     * @param time time of bookmark
     * @param source source word
     */

    public void deleteTable(String source, String target) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM bookmark WHERE source = ? AND target = ?");
            pstmt.setString(1, source);
            pstmt.setString(2, target);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Delete bookmark by time.
     * @param time time of bookmark
     */

    public void deleteTable(String time) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM bookmark WHERE time = ?");
            pstmt.setString(1, time);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get all bookmarks.
     * @return ArrayList of bookmarks
     */

    public ArrayList<Word> getSourceList() {
        ArrayList<Word> sourceList = new ArrayList<>();
        try {
            rs = stmt.executeQuery("SELECT DISTINCT * FROM bookmark");
            while (rs.next()) {
                String time = rs.getString("time");
                String source = rs.getString("source");
                String target = rs.getString("target");
                String audio = rs.getString("audio");
                String pronounce = rs.getString("pronounce");
                String type = rs.getString("type");
                String definition = rs.getString("definition");
                String example = rs.getString("example");
                String synonyms = rs.getString("synonyms");
                String targetLang = rs.getString("targetLang");
                Word word = new Word(time, source, target, audio, pronounce, type, definition, example, synonyms, targetLang);
                sourceList.add(0, word);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sourceList;
    }

    /**
     * Get bookmark by time and source word
     * @param time time of bookmark
     * @param source source word
     */

    public String getTargetString(String time, String source) {
        String target = "";
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bookmark WHERE time = ? AND source = ?");
            pstmt.setString(1, time);
            pstmt.setString(2, source);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                target = rs.getString("target");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return target;
    }

    /**
     * Get info of word by time and source word
     * @param time time of bookmark
     * @param source source word
     * @return info of word
     */

    public String getInfoString(String time, String source) {
        String info = "";
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM bookmark WHERE time = ? AND source = ?");
            pstmt.setString(1, time);
            pstmt.setString(2, source);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                info = rs.getString("info");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return info;
    }

    /**
     * Get audio file url of word by time and source word
     * @param source source word
     * @return audio file url
     */

    public String getAudioString(String source) {
        String audio = "";
        try {
            rs = stmt.executeQuery("SELECT DISTINCT audio FROM bookmark WHERE source = '" + source + "'");
            while (rs.next()) {
                audio = rs.getString("audio");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return audio;
    }

    /**
     * Check if bookmark exists.
     * @param source source word
     * @param target target word
     * @return true if bookmark exists
     */

    public boolean checkData(String source, String target) {
        source = source.replace("\\s+", "").toLowerCase();
        target = target.replace("\\s+", "").toLowerCase();
        try {
            rs = stmt.executeQuery("SELECT * FROM bookmark WHERE source = '" + source + "' AND target = '" + target + "'");
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false; 
    }

    private String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        return time;
    }

    /**
     * Update word info.
     * @param word
     */

    public void updateWord(Word word) {
        String time = word.getTime();
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE bookmark SET time = ?, source = ?, target = ?, pronounce = ?, type = ?, definition = ?, example = ?, synonyms = ?, targetLang = ? WHERE time = ?");
            pstmt.setString(1, getTime());
            pstmt.setString(2, word.getSourceWord());
            pstmt.setString(3, word.getTargetWord());
            pstmt.setString(4, word.getPronounce());
            pstmt.setString(5, word.getType());
            pstmt.setString(6, word.getDefinition());
            pstmt.setString(7, word.getExample());
            pstmt.setString(8, word.getSynonyms());
            pstmt.setString(9, word.getTargetLang());
            pstmt.setString(10, time);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Add word to bookmark.
     * @param word
     */

    public void addWord(Word word) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO bookmark (time, source, target, audio, pronounce, type, definition, example, synonyms, targetLang) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, getTime());
            pstmt.setString(2, word.getSourceWord());
            pstmt.setString(3, word.getTargetWord());
            pstmt.setString(4, word.getAudio());
            pstmt.setString(5, word.getPronounce());
            pstmt.setString(6, word.getType());
            pstmt.setString(7, word.getDefinition());
            pstmt.setString(8, word.getExample());
            pstmt.setString(9, word.getSynonyms());
            pstmt.setString(10, word.getTargetLang());
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        
    }
}
