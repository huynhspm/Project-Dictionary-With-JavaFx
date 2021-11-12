package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SceneLookUpController implements Initializable {
    private String meaning;
    private boolean API;

    @FXML
    private TextField insertWord;

    @FXML
    private TextArea explainWord;

    @FXML
    private Label labelDelete, labelPronounce, labelEdit, labelConfirm, labelAPI;

    @FXML
    private ListView<String> suggestWord;

    @FXML
    private void enterLabelDelete(){
        if(!insertWord.getText().isEmpty() && !explainWord.getText().isEmpty()) {
            labelDelete.setStyle("-fx-background-color: #0093cf");
        }
    }

    @FXML
    private void exitLabelDelete(){
        labelDelete.setStyle("-fx-background-color: #8dd1bb");
    }

    @FXML
    private void clickLabelDelete(){
        if(!insertWord.getText().isEmpty() && !explainWord.getText().isEmpty()){
            MainDictionary.dictionaryManagement.deleteFromCommandline(insertWord.getText());
            insertWord.setText("");
            explainWord.setText("");
            explainWord.setEditable(false);
            meaning = null;
        }
    }

    @FXML
    private void enterLabelEdit(){
        if(!insertWord.getText().isEmpty() && !explainWord.getText().isEmpty()) {
            labelEdit.setStyle("-fx-background-color: #0093cf");
        }
    }

    @FXML
    private void exitLabelEdit(){
        labelEdit.setStyle("-fx-background-color: #8dd1bb");
    }

    @FXML
    private void clickLabelEdit(){
        if(!insertWord.getText().isEmpty() && !explainWord.getText().isEmpty()){
            explainWord.setEditable(true);
            meaning = explainWord.getText();
        }
    }

    @FXML
    private void enterLabelConfirm(){
        if(API || (meaning != null && !meaning.equals(explainWord.getText()))){
            labelConfirm.setStyle("-fx-background-color: #0093cf");
        }
    }

    @FXML
    private void exitLabelConfirm(){
        labelConfirm.setStyle("-fx-background-color: #8dd1bb");
    }

    @FXML
    private void clickLabelConfirm() throws Exception{
        if(API){
            explainWord.setText(new APITranslate().APITranslator(insertWord.getText()));
            return;
        }
        if(meaning != null && !meaning.equals(explainWord.getText())){
            MainDictionary.dictionaryManagement.deleteFromCommandline(insertWord.getText());
            MainDictionary.dictionaryManagement.insertFromCommandline(insertWord.getText(), explainWord.getText());
            explainWord.setEditable(false);
            insertWord.setText("");
            explainWord.setText("");
            meaning = null;
        }
    }

    @FXML
    private void enterLabelAPI(){
        labelAPI.setStyle("-fx-background-color: #0093cf");
    }

    @FXML
    private void exitLabelAPI(){
        labelAPI.setStyle("-fx-background-color: #8dd1bb");
    }

    @FXML
    private void clickLabelAPI(){
        explainWord.setEditable(false);
        insertWord.setText("");
        explainWord.setText("");
        meaning = null;
        API = !API;
    }

    @FXML
    private void clickLabelPronounce(){
        if(!insertWord.getText().isEmpty() && !explainWord.getText().isEmpty()) {
            new APIPronounce().TextToSpeech(insertWord.getText());
        }
    }

    @FXML
    private void enterLabelPronounce(){
        if(!insertWord.getText().isEmpty() && !explainWord.getText().isEmpty()) {
            labelPronounce.setStyle("-fx-background-color: #0093cf");
        }
    }

    @FXML
    private void exitLabelPronounce(){
        labelPronounce.setStyle("-fx-background-color: #ffffff");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        API = false;
        labelDelete.setStyle("-fx-background-color: #8dd1bb");
        labelPronounce.setStyle("-fx-background-color: #ffffff");

        insertWord.textProperty().addListener((observableValue, lastWord, newWord)-> {
            if(!API) {
                ArrayList<Word> list = new WordListLookUp(newWord).getList();
                suggestWord.getItems().clear();
                suggestWord.setVisible(false);

                explainWord.setText("");
                explainWord.setPromptText("EXPLAIN WORD");

                if (!list.isEmpty()) {
                    suggestWord.setVisible(true);
                    list.forEach(word -> suggestWord.getItems().add(word.getWord_target()));
                    if (list.get(0).getWord_target().equals(newWord)) {
                        insertWord.setText(newWord);
                        explainWord.setText(solveString(list.get(0).getWord_explain()));
                    }
                }
            }
        });

        suggestWord.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String word = suggestWord.getSelectionModel().getSelectedItem();
            insertWord.setText(word);
        });
    }

    private String solveString(String s){
        int cnt = 0;
        int index = 0;
        for(int i = 0; i < s.length(); ++ i){
            if(s.charAt(i) == ' '){
                index = i;
            }
            cnt ++;
            if(cnt >= 50){
                return s.substring(0, index) + '\n' + s.substring(index + 1);
            }
        }
        return s;
    }
}
