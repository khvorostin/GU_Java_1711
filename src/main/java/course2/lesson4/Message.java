package course2.lesson4;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Хранилище сообщений. Сейчас реализован в виде коллекции карт, где допустимо хранение юзернейма и сообщения
 * (сделал задел под добавление сопутствующих свойств: временной метки, стиля оформления, флага прочитанности и т.п.).
 */
public class Message {

    private String author;
    private String message;

    private static final ArrayList<Message> messages = new ArrayList<>();

    public Message(String author, String message) {
        this.author = author;
        this.message = message;
        messages.add(this);
    }

    /**
     * Получение ленты сообщений
     * @return Коллекция сообщения
     */
    public static ArrayList<Message> getMessages() {
        return messages;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
