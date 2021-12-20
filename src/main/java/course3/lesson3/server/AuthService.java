package course3.lesson3.server;

import java.sql.SQLException;

public interface AuthService {

    void start() throws SQLException;

    String getNickByLoginPass(String login, String pass) throws SQLException;

    void stop();
}
