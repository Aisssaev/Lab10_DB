package com.university.lab10_db;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLLoginController {
    public SQLLoginController() throws SQLException {
    }

    @FXML
    public ComboBox<String> comboBox;
    @FXML
    public Label mySQL;
    @FXML
    public TextField username;
    @FXML
    public TextField database;
    @FXML
    public PasswordField password;
    @FXML
    public TextField port;
    @FXML
    public Button connect;


    String dbUrl = "jdbc:mysql://localhost:3306";
    String dbUser = "Test";
    String dbPassword = "hghghghg2006";

    public static Connection conn;

    @FXML
    protected void onConnectionButtonClick() throws SQLException, IOException {
        if (database.getText().isEmpty() || port.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, "Усі поля повинні бути заповнені", "Інформація про з'єднання");
            return;
        }
        dbUrl = "jdbc:mysql://" + database.getText() + ":" + port.getText();
        dbUser = username.getText();
        dbPassword = password.getText();

        conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        if (conn == null) {
            Utils.showAlert(Alert.AlertType.ERROR, "Помилка з'єднання", "Інформація про з'єднання");
            return;
        } else {
            Utils.showAlert(Alert.AlertType.INFORMATION, "З'єднання успішне", "Інформація про з'єднання");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("request-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage newStage = new Stage();
            newStage.setTitle("SQL Requests");
            newStage.setScene(scene);
            newStage.show();
            newStage.setFullScreen(true);
            //connect.getScene().getWindow().hide();
        }
    }
}
