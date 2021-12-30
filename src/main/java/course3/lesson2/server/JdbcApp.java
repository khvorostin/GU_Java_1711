package course3.lesson2.server;

import java.sql.*;

public class JdbcApp {

    private static Connection connection;
    private static Statement stmt;

    public JdbcApp() {
        try {
            Class.forName("org.sqlite.JDBC");
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
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
}
