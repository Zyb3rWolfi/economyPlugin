package io.guthub.zyb3rwolfi.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class AddMoney implements CommandExecutor {
    private Economy economy;

    public AddMoney(Economy economy) {
        this.economy = economy;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player");
            return true;
        }

        String playerName = args[0];
        Player targetPlayer = Bukkit.getServer().getPlayer(playerName);
        if (targetPlayer == null) {
            Player player = (Player) sender;
            player.sendMessage("Player is not found");
            return true;
        }

        int amount;
        amount = Integer.parseInt(args[1]);
        Player player = (Player) sender;

        economy.getDatabaseManager().addMonetToPlayer(targetPlayer, amount);

        try {
            int balance = economy.getDatabaseManager().getPlayerBalance(targetPlayer);
            player.sendMessage("Your balance: " + balance);
        } catch (SQLException e) {

        }

        return true;
    }
}
