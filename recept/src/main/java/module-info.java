module com.recept.recept {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.recept.recept to javafx.fxml;
    exports com.recept.recept;
}
