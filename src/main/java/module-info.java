module com.university.lab10_db {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.university.lab10_db to javafx.fxml;
    exports com.university.lab10_db;
}