package course3.lesson3.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcApp {

    private static Connection connection;
    private static Statement stmt;

    public JdbcApp() {
        try {
            Class.forName("org.sqlite.JDBC");
            connect();
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            disconnect();
        }
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:javachat.db");
        stmt = connection.createStatement();
    }

    public void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getNickByLoginPass(String login, String pass) throws SQLException {
        String nick = null;
        PreparedStatement ps = connection.prepareStatement("SELECT username FROM users WHERE login = ? AND pass = ?");
        ps.setString(1, login);
        ps.setString(2, pass);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                nick = rs.getString("username");
            }
        }

        return nick;
    }

    private int getIdByNick(String nick) throws SQLException {
        int id = 0;
        if (null == nick) {
            return id; // RETURN
        }

        PreparedStatement ps = connection.prepareStatement("SELECT id FROM users WHERE username = ?");
        ps.setString(1, nick);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                id = rs.getInt("id");
            }
        }

        return id;
    }

    public void saveMessageInDB(String from, String to, String messsage) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO messages (author_id, private_for, message) VALUES (?, ?, ?)");
            ps.setInt(1, getIdByNick(from));
            ps.setInt(2, getIdByNick(to));
            ps.setString(3, messsage);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<String> getHistoryForNick(String nick) {

        ArrayList<String> messagesHistory = new ArrayList<>();

        String query = "SELECT u.username, m.message, m.private_for " +
            " FROM messages AS m " +
            " INNER JOIN users AS u ON u.id = m.author_id " +
            " WHERE m.id IN (SELECT id FROM messages WHERE private_for IN (0, ?) OR author_id = ? ORDER BY id DESC LIMIT 100) " +
            " ORDER BY m.id";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            int id = getIdByNick(nick);
            ps.setInt(1, id);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String message = rs.getString("username") + ": " + rs.getString("message");
                    if (rs.getInt("private_for") > 0) {
                        message += " [Личное сообщение]";
                    }
                    messagesHistory.add(message);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return messagesHistory;
    }
}
