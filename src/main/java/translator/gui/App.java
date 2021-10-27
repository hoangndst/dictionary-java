package translator.gui;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXMLLoader loader = new FXMLLoader(new File("C:/Users/hoang/Desktop/code/dictionary-java/src/main/resources/fxml/demo.fxml").toURI().toURL());
        // Parent root = loader.load();
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/demo.fxml"));
        primaryStage.setTitle("Translator");
        primaryStage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/assert/icon.png"));
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
    }
}