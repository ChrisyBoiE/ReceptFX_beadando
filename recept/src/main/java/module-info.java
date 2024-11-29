module com.mozi.recept {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mozi.recept to javafx.fxml;
    exports com.mozi.recept;
}