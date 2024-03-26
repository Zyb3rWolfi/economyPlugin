package io.guthub.zyb3rwolfi.economy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class OnPlayerJoin  implements Listener {

    private Economy economy;
    public OnPlayerJoin(Economy economy) {
        this.economy = economy;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try {
            economy.getDatabaseManager().addPlayerToDB(player);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
