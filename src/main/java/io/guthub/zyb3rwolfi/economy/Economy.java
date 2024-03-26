package io.guthub.zyb3rwolfi.economy;

import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Economy extends JavaPlugin {

    private Connection connection;
    private DatabaseManager databaseManager;
    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            databaseManager = new DatabaseManager(getDataFolder().getAbsolutePath() + "/money.db");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed:" + e.getMessage());
        }

        getCommand("balance").setExecutor(new ShowBalance(this));
        getCommand("add").setExecutor(new AddMoney(this));
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(this), this);

    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
