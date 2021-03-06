package me.bryangaming.recoverhealth.manager;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class FileManager extends YamlConfiguration {

    private final String fileName;
    private final Plugin plugin;
    private final File file;

    public FileManager(Plugin plugin, String fileName, String fileExtension,
                         File folder) {
        this.fileName = fileName + (fileName.endsWith(fileExtension) ? "" : fileExtension);
        this.plugin = plugin;

        this.file = new File(folder, this.fileName);
        this.createFile();
    }

    public FileManager(Plugin plugin, String fileName) {
        this(plugin, fileName, ".yml");
    }

    public FileManager(Plugin plugin, String fileName, String fileExtension) {
        this(plugin, fileName, fileExtension, plugin.getDataFolder());
    }

    private void createFile() {
        try {
            if (file.exists()) {

                if (this.plugin.getResource(this.fileName) != null) {
                    this.plugin.saveResource(this.fileName, false);
                }

                load(file);
                return;
            }

            if (plugin.getResource(fileName) != null) {
                plugin.saveResource(fileName, false);
            } else {
                save(file);
            }

            load(file);

        } catch (InvalidConfigurationException | IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Creation of Configuration '" + fileName + "' failed.", e);
        }

    }


    public void save() {
        try {
            save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Save of the file '" + fileName + "' failed.", e);
        }
    }

    public void reload() {
        try {
            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().log(Level.SEVERE, "Reload of the file '" + fileName + "' failed.", e);
        }
    }

}