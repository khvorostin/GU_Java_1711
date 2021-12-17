package course3.lesson2.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {

    private JdbcApp jdbcApp = new JdbcApp();

    @Override
    public void start() throws SQLException {
        jdbcApp.connect();
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        jdbcApp.disconnect();
        System.out.println("Сервис аутентификации остановлен");
    }

    @Override
    public String getNickByLoginPass(String login, String pass) throws SQLException {
        return jdbcApp.getNickByLoginPass(login, pass);
    }
}
