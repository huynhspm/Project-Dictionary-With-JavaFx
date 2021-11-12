package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class SceneMenuController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label labelHome, labelSearch, labelAdd, labelShow, labelQuit;

    private Parent lastRoot;
    private String current;

    @FXML
    private void clickHome() throws Exception{
        solveMouseClick("Home");
    }

    @FXML
    private void enterHome(){
        labelHome.setVisible(true);
    }

    @FXML
    private void exitHome(){
        if(!current.equals("Home")) {
            labelHome.setVisible(false);
        }
    }

    @FXML
    private void clickSearch() throws Exception{
        solveMouseClick("LookUp");
    }

    @FXML
    private void enterSearch(){
        labelSearch.setVisible(true);
    }

    @FXML
    private void exitSearch(){
        if(!current.equals("LookUp")) {
            labelSearch.setVisible(false);
        }
    }

    @FXML
    private void clickAdd() throws Exception{
        solveMouseClick("InsertNewWord");
    }

    @FXML
    private void enterAdd(){
        labelAdd.setVisible(true);
    }

    @FXML
    private void exitAdd(){
        if(!current.equals("InsertNewWord")) {
            labelAdd.setVisible(false);
        }
    }

    @FXML
    private void clickShow() throws Exception{
        solveMouseClick("ShowAllWord");
    }

    @FXML
    private void enterShow(){
        labelShow.setVisible(true);
    }

    @FXML
    private void exitShow(){
        if(!current.equals("ShowAllWord")) {
            labelShow.setVisible(false);
        }
    }

    @FXML
    private void clickQuit(MouseEvent event) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("QUIT");
        alert.setHeaderText("ALERT QUIT");
        alert.setContentText("CHOOSE YOUR OPTION");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == buttonTypeYes) {
            MainDictionary.dictionaryManagement.dictionaryExportToFile();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void enterQuit(){
        labelQuit.setVisible(true);
    }

    @FXML
    private void exitQuit(){
        labelQuit.setVisible(false);
    }


    private void solveMouseClick(String s) throws Exception{
        if(current.equals(s)){
            return;
        }

        anchorPane.getChildren().remove(lastRoot);
        Parent currentRoot = MainDictionary.loadFXML(s);
        anchorPane.getChildren().add(currentRoot);

        lastRoot = currentRoot;
        current = s;

        labelHome.setVisible(false);
        labelShow.setVisible(false);
        labelSearch.setVisible(false);
        labelAdd.setVisible(false);
        labelQuit.setVisible(false);

        if(current.equals("Home")){
            labelHome.setVisible(true);
        }
        else if(current.equals("InsertNewWord")){
            labelAdd.setVisible(true);
        }
        else if(current.equals("LookUp")){
            labelSearch.setVisible(true);
        }
        else if(current.equals("ShowAllWord")){
            labelShow.setVisible(true);
        }
        else{
            labelQuit.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            current = "Home";
            Parent currentRoot = MainDictionary.loadFXML("Home");
            anchorPane.getChildren().add(currentRoot);
            lastRoot = currentRoot;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
