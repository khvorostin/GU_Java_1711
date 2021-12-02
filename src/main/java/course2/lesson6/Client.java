package course2.lesson6;

import course2.lesson6.views.UIBuilder;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {
        // Класс, в котором скрыта вся логика работы с интерфейсом
        // создаем окно чата и обновляем панель с сообщениями
        UIBuilder uiBuilder = new UIBuilder();
        uiBuilder.createChatWindow("Chat Room Client");
        uiBuilder.refreshChatPane();
    }
}
