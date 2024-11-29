package com.recept.recept;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OlvasController {
    public void showInMainView(BorderPane root) {
        // Táblázat létrehozása
        TableView<CombinedData> table = new TableView<>();

        // Oszlopok létrehozása
        TableColumn<CombinedData, Integer> idCol = new TableColumn<>("Étel ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("etelId"));

        TableColumn<CombinedData, String> etelNevCol = new TableColumn<>("Étel Név");
        etelNevCol.setCellValueFactory(new PropertyValueFactory<>("etelNev"));

        TableColumn<CombinedData, String> kategoriaNevCol = new TableColumn<>("Kategória Név");
        kategoriaNevCol.setCellValueFactory(new PropertyValueFactory<>("kategoriaNev"));

        TableColumn<CombinedData, String> hozzavaloNevCol = new TableColumn<>("Hozzávaló Név");
        hozzavaloNevCol.setCellValueFactory(new PropertyValueFactory<>("hozzavaloNev"));

        table.getColumns().addAll(idCol, etelNevCol, kategoriaNevCol, hozzavaloNevCol);

        // Adatok betöltése
        ObservableList<CombinedData> data = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT " +
                             "e.id AS etel_id, " +
                             "e.nev AS etel_nev, " +
                             "k.nev AS kategoria_nev, " +
                             "h.nev AS hozzavalo_nev " +
                             "FROM etelek e " +
                             "LEFT JOIN kategoriak k ON e.kategoriaid = k.id " +
                             "LEFT JOIN hasznalt ha ON e.id = ha.etelid " +
                             "LEFT JOIN hozzavalok h ON ha.hozzavaloid = h.id " +
                             "LIMIT 25")) {
            while (rs.next()) {
                data.add(new CombinedData(
                        rs.getInt("etel_id"),
                        rs.getString("etel_nev"),
                        rs.getString("kategoria_nev"),
                        rs.getString("hozzavalo_nev")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setItems(data);

        // Táblázat megjelenítése a főablakban
        root.setCenter(table);
    }
}
