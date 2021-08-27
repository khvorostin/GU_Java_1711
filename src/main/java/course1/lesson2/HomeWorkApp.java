package course1.lesson2;

public class HomeWorkApp {

    public static void main(String[] args) {

        System.out.println(compareNumbers(10, 5));
        System.out.println(compareNumbers(20, 5));

        checkIfNumberPositive(5);
        checkIfNumberPositive(-5);

        System.out.println(checkIfNumberNegative(-5));
        System.out.println(checkIfNumberNegative(5));

        repeatLine("READY PLAYER 1", 10);

        System.out.println(ifYearIsLeap(2000));
        System.out.println(ifYearIsLeap(1900));
        System.out.println(ifYearIsLeap(2020));
        System.out.println(ifYearIsLeap(2021));
    }

    /**
     * Метод, принимает на вход два целых числа и проверяет, что их сумма лежит в пределах от 10 до 20 (включительно),
     * если да – вернуть true, в противном случае – false.
     *
     * @param a Первое число
     * @param b Второе число
     * @return true, если сумма переданных чисел в пределах от 10 до 20, в противном случае - false
     */
    public static boolean compareNumbers(int a, int b) {
        int sum = a + b;
        return 10 <= sum && sum <= 20;
    }

    /**
     * Метод, которому в качестве параметра передается целое число, метод печатает в консоль,
     * положительное ли число передали или отрицательное. Замечание: ноль считаем положительным числом.
     *
     * @param a Проверяемое число
     */
    public static void checkIfNumberPositive(int a) {
        if (a >= 0) {
            System.out.println("Передано положительное число");
        } else {
            System.out.println("Передано отрицательное число");
        }
    }

    /**
     * Метод в качестве параметра получает целое число и проверяет, отрицательное ли оно.
     *
     * @param a Проверяемое число
     * @return true, если переданное на вход число отрицательное, false - если положительное.
     */
    public static boolean checkIfNumberNegative(int a) {
        return a < 0;
    }

    /**
     * Выводит в консоль переданную строку указанное количество раз.
     *
     * @param line Строка, которую нужно вывести в консоль
     * @param num Количество повторов
     */
    public static void repeatLine(String line, int num) {
        for (var i = 0; i < num; i++) {
            System.out.println(line);
        }
    }

    /**
     * Метод определяет, является ли год високосным. Каждый 4-й год является високосным, кроме каждого 100-го,
     * при этом каждый 400-й – високосный.
     *
     * @param year Год для проверки
     * @return високосный - true, не високосный - false
     */
    public static boolean ifYearIsLeap(int year) {
        boolean leap;

        if (year % 400 == 0) {
            leap = true;
        } else if (year % 100 == 0) {
            leap = false;
        } else {
            leap = year % 4 == 0;
        }

        return leap;
    }
}
