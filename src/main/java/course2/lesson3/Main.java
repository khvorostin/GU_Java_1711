package course2.lesson3;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        // 1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). Найти и вывести список
        // уникальных слов, из которых состоит массив (дубликаты не считаем).
        // Посчитать сколько раз встречается каждое слово.

        // создаём массив
        String[] words = {
            "Автово", "Удельная", "Девяткино", "Звёздная", "Купчино", "Лесная", "Международная", "Девяткино", "Обухово",
            "Удельная", "Озерки", "Парнас", "Международная", "Рыбацкое", "Садовая", "Девяткино", "Спортивная",
            "Удельная", "Электросила", "Парнас"
        };

        // находим и выводим список уникальных слов
        TreeSet<String> uniqueWords = new TreeSet<>(Arrays.asList(words));
        System.out.println(uniqueWords);

        // выводим информацию о том, сколько раз выводится каждое слово:
        // собираем карту, где ключами являются слова, а значениями - количество каждого слова в массиве
        HashMap<String, Integer> wordsCounter = new HashMap<>();
        for (String word : words) {
            if (wordsCounter.containsKey(word)) {
                wordsCounter.put(word, wordsCounter.get(word) + 1);
            } else {
                wordsCounter.put(word, 1);
            }
        }
        // выводим результат
        System.out.println(wordsCounter);

        // 2. Написать простой класс Телефонный Справочник, который хранит в себе список фамилий и телефонных номеров.
        // В этот телефонный справочник с помощью метода add() можно добавлять записи, а с помощью метода get() искать
        // номер телефона по фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов (в случае
        // однофамильцев), тогда при запросе такой фамилии должны выводиться все телефоны.

        Phonebook phonebook = new Phonebook();
        phonebook.add("Иванов", 79011234567L);
        phonebook.add("Иванов", 79012234567L);
        phonebook.add("Петров", 79033423467L);
        phonebook.add("Сидоров", 793423434237L);
        phonebook.add("Петров", 79342345237L);

        System.out.println(phonebook.get("Иванов"));
        System.out.println(phonebook.get("Петров"));
        System.out.println(phonebook.get("Сидоров"));
    }
}
