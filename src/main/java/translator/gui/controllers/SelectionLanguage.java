package translator.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectionLanguage {
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

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXComboBox<String> selectSourceBox;

    @FXML
    private JFXComboBox<String> selectTargetBox;

    @FXML
    private JFXButton swapButton;

    @FXML
    private JFXButton undoButton;

    @FXML
    private JFXButton redoButton;

    @FXML
    void selectSource(ActionEvent event) {
        this.sourceLang = languages.get(selectSourceBox.getValue());
        System.err.println(this.sourceLang);
    }

    @FXML
    void selectTarget(ActionEvent event) {
        this.targetLang = languages.get(selectTargetBox.getValue());
        System.err.println(this.targetLang);
    }

    @FXML
    void swap(ActionEvent event) {
        String temp = this.sourceLang;
        this.sourceLang = this.targetLang;
        this.targetLang = temp;
        temp = selectSourceBox.getValue();
        selectSourceBox.setValue(selectTargetBox.getValue());
        selectTargetBox.setValue(temp);
    }

    @FXML
    void redo(ActionEvent event) {

    }

    @FXML
    void undo(ActionEvent event) {

    }

    @FXML
    void initialize() {
        selectSourceBox.getItems().addAll(languages.keySet());
        selectTargetBox.getItems().addAll(languages.keySet());
        assert selectSourceBox != null : "fx:id=\"selectSourceBox\" was not injected: check your FXML file 'SelectionLanguage.fxml'.";
        assert selectTargetBox != null : "fx:id=\"selectTargetBox\" was not injected: check your FXML file 'SelectionLanguage.fxml'.";
        assert swapButton != null : "fx:id=\"swapButton\" was not injected: check your FXML file 'SelectionLanguage.fxml'.";
        assert undoButton != null : "fx:id=\"undoButton\" was not injected: check your FXML file 'SelectionLanguage.fxml'.";
        assert redoButton != null : "fx:id=\"redoButton\" was not injected: check your FXML file 'SelectionLanguage.fxml'.";
    }
}
