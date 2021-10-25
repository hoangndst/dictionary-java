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

    private String url = "jdbc:sqlite:src\\main\\resources\\data\\bookmark.sqlite";
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    /**
     * Constructor 1.
     */

    public Database() {
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
     * Insert a new bookmark.
     * @param source Source word
     * @param target Target word 
     * @param info information about word
     * @param audio audio file url
     * @param targetLang target language
     */

    public void createTable(String source, String target, String info, String audio, String targetLang) {
        boolean tableExists = checkData(source, target);
        source = source.replace("\\s+", "").toLowerCase();
        String time = getTime();
        try {
            if (!tableExists) {
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO bookmark (time, source, target, info, audio, targetLang) VALUES (?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, time);
                pstmt.setString(2, source);
                pstmt.setString(3, target);
                pstmt.setString(4, info);
                pstmt.setString(5, audio);
                pstmt.setString(6, targetLang);
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

    public void deleteTable(String time, String source) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM bookmark WHERE time = ? AND source = ?");
            pstmt.setString(1, time);
            pstmt.setString(2, source);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get all bookmarks.
     * @return ArrayList of bookmarks
     */

    public ArrayList<WordFromDB> getSourceList() {
        ArrayList<WordFromDB> sourceList = new ArrayList<>();
        try {
            rs = stmt.executeQuery("SELECT DISTINCT * FROM bookmark");
            while (rs.next()) {
                String source = rs.getString("source");
                String target = rs.getString("target");
                String info = rs.getString("info");
                String audio = rs.getString("audio");
                String time = rs.getString("time");
                String targetLang = rs.getString("targetLang");
                WordFromDB word = new WordFromDB(time, source, target, info, audio, targetLang);
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

    public static void main(String[] args) throws SQLException {
        
    }
}
