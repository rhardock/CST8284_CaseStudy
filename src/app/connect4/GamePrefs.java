package app.connect4;

import java.io.*;
import java.util.Properties;

public class GamePrefs {
    private static final String FILE_PATH = "settings.properties";
    private Properties props = new Properties();

    public GamePrefs() {
        load();
    }

    // Load settings from disk
    public void load() {
        try (FileInputStream in = new FileInputStream(FILE_PATH)) {
            props.load(in);
        } catch (IOException e) {
            // If file doesn't exist, set defaults
            props.setProperty("rows", "6");
            props.setProperty("cols", "7");
            props.setProperty("winCount", "0");
            props.setProperty("lossCount", "0");
            props.setProperty("drawCount", "0");
        }
    }

    // Save settings to disk
    public void save(int rows, int cols, int wins, int losses, int draws) {
        props.setProperty("rows", String.valueOf(rows));
        props.setProperty("cols", String.valueOf(cols));
        props.setProperty("winCount", String.valueOf(wins));
        props.setProperty("lossCount", String.valueOf(losses));
        props.setProperty("drawCount", String.valueOf(draws));

        try (FileOutputStream out = new FileOutputStream(FILE_PATH)) {
            props.store(out, "Connect 4 User Preferences");
        } catch (IOException e) {
            System.err.println("Error saving preferences: " + e.getMessage());
        }
    }

    public int getRows() { return Integer.parseInt(props.getProperty("rows", "6")); }
    public int getCols() { return Integer.parseInt(props.getProperty("cols", "7")); }
    public int getWinCount() { return Integer.parseInt(props.getProperty("winCount", "0")); }
    public int getLossCount() { return Integer.parseInt(props.getProperty("lossCount", "0")); }
    public int getDrawCount() { return Integer.parseInt(props.getProperty("drawCount", "0")); }
}
