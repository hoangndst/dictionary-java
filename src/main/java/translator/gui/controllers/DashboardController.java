package translator.gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Stack;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import translator.Models.Database;
import translator.Models.Word;
import translator.api.Translate;

public class DashboardController implements Initializable {

	private Word word;
	private String sourceLang;
	private String targetLang;
	
	private Stack<Word> history = new Stack<>();
	private Stack<Word> future = new Stack<>();
	private ArrayList<Word> wordsList = new ArrayList<>();

  	private final HashMap<String, String> languages = new HashMap<String, String>() {
    	{
      		put("English", "en");
      		put("French", "fr");
      		put("German", "de");
			put("Italian", "it");
			put("Spanish", "es");
			put("Vietnamese", "vi");
			put("Chinese (Simplified)", "zh-CN");
			put("Chinese (Traditional)", "zh-TW");
			put("Japanese", "ja");
			put("Korean", "ko");
			put("Portuguese", "pt");
			put("Russian", "ru");
			put("Turkish", "tr");
			put("Thai", "th");
			put("Lao", "lo");
    	}
  	};

  	public void setWord(Word word) {
    	this.word = word;
  	}
	
	public void setWordsList(ArrayList<Word> wordsList) {
		this.wordsList = wordsList;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	// TextArea

	@FXML
	private JFXTextArea InputTextField;

	@FXML
	private JFXTextArea OutputTextArea;

	@FXML
	private JFXTextArea stringTextArea;

	@FXML
	private JFXTextArea longOutputTextArea;

	//

	@FXML
	private JFXButton audioButton;

	@FXML
	private JFXButton copySourceButton;

	@FXML
	private JFXButton copyTargetButton;

	@FXML
	private JFXButton redoButton;

	@FXML
	private JFXButton aboutButton;

	@FXML
	private JFXButton translateButton;

	@FXML
	private JFXButton bookMarkButton;

	@FXML
	private JFXButton showBMButton;

	@FXML
    private JFXButton editButton;

	@FXML
    private JFXButton removeButton;

	@FXML
	private JFXComboBox<String> selectSourceBox;

	@FXML
	private JFXComboBox<String> selectTargetBox;

	@FXML
	private JFXButton swapButton;

	@FXML
    private JFXListView<Word> textListViewBoxOffline;

	@FXML
	private JFXButton undoButton;


	@FXML
    private VBox offlineVbox;

    @FXML
    private VBox onlineVbox;

	@FXML
    private JFXButton changeModeButton;

	@FXML
    private TextField searchTextArea;

	@FXML
    void changeMode(ActionEvent event) {
		if (offlineVbox.isVisible()) {
			offlineVbox.setVisible(false);
			onlineVbox.setVisible(true);
			translateButton.setDisable(false);
			OutputTextArea.clear();
            stringTextArea.clear();
			audioButton.setDisable(true);
			editButton.setVisible(false);
			removeButton.setVisible(false);
			// OutputTextArea.setEditable(false);
			// InputTextField.setEditable(false);
			changeModeButton.setText("Online");
		} else {
			offlineVbox.setVisible(true);
			onlineVbox.setVisible(false);
			translateButton.setDisable(true);
			// OutputTextArea.setEditable(true);
			// stringTextArea.setEditable(true);
			editButton.setVisible(true);
			editButton.setDisable(true);
			removeButton.setVisible(true);
			removeButton.setDisable(true);
			changeModeButton.setText("Offline");
			Database database = new Database();
			ArrayList<Word> list = database.getSourceList();
			setWordsList(list);
			textListViewBoxOffline.setItems(FXCollections.observableArrayList(list));
			database.close();
		}
    }

	@FXML
    void edit(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../fxml/EditWord.fxml"));
			Parent root1 = loader.load();

            EditController editController = loader.getController();
            editController.setSelectedWord(this.word);
			editController.setIsBookmark(false);
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
                OutputTextArea.setText(this.word.getTargetWord());
                stringTextArea.setText(this.word.getString());
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@FXML
    void remove(ActionEvent event) {
		if (!this.word.equals(null)) {
			try {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Confirm Delete " + this.word.getSourceWord() + "!");
                alert.setContentText("Are you sure you want to delete this word?");
                ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
				.add(new javafx.scene.image.Image("file:src/main/resources/assert/icon.png"));
                alert.showAndWait();
				if (alert.getResult() == javafx.scene.control.ButtonType.OK) {
					Database database = new Database();
					database.deleteTable(this.word.getTime());
					wordsList.remove(this.word);
					textListViewBoxOffline.getItems().remove(textListViewBoxOffline.getSelectionModel().getSelectedItem());
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
	void audio(ActionEvent event) {
		String URL = "https:" + this.word.getAudio();
		Media sound = new Media(URL);
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}

	@FXML
	void copySource(ActionEvent event) {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(InputTextField.getText());
		clipboard.setContent(content);
	}

	@FXML
	void copyTarget(ActionEvent event) {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(word.getTargetWord());
		clipboard.setContent(content);
	}


	@FXML
	void translate(ActionEvent event) {
		if (onlineVbox.isDisable()) {
			return;
		}
		if (InputTextField.getText().isEmpty()) {
			return;
		}
		Trans();
		update(false);
	}

	@FXML
	void outputText(MouseEvent event) {

	}

	@FXML
	void bookMark(ActionEvent event) {
		int words = this.word.getSourceWord().split("\\s+").length;
		Database database = new Database("jdbc:sqlite:src\\main\\resources\\data\\bookmark.sqlite");
		if (words == 1) {
			if (database.checkData(word.getSourceWord(), selectTargetBox.getValue())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Word already exists");
				alert.setContentText("Please try again");
				alert.showAndWait();
			} else {
				database.createTable(this.word.getSourceWord(), this.word.getTargetWord(), this.word.getAudio(),
				this.word.getPronounce(), this.word.getType(), this.word.getDefinition(), this.word.getExample(),
				this.word.getSynonyms(), selectTargetBox.getValue());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText("The word has been added to the bookmark");
					((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
					.add(new javafx.scene.image.Image("file:src/main/resources/assert/icon.png"));
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a single word");
			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
			.add(new javafx.scene.image.Image("file:src/main/resources/assert/icon.png"));
			alert.showAndWait();
		}
		database.close();
	}

	@FXML
	void showBookMark(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../fxml/Bookmark.fxml"));
			Parent root1 = loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root1);
			stage.setScene(scene);
			stage.setTitle("Bookmark");
			stage.setResizable(false);
			stage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/assert/icon.png"));
			stage.show();
			showBMButton.setDisable(true);
			stage.setOnCloseRequest((WindowEvent) -> {
				showBMButton.setDisable(false);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void selectSource(ActionEvent event) {
		this.sourceLang = languages.get(selectSourceBox.getValue());
	}

	@FXML
	void selectTarget(ActionEvent event) {
		this.targetLang = languages.get(selectTargetBox.getValue());
	}

	@FXML
	void swap(ActionEvent event) {
		this.sourceLang = languages.get(selectTargetBox.getValue());
		this.targetLang = languages.get(selectSourceBox.getValue());
		String temp = selectSourceBox.getValue();
		selectSourceBox.setValue(selectTargetBox.getValue());
		selectTargetBox.setValue(temp);
	}

	@FXML
    void textListViewOffline(ActionEvent event) {

    }

	@FXML
    void searchText(ActionEvent event) {
		String searchText = searchTextArea.getText().replaceAll("\\s+", "").toLowerCase();
        if (searchText.equals("") || searchText.isEmpty()) {
            textListViewBoxOffline.setItems(FXCollections.observableArrayList(wordsList));
        } else {
            ArrayList<Word> searchList = new ArrayList<>();
            for (Word word : wordsList) {
                if (word.getSourceWord().startsWith(searchText)) {
                    searchList.add(word);
                }
            }
            textListViewBoxOffline.setItems(FXCollections.observableArrayList(searchList));
        }
    }

	@FXML
	void undo(ActionEvent event) {
		if (history.size() > 0) {
			future.push(this.word);
			this.word = history.pop();
			update(true);
		}
	}

	@FXML
	void redo(ActionEvent event) {
		if (future.size() > 0) {
			history.push(this.word);
			this.word = future.pop();
			update(true);
		}
	}

	@FXML
	void about(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../fxml/About.fxml"));
			Parent root1 = loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root1);
			stage.setScene(scene);
			stage.setTitle("About");
			stage.setResizable(false);
			stage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/assert/icon.png"));
			stage.show();
			aboutButton.setDisable(true);
			stage.setOnCloseRequest((WindowEvent) -> {
				aboutButton.setDisable(false);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void Trans() {
		if (!word.getSourceWord().equals("null")) {
			future.clear();
			history.push(this.word);
		}
		String source = InputTextField.getText().trim();
		Translate trans = new Translate(source, "", targetLang);
		trans.translateWord();
		this.word = trans.getWord();
	}

	void update(boolean flag) {
		stringTextArea.clear();
		OutputTextArea.clear();
		longOutputTextArea.clear();
		if (flag) {
			InputTextField.setText(this.word.getSourceWord());
		}
		int words = this.word.getSourceWord().split("\\s+").length;
		if (words == 1) {
			longOutputTextArea.setVisible(false);
			OutputTextArea.setVisible(true);
			stringTextArea.setVisible(true);
			OutputTextArea.setText(this.word.getTargetWord());
			stringTextArea.setText(this.word.getString());
			if (!this.word.getAudio().equals("none")) {
				audioButton.setDisable(false);
			} else {
				audioButton.setDisable(true);
			}
		} else {
			audioButton.setDisable(true);
			OutputTextArea.setVisible(false);
			stringTextArea.setVisible(false);
			longOutputTextArea.setVisible(true);
			longOutputTextArea.setText(this.word.getTargetWord());
		}

		if (history.size() > 0) {
			undoButton.setDisable(false);
		} else {
			undoButton.setDisable(true);
		}

		if (future.size() > 0) {
			redoButton.setDisable(false);
		} else {
			redoButton.setDisable(true);
		}
		String target = this.word.getTargetWord();
		target = target.replaceAll("\\s+", "");
		if (!target.equals("") && !target.equals("null")) {
			bookMarkButton.setDisable(false);
		} else {
			bookMarkButton.setDisable(true);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		redoButton.setDisable(true);
		undoButton.setDisable(true);
		bookMarkButton.setDisable(true);
		InputTextField.setOnKeyPressed(event -> {
			if (event.isShiftDown()) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					Trans();
					update(false);
					event.consume();
				}
			}
		});
		word = new Word();
		audioButton.setDisable(true);
		selectSourceBox.getItems().addAll(languages.keySet());
		selectTargetBox.getItems().addAll(languages.keySet());
		selectSourceBox.setValue("English");
		selectTargetBox.setValue("Vietnamese");
		sourceLang = languages.get(selectSourceBox.getValue());
		targetLang = languages.get(selectTargetBox.getValue());

		textListViewBoxOffline.setCellFactory(param -> new ListCell<Word>() {
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

		textListViewBoxOffline.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setWord(textListViewBoxOffline.getSelectionModel().getSelectedItem());
            if (newValue != null) {
				audioButton.setDisable(false);
				bookMarkButton.setDisable(false);
				editButton.setDisable(false);
				removeButton.setDisable(false);
				setWord(newValue);
				searchTextArea.setText(newValue.getSourceWord());
                OutputTextArea.setText(newValue.getTargetWord());
                stringTextArea.setText(newValue.getString());
                selectTargetBox.setValue(newValue.getTargetLang());
            } else {
                OutputTextArea.clear();
                stringTextArea.clear();
                selectTargetBox.setValue("Vietnamese");
            }
        });

	}

}
