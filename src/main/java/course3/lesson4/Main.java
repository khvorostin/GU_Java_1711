package course3.lesson4;

public class Main {

    public static void main(String[] args) {

        Thread thread1 = new Thread(new LetterPrinter('A'));
        Thread thread2 = new Thread(new LetterPrinter('B'));
        Thread thread3 = new Thread(new LetterPrinter('C'));

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class LetterPrinter implements Runnable {

    private char lastPrintedLetter = 'C';
    private char waitForLetter = 'C';

    private final char letter;

    public LetterPrinter(char letter) {
        this.letter = letter;
        switch (letter) {
            case 'A':
                waitForLetter = 'C';
                break;
            case 'B':
                waitForLetter = 'A';
                break;
            case 'C':
                waitForLetter = 'B';
                break;
        }
    }

    @Override
    public void run() {
        printLetter();
    }

    synchronized void printLetter() {
        for (int i = 0; i < 5; i++) {
            while (lastPrintedLetter != waitForLetter) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.print(letter);
            lastPrintedLetter = letter;
            notifyAll();
        }
    }
}