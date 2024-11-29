package com.recept.recept;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClosePositionController {
    public void showInMainView(BorderPane root) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label idLabel = new Label("Pozíció ID:");
        TextField idField = new TextField();

        Button closeButton = new Button("Pozíció zárás");
        closeButton.setOnAction(event -> {
            String positionId = idField.getText();

            // Itt implementáld az API hívást a pozíció zárására
            System.out.println("Pozíció zárása ID: " + positionId);
        });

        layout.getChildren().addAll(idLabel, idField, closeButton);
        root.setCenter(layout);
    }
}
