package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SceneInsertNewWordController {
    @FXML
    private TextField insertWord;

    @FXML
    private TextField explainWord;

    @FXML
    private void confirm(){
        if(!insertWord.getText().isEmpty() && !explainWord.getText().isEmpty()){
            MainDictionary.dictionaryManagement.
                    insertFromCommandline(insertWord.getText(), explainWord.getText());
            insertWord.setText("");
            explainWord.setText("");
        }
    }
}
