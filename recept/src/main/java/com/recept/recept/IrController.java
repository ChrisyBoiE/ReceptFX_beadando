package com.recept.recept;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class IrController {
    public void show(Stage primaryStage) {
        TextField nameField = new TextField();
        nameField.setPromptText("Étel neve");

        Button saveButton = new Button("Mentés");

        saveButton.setOnAction(event -> {
            String name = nameField.getText();

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO etelek (nev) VALUES (?)")) {
                pstmt.setString(1, name);
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(nameField, saveButton);
        Scene scene = new Scene(vbox);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Új Rekord Hozzáadása");
        stage.show();
    }
}
