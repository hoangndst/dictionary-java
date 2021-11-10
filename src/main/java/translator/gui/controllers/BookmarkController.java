package translator.gui.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import translator.Models.Database;
import translator.Models.Word;

public class BookmarkController {

    private Word sourceText;
    private ArrayList<Word> bookmarkList;

    public void setSourceText(Word word) {
        this.sourceText = word;
    }

    public Word getSourceText() {
        return sourceText;
    }

    public void setBookmarkList(ArrayList<Word> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }

    @FXML
    private Label sourceLangLabel;

    @FXML
    private JFXTextArea OutputTextArea;

    @FXML
    private JFXButton audioButton;

    @FXML
    private JFXButton copySourceButton;

    @FXML
    private JFXButton copyTargetButton;

    @FXML
    private JFXButton removeButton;

    @FXML
    private JFXButton editButton;

    @FXML
    private JFXTextArea stringTextArea;

    @FXML
    private TextField searchTextArea;

    @FXML
    private JFXListView<Word> textListViewBox;

    @FXML
    void searchText(ActionEvent event) {
        String searchText = searchTextArea.getText().replaceAll("\\s+", "").toLowerCase();
        if (searchText.equals("") || searchText.isEmpty()) {
            textListViewBox.setItems(FXCollections.observableArrayList(bookmarkList));
        } else {
            ArrayList<Word> searchList = new ArrayList<>();
            for (Word word : bookmarkList) {
                if (word.getSourceWord().startsWith(searchText)) {
                    searchList.add(word);
                }
            }
            textListViewBox.setItems(FXCollections.observableArrayList(searchList));
        }
    }

    @FXML
    void audio(ActionEvent event) {
        String a = textListViewBox.getSelectionModel().getSelectedItem().getAudio();
        String URL = "https:" + a;
        Media sound = new Media(URL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @FXML
    void copySource(ActionEvent event) {
        if (textListViewBox.getSelectionModel().getSelectedItem() != null) {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(sourceText.getSourceWord());
            clipboard.setContent(content);
        }
    }

    @FXML
    void copyTarget(ActionEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(OutputTextArea.getText());
        clipboard.setContent(content);
    }

    @FXML
    void outputText(MouseEvent event) {

    }

    @FXML
    void textListView(ActionEvent event) {

    }

    @FXML
    void remove(ActionEvent event) {
        if (textListViewBox.getSelectionModel().getSelectedItem() != null) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Confirm Delete " + sourceText.getSourceWord() + "!");
                alert.setContentText("Are you sure you want to delete this bookmark?");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                .add(new javafx.scene.image.Image("file:src/main/resources/assert/icon.png"));
                alert.showAndWait();
                if (alert.getResult() == javafx.scene.control.ButtonType.OK) {
                    Database database = new Database("jdbc:sqlite:src\\main\\resources\\data\\bookmark.sqlite");
                    database.deleteTable(sourceText.getSourceWord(), sourceText.getTargetWord());
                    textListViewBox.getItems().remove(textListViewBox.getSelectionModel().getSelectedItem());
                    database.close();
                } else {
                    alert.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void undo(MouseEvent event) {

    }

    @FXML
    void edit(ActionEvent event) {
        try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../fxml/EditWord.fxml"));
			Parent root1 = loader.load();

            EditController editController = loader.getController();
            editController.setSelectedWord(textListViewBox.getSelectionModel().getSelectedItem());
            editController.setIsBookmark(true);
			Stage stage = new Stage();
			Scene scene = new Scene(root1);
			stage.setScene(scene);
			stage.setTitle("Edit");
			stage.setResizable(false);
			stage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/assert/icon.png"));
			stage.show();
			editButton.setDisable(true);
			stage.setOnCloseRequest((WindowEvent) -> {
				editButton.setDisable(false);
                OutputTextArea.setText(textListViewBox.getSelectionModel().getSelectedItem().getTargetWord());
                stringTextArea.setText(textListViewBox.getSelectionModel().getSelectedItem().getString());
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        Database database = new Database("jdbc:sqlite:src\\main\\resources\\data\\bookmark.sqlite");
        ArrayList<Word> list = database.getSourceList();
        Collections.sort(list , (o1, o2) -> o1.getSourceWord().compareTo(o2.getSourceWord()));
        setBookmarkList(list);
        textListViewBox.setItems(FXCollections.observableArrayList(list));
        textListViewBox.setCellFactory(param -> new ListCell<Word>() {
            @Override
            protected void updateItem(Word item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getSourceWord());
                }
            }
        });
        
        audioButton.setDisable(true);
        editButton.setDisable(true);
        textListViewBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setSourceText(textListViewBox.getSelectionModel().getSelectedItem());
            audioButton.setDisable(false);
            editButton.setDisable(false);
            if (newValue != null) {
                searchTextArea.setText(newValue.getSourceWord());
                OutputTextArea.setText(newValue.getTargetWord());
                stringTextArea.setText(newValue.getString());
                sourceLangLabel.setText(newValue.getTargetLang());
            } else {
                OutputTextArea.clear();
                stringTextArea.clear();
                sourceLangLabel.setText("Language");
            }
        });
        database.close();
    }

}
