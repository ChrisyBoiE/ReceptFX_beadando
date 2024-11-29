module com.recept.recept {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive com.zaxxer.hikari;

    opens com.recept.recept to javafx.fxml;
    exports com.recept.recept;
}
