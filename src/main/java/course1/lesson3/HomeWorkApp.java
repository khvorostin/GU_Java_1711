package course1.lesson3;

import java.util.StringJoiner;

public class HomeWorkApp {

    public static void main(String[] args) {

        System.out.println("Задача 1. инвертирование массива");
        int[] arr1 = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        printArray(arr1); // выводим в консоль исходный массив
        printArray(invertArray(arr1)); // выводим в консоль измененный массив

        System.out.println("\nЗадача 2. Создание массива из 100 элементов");
        printArray(getArray(100), ", ");

        System.out.println("\nЗадача 3. Умножение на 2 элементов массива, которые меньше 6");
        int[] arr2 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        printArray(arr2, ", ");
        printArray(multiplyByTwoElementsLessThanSix(arr2), ", ");

        System.out.println("\nЗадача 4. Создание квадратного массива с диаганалями");
        createSquare(5);

        System.out.println("\nЗадача 5. Создание массива, все элементы которого равны заданному числу");
        printArray(createArrayWithInitialValues(10, 7), ", ");

        System.out.println("\nЗадача 6. Поиск минимального и максимального элемента массива");
        int[] arr3 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        findMinAndMax(arr3); // используем массив из задачи 3, но он был изменен выше, инициализируем его заново

        System.out.println("\nЗадача 7. Проверка возможности разделить массив на две равные части");
        int[] arr4 = {2, 2, 2, 1, 2, 2, 10, 1};
        System.out.println(checkBalance(arr4));
        int[] arr5 = {1, 1, 1, 2, 1};
        System.out.println(checkBalance(arr5));
        int[] arr6 = {1, 1, 1, 2, 1, 1};
        System.out.println(checkBalance(arr6));

        System.out.println("\nЗадача 8. Сдвиг элементов массива");
        int[] arr7 = {1, 2, 3};
        printArray(arr7, ", ");
        printArray(shiftArray(arr7, 1), ", ");

        int[] arr8 = {3, 5, 6, 1};
        printArray(arr8, ", ");
        printArray(shiftArray(arr8, -2), ", ");
    }

    /**
     * Метод инвертирует переданный массив: с помощью цикла и условия заменяет 0 на 1, 1 на 0.
     *
     * @param arr Массив, элементами которого являются 0 и 1.
     * @return Массив, в котором 0 заменены на 1, 1 на 0
     */
    public static int[] invertArray(int[] arr) {
        // чтобы не считать длину массива на каждом шаге цикла, считаем в блоке инициализации
        for (int i = 0, l = arr.length; i < l; i++) {
            // вместо if.. else используем тернарный оператор
            arr[i] = (arr[i] > 0) ? 0 : 1;
        }

        return arr;
    }

    //

    /**
     * Создает пустой целочисленный массив заданной длины и с помощью цикла заполяет его значениями 1, 2, 3, ... N.
     *
     * @param length Длина возвращаемого массива
     * @return Итоговый массив
     */
    public static int[] getArray(int length) {
        int[] arr = new int[length];
        for (int k = 0, v = 1; k < length; k++, v++) {
            arr[k] = v;
        }

        return arr;
    }

    /**
     * Проходит циклом по переданному массиву и умножает на 2 числа, которые меньше 6.
     *
     * @param arr Массив для изменения
     * @return Итоговый массив
     */
    public static int[] multiplyByTwoElementsLessThanSix(int[] arr) {
        for (int i = 0, l = arr.length; i < l; i++) {
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }

        return arr;
    }

    /**
     * Создает квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое),
     * с помощью циклов заполняет его диагональные элементы единицами.
     *
     * @param length Ширина создаваемого массива
     */
    public static void createSquare(int length) {
        // создаем квадратный массив, на этом этапе все элементы - нули
        int[][] arr = new int[length][length];

        // элементам "по диаганалям" задаём значение 1
        for (int i = 0; i < length; i++) {
            for (int y = 0; y < length; y++) {
                if (i == y || i == (length - 1 - y)) {
                    arr[i][y] = 1;
                }
            }
        }

        // выводим массив в консоль
        for (int i = 0; i < length; i++) {
            printArray(arr[i]);
        }
    }

    /**
     * Создает одномерный массив заданной длины, все элементы которого равны указанному значению.
     *
     * @param len Длина возвращаемого массива
     * @param initialValue Значение элементов возвращаемого массива
     * @return одномерный массив типа int длиной len, каждый элемент которого равен initialValue
     */
    public static int[] createArrayWithInitialValues(int len, int initialValue) {
        int[] arr = new int[len];
        for (var i = 0; i < len; i++) {
            arr[i] = initialValue;
        }

        return arr;
    }

    //* Задать одномерный массив и найти в нем минимальный и максимальный элементы ;
    public static void findMinAndMax(int[] arr) {
        // инициализуем переменные первыми элементами переданного массива,
        // это можно было бы и в цикле сделать позже, но так проще далее использовать foreach для цикла
        int min = arr[0];
        int max = arr[0];

        for (int i : arr) {
            if (i < min) min = i; // if в одну строку, так как условие простое
            if (max < i) max = i;
        }

        System.out.println("Минимальный элемент массива: " + min + ", максимальный элемент: " + max);
    }

    /**
     * Проверяет, возможно ли разбить на две равные части переданный непустой одномерный целочисленный массив.
     *
     * @param arr Одномерный целочисленный массив
     * @return true, если в массиве есть место, в котором сумма левой и правой части массива равны,
     * false - нет такого места
     */
    public static boolean checkBalance(int[] arr) {

        // первая проверка: пустой массив нельзя разделить на равные части
        // по условию задачи такой вариант не предполагается, но всё-таки
        if (arr.length == 0) {
            return false;
        }

        // вторая проверка: сумма элементов должна делиться на 2 без остатка
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }

        if (sum % 2 > 0) {
            return false; // при делении на 2 есть остаток -> нельзя разделить массив на равные части
        }

        // третья проверка: ищем место, где промежуточная сумма элементов массива
        // равна половите суммы всех элементов
        int half = sum/2;
        int left = 0;
        for (int i : arr) {
            left += i;
            // нет смысла проверять весь массив, достаточно найти место, где промежуточная
            // сумма равна (или стала больше) половине суммы всех элементов
            if (half == left) {
                return true; // есть возможность разделить на две части
            } else if (half < left) {
                return false; // нет возможности разделить на две части
            }
        }

        return false;
    }

    /**
     * Смещает все элементы переданного массива на заданное число позиций. Элементы смещаются циклично.
     *
     * Примеры:
     * [1, 2, 3] при n = 1 (на один вправо) -> [3, 1, 2];
     * [3, 5, 6, 1] при n = -2 (на два влево) -> [6, 1, 3, 5].
     *
     * @param arr Одномерный массив
     * @param shift Размер сдвига (может быть положительным, или отрицательным)
     * @return измененный массив
     */
    public static int[] shiftArray(int[] arr, int shift) {
        // так как shift может быть больше длины массива, уменьшим его
        shift = shift % arr.length;

        // если сдвиг - ноль, то сразу возвращаем массив
        if (shift == 0) {
            return arr;
        }

        // приведем отрицательный shift к положительному
        if (shift < 0) {
            shift = arr.length + shift;
        }

        // 'shift' раз делаем сдвиг элементов массива вправо
        for (int i = 0; i < shift; i++) {
            int yy; // соседняя позиция относительно ii (см. ниже)
            int tmp = 0;
            // сдвигаем все на шаг
            for (int ii = 0; ii < arr.length; ii++) {
                // определяем индекс соседней позиции, учитывая конечность массива
                yy = (arr.length + ii + 1) % arr.length;
                // значение первого элемента пишем во временную переменную
                if (ii == 0) tmp = arr[ii];
                // меняем значения временной переменной и элемента [yy] местами
                tmp += arr[yy];
                arr[yy] = tmp - arr[yy];
                tmp -= arr[yy];
            }
        }

        return arr;
    }

    /**
     * Вспомогательный метод: выводит в консоль переданный массив чисел в виде строки
     *
     * @param arr Числовой массив, который нужно вывести в консоль в виде строки.
     * @param delimiter Разделитель
     */
    private static void printArray(int[] arr, String delimiter) {

        StringJoiner sj = new StringJoiner(delimiter);
        for (int i : arr) {
            sj.add(String.valueOf(i));
        }

        System.out.println(sj);
    }

    /**
     * Вспомогательный метод: выводит в консоль переданный массив чисел в виде строки, разделителя между числами нет
     *
     * @param arr Числовой массив, который нужно вывести в консоль в виде строки.
     */
    private static void printArray(int[] arr) {
        printArray(arr, "");
    }
}
