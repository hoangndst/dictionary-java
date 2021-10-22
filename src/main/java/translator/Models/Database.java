package translator.Models;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.Date;

import javafx.scene.Cursor;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {    

    private String url = "jdbc:sqlite:src\\main\\resources\\data\\bookmark.sqlite";
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private Cursor cursor;

    public Database() {
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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

    String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        return time;
    }

    public static void main(String[] args) throws SQLException {
        
    }
}
