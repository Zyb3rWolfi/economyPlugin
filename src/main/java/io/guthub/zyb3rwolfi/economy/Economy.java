package io.guthub.zyb3rwolfi.economy;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public final class Economy extends JavaPlugin {

    private Connection connection;
    private DatabaseManager databaseManager;
    private String prefix;
    @Override
    public void onEnable() {
        // Plugin startup logic

        saveResource("config.yml", false);
        saveDefaultConfig();
        int startingBalance = getConfig().getInt("starting_balance");
        prefix = getConfig().getString("prefix");

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
        getCommand("set").setExecutor(new SetMoney(this));
        getCommand("give").setExecutor(new GiveMoney(this));
        getCommand("baltop").setExecutor(new TopBalance(this));
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(this, startingBalance), this);

    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void sendMessage(String message, Player player) {
        String msg = prefix + message;
        player.sendMessage(msg);
    }
}
