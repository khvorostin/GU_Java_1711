package course1.lesson3;

import java.util.StringJoiner;

public class HomeWorkApp {

    public static void main(String[] args) {

        System.out.println("Задача 1. инвертирование массива");
        int[] arr1 = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        printArray(arr1); // выводим в консоль исходный массив
        invertArray(arr1);
        printArray(arr1); // выводим в консоль измененный массив

        System.out.println("\nЗадача 2. Создание массива из 100 элементов");
        printArray(getArray(100), ", ");

        System.out.println("\nЗадача 3. Умножение на 2 элементов массива, которые меньше 6");
        int[] arr2 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        printArray(arr2, ", ");
        multiplyByTwoElementsLessThanSix(arr2);
        printArray(arr2, ", ");

        System.out.println("\nЗадача 4. Создание квадратного массива с диаганалями");
        createSquare(7);

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
     */
    public static void invertArray(int[] arr) {
        // чтобы не считать длину массива на каждом шаге цикла, считаем в блоке инициализации
        for (int i = 0, l = arr.length; i < l; i++) {
            // вместо if.. else используем тернарный оператор
            arr[i] = (arr[i] > 0) ? 0 : 1;
        }

        // return arr; -- массив - ссылочный тип, поэтому return не нужен
    }

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
     */
    public static void multiplyByTwoElementsLessThanSix(int[] arr) {
        for (int i = 0, l = arr.length; i < l; i++) {
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }
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
        for (int i = 0, y = length - 1; i < length; i++, y--) {
            arr[i][i] = 1;
            arr[i][y] = 1;
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

    /**
     * Находит в переданном одномерном массиве целых чисел минимальный и максимальный элементы.
     *
     * @param arr Одномерный массив чисел
     */
    public static void findMinAndMax(int[] arr) {

        // если массив нулевой длины, то нет смысла бежать по нему
        if (arr.length == 0) {
            System.out.println("Передан массив нулевой длины");
            return;
        }

        // в массиве один элемент -> он и минимальный и максимальный
        if (arr.length == 1) {
            System.out.println("Минимальный элемент массива: " + arr[0] + ", максимальный элемент: " + arr[0]);
            return;
        }

        // инициализуем переменные первыми элементами переданного массива,
        // это можно было бы и в цикле сделать позже, но так проще далее использовать foreach для цикла
        int min = arr[0];
        int max = arr[0];

        for (int i : arr) {
            if (i < min) {
                min = i;
            }

            if (max < i) {
                max = i;
            }
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

        int arrLength = arr.length;

        // обрабатываем пограничные ситуации:
        if (arrLength == 0 || shift == 0 || shift % arrLength == 0) {
            return arr;
        }

        // так как shift может быть больше длины массива, уменьшим его
        shift %= arrLength;

        // если нет задачи получить минимальное количество шагов, можно всегда сдвигать массив вправо
        // это позволит убрать else ниже

        // посчитаем, в какую сторону выгоднее сдвигать массив
        int reverseShift = (shift < 0) ? shift + arrLength : shift - arrLength;
        if (Math.abs(reverseShift) < Math.abs(shift)) {
            shift = reverseShift;
        }

        if (shift > 0) {
            // 'shift' раз делаем сдвиг элементов массива вправо
            for (int i = 0; i < shift; i++) {
                // последовательно меняем значения первого элемента со вторым, третьим... последним.
                // в итоге получаем сдвиг вправо на одну позицию
                for (int y = 1; y < arr.length; y++) {
                    // трюк с заменой значений двух переменных без вспомогательной
                    arr[0] += arr[y];
                    arr[y] = arr[0] - arr[y];
                    arr[0] -= arr[y];
                }
            }
        } else {
            int lastElem = arrLength - 1;
            for (int i = shift; i < 0; i++) {
                for (int y = arrLength - 2; y >= 0; y--) {
                    arr[lastElem] += arr[y];
                    arr[y] = arr[lastElem] - arr[y];
                    arr[lastElem] -= arr[y];
                }
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
