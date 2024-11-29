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
        Menu forexMenu = new Menu("Forex");

        // Menüpontok létrehozása
        MenuItem olvasMenu = new MenuItem("Olvas");
        MenuItem olvas2Menu = new MenuItem("Olvas2");
        MenuItem irMenu = new MenuItem("Ír");
        MenuItem modositMenu = new MenuItem("Módosít");
        MenuItem torolMenu = new MenuItem("Töröl");
        MenuItem parallelExecutionMenu = new MenuItem("Párhuzamos Frissítés");
        MenuItem accountInfoMenu = new MenuItem("Számlainformációk");
        MenuItem currentPriceMenu = new MenuItem("Aktuális árak");
        MenuItem openPositionMenu = new MenuItem("Pozíció nyitás");
        MenuItem closePositionMenu = new MenuItem("Pozíció zárás");

        // Menüpontok hozzáadása
        databaseMenu.getItems().addAll(olvasMenu, olvas2Menu, irMenu, modositMenu, torolMenu);
        parallelMenu.getItems().add(parallelExecutionMenu);

        // Forex menüelemek hozzáadása
        if (!forexMenu.getItems().contains(accountInfoMenu)) {
            forexMenu.getItems().add(accountInfoMenu);
        }
        if (!forexMenu.getItems().contains(currentPriceMenu)) {
            forexMenu.getItems().add(currentPriceMenu);
        }
        if (!forexMenu.getItems().contains(openPositionMenu)) {
            forexMenu.getItems().add(openPositionMenu);
        }
        if (!forexMenu.getItems().contains(closePositionMenu)) {
            forexMenu.getItems().add(closePositionMenu);
        }

        // Menü hozzáadása a menüsávhoz
        menuBar.getMenus().addAll(databaseMenu, parallelMenu, forexMenu);

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

        //.setOnAction(event -> {
        //    ModositController modositController = new ModositController();
        //    modositController.showInMainView(root);
        //});

        //torolMenu.setOnAction(event -> {
        //    TorolController torolController = new TorolController();
        //    torolController.showInMainView(root);
        //});

        parallelExecutionMenu.setOnAction(event -> {
            ParallelController parallelController = new ParallelController();
            parallelController.showInMainView(root);
        });

        accountInfoMenu.setOnAction(event -> {
            ForexController forexController = new ForexController();
            forexController.showAccountInfo(root);
        });

        currentPriceMenu.setOnAction(event -> {
            ForexPriceController forexPriceController = new ForexPriceController();
            forexPriceController.showInMainView(root);
        });

        openPositionMenu.setOnAction(event -> {
            OpenPositionController openPositionController = new OpenPositionController();
            openPositionController.showInMainView(root);
        });

        closePositionMenu.setOnAction(event -> {
            ClosePositionController closePositionController = new ClosePositionController();
            closePositionController.showInMainView(root);
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
