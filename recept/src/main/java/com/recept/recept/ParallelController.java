package com.recept.recept;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ParallelController {
    private ScheduledExecutorService executor;

    public void showInMainView(BorderPane root) {
        // UI elemek létrehozása
        Label label1 = new Label("Label 1: Waiting...");
        Label label2 = new Label("Label 2: Waiting...");
        Button startButton = new Button("Start Párhuzamos Frissítés");

        // VBox az elemek elhelyezésére
        VBox content = new VBox(10, label1, label2, startButton);

        // Gomb eseménykezelő
        startButton.setOnAction(event -> startParallelUpdates(label1, label2));

        // Tartalom megjelenítése a BorderPane közepén
        root.setCenter(content);
    }

    private void startParallelUpdates(Label label1, Label label2) {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow(); // Leállítja az előző futtatást
        }

        executor = Executors.newScheduledThreadPool(2);

        // Label 1 frissítése 1 másodpercenként
        executor.scheduleAtFixedRate(() -> Platform.runLater(() ->
                label1.setText("Label 1: " + System.currentTimeMillis())
        ), 0, 1, TimeUnit.SECONDS);

        // Label 2 frissítése 2 másodpercenként
        executor.scheduleAtFixedRate(() -> Platform.runLater(() ->
                label2.setText("Label 2: " + System.currentTimeMillis())
        ), 0, 2, TimeUnit.SECONDS);
    }

    public void stop() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }
}
