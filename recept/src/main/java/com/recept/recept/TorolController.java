package com.recept.recept;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TorolController {
    public void showInMainView(BorderPane root) {
        // Űrlap elemek
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        // Input mezők
        ComboBox<Integer> idComboBox = new ComboBox<>();
        Button deleteButton = new Button("Törlés");

        // Rekordok betöltése az ID lenyíló listába
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM etelek")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                idComboBox.getItems().add(rs.getInt("id")); // ID értékek betöltése
            }
        } catch (Exception e) {
            showAlert("Hiba", "Nem sikerült betölteni a rekordokat: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        // Törlés gomb eseménykezelője
        deleteButton.setOnAction(event -> {
            Integer selectedId = idComboBox.getValue();
            if (selectedId == null) {
                showAlert("Hiba", "Válassz egy rekordot a törléshez!", Alert.AlertType.ERROR);
                return;
            }

            // Rekord törlése az adatbázisból
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM etelek WHERE id = ?")) {
                stmt.setInt(1, selectedId);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    showAlert("Siker", "Rekord sikeresen törölve!", Alert.AlertType.INFORMATION);
                    idComboBox.getItems().remove(selectedId); // Törölt rekord eltávolítása a lenyíló listából
                } else {
                    showAlert("Hiba", "Nem található a rekord az adatbázisban!", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("Hiba", "Nem sikerült törölni a rekordot: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // Űrlap elrendezése
        form.add(new Label("Rekord ID:"), 0, 0);
        form.add(idComboBox, 1, 0);
        form.add(deleteButton, 1, 1);

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
