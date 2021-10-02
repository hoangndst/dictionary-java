package translator.gui.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton copyTargetButton;

    @FXML
    private JFXButton copySourceButton;

    @FXML
    private JFXButton audioButton;

    @FXML
    void audio(ActionEvent event) {

    }

    @FXML
    void copySource(ActionEvent event) {

    }

    @FXML
    void copyTarget(ActionEvent event) {

    }

    @FXML
    void undo(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert copyTargetButton != null : "fx:id=\"copyTargetButton\" was not injected: check your FXML file 'demo.fxml'.";
        assert copySourceButton != null : "fx:id=\"copySourceButton\" was not injected: check your FXML file 'demo.fxml'.";
        assert audioButton != null : "fx:id=\"audioButton\" was not injected: check your FXML file 'demo.fxml'.";
    }
}
