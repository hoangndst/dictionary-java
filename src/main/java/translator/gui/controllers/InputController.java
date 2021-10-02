package translator.gui.controllers;

import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InputController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField inputTextBox;

    @FXML
    private JFXListView<?> textListViewBox;

    @FXML
    void inputText(ActionEvent event) {

    }

    @FXML
    void textListView(ActionEvent event) {

    }   

    public String getInputText() {
        return inputTextBox.getText();
    }

    @FXML
    void initialize() {
        assert inputTextBox != null : "fx:id=\"inputTextBox\" was not injected: check your FXML file 'InputText.fxml'.";
        assert textListViewBox != null : "fx:id=\"textListViewBox\" was not injected: check your FXML file 'InputText.fxml'.";

    }
}