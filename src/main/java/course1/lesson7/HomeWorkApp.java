package course1.lesson7;

import java.util.Arrays;

public class HomeWorkApp {

    public static void main(String[] args) {

        // создадим массив котов
        Cat[] cats = {
            new Cat("Барсик", 5),
            new Cat("Марсик", 8),
            new Cat("Мурзик", 7),
            new Cat("Дымок", 4),
            new Cat("Паштет", 6),
        };

        // выведем начальное состояние котов (изначально они все голодные)
        for (Cat cat : cats) {
            cat.displayInfoAboutSatiety();
        }

        // создаем тарелку с едой
        Plate plate = new Plate(50);

        // пусть коты едят каждые 6 часов, а каждые 8 часов в тарелку добавляетя еда
        for (int hour = 0; hour < 24; hour++) {

            if (hour > 0 && hour % 8 == 0) {
                plate.addFood(50);
                System.out.println("\n" + beautifyTime(hour) + ": добавили в тарелку еды, сейчас там: " + plate.getFood());
            }

            if (hour % 6 != 0) {
                continue;
            }

            System.out.println("\nНа часах " + beautifyTime(hour) + ", коты голодные и пытаются поесть. Еды в тарелке: " + plate.getFood());

            for (Cat cat : cats) {
                cat.getHungry();
                cat.eat(plate);
                cat.displayInfoAboutSatiety();
            }
        }
    }

    /**
     * Возвращает время в человекочитаемом виде.
     *
     * @param hour Время в часах
     * @return Время в виде строки
     */
    private static String beautifyTime(int hour)
    {
        if (hour == 0) {
            return "полночь";
        }

        if (hour == 1 || hour == 21) {
            return hour + " час";
        }

        int[] hours = {2, 3, 4, 22, 23};
        if (Arrays.asList(hours).contains(hour)) {
            return hour + " часа";
        }

        return hour + " часов";
    }
}
