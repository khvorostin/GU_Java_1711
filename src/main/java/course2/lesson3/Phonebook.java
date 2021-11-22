package course2.lesson3;

import java.util.HashMap;
import java.util.HashSet;

public class Phonebook {

    private HashMap<String, HashSet<Long>> book = new HashMap<>();

    public void add(String surname, Long phoneNumber) {

        HashSet<Long> phones;
        if (book.containsKey(surname)) {
            phones = book.get(surname);
        } else {
            phones = new HashSet<>();
        }

        if (!phones.contains(phoneNumber)) {
            phones.add(phoneNumber);
        }

        book.put(surname, phones);
    }

    public HashSet<Long> get(String surname) {
        if (book.containsKey(surname)) {
            return book.get(surname);
        }

        return new HashSet<>();
    }
}
