package course2.lesson8.client;

import course2.lesson8.client.views.UIBuilder;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args) throws IOException {

        MessagesBox messagesBox = new MessagesBox();

        // Класс, в котором скрыта вся логика работы с интерфейсом
        // создаем окно чата и обновляем панель с сообщениями
        UIBuilder uiBuilder = new UIBuilder(messagesBox);
        uiBuilder.createChatWindow("Chat Room");
        uiBuilder.refreshChatPane();
    }

}
