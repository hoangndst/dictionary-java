package translator.gui.controllers;

import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class InputController {

    private DashboardController dashboardController;
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox InputTextBox;

    @FXML
    private TextField InputTextField;

    @FXML
    private JFXListView<?> textListViewBox;

    @FXML
    void inputText(ActionEvent event) {
        System.err.println(InputTextField.getText());
    }

    @FXML
    void textListView(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert InputTextBox != null : "fx:id=\"InputTextBox\" was not injected: check your FXML file 'InputText.fxml'.";
        assert InputTextField != null : "fx:id=\"InputTextField\" was not injected: check your FXML file 'InputText.fxml'.";
        assert textListViewBox != null : "fx:id=\"textListViewBox\" was not injected: check your FXML file 'InputText.fxml'.";

    }
}
