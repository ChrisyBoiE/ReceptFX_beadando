package com.recept.recept;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class OpenPositionController {
    public void showInMainView(BorderPane root) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label pairLabel = new Label("Devizapár:");
        ComboBox<String> pairBox = new ComboBox<>();
        pairBox.getItems().addAll("EUR/USD", "USD/JPY", "GBP/USD");

        Label amountLabel = new Label("Mennyiség:");
        TextField amountField = new TextField();

        Label directionLabel = new Label("Irány:");
        ToggleGroup directionGroup = new ToggleGroup();
        RadioButton buyButton = new RadioButton("Vétel");
        RadioButton sellButton = new RadioButton("Eladás");
        buyButton.setToggleGroup(directionGroup);
        sellButton.setToggleGroup(directionGroup);

        Button openButton = new Button("Pozíció nyitás");
        openButton.setOnAction(event -> {
            String pair = pairBox.getValue();
            String amount = amountField.getText();
            String direction = buyButton.isSelected() ? "Vétel" : "Eladás";

            // Itt implementáld az API hívást a pozíció megnyitására
            System.out.println("Pozíció nyitása: " + pair + ", " + amount + ", " + direction);
        });

        layout.getChildren().addAll(pairLabel, pairBox, amountLabel, amountField, directionLabel, buyButton, sellButton, openButton);
        root.setCenter(layout);
    }
}
