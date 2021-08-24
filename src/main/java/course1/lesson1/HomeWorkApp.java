package course1.lesson1;

public class HomeWorkApp
{
    public static void main(String[] args)
    {
        printThreeWords();
        checkSumSign();
        printColor();
        compareNumbers();
    }

    /**
     * Печатает в столбец три слова: Orange, Banana, Apple.
     */
    public static void printThreeWords()
    {
        // простое решение:
        // System.out.println("Orange");
        // System.out.println("Banana");
        // System.out.println("Banana");

        // то же, с обходом массива
        String[] words = {"Orange", "Banana", "Banana"};
        for (String word : words) {
            System.out.println(word);
        }
    }

    /**
     * В теле метода объявлены две int переменные a и b, инициализированы случайными числами в диапазоне от -10 до 10.
     * Переменные суммируются, и если их сумма больше или равна 0, то в консоль выводится сообщение “Сумма положительная”,
     * в противном случае - “Сумма отрицательная”.
     */
    public static void checkSumSign()
    {
        int a = getRandomInt(-10, 10);
        int b = getRandomInt(-10, 10);
        int sum = a + b;
        String msg;

        if (sum >= 0) { // т.к. я генерирую рандомные числа, текст сообщения расширен
            msg = "Сумма [" + a + "] и [" + b + "] положительная";
        } else {
            msg = "Сумма [" + a + "] и [" + b + "] отрицательная";
        }

        System.out.println(msg);
    }

    /**
     *  В теле метода задается int переменную value и инициализируется случайным значением от -100 до 200.
     *  Если value меньше 0 (0 включительно), то в консоль выводится сообщение “Красный”,
     *  если лежит в пределах от 0 (0 исключительно) до 100 (100 включительно), то “Желтый”,
     *  если больше 100 (100 исключительно) - “Зеленый”
     */
    public static void printColor()
    {
        int value = getRandomInt(-100, 200);

        if (value <= 0) {
            System.out.println("Красный [" + value + "]");
        } else if (value <= 100) { // предполагается [value > 0 && value <= 100], но value > 0 тут излишне
            System.out.println("Желтый [" + value + "]");
        } else {
            System.out.println("Зелёный [" + value + "]");
        }
    }

    /**
     * В теле метода объявлены две int переменные a и b, и инициализированы случайными значениями в диапазоне между
     * -10 и 10. Если a больше или равно b, то необходимо вывести в консоль сообщение “a >= b”, в противном случае “a < b”;
     */
    public static void compareNumbers()
    {
        int a = getRandomInt(-10, 10);
        int b = getRandomInt(-10, 10);

        if (a >= b) {
            System.out.println("a >= b [" + a + " >= " +  b + "]");
        } else {
            System.out.println("a < b [" + a + " < " +  b + "]");
        }
    }

    /**
     * Вспомогательный метод для герерации случайных чисел в заданном диапазоне
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandomInt(int min, int max)
    {
        return (int)(Math.random() * (max - min)) + min;
    }
}
