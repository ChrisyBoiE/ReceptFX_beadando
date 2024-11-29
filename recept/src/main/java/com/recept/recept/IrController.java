package com.recept.recept;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IrController {
    public void showInMainView(BorderPane root) {
        // Űrlap elemek
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        // Input mezők
        TextField nameField = new TextField();
        ComboBox<String> categoryComboBox = new ComboBox<>();
        TextField dateField = new TextField();
        Button saveButton = new Button("Mentés");

        // Kategóriák betöltése
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT nev FROM kategoriak")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categoryComboBox.getItems().add(rs.getString("nev")); // Csak a neveket töltjük be
            }
        } catch (Exception e) {
            showAlert("Hiba", "Nem sikerült betölteni a kategóriákat: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        // Mentés eseménykezelő
        saveButton.setOnAction(event -> {
            String name = nameField.getText();
            String category = categoryComboBox.getValue(); // Kategória név
            String date = dateField.getText();

            if (name.isEmpty() || category == null || date.isEmpty()) {
                showAlert("Hiba", "Minden mezőt ki kell tölteni!", Alert.AlertType.ERROR);
                return;
            }

            // Kategória azonosító lekérdezése
            int categoryId = -1; // Alapértelmezett hibás érték
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT id FROM kategoriak WHERE nev = ?")) {
                stmt.setString(1, category);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    categoryId = rs.getInt("id"); // Kategória ID lekérése
                } else {
                    showAlert("Hiba", "Nem található a kategória az adatbázisban!", Alert.AlertType.ERROR);
                    return;
                }
            } catch (Exception e) {
                showAlert("Hiba", "Hiba történt a kategória azonosító lekérdezésekor: " + e.getMessage(), Alert.AlertType.ERROR);
                return;
            }

            // Rekord mentése
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO etelek (nev, kategoriaid, felirdatum) VALUES (?, ?, ?)")) {
                stmt.setString(1, name);
                stmt.setInt(2, categoryId); // Kategória azonosító használata
                stmt.setString(3, date);
                stmt.executeUpdate();

                showAlert("Siker", "Rekord mentése sikeres!", Alert.AlertType.INFORMATION);

                // Mezők kiürítése
                nameField.clear();
                categoryComboBox.setValue(null);
                dateField.clear();

            } catch (Exception e) {
                showAlert("Hiba", "Nem sikerült a rekord mentése: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // Űrlap elrendezése
        form.add(new Label("Étel neve:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Kategória:"), 0, 1);
        form.add(categoryComboBox, 1, 1);
        form.add(new Label("Felvétel dátuma:"), 0, 2);
        form.add(dateField, 1, 2);
        form.add(saveButton, 1, 3);

        // Űrlap hozzáadása a főablakhoz
        root.setCenter(form);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
