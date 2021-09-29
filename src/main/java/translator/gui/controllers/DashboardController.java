package translator.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private Button swapButton;

    @FXML
    private Button copyTargetButton;

    @FXML
    private Button copySourceButton;

    @FXML
    private Button audioButton;

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
    void redo(ActionEvent event) {

    }

    @FXML
    void swap(ActionEvent event) {

    }

    @FXML
    void undo(ActionEvent event) {
        System.out.println("undo");
    }

    @FXML
    void initialize() {
        assert undoButton != null : "fx:id=\"undoButton\" was not injected: check your FXML file 'demo.fxml'.";
        assert redoButton != null : "fx:id=\"redoButton\" was not injected: check your FXML file 'demo.fxml'.";
        assert swapButton != null : "fx:id=\"swapButton\" was not injected: check your FXML file 'demo.fxml'.";
        assert copyTargetButton != null : "fx:id=\"copyTargetButton\" was not injected: check your FXML file 'demo.fxml'.";
        assert copySourceButton != null : "fx:id=\"copySourceButton\" was not injected: check your FXML file 'demo.fxml'.";
        assert audioButton != null : "fx:id=\"audioButton\" was not injected: check your FXML file 'demo.fxml'.";

    }
}
