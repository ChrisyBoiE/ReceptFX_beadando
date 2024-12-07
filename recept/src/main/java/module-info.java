module com.recept.recept {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.xerial.sqlitejdbc;
    requires com.zaxxer.hikari;
    requires org.slf4j;
    requires v20;
    requires java.net.http;

    exports com.recept.recept;
}
