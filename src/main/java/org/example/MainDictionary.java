package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainDictionary extends Application {
    static org.example.DictionaryManagement dictionaryManagement = new org.example.DictionaryManagement();

    @Override
    public void start(Stage stage) throws IOException {
        dictionaryManagement.insertFromFile();
        Scene scene = new Scene(loadFXML("Menu"));
        scene.getStylesheets().add(getClass().getResource("graphics.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainDictionary.class.getResource("scene" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
