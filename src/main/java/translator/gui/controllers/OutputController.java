package translator.gui.controllers;

import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class OutputController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextArea outputTextBox;

    @FXML
    void outputText(MouseEvent event) {
        
    }

    public void setOutputText(String text) {
        outputTextBox.setText(text);
    }

    @FXML
    void initialize() {
        assert outputTextBox != null : "fx:id=\"outputTextBox\" was not injected: check your FXML file 'OutputText.fxml'.";

    }
}