package com.recept.recept;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ModositController {
    public void showInMainView(BorderPane root) {
        // Űrlap elemek
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        // Input mezők
        ComboBox<Integer> idComboBox = new ComboBox<>();
        TextField nameField = new TextField();
        ComboBox<String> categoryComboBox = new ComboBox<>();
        TextField dateField = new TextField();
        Button loadButton = new Button("Betöltés");
        Button saveButton = new Button("Mentés");

        // Rekordok betöltése az ID lenyíló listába
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM etelek")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                idComboBox.getItems().add(rs.getInt("id"));
            }
        } catch (Exception e) {
            showAlert("Hiba", "Nem sikerült betölteni a rekordokat: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        // Kategóriák betöltése a kategória lenyíló listába
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT nev FROM kategoriak")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categoryComboBox.getItems().add(rs.getString("nev"));
            }
        } catch (Exception e) {
            showAlert("Hiba", "Nem sikerült betölteni a kategóriákat: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        // Betöltés gomb eseménykezelője
        loadButton.setOnAction(event -> {
            Integer selectedId = idComboBox.getValue();
            if (selectedId == null) {
                showAlert("Hiba", "Válassz egy rekordot a módosításhoz!", Alert.AlertType.ERROR);
                return;
            }

            // Rekord adatok betöltése
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT nev, kategoriaid, felirdatum FROM etelek WHERE id = ?")) {
                stmt.setInt(1, selectedId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    nameField.setText(rs.getString("nev"));

                    // Kategória azonosítót nevünkre alakítjuk
                    int categoryId = rs.getInt("kategoriaid");
                    try (PreparedStatement categoryStmt = conn.prepareStatement("SELECT nev FROM kategoriak WHERE id = ?")) {
                        categoryStmt.setInt(1, categoryId);
                        ResultSet categoryRs = categoryStmt.executeQuery();
                        if (categoryRs.next()) {
                            categoryComboBox.setValue(categoryRs.getString("nev"));
                        }
                    }

                    dateField.setText(rs.getString("felirdatum"));
                }
            } catch (Exception e) {
                showAlert("Hiba", "Nem sikerült betölteni a rekord adatait: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // Mentés gomb eseménykezelője
        saveButton.setOnAction(event -> {
            Integer selectedId = idComboBox.getValue();
            String name = nameField.getText();
            String category = categoryComboBox.getValue();
            String date = dateField.getText();

            if (selectedId == null || name.isEmpty() || category == null || date.isEmpty()) {
                showAlert("Hiba", "Minden mezőt ki kell tölteni a mentéshez!", Alert.AlertType.ERROR);
                return;
            }

            // Kategória azonosító lekérdezése
            int categoryId = -1;
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT id FROM kategoriak WHERE nev = ?")) {
                stmt.setString(1, category);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    categoryId = rs.getInt("id");
                } else {
                    showAlert("Hiba", "Nem található a kategória az adatbázisban!", Alert.AlertType.ERROR);
                    return;
                }
            } catch (Exception e) {
                showAlert("Hiba", "Nem sikerült lekérdezni a kategória azonosítót: " + e.getMessage(), Alert.AlertType.ERROR);
                return;
            }

            // Rekord frissítése az adatbázisban
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE etelek SET nev = ?, kategoriaid = ?, felirdatum = ? WHERE id = ?")) {
                stmt.setString(1, name);
                stmt.setInt(2, categoryId);
                stmt.setString(3, date);
                stmt.setInt(4, selectedId);
                stmt.executeUpdate();

                showAlert("Siker", "Rekord módosítása sikeres!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Hiba", "Nem sikerült a rekord módosítása: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // Űrlap elrendezése
        form.add(new Label("Rekord ID:"), 0, 0);
        form.add(idComboBox, 1, 0);
        form.add(loadButton, 2, 0);
        form.add(new Label("Étel neve:"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("Kategória:"), 0, 2);
        form.add(categoryComboBox, 1, 2);
        form.add(new Label("Felvétel dátuma:"), 0, 3);
        form.add(dateField, 1, 3);
        form.add(saveButton, 1, 4);

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
