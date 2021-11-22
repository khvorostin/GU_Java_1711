package course2.lesson6.views;

import course2.lesson6.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Обёртка поверх Swing. Класс, в котором реализован графический интерфейс.
 */
public class UIBuilder {

    // ширина окна
    private int jFrameWidth;
    // высота окна
    private int jFrameHeight;
    // отступ от края экрана до границы окна
    private int jFrameMargin;

    // название окна в шапке
    private String frameTitle;
    // юзернейм (пока зашит жёстко)
    private final String userName = "Client";

    // текстовая панель, на которой выводятся сообщения пользователей
    JTextPane chatTextPane;

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    /**
     * Создание фрейма и заполнение его элементами.
     * @param frameTitle Заголовок окна
     */
    public void createChatWindow(String frameTitle) throws IOException {

        openConnection();

        this.frameTitle = frameTitle;

        JFrame jFrame = getFrame();

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

    public void openConnection() throws IOException {

        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String strFromServer = in.readUTF();
                        if (!strFromServer.trim().isEmpty()) {
                            new Message("Server", strFromServer);
                        }

                        refreshChatPane();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
        ArrayList<Message> messages = Message.getMessages();

        for (int i = 0, currElem = 1, totalReplicas = messages.size(); i < totalReplicas; i++, currElem++) {
            Message message = messages.get(i);
            String line = message.getAuthor() + ": " + message.getMessage();
            sb.append(line);
            if (currElem < totalReplicas) {
                sb.append("\n");
            }
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
            jFrameWidth = dimension.width - 400;
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
                        out.writeUTF(textPane.getText());
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
        new Message(userName, message);
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
}
