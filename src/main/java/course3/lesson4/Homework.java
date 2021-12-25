package course3.lesson4;

public class Homework {

    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) throws InterruptedException {
        Homework hw = new Homework();
        Thread thread1 = new Thread(() -> {
            hw.printLetterAfterLetter('A', 'B');
        });
        Thread thread2 = new Thread(() -> {
            hw.printLetterAfterLetter('B', 'C');
        });
        Thread thread3 = new Thread(() -> {
            hw.printLetterAfterLetter('C', 'A');
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    public void printLetterAfterLetter(char letterToPrint, char letterToWait) {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != letterToPrint) {
                        mon.wait();
                    }
                    System.out.print(letterToPrint);
                    currentLetter = letterToWait;
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
