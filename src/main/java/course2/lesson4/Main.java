package course2.lesson4;

import course2.lesson4.views.UIBuilder;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        // Создаем хранилище сообщений в чате, добавляем в него два текстовых сообщения
        new Message("Java", "Привет!");
        new Message("Java", "Напиши что-нибудь в форме ниже.");

        // Класс, в котором скрыта вся логика работы с интерфейсом
        // создаем окно чата и обновляем панель с сообщениями
        UIBuilder uiBuilder = new UIBuilder();
        uiBuilder.createChatWindow("Chat Room Client");
        uiBuilder.refreshChatPane();
    }
}
