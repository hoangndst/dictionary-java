package translator.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import translator.api.Translate;
import translator.Models.Word;

public class DashboardController implements Initializable{

    private Word word;
    private String sourceLang;
    private String targetLang;
    

    private final HashMap<String, String> languages = new HashMap<String, String>() {{
        put("English", "en");
        put("French", "fr");
        put("German", "de");
        put("Italian", "it");
        put("Spanish", "es");
        put("Vietnamese", "vi");
    }};

    public void setWord(Word word) {
        this.word = word;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    // TextArea

    @FXML
    private JFXTextArea InputTextField;

    @FXML
    private JFXTextArea OutputTextArea;

    @FXML
    private JFXTextArea stringTextArea;

    @FXML
    private JFXTextArea longOutputTextArea;

    //

    @FXML
    private JFXButton audioButton;

    @FXML
    private JFXButton copySourceButton;

    @FXML
    private JFXButton copyTargetButton;

    @FXML
    private JFXButton redoButton;

    @FXML
    private JFXButton aboutButton;

    @FXML
    private JFXButton translateButton;

    @FXML
    private JFXComboBox<String> selectSourceBox;

    @FXML
    private JFXComboBox<String> selectTargetBox;

    @FXML
    private JFXButton swapButton;

    @FXML
    private JFXListView<?> textListViewBox;

    @FXML
    private JFXButton undoButton;

    @FXML
    void audio(ActionEvent event) {
        String URL = "https:" + this.word.getAudio();
        Media sound = new Media(URL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @FXML
    void copySource(ActionEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(InputTextField.getText());
        clipboard.setContent(content);
    }

    @FXML
    void copyTarget(ActionEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(word.getTargetWord());
        clipboard.setContent(content);
    }


    @FXML
    void translate(ActionEvent event) {
        run();
    }

    // @FXML
    // void inputText(ActionEvent event) {
    //     System.out.println(InputTextField.getText());
    //     int words = InputTextField.getText().split("\\s+").length;
    //     System.out.println(words);
    //     Trans();
    //     if (words == 1) {
    //         OutputTextArea.setText(this.word.getTargetWord());
    //         stringTextArea.setText(this.word.getString());
    //         if (!this.word.getAudio().equals("none")) {
    //             audioButton.setDisable(false);
    //         } else {
    //             audioButton.setDisable(true);
    //         }
    //     } else {
    //         longOutputTextArea.setText(this.word.getTargetWord());
    //     }
    // }

    @FXML
    void outputText(MouseEvent event) {
    }

    @FXML
    void redo(ActionEvent event) {

    }

    @FXML
    void selectSource(ActionEvent event) {
        this.sourceLang = languages.get(selectSourceBox.getValue());
    }

    @FXML
    void selectTarget(ActionEvent event) {
        this.targetLang = languages.get(selectTargetBox.getValue());
    }

    @FXML
    void swap(ActionEvent event) {
        this.sourceLang = languages.get(selectTargetBox.getValue());
        this.targetLang = languages.get(selectSourceBox.getValue());
        String temp = selectSourceBox.getValue();
        selectSourceBox.setValue(selectTargetBox.getValue());
        selectTargetBox.setValue(temp);
    }

    @FXML
    void textListView(ActionEvent event) {

    }

    @FXML
    void undo(MouseEvent event) {

    }

    @FXML
    void about(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../fxml/About.fxml"));	
		    Parent root1 = loader.load();	
            Stage stage = new Stage();
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.setTitle("About");
            stage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/assert/icon.png"));
            stage.show();
            aboutButton.setDisable(true);
            stage.setOnCloseRequest((WindowEvent) -> {
                aboutButton.setDisable(false);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void Trans() {
        stringTextArea.clear();
        OutputTextArea.clear();
        longOutputTextArea.clear();
        Translate trans = new Translate(InputTextField.getText(), "", targetLang);
        trans.translateWord();
        this.word = trans.getWord();
    }

    void run() {
        System.out.println(InputTextField.getText());
        int words = InputTextField.getText().split("\\s+").length;
        System.out.println(words);
        Trans();
        if (words == 1) {
            longOutputTextArea.setDisable(true);
            OutputTextArea.setText(this.word.getTargetWord());
            stringTextArea.setText(this.word.getString());
            if (!this.word.getAudio().equals("none")) {
                audioButton.setDisable(false);
            } else {
                audioButton.setDisable(true);
            }
        } else {
            audioButton.setDisable(true);
            longOutputTextArea.setDisable(false);
            longOutputTextArea.setText(this.word.getTargetWord());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        InputTextField.setOnKeyPressed(event -> {
            if (event.isShiftDown()) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    run();
                    event.consume();
                }
            }
        });

        word = new Word();
        audioButton.setDisable(true);
        selectSourceBox.getItems().addAll(languages.keySet());
        selectTargetBox.getItems().addAll(languages.keySet());
        selectSourceBox.setValue("English");
        selectTargetBox.setValue("Vietnamese");
        sourceLang = languages.get(selectSourceBox.getValue());
        targetLang = languages.get(selectTargetBox.getValue());
    }

}
