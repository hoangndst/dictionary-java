package translator.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import translator.Models.Link;

public class AboutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane AboutPane;

    @FXML
    private Hyperlink reportBug;

    @FXML
    void AboutPaneClick(MouseEvent event) {

    }

    @FXML
    void initialize() {
        reportBug.setOnAction(event -> {
            Link.Open("https://github.com/hoangndst/dictionary-java/issues");
        });
        assert AboutPane != null : "fx:id=\"AboutPane\" was not injected: check your FXML file 'About.fxml'.";
        assert reportBug != null : "fx:id=\"reportBug\" was not injected: check your FXML file 'About.fxml'.";
    }

}
