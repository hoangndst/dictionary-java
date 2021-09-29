package translator.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controllers {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnctrl;

    @FXML
    void handle(ActionEvent event) {
        System.out.println("DCMMMMMMMMMMMMMMMMMMMMMMMMMMMM PLEaasSSS");
    }

    @FXML
    void initialize() {
        assert btnctrl != null : "fx:id=\"btnctrl\" was not injected: check your FXML file 'test.fxml'.";

    }
}
