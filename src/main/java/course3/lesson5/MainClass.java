package course3.lesson5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {

    public static final int CARS_COUNT = 4;

    public static Semaphore semaphore = new Semaphore(CARS_COUNT / 2);

    public static void main(String[] args) throws InterruptedException {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!");

        Race race = new Race(new Road(60), new Tunnel(semaphore), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        // числу участников + основной поток
        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT + 1);

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cb);
        }

        Thread[] threads = new Thread[CARS_COUNT];
        for (int i = 0; i < CARS_COUNT; i++) {
            threads[i] = new Thread(cars[i]);
        }

        for (int i = 0; i < CARS_COUNT; i++) {
            threads[i].start();
        }

        try {
            // здесь ждём, пока все участники будут готовы
            cb.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!");

        try {
            // а здесь ждём, пока все участники закончат прохождение всех этапов
            cb.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!");
    }
}