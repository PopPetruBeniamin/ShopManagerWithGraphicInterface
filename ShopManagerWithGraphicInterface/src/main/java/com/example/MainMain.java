package com.example;

import com.example.lab4_map.Config.SettingsManager;
import com.example.lab4_map.MainApplication;
import com.example.lab4_map.main;

public class MainMain {
    public static void main(String[] args) {
        SettingsManager settings = new SettingsManager("src\\main\\java\\com\\example\\lab4_map\\Config\\settings.properties");
        if(settings.getProperty("Start").equalsIgnoreCase("javafx")){
            MainApplication.main(args);
        } else if(settings.getProperty("Start").equalsIgnoreCase("console")) {
            main.main(args);
        }
    }
}
