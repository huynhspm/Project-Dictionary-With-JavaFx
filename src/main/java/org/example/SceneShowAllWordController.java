package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SceneShowAllWordController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Word> tableView;

    @FXML
    private TableColumn<Word, String> english, vietnamese;

    @FXML
    private Label labelDelete;

    private Word selectedWord;

    private ObservableList<Word> wordList;

    private Label labelSelectedWord;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelDelete.setStyle("-fx-background-color: #ffffff");

        labelSelectedWord = new Label("");
        labelSelectedWord.setTextFill(Color.DARKGREEN);
        labelSelectedWord.setVisible(false);
        anchorPane.getChildren().add(labelSelectedWord);

        ArrayList<Word> list; list = MainDictionary.dictionaryManagement.showAllWord();
        wordList = FXCollections.observableArrayList(list);
        english.setCellValueFactory(new PropertyValueFactory<>("word_target"));
        vietnamese.setCellValueFactory(new PropertyValueFactory<>("word_explain"));
        tableView.setItems(wordList);
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selectedWord = tableView.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                labelSelectedWord.setVisible(true);
                labelSelectedWord.setTranslateX(event.getX() + 3);
                labelSelectedWord.setTranslateY(event.getY() + 3);
                labelSelectedWord.setText(selectedWord.getWord_target());
            }
        });
    }

    @FXML
    private void mouseMove(MouseEvent event){
        if(selectedWord == null){
            return;
        }

        labelSelectedWord.setTranslateX(event.getX() + 3);
        labelSelectedWord.setTranslateY(event.getY() + 3);
    }

    @FXML
    private void clickLabelDelete(){
        if(selectedWord == null){
            return;
        }
        wordList.remove(selectedWord);
        MainDictionary.dictionaryManagement.deleteFromCommandline(selectedWord.getWord_target());
        selectedWord = null;
        labelSelectedWord.setVisible(false);
    }

    @FXML
    private void enterLabelDelete(){
        if(selectedWord == null){
            return;
        }
        labelDelete.setStyle("-fx-background-color: #999999");
    }

    @FXML
    private void exitLabelDelete(){
        labelDelete.setStyle("-fx-background-color: #ffffff");
    }
}