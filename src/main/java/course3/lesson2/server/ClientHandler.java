package course3.lesson2.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {

    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String name;

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            new Thread(() -> {
                try {
                    this.socket.setSoTimeout(120000); // 2 минуты ждем и закрываем соединение
                    authentication();
                    readMessages();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Закрываем соединение");
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
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
                    sendMsg("/authok " + nick);
                    name = nick;
                    myServer.broadcastMsg("App: " + name + " зашел в чат");
                    myServer.subscribe(this);
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
            System.out.println("от " + name + ": " + strFromClient);

            if (strFromClient.equals("/end")) {
                return;
            }

            if (strFromClient.startsWith("/w ")) {
                String[] parts = strFromClient.split("\\s", 3);
                if (parts.length == 3) {
                    myServer.unicastMsg(name, name + ": " + parts[2] + " [Личное сообщение]");
                    myServer.unicastMsg(parts[1], name + ": " + parts[2]);
                }
            } else {
                myServer.broadcastMsg(name + ": " + strFromClient);
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            System.out.println("<" + msg + ">");
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMsg("App: " + name + " вышел из чата");
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
