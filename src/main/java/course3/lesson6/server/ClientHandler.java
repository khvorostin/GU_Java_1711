package course3.lesson6.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ClientHandler {

    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private JdbcApp jdbcApp = new JdbcApp();

    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class.getName());

    private String name;

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket, ExecutorService executorService) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            executorService.execute(() -> {
                try {
                    this.socket.setSoTimeout(120000); // 2 минуты ждем и закрываем соединение
                    authentication();
                    readMessages();
                } catch (IOException | SQLException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } finally {
                    LOGGER.info("Закрываем соединение");
                    closeConnection();
                }
            });
        } catch (IOException e) {
            String errorMsg = "Проблемы при создании обработчика клиента";
            LOGGER.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
    }

    public void authentication() throws IOException, SQLException {
        while (true) {
            String str = in.readUTF();
            if (str.startsWith("/auth")) {
                String[] parts = str.split("\\s");
                String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                if (nick == null) {
                    sendMsg("App: Неверные логин/пароль");
                } else if (!myServer.isNickBusy(nick)) {
                    sendMsg("/authok " + nick + " " + parts[1]);
                    name = nick;
                    myServer.broadcastMsg("App: " + name + " зашел в чат");
                    myServer.subscribe(this);
                    LOGGER.info(name + " зашёл в чат");
                    List<String> messagesHistory = jdbcApp.getHistoryForNick(nick);
                    for (String msg : messagesHistory) {
                        myServer.unicastMsg(nick, msg);
                    }
                    return;
                } else {
                    sendMsg("App: Учетная запись уже используется");
                }
            }
        }
    }

    public void readMessages() throws IOException {
        while (true) {
            String strFromClient = in.readUTF();
            LOGGER.info(name + " прислал сообщение: " + strFromClient);

            if (strFromClient.equals("/end")) {
                return;
            }

            if (strFromClient.startsWith("/w ")) {
                String[] parts = strFromClient.split("\\s", 3);
                if (parts.length == 3) {
                    myServer.unicastMsg(name, name + ": " + parts[2] + " [Личное сообщение]");
                    myServer.unicastMsg(parts[1], name + ": " + parts[2]);
                    jdbcApp.saveMessageInDB(name, parts[1], parts[2]);
                }
            } else {
                myServer.broadcastMsg(name + ": " + strFromClient);
                jdbcApp.saveMessageInDB(name, null, strFromClient);
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            LOGGER.info("<" + msg + ">");
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMsg("App: " + name + " вышел из чата");
        LOGGER.info(name + " вышел из чата");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
