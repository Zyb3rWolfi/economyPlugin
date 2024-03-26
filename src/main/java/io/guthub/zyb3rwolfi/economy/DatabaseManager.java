package io.guthub.zyb3rwolfi.economy;

import org.bukkit.entity.Player;

import java.sql.*;

public class DatabaseManager {

    private final Connection connection;

    public DatabaseManager(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
            CREATE TABLE IF NOT EXISTS players (
            uuid TEXT PRIMARY KEY,
            username TEXT NOT NULL,
            money INTEGER NOT NULL DEFAULT 0
            )
        
        """);
        }

    }



    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public int getPlayerBalance(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT money FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("money");
            } else {
                return 0;
            }
        }
    }

    public int addPlayerToDB(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players VALUES (? , ?, ?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, player.displayName().toString());
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();

            return 1;
        }
    }
}
