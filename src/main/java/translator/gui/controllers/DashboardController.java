package translator.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Stack;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
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
  private JFXComboBox<String> selectSourceBox;

  @FXML
  private JFXComboBox<String> selectTargetBox;

  @FXML
  private JFXButton swapButton;

  @FXML
  private JFXListView<?> textListViewBox;

  @FXML
  private JFXButton undoButton;

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
    Trans();
    update(false);
  }

  @FXML
  void outputText(MouseEvent event) {

  }

  @FXML
  void bookMark(ActionEvent event) {
    int words = this.word.getSourceWord().split("\\s+").length;
    if (words == 1) {
      Database database = new Database();
      database.createTable(this.word.getSourceWord(), this.word.getTargetWord(),
          this.word.getString(), this.word.getAudio(), selectTargetBox.getValue());
      database.close();
    } else {
      System.err.println("Please enter a single word");
    }
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
  void textListView(ActionEvent event) {

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
      longOutputTextArea.setDisable(true);
      OutputTextArea.setText(this.word.getTargetWord());
      stringTextArea.setText(this.word.getString());
      if (!this.word.getAudio().equals("none")) {
        audioButton.setDisable(false);
      } else {
        audioButton.setDisable(true);
      }
    } else {
      audioButton.setDisable(true);
      longOutputTextArea.setDisable(false);
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

  }

}