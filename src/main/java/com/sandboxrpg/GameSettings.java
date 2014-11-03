package com.sandboxrpg;

import java.io.*;
import java.util.Properties;

public class GameSettings {

    private String settingsfile = "client.properties";
    private Properties settings = new Properties();
    public static GameSettings instance = new GameSettings();

    public GameSettings() {
        load();
    }

    private void load() {
        if (new File(settingsfile).exists()) {
            try {
                FileInputStream in = new FileInputStream(settingsfile);
                settings.load(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            generateSettingsFile();
            save();
        }
    }

    public String getSetting(String key) {
        return settings.getProperty(key);
    }

    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    public int getIntSetting(String key) {
        return Integer.parseInt(settings.getProperty(key));
    }

    public void setIntSetting(String key, int value) {
        settings.put(key, String.valueOf(value));
    }

    public boolean getBoolSetting(String key) {
        return settings.getProperty(key).equals("true");
    }

    public void setBoolSetting(String key, boolean value) {
        settings.put(key, String.valueOf(value));
    }

    private void save() {
        try {
            settings.store(new FileOutputStream(settingsfile), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateSettingsFile() {
        settings.put("resx", "1280");
        settings.put("resy", "720");
        settings.put("fullscreen", "false");
        settings.put("debug", "true");
    }
}
