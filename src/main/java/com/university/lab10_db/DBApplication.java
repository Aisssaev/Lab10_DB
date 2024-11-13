package com.university.lab10_db;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;


public class DBApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DBApplication.class.getResource("request-view.fxml"));
        Locale.setDefault(new Locale("uk", "UA"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Table");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}