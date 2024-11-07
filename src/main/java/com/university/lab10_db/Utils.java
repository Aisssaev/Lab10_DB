package com.university.lab10_db;

import javafx.scene.control.Alert;

public class Utils {
    public static void showAlert(Alert.AlertType alertType, String message, String title) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
