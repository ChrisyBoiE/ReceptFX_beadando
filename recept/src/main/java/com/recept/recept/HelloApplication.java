package com.recept.recept;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private BorderPane root;

    @Override
    public void start(Stage stage) {
        // Fő elrendezés
        root = new BorderPane();

        // Menü létrehozása
        MenuBar menuBar = new MenuBar();
        Menu databaseMenu = new Menu("Adatbázis");
        MenuItem olvasMenu = new MenuItem("Olvas");
        MenuItem olvas2Menu = new MenuItem("Olvas2");

        databaseMenu.getItems().addAll(olvasMenu, olvas2Menu);
        menuBar.getMenus().add(databaseMenu);

        root.setTop(menuBar);

        // Menüfunkciók
        olvasMenu.setOnAction(event -> new OlvasController().showInMainView(root));
        olvas2Menu.setOnAction(event -> new Olvas2Controller().showInMainView(root));

        // Alapértelmezett jelenet
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("JavaFX CRUD Alkalmazás");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
