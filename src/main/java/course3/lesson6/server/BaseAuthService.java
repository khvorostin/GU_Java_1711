package course3.lesson6.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class BaseAuthService implements AuthService {

    private JdbcApp jdbcApp = new JdbcApp();
    private static final Logger LOGGER = LogManager.getLogger(MyServer.class.getName());

    @Override
    public void start() throws SQLException {
        jdbcApp.connect();
        LOGGER.info("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        jdbcApp.disconnect();
        LOGGER.info("Сервис аутентификации остановлен");
    }

    @Override
    public String getNickByLoginPass(String login, String pass) throws SQLException {
        return jdbcApp.getNickByLoginPass(login, pass);
    }
}
