package com.recept.recept;

public class Launcher {
    public static void main(String[] args) {
        System.setProperty("javafx.preloader", "com.recept.recept.MyPreloader");
        MainApp.main(args);
    }
}