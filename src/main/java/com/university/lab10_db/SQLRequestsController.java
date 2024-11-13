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
    public ComboBox<String> schemaComboBox;
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
        //schemaComboBox.setItems(getSchemaTable());
        tableComboBox.setItems(getTables());
    }

    @FXML
    protected void onTableComboBoxSelected() throws SQLException {
        var selectedItem = tableComboBox.getValue();
        if (selectedItem != null) {
            loadTableData(selectedItem);
        }
    }

    @FXML
    protected void onSaveChangesButton() {
    }

    private ObservableList<String> getTables() throws SQLException {
        ObservableList<String> tableNames = FXCollections.observableArrayList();
        var metaData = conn.getMetaData();
        var tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
        while (tables.next()) {
            tableNames.add(tables.getString("TABLE_NAME"));
        }
        tables.close();
        return tableNames;
    }

    private String findSchemaForTable(String tableName) throws SQLException {
        var metaData = conn.getMetaData();
        var schemas = metaData.getCatalogs();

        while (schemas.next()) {
            var schemaName = schemas.getString(1);
            var tables = metaData.getTables(schemaName, null, tableName, new String[]{"TABLE"});

            if (tables.next()) { // Якщо таблиця знайдена в цій схемі
                tables.close();
                schemas.close();
                return schemaName; // Повертаємо назву схеми
            }

            tables.close();
        }

        schemas.close();
        return null;
    }

    private void loadTableData(String tableName) throws SQLException {
        tableView.getColumns().clear();
        ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
        var stm = conn.createStatement();
        stm.execute("use " + findSchemaForTable(tableName));
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
        tableView.setEditable(true);
    }
}