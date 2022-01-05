package course3.lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class MainTest {

    private final Main main = new Main();

    /**
     * Тестирование метода, который возвращает хвост массива чисел: все числа после последней четверки в массиве.
     * Этот тест покрывает кейсы, в которых все массивы не содержат ни одной четвёрки.
     *
     * @param array Тестовые данные
     *
     * @see MainTest#testNumbersAfterLastFourInArray(int[], int[]) - тестирование метода, если передан массив
     * минимум с одной четверкой
     */
    @ParameterizedTest
    @MethodSource("dataForRuntimeExceptionInNumbersAfterLastFourInArray")
    public void testRuntimeExceptionInNumbersAfterLastFourInArray(int[] array) {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            // 1 кейс: в массиве нет 4 - т.к. бросается исключение, тестируем отдельно
            // кейсы 2-7 см в методе testNumbersAfterLastFourInArray()
            main.getNumbersAfterLastFourInArray(array);
        });

        Assertions.assertEquals("no 4 in array", thrown.getMessage());
    }

    private static Stream< Arguments > dataForRuntimeExceptionInNumbersAfterLastFourInArray() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[] {}));
        out.add(Arguments.arguments(new int[] {1, 2, 3}));
        return out.stream();
    }

    /**
     * Тестирование метода, который возвращает хвост массива чисел: все числа после последней четверки в массиве.
     * Этот тест покрывает кейсы, в которых все массивы содержат минимум одну четвёрку.
     *
     * @param array Тестовые данные
     * @param result Ожидаемый результат
     *
     * @see MainTest#testRuntimeExceptionInNumbersAfterLastFourInArray(int[]) - тестирование метода, если передан
     * массив без четвёрок
     */
    @ParameterizedTest
    @MethodSource("dataForNumbersAfterLastFourInArray")
    public void testNumbersAfterLastFourInArray(int[] array, int[] result) {
        int[] actual = main.getNumbersAfterLastFourInArray(array);
        Assertions.assertArrayEquals(result, actual);
    }

    private static Stream< Arguments > dataForNumbersAfterLastFourInArray() {
        List<Arguments> out = new ArrayList<>();
        // 1 кейс: в массиве только одна 4
        out.add(Arguments.arguments(new int[] {4}, new int[] {}));
        // 2 кейс: в массиве несколько 4
        out.add(Arguments.arguments(new int[] {4, 4, 4, 4}, new int[] {}));
        // 3 кейс: одна 4 в начале массива
        out.add(Arguments.arguments(new int[] {4, 2, 0, 8, 2, 3, 1, 9, 1, 7}, new int[] {2, 0, 8, 2, 3, 1, 9, 1, 7}));
        // 4 кейс: одна 4 в середине массива
        out.add(Arguments.arguments(new int[] {1, 2, 0, 8, 4, 2, 3, 9, 1, 7}, new int[] {2, 3, 9, 1, 7}));
        // 5 кейс: одна 4 в конце массива
        out.add(Arguments.arguments(new int[] {1, 2, 0, 8, 2, 3, 7, 9, 1, 4}, new int[] {}));
        // 6 кейс: две 4 в середине массива
        out.add(Arguments.arguments(new int[] {1, 2, 0, 8, 4, 4, 2, 9, 1, 7}, new int[] {2, 9, 1, 7}));
        // 7 кейс: три 4 в середине массива
        out.add(Arguments.arguments(new int[] {1, 2, 0, 8, 4, 4, 4, 9, 1, 7}, new int[] {9, 1, 7}));
        return out.stream();
    }

    /**
     * Тестирование метода, который проверяет наличие в массиве целочисленных чисел 1 и 4.
     *
     * @param array Тестовые данные
     * @param result Ожидаемый результат
     */
    @ParameterizedTest
    @MethodSource("dataForArrayHasExpectedNumbers")
    public void testArrayHasExpectedNumbers(int[] array, boolean result) {
        boolean actual = main.arrayHasExpectedNumbers(array);
        if (result) {
           Assertions.assertTrue(actual);
        } else {
            Assertions.assertFalse(actual);
        }
    }

    private static Stream< Arguments > dataForArrayHasExpectedNumbers() {
        List<Arguments> out = new ArrayList<>();
        // 1 кейс: пустой массив
        out.add(Arguments.arguments(new int[] {}, false));
        // 2 кейс: массив любых чисел, но нет 1 и 4
        out.add(Arguments.arguments(new int[] {2, 3, 5}, false));
        // 3 кейс: массив любых чисел кроме 1
        out.add(Arguments.arguments(new int[] {4, 8, 7}, false));
        // 4 кейс: массив любых чисел кроме 4
        out.add(Arguments.arguments(new int[] {9, 5, 1}, false));
        // 5 кейс: массив любых чисел включая 1 и 4)
        out.add(Arguments.arguments(new int[] {1, 3, 4}, true));
        // 6 кейс: массив любых чисел, несколько 1
        out.add(Arguments.arguments(new int[] {1, 1, 3, 4}, true));
        // 6 кейс: массив любых чисел, несколько 4
        out.add(Arguments.arguments(new int[] {1, 6, 8, 4, 9, 4}, true));
        return out.stream();
    }
}