package course3.lesson1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Задание 1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа).

        String[] stringsToReplace = {"lorem", "ipsum", "dolor", "sit", "amet"};
        System.out.println(Arrays.toString(replaceElements(stringsToReplace, 1, 2)));
        System.out.println(Arrays.toString(replaceElements(stringsToReplace, 0, 4)));

        Integer[] integersToReplace = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(replaceElements(integersToReplace, 1, 2)));
        System.out.println(Arrays.toString(replaceElements(integersToReplace, 0, 4)));

        // Задание 2. Написать метод, который преобразует массив в ArrayList.

        Apple[] apples = {new Apple(), new Apple(), new Apple(), new Apple()};
        ArrayList<Apple> al = arrayToArrayList(apples);
        System.out.println(al);

        String[] strings = {"lorem", "ipsum", "dolor", "sit", "amet"};
        ArrayList<String> sl = arrayToArrayList(strings);
        System.out.println(sl);

        Integer[] ints = {1, 23, 343, 54};
        ArrayList<Integer> il = arrayToArrayList(ints);
        System.out.println(il);

        // Задание 3. Задача про коробки с яблоками и апельсинами

        // заполняем три ящика фруктами

        Box box1 = new Box();
        box1.addOne(new Apple());
        box1.addOne(new Apple());

        Box box2 = new Box();
        box2.addOne(new Orange());
        box2.addOne(new Orange());
        box2.addOne(new Orange());

        Box box3 = new Box();
        box3.addOne(new Apple());
        box3.addOne(new Apple());

        // определяем вес ящика
        System.out.println(box1.getWeight()); // 2.0
        System.out.println(box2.getWeight()); // 4.5

        // сравнение коробок
        System.out.println(box1.compare(box2)); // false
        System.out.println(box1.compare(box3)); // true

        // пересыпание коробок
        System.out.println("Фруктов в первой коробке: " + box1.getFruitsCounter()); // 2 яблока
        System.out.println("Фруктов во второй коробке: " + box2.getFruitsCounter()); // 3 апельсина
        System.out.println("Фруктов в третьей коробке: " + box3.getFruitsCounter()); // 2 яблока

        if (box1.shiftTo(box2)) {
            System.out.println("Пересыпали фрукты из коробки 1 в коробку 2");
        } else {
            System.out.println("Не удалось пересыпать фрукты из коробки 1 в коробку 2");
        }

        System.out.println("Фруктов в первой коробке: " + box1.getFruitsCounter()); // 2 яблока
        System.out.println("Фруктов во второй коробке: " + box2.getFruitsCounter()); // 3 апельсина
        System.out.println("Фруктов в третьей коробке: " + box3.getFruitsCounter()); // 2 яблока

        if (box1.shiftTo(box3)) {
            System.out.println("Пересыпали фрукты из коробки 1 в коробку 3");
        } else {
            System.out.println("Не удалось пересыпать фрукты из коробки 1 в коробку 3");
        }

        System.out.println("Фруктов в первой коробке: " + box1.getFruitsCounter()); // 0 яблок
        System.out.println("Фруктов во второй коробке: " + box2.getFruitsCounter()); // 3 апельсина
        System.out.println("Фруктов в третьей коробке: " + box3.getFruitsCounter()); // 4 яблока
    }

    /**
     * Меняет указанные элементы переданного местами.
     *
     * @param array Массив, элементы которого меняем местами
     * @param pos1 Позиция первого элемента
     * @param pos2 Позиция второго элемента
     * @param <T> Класс элементов переданного массива
     * @return Изменённый массив
     */
    private static <T> T[] replaceElements(T[] array, int pos1, int pos2) {
        if (pos1 < 0 || pos1 >= array.length || pos2 < 0 || pos2 >= array.length) {
            throw new IndexOutOfBoundsException("Передан некорректный идентификатор элемента");
        }

        T elem = array[pos2];
        array[pos2] = array[pos1];
        array[pos1] = elem;

        return array;
    }

    /**
     * Преобразует массив в ArrayList.
     *
     * @param array Массив, который нужно преобразовать в ArrayList
     * @param <T> Класс элементов переданного массива
     * @return Трансформированный в ArrayList массив
     */
    private static <T> ArrayList<T> arrayToArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }
}
