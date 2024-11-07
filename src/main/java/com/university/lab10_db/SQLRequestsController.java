package com.university.lab10_db;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.university.lab10_db.SQLLoginController.conn;

public class SQLRequestsController {
    @FXML
    public Button saveChangesButton;
    @FXML
    public TableView tableView;
    @FXML
    public ComboBox<ComboBox<String>> schemaComboBox;
    @FXML
    public ComboBox<String> tableComboBox;
    @FXML
    protected VBox vBox;
    @FXML
    private Label welcomeText;

    public SQLRequestsController() throws SQLException {

    }

    @FXML
    private void initialize() throws SQLException {
        schemaComboBox.setItems(getSchemaTables());
        //tableComboBox.setItems(getSchemaTables());
    }

    @FXML
    protected void onTableComboBoxSelected() throws SQLException {
        var selectedSchema = schemaComboBox.getValue();
        if (selectedSchema != null) {
            var selectedTable = tableComboBox.getValue();
            if (selectedTable != null) {
                loadTableData(selectedTable);
            }
        }
    }

    @FXML
    protected void onSchemaComboBoxSelected() throws SQLException {
        var selectedItem = schemaComboBox.getValue();
        if (selectedItem != null) {
            //loadTableNames(selectedItem);
        }
    }

    @FXML
    protected void onSaveChangesButton() {
    }

    private ObservableList<ComboBox<String>> getSchemaTables() throws SQLException {
        ObservableList<ComboBox<String>> schemaTableList = FXCollections.observableArrayList();
        var metaData = conn.getMetaData();
        var schemas = metaData.getCatalogs();
        while (schemas.next()) {
            var schemaName = schemas.getString(1);
            ComboBox<String> tableComboBox = new ComboBox<>();
            tableComboBox.setPromptText(schemaName);

            var tables = metaData.getTables(schemaName, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                var tableName = tables.getString("TABLE_NAME");
                tableComboBox.getItems().add(tableName);
            }

            tables.close();

            schemaTableList.add(tableComboBox);
        }
        schemas.close();

        return schemaTableList;
    }

    private ObservableList<String> getAllSchemas() throws SQLException {
        ObservableList<String> schemaNames = FXCollections.observableArrayList();
        var metaData = conn.getMetaData();
        var resultSet = metaData.getCatalogs();

        while (resultSet.next()) {
            var schemaName = resultSet.getString(1);
            schemaNames.add(schemaName);
        }
        return schemaNames;
    }

    protected void loadTableNames(String schema) throws SQLException {
        tableComboBox.getItems().clear();
        var stm = conn.createStatement();
        var metaData = conn.getMetaData();
        var resultSet = metaData.getTables(null, schema, "%", new String[]{"TABLE"});
    }

    private void loadTableData(String tableName) throws SQLException {
        tableView.getColumns().clear();
        ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
        var stm = conn.createStatement();
        var resultSet = stm.executeQuery("select * from " + tableName);

        var metaData = resultSet.getMetaData();
        var columnCount = metaData.getColumnCount();
        var observableList = FXCollections.observableArrayList();

        tableView.getColumns().clear();
        tableView.setFixedCellSize(40);
        for (int i = 0; i < columnCount; i++) {
            final int columnIndex = i;
            TableColumn<Map<String, String>, String> tableColumn = new TableColumn<>(metaData.getColumnName(i + 1));
            tableColumn.setCellValueFactory(data -> {
                try {
                    return new SimpleStringProperty(data.getValue().get(metaData.getColumnName(columnIndex + 1)));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            tableView.getColumns().add(tableColumn);
        }
        vBox.setMinWidth(tableView.getWidth());
        while (resultSet.next()) {
            Map<String, String> row = new HashMap<>();
            for (int i = 0; i < columnCount; i++) {
                row.put(metaData.getColumnName(i + 1), resultSet.getString(i + 1));
            }
            observableList.add(row);
        }
        tableView.setItems(observableList);
    }


//    @FXML
//    protected void onExecuteButtonClick() {
//        try (var statement = conn.createStatement()) {
//            boolean hasResultSet = statement.execute(queryArea.getText());
//            if (hasResultSet) {
//                try (ResultSet resultSet = statement.getResultSet()) {
//                    var metaData = resultSet.getMetaData();
//                    var columnCount = metaData.getColumnCount();
//                    var observableList = FXCollections.observableArrayList();
//                    tableView.getColumns().clear();
//                    tableView.setFixedCellSize(40);
//                    for (int i = 0; i < columnCount; i++) {
//                        final int columnIndex = i;
//                        TableColumn<Map<String, String>, String> tableColumn= new TableColumn<>(metaData.getColumnName(i+1));
//                        tableColumn.setCellValueFactory(data -> {
//                            try {
//                                return new SimpleStringProperty(data.getValue().get(metaData.getColumnName(columnIndex + 1)));
//                            } catch (SQLException e) {
//                                throw new RuntimeException(e);
//                            }
//                        });
//                        tableView.getColumns().add(tableColumn);
//                    }
//                    vBox.setMinWidth(tableView.getWidth());
//                    while (resultSet.next()) {
//                        Map<String, String> row = new HashMap<>();
//                        for (int i = 0; i < columnCount; i++) {
//                            row.put(metaData.getColumnName(i + 1), resultSet.getString(i + 1));
//                        }
//                        observableList.add(row);
//                    }
//                    tableView.setItems(observableList);
//                    showAlert(Alert.AlertType.INFORMATION, "Запит успішно виконано -> " + queryArea.getText(), "Результат SQL-запиту");
//                }
//            } else {
//                showAlert(Alert.AlertType.INFORMATION, "Запит успішно виконано -> " + queryArea.getText(), "Результат SQL-запиту");
//            }
//        } catch (SQLException e) {
//            showAlert(Alert.AlertType.ERROR, "Помилка при виконанні запиту: " + e.getMessage(), "Результат SQL-запиту");
//            throw new RuntimeException(e);
//        }
//    }

//    @FXML
//    protected void onCSVButtonClick() {
//        try(var stmt = conn.createStatement()) {
//            FileWriter csvWriter = new FileWriter("result.csv");
//
//            ResultSet rs = stmt.executeQuery(queryArea.getText());
//            int columns = rs.getMetaData().getColumnCount();
//
//            for (int i = 1; i <= columns; i++) {
//                csvWriter.append(rs.getMetaData().getColumnName(i)).append(",");
//            }
//            csvWriter.append("\n");
//
//            while (rs.next()) {
//                for (int i = 1; i <= columns; i++) {
//                    csvWriter.append(rs.getString(i)).append(",");
//                }
//                csvWriter.append("\n");
//            }
//
//            csvWriter.flush();
//            showAlert(Alert.AlertType.INFORMATION, "Дані збережено до файлу result.csv", "Збереження фалйу");
//        } catch (SQLException | IOException e) {
//            showAlert(Alert.AlertType.ERROR, "Помилка збереження: " + e.getMessage(), "Збереження фалйу");
//            throw new RuntimeException(e);
//        }
//
//    }


}