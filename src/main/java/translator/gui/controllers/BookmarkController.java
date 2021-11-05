package translator.gui.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import translator.Models.Database;
import translator.Models.WordFromDB;

public class BookmarkController {
	List<WordFromDB> list = new ArrayList<>();
	private WordFromDB sourceText;

	public void setSourceText(WordFromDB sourceText) {
		this.sourceText = sourceText;
	}

	public WordFromDB getSourceText() {
		return sourceText;
	}

	@FXML
	private Label sourceLangLabel;

	@FXML
	private JFXTextArea searchBar;

	@FXML
	private JFXTextArea OutputTextArea;

	@FXML
	private JFXButton searchButton;

	@FXML
	private JFXButton audioButton;

	@FXML
	private JFXButton copySourceButton;

	@FXML
	private JFXButton copyTargetButton;

	@FXML
	private JFXButton removeButton;

	@FXML
	private JFXTextArea stringTextArea;

	@FXML
	private JFXListView<WordFromDB> textListViewBox;

	@FXML
	void search(ActionEvent event) {
		textListViewBox.getItems().clear();
		Database database = new Database();
		list = database.getSourceList();
		list = searchList(searchBar.getText(), list);
		Collections.reverse(list);
		textListViewBox.setItems(FXCollections.observableArrayList(list));
		database.close();
	}

	private List<WordFromDB> searchList(String searchWords,
			List<WordFromDB> listOfWords) {
		return listOfWords.stream().filter(input -> input.getSourceWord()
				.toLowerCase().startsWith(searchWords))
				.collect(Collectors.toList());
	}
	@FXML
	void audio(ActionEvent event) {
		String a = textListViewBox.getSelectionModel().getSelectedItem()
				.getAudio();
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
		Database database = new Database();
		if (textListViewBox.getSelectionModel().getSelectedItem() != null) {
			try {
				database.deleteTable(sourceText.getTime(),
						sourceText.getSourceWord());
				textListViewBox.getItems().remove(
						textListViewBox.getSelectionModel().getSelectedItem());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		database.close();
	}

	@FXML
	void undo(MouseEvent event) {

	}

	@FXML
	void initialize() {
		Database database = new Database();
		list = database.getSourceList();
		Collections.reverse(list);
		textListViewBox.setItems(FXCollections.observableArrayList(list));
		textListViewBox.setCellFactory(param -> new ListCell<WordFromDB>() {
			@Override
			protected void updateItem(WordFromDB item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
				} else {
					setText(item.getSourceWord());
				}
			}
		});
		searchBar.setPromptText("Search");
		audioButton.setDisable(true);
		textListViewBox.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					setSourceText(textListViewBox.getSelectionModel()
							.getSelectedItem());
					audioButton.setDisable(false);
					OutputTextArea.setText(newValue.getTargetWord());
					stringTextArea.setText(newValue.getInfo());
					sourceLangLabel.setText(newValue.getTargetLang());
				});
		database.close();
	}

}
