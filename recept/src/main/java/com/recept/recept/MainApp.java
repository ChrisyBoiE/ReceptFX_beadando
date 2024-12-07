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

        // Menü és menüpontok létrehozása
        MenuBar menuBar = new MenuBar();

        Menu databaseMenu = new Menu("Adatbázis");
        Menu parallelMenu = new Menu("Párhuzamos");
        Menu forexMenu = new Menu("Forex");

        MenuItem olvasMenu = new MenuItem("Olvas");
        MenuItem olvas2Menu = new MenuItem("Olvas2");
        MenuItem irMenu = new MenuItem("Ír");
        MenuItem modositMenu = new MenuItem("Módosít");
        MenuItem torolMenu = new MenuItem("Töröl");
        MenuItem parallelExecutionMenu = new MenuItem("Párhuzamos Frissítés");
        MenuItem accountInfoMenu = new MenuItem("Számlainformációk");
        MenuItem currentPriceMenu = new MenuItem("Aktuális árak");
        MenuItem historicalPricesMenu = new MenuItem("Historikus árak");
        MenuItem openPositionMenu = new MenuItem("Pozíció nyitás");
        MenuItem closePositionMenu = new MenuItem("Pozíció zárás");
        MenuItem openPositionsMenu = new MenuItem("Nyitott pozíciók");

        // Menüpontok hozzárendelése a menükhöz
        databaseMenu.getItems().addAll(olvasMenu, olvas2Menu, irMenu, modositMenu, torolMenu);
        parallelMenu.getItems().add(parallelExecutionMenu);
        forexMenu.getItems().addAll(
                accountInfoMenu,
                currentPriceMenu,
                historicalPricesMenu,
                openPositionMenu,
                closePositionMenu,
                openPositionsMenu
        );

        // Menük hozzáadása a MenüSávhoz
        menuBar.getMenus().addAll(databaseMenu, parallelMenu, forexMenu);

        // Menü eseménykezelők (feltételezzük, hogy a controller osztályok léteznek és ugyanebben a csomagban vannak)
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

        accountInfoMenu.setOnAction(event -> {
            AccountInfoController controller = new AccountInfoController();
            controller.showInMainView(root);
        });

        currentPriceMenu.setOnAction(event -> {
            PricePollingController controller = new PricePollingController();
            controller.showInMainView(root);
        });

        historicalPricesMenu.setOnAction(event -> {
            HistoricalPricesController controller = new HistoricalPricesController();
            controller.showInMainView(root);
        });

        openPositionMenu.setOnAction(event -> {
            OpenPositionController controller = new OpenPositionController();
            controller.showInMainView(root);
        });

        closePositionMenu.setOnAction(event -> {
            ClosePositionController controller = new ClosePositionController();
            controller.showInMainView(root);
        });

        openPositionsMenu.setOnAction(event -> {
            OpenPositionsController controller = new OpenPositionsController();
            controller.showInMainView(root);
        });

        parallelExecutionMenu.setOnAction(event -> {
            ParallelController parallelController = new ParallelController();
            parallelController.showInMainView(root);
        });

        // Menü elhelyezése a fő elrendezés tetején
        root.setTop(menuBar);

        // Jelenet és Stage beállítása
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("JavaFX Forex Alkalmazás");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Indítás a MainApp osztály megadásával és az argumentumok átadásával
        Application.launch(MainApp.class, args);
    }
}
