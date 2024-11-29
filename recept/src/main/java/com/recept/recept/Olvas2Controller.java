package com.recept.recept;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Olvas2Controller {
    public void showInMainView(BorderPane root) {
        // Űrlap elemek
        TextField searchField = new TextField();
        searchField.setPromptText("Keresés név szerint");

        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("Kategória kiválasztása");

        CheckBox includeIngredientsCheckBox = new CheckBox("Hozzávalók megjelenítése");

        Button searchButton = new Button("Keresés");

        // Táblázat létrehozása
        TableView<CombinedData> table = new TableView<>();

        TableColumn<CombinedData, Integer> idCol = new TableColumn<>("Étel ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("etelId"));

        TableColumn<CombinedData, String> etelNevCol = new TableColumn<>("Étel Név");
        etelNevCol.setCellValueFactory(new PropertyValueFactory<>("etelNev"));

        TableColumn<CombinedData, String> kategoriaNevCol = new TableColumn<>("Kategória Név");
        kategoriaNevCol.setCellValueFactory(new PropertyValueFactory<>("kategoriaNev"));

        TableColumn<CombinedData, String> hozzavaloNevCol = new TableColumn<>("Hozzávaló Név");
        hozzavaloNevCol.setCellValueFactory(new PropertyValueFactory<>("hozzavaloNev"));

        table.getColumns().addAll(idCol, etelNevCol, kategoriaNevCol, hozzavaloNevCol);


        // Kategória ComboBox feltöltése
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT nev FROM kategoriak")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categoryComboBox.getItems().add(rs.getString("nev"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Keresési funkció
        searchButton.setOnAction(event -> {
            String searchTerm = searchField.getText();
            String selectedCategory = categoryComboBox.getValue();
            boolean includeIngredients = includeIngredientsCheckBox.isSelected();

            ObservableList<CombinedData> data = FXCollections.observableArrayList();

            // SQL lekérdezés összeállítása
            String query = "SELECT e.id AS etel_id, e.nev AS etel_nev, k.nev AS kategoria_nev";
            if (includeIngredients) {
                query += ", h.nev AS hozzavalo_nev " +
                        "FROM etelek e " +
                        "LEFT JOIN kategoriak k ON e.kategoriaid = k.id " +
                        "LEFT JOIN hasznalt ha ON e.id = ha.etelid " +
                        "LEFT JOIN hozzavalok h ON ha.hozzavaloid = h.id ";
            } else {
                query += " FROM etelek e " +
                        "LEFT JOIN kategoriak k ON e.kategoriaid = k.id ";
            }

            // Szűrés hozzáadása
            query += "WHERE e.nev LIKE ? ";
            if (selectedCategory != null) {
                query += "AND k.nev = ? ";
            }
            query += "LIMIT 10";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, "%" + searchTerm + "%");
                if (selectedCategory != null) {
                    pstmt.setString(2, selectedCategory);
                }

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    data.add(new CombinedData(
                            rs.getInt("etel_id"),
                            rs.getString("etel_nev"),
                            rs.getString("kategoria_nev"),
                            includeIngredients ? rs.getString("hozzavalo_nev") : null
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            table.setItems(data);
        });

        // Űrlap és táblázat megjelenítése
        VBox form = new VBox(10, searchField, categoryComboBox, includeIngredientsCheckBox, searchButton);
        root.setTop(form); // Az űrlap a tetején jelenik meg
        root.setCenter(table); // A táblázat a központi részben jelenik meg
    }
}
