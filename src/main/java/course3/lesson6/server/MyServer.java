package course3.lesson6.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {

    private final int PORT = 8189;
    private final int nThreads = 3;

    private List< ClientHandler > clients;
    private AuthService authService;
    private ExecutorService executorService;

    private static final Logger LOGGER = LogManager.getLogger(MyServer.class.getName());

    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {

        try (ServerSocket server = new ServerSocket(PORT)) {
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            executorService = Executors.newFixedThreadPool(nThreads);
            while (true) {
                LOGGER.info("Сервер ожидает подключения");
                Socket socket = server.accept();
                LOGGER.info("Клиент подключился");
                new ClientHandler(this, socket, executorService);
            }
        } catch (IOException | SQLException e) {
            LOGGER.fatal("Ошибка в работе сервера");
        } finally {
            if (authService != null) {
                authService.stop();
            }

            if (executorService != null) {
                executorService.shutdown();
            }
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler client : clients) {
            if (client.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler client : clients) {
            client.sendMsg(msg);
        }
    }

    public synchronized void unicastMsg(String nick, String msg) {
        for (ClientHandler client : clients) {
            if (client.getName().equals(nick)) {
                client.sendMsg(msg);
            }
        }
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }

    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
    }
}
