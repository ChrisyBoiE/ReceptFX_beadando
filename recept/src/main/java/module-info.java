module com.recept.recept {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.recept.recept to javafx.fxml;
    exports com.recept.recept;
}