package translator.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import translator.Models.Database;
import translator.Models.Word;

public class EditController {

    private Word selectedWord;
    private boolean isBookmark = true;

    public void setIsBookmark(boolean isBookmark) {
        this.isBookmark = isBookmark;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextArea SynText;

    @FXML
    private JFXButton applyButton;

    @FXML
    private JFXButton audioButton;

    @FXML
    private JFXButton copySourceButton;

    @FXML
    private JFXButton copyTargetButton;

    @FXML
    private JFXTextArea defTex;

    @FXML
    private JFXTextArea exTex;

    @FXML
    private JFXTextArea meaningText;

    @FXML
    private JFXTextArea pronounceText;

    @FXML
    private Label sourceLangLabel;

    @FXML
    private Label sourceWord;

    @FXML
    private JFXTextArea typeText;

    @FXML
    void apply(ActionEvent event) {
        this.selectedWord.setTargetWord(this.meaningText.getText());
        this.selectedWord.setPronounce(this.pronounceText.getText());
        this.selectedWord.setType(this.typeText.getText());
        this.selectedWord.setExample(this.exTex.getText());
        this.selectedWord.setSynonyms(this.SynText.getText());
        this.selectedWord.setDefinition(this.defTex.getText());
        try {
            if (isBookmark) {
                Database db = new Database("jdbc:sqlite:src\\main\\resources\\data\\bookmark.sqlite");
                db.updateWord(this.selectedWord);
                db.close();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("The word has been updated to the bookmark");
                    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                    .add(new javafx.scene.image.Image("file:src/main/resources/assert/mess.png"));
                alert.showAndWait();
            } else {
                Database db = new Database();
                db.updateWord(this.selectedWord);
                db.close();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("The word has been updated to the dictionary");
                    ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                    .add(new javafx.scene.image.Image("file:src/main/resources/assert/mess.png"));
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void audio(ActionEvent event) {
        String a = this.selectedWord.getAudio();
        String URL = "https:" + a;
        Media sound = new Media(URL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @FXML
    void copySource(ActionEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(this.selectedWord.getSourceWord());
        clipboard.setContent(content);
    }

    @FXML
    void copyTarget(ActionEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(this.selectedWord.getTargetWord());
        clipboard.setContent(content);
    }

    public void setSelectedWord(Word selectedWord) {
        this.selectedWord = selectedWord;
        sourceWord.setText(selectedWord.getSourceWord());
        meaningText.setText(selectedWord.getTargetWord());
        SynText.setText(selectedWord.getSynonyms());
        defTex.setText(selectedWord.getDefinition());
        exTex.setText(selectedWord.getExample());
        typeText.setText(selectedWord.getType());
        pronounceText.setText(selectedWord.getPronounce());
        sourceLangLabel.setText(selectedWord.getTargetLang());
    }

    @FXML
    void initialize() {
        assert SynText != null : "fx:id=\"SynText\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert applyButton != null : "fx:id=\"applyButton\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert audioButton != null : "fx:id=\"audioButton\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert copySourceButton != null : "fx:id=\"copySourceButton\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert copyTargetButton != null : "fx:id=\"copyTargetButton\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert defTex != null : "fx:id=\"defTex\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert exTex != null : "fx:id=\"exTex\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert meaningText != null : "fx:id=\"meaningText\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert pronounceText != null : "fx:id=\"pronounceText\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert sourceLangLabel != null : "fx:id=\"sourceLangLabel\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert sourceWord != null : "fx:id=\"sourceWord\" was not injected: check your FXML file 'EditWord.fxml'.";
        assert typeText != null : "fx:id=\"typeText\" was not injected: check your FXML file 'EditWord.fxml'.";

    }

}
