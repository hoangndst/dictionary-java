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
    private JFXTextArea OutputTextArea;

    @FXML
    void outputText(MouseEvent event) {
    }

    public void setOutputText(String text) {
        OutputTextArea.setText(text);
    }

    @FXML
    void initialize() {

        assert OutputTextArea != null : "fx:id=\"OutputTextArea\" was not injected: check your FXML file 'OutputText.fxml'.";
    }
}
