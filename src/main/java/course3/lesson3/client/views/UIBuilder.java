package course3.lesson3.client.views;

import course3.lesson3.client.MessagesBox.Message;
import course3.lesson3.client.MessagesBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Обёртка поверх Swing. Класс, в котором реализован графический интерфейс.
 */
public class UIBuilder {

    private MessagesBox messagesBox;
    private JFrame jFrame;

    // ширина окна
    private int jFrameWidth;
    // высота окна
    private int jFrameHeight;
    // отступ от края экрана до границы окна
    private int jFrameMargin;

    // название окна в шапке
    private String frameTitle;
    // юзернейм
    private String userName = "Unauthtorized User [local]";

    private String userLogin = "";

    // текстовая панель, на которой выводятся сообщения пользователей
    JTextPane chatTextPane;

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private boolean connected = false;
    private boolean authorized = false;

    public UIBuilder(MessagesBox messagesBox) {
        this.messagesBox = messagesBox;
    }

    /**
     * Создание фрейма и заполнение его элементами.
     * @param frameTitle Заголовок окна
     */
    public void createChatWindow(String frameTitle) throws IOException {

        openConnection();

        this.frameTitle = frameTitle;

        jFrame = getFrame();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 5,5,5));

        GridBagConstraints c = new GridBagConstraints();

        // Верхняя часть окна: поле с лентой сообщений
        setTopPanePositions(c);
        chatTextPane = createTextPane();
        JScrollPane chatScrollPane = createScrollPane(chatTextPane);
        mainPanel.add(chatScrollPane, c);

        // Нижняя часть окна: поле ввода
        setBottomLeftPanePositions(c);
        JTextPane inputTextPane = createTextPaneWithListener();
        JScrollPane inputScrollPane = createScrollPane(inputTextPane);
        mainPanel.add(inputScrollPane, c);

        // Нижняя часть окна: кнопка отправки сообщения
        setBottomRightPanePositions(c);
        JButton sendButton = createSendButton(inputTextPane);
        mainPanel.add(sendButton, c);

        jFrame.add(mainPanel);
        mainPanel.revalidate();
    }

    private void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    private void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        jFrame.setTitle("Chat Room " + userName);
    }

    public void openConnection() throws IOException {

        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        setConnected(true);
        setAuthorized(false);

        Thread serverListener = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    connected = true;

                    // висим здесь, пока не получим информацию об успешной авторизации
                    while (true) {
                        String strFromServer = in.readUTF();
                        String[] parts;
                        if (strFromServer.startsWith("App: ")) {
                            parts = strFromServer.split("\\s", 2);
                            if (parts.length == 2) {
                                messagesBox.addMessage("App", parts[1]);
                            }
                        }

                        if (strFromServer.contains("/authok")) {
                            parts = strFromServer.split("\\s");
                            setUserName(parts[1]); // сохраняем имя пользователя
                            userLogin = parts[2]; // сохраняем логин пользователя
                            messagesBox.clearAll();
                            messagesBox.addMessage("App", "Вы авторизованы как " + parts[1]);
                            setConnected(true);
                            setAuthorized(true);
                            refreshChatPane();
                            break;
                        }
                    }

                    // висим здесь, пока не получим сообщение об отключении
                    while (true) {
                        String strFromServer = in.readUTF();
                        if (strFromServer.equalsIgnoreCase("/end")) {
                            break;
                        }

                        String[] parts = strFromServer.split(": ", 2);
                        messagesBox.addMessage(parts[0], parts[1]);
                        refreshChatPane();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    connected = false;
                    messagesBox.addMessage("App", "Сеанс завершён, перезапустите приложение, чтобы подключиться");
                    refreshChatPane();
                    closeConnection();
                }
            }
        });

        serverListener.start();
    }

    public void closeConnection() {
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

    /**
     * Обновление текстовой панели с сообщениями. Через StringBuilder собирается строка для вывода (на основе данных из
     * коллекции сообщений), текущий текст удаляется и меняется на новый.
     */
    public void refreshChatPane()
    {
        StringBuilder sb = new StringBuilder();

        if (!connected) {
            messagesBox.addMessage("App", "Вы не подключены к серверу");
        }

        if (!authorized) {
            messagesBox.addMessage("App", "Вы не авторизованы, введите логин/пароль в сообщении вида /auth login password");
        }

        List<Message> messages = messagesBox.getMessages();
        for (Message message : messages) {
            sb.append(message.getAuthor() + ": " + message.getMessage() + "\n");
        }

        chatTextPane.setText("");
        chatTextPane.setText(sb.toString());
    }

    private JFrame getFrame() {
        calcFrameSize();
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle(frameTitle);
        jFrame.setBounds(jFrameMargin, jFrameMargin, jFrameWidth, jFrameHeight);
        jFrame.setVisible(true);
        return jFrame;
    }

    private void calcFrameSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        if (dimension.width > 500) {
            jFrameWidth = 400; // dimension.width - 400;
            jFrameHeight = dimension.height - 400;
            jFrameMargin = 200;
        } else if (dimension.width > 200) {
            jFrameWidth = dimension.width - 50;
            jFrameHeight = dimension.height - 50;
            jFrameMargin = 25;
        } else {
            jFrameWidth = dimension.width;
            jFrameHeight = dimension.height;
            jFrameMargin = 0;
        }
    }

    private JTextPane createTextPaneWithListener() {
        JTextPane textPane = createTextPane();
        textPane.addKeyListener(createKeyListener(textPane));
        return textPane;
    }

    private JTextPane createTextPane() {
        JTextPane textPane = new JTextPane();
        textPane.setBackground(UISettings.getBackgroundColor());
        textPane.setBorder(UISettings.getThickBorder());
        textPane.setContentType(UISettings.getPaneContentType());
        return textPane;
    }

    private KeyListener createKeyListener(JTextPane textPane) {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // do nothing
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        String msg = textPane.getText();
                        if (msg.equals("/save")) {
                            saveToFile();
                        } else {
                            out.writeUTF(msg);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    addMessageToContainer(textPane.getText());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    textPane.setText("");
                }
            }
        };
    }

    private void addMessageToContainer(String message) {
        // new Message(userName, message);
        refreshChatPane();
    }

    private JScrollPane createScrollPane(JTextPane textPane) {
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBackground(UISettings.getBackgroundColor());
        scrollPane.setBorder(UISettings.getEmptyBorder());
        return scrollPane;
    }

    private JButton createSendButton(JTextPane textPane) {
        JButton sendButton = new JButton("Отправить");
        sendButton.setBackground(UISettings.getBackgroundColor());
        sendButton.setBorder(UISettings.getThickBorder());
        sendButton.setFocusable(false); // скрываем рамку-индикатор фокуса
        sendButton.addActionListener(e -> {
            String userText = textPane.getText();
            if (userText.length() > 0) {
                try {
                    out.writeUTF(userText);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                addMessageToContainer(userText);
                textPane.setText("");
            }
        });

        return sendButton;
    }

    private void setTopPanePositions(GridBagConstraints c) {
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0f;
        c.weighty = 0.9f;
        c.gridx = 0;
        c.gridwidth = 5;
        c.gridheight = 4;
        c.gridy = 0;
        c.insets = new Insets(0,0,0,0);  //top padding
        c.anchor = GridBagConstraints.PAGE_START; //top of space
    }

    private void setBottomLeftPanePositions(GridBagConstraints c) {
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0f;
        c.weighty = 0.1f;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.gridy = 4;
        c.insets = new Insets(5,0,0,5);  //top padding
        c.anchor = GridBagConstraints.PAGE_END; // bottom of space
    }

    private void setBottomRightPanePositions(GridBagConstraints c) {
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.1f;
        c.weighty = 0.1f;
        c.gridx = 4;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridy = 4;
        c.insets = new Insets(5,0,0,0);  //top padding
        c.anchor = GridBagConstraints.PAGE_END; // bottom of space
    }

    private void saveToFile() {
        try (
            FileWriter fw = new FileWriter("history_" + userLogin + ".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
        ) {
            String txt = chatTextPane.getText();
            out.println(txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
