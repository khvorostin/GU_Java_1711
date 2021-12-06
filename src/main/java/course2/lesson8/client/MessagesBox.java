package course2.lesson8.client;

import java.util.LinkedList;
import java.util.List;

public class MessagesBox {

    private List<Message> messages;

    public class Message {

        private String author;
        private String message;

        public Message(String author, String message) {
            this.author = author;
            this.message = message;
        }

        public String getAuthor() {
            return author;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "{" + author + ": " + message + "}";
        }
    }

    public MessagesBox() {
        this.messages = new LinkedList<>();
    }

    public void addMessage(String from, String text) {
        messages.add(new Message(from, text));
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void clearAll() {
        messages.clear();
    }
}
