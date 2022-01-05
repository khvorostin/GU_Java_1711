package course3.lesson6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public Main() {
        // do nothing
    }

    public static void main(String[] args) {

        Main main = new Main();

        int[] i = {1, 2, 4, 4, 2, 3, 4, 1, 7};
        int[] r = main.getNumbersAfterLastFourInArray(i);
        System.out.println(Arrays.toString(r));
    }

    /**
     * Метод, которому в качестве аргумента передается непустой одномерный целочисленный массив.
     * Метод возвращает новый массив, который получен путем вытаскивания из исходного массива элементов,
     * идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
     * иначе в методе необходимо выбросить RuntimeException.
     *
     * Набор тестов для этого метода (по 3-4 варианта входных данных) см. в классе MainTest в области видимости test
     *
     * @param ints Непустой одномерный целочисленный массив
     */
    public int[] getNumbersAfterLastFourInArray(int[] ints) {

        List<Integer> list = new ArrayList<>();

        boolean addToResult = false;

        for (int i : ints) {
            if (i == 4) {
                list = new ArrayList<>();
                addToResult = true;
            } else if (addToResult) {
                list.add(i);
            }
        }

        if (!addToResult) {
            throw new RuntimeException("no 4 in array");
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * Метод, который проверяет наличие в массиве чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
     * то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).
     */
    public boolean arrayHasExpectedNumbers(int[] ints) {

        boolean has1 = false;
        boolean has4 = false;

        for (int i : ints) {
            if (i == 1) {
                has1 = true;
            }

            if (i == 4) {
                has4 = true;
            }

            if (has1 && has4) {
                return true;
            }
        }

        return false;
    }
}
