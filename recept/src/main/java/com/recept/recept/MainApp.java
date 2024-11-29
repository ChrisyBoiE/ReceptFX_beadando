package com.recept.recept;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    private BorderPane root; // A fő elrendezés

    @Override
    public void start(Stage stage) {
        // Fő elrendezés létrehozása
        root = new BorderPane();

        // Menü létrehozása
        MenuBar menuBar = new MenuBar();
        Menu databaseMenu = new Menu("Adatbázis");
        Menu parallelMenu = new Menu("Párhuzamos");

        // Menüpontok létrehozása
        MenuItem olvasMenu = new MenuItem("Olvas");
        MenuItem olvas2Menu = new MenuItem("Olvas2");
        MenuItem irMenu = new MenuItem("Ír");
        MenuItem modositMenu = new MenuItem("Módosít");
        MenuItem torolMenu = new MenuItem("Töröl");
        MenuItem parallelExecutionMenu = new MenuItem("Párhuzamos Frissítés");

        // Menüpontok hozzáadása a "Adatbázis" és "Párhuzamos" menühöz
        databaseMenu.getItems().addAll(olvasMenu, olvas2Menu, irMenu, modositMenu, torolMenu);
        parallelMenu.getItems().add(parallelExecutionMenu);

        // Menü hozzáadása a menüsávhoz
        menuBar.getMenus().addAll(databaseMenu, parallelMenu);

        // Menü eseménykezelők
        olvasMenu.setOnAction(event -> {
            OlvasController olvasController = new OlvasController();
            olvasController.showInMainView(root);
        });

        olvas2Menu.setOnAction(event -> {
            Olvas2Controller olvas2Controller = new Olvas2Controller();
            olvas2Controller.showInMainView(root);
        });

        irMenu.setOnAction(event -> {
            IrController irController = new IrController();
            irController.showInMainView(root);
        });

        modositMenu.setOnAction(event -> {
            ModositController modositController = new ModositController();
            modositController.showInMainView(root);
        });

        torolMenu.setOnAction(event -> {
            TorolController torolController = new TorolController();
            torolController.showInMainView(root);
        });

        // Párhuzamos frissítés menü eseménykezelő
        parallelExecutionMenu.setOnAction(event -> {
            ParallelController parallelController = new ParallelController();
            parallelController.showInMainView(root);
        });

        // Menü megjelenítése a fő elrendezés tetején
        root.setTop(menuBar);

        // Jelenet létrehozása és megjelenítése
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("JavaFX CRUD Alkalmazás");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
