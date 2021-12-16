package course3.lesson2.server;

public interface AuthService {

    void start();

    String getNickByLoginPass(String login, String pass);

    void stop();
}
