package course2.lesson5;

import java.util.Arrays;

public class Main {

    private static final int SIZE = 1000000;

    public static void main(String[] args) {
        float[] arr1 = firstMethod();
        float[] arr2 = secondMethod();

        // по ДЗ эта проверка не нужна; делаем ее, чтобы убедиться,
        // что мы получили корректный результат
        if (Arrays.equals(arr1, arr2)) {
            System.out.println("Equal arrays");
        } else {
            System.out.println("Diff in arrays");
        }
    }

    /**
     * Метод бежит по массиву и вычисляет значения.
     */
    public static float[] firstMethod() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1.0f);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = calcValue(i);
        }

        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
        return arr;
    }

    /**
     * Метод разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает
     * эти массивы обратно в один.
     */
    public static float[] secondMethod() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1.0f);

        int halfSize = SIZE/2;

        float[] a1 = new float[halfSize];
        float[] a2 = new float[halfSize];

        long startTime = System.currentTimeMillis();

        // Разбивка массива на два
        System.arraycopy(arr, 0, a1, 0, halfSize);
        System.arraycopy(arr, halfSize, a2, 0, halfSize);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < halfSize; i++) {
                a1[i] = calcValue(i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < halfSize; i++) {
                a2[i] = calcValue(i + halfSize);
            }
        });

        thread1.start();
        thread2.start();

        // нам нужны результаты работы потоков, поэтому ждём
        while (thread1.isAlive() || thread2.isAlive()) {
            // do nothing
        }

        System.arraycopy(a1, 0, arr, 0, halfSize);
        System.arraycopy(a2, 0, arr, halfSize, halfSize);

        System.out.println("Two thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
        return arr;
    }

    private static float calcValue(int pos) {
        // arr[i] из формулы из ДЗ везде 1.0, т.е. для расчета нужна только позиция элемента в массиве
        // arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2)
        float i = (float)pos;
        return (float)(Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
    }

}
