package course2.lesson1;

public class Main {

    public static void main(String[] args) {

        // массив участников
        Actor[] actors = new Actor[]{
            new Human("Alice", 500, 2),
            new Cat("Harfield", 110, 2),
            new Cat("Matroskin", 90, 1),
            new Robot("Bender", 1000, 5),
            new Robot("3PO", 40, 0),
            new Human("Bob", 60, 0),
        };

        // массив препятствий
        Plot[] plots = new Plot[]{
            new Track(100),
            new Track(50),
            new Barrier(1),
            new Track(150),
            new Barrier(2),
            new Barrier(3),
        };

        // каждый из участников преодолевает препятстивия по очереди
        for (Actor actor : actors) {
            // второй массив обходим внутри отдельного метода, чтобы иметь возможность прерваться,
            // если какой-то этап провален (можно было бы использовать label, но вариант с return
            // мне нравится больше + меньше уровней вложенности)
            overcomePlots(actor, plots);
        }
    }

    /**
     * Метод, в котором реализован цикл обхода препятствий конкретным участником. Если на каком-то этапе
     * участник терпит неудачу, обход цикла прерывается и выводится соответствующее сообщение.
     *
     * @param actor Участник
     * @param plots Массив препятствий
     */
    private static void overcomePlots(Actor actor, Plot[] plots) {
        // последовательный проход по участкам полосы препятствий
        for (Plot plot : plots) {
            // если участок пройден успешно - участник двигается дальше,
            // иначе сходит с полосы препятствий
            if (!plot.overcome(actor)) {
                System.out.println("*** " + actor.getName() + " сходит с полосы препятствий ***");
                // прерываем движение по полосе препятствий
                return;
            }
        }
    }

}
