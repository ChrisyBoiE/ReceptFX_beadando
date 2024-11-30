module com.recept.recept {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive com.zaxxer.hikari;
    requires java.net.http;
    requires com.google.gson;
    requires java.naming;
    requires v20;

    opens com.recept.recept to javafx.fxml, javafx.controls, com.google.gson;
    exports com.recept.recept;
}
