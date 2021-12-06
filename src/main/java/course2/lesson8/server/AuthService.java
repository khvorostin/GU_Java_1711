package course2.lesson8.server;

public interface AuthService {

    void start();

    String getNickByLoginPass(String login, String pass);

    void stop();
}
