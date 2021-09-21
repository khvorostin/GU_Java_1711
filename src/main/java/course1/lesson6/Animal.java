package course1.lesson6;

public class Animal {

    /** Минимально возможная дистанция (м.) */
    private static final int MIN_DISTANCE = 0;

    /** Максимально возможная дистанция (м.) */
    private static final int MAX_DISTANCE = 2000;

    /** Ограничение по бегу для конкретного животного (м.) */
    protected int runLimit;

    /** Ограничение по плаванию для конкретного животного (м.) */
    protected int swimLimit;

    /** Кличка животного */
    protected String nickname;

    /** Счетчик созданных животных */
    private static int amimalsCount = 0;

    /** Счетчик созданных собак */
    private static int dogsCount = 0;

    /** Счетчик созданных котов */
    private static int catsCount = 0;

    public Animal(String nickname, int runLimit, int swimLimit) {

        // если передана отрицательная дистанция, то сбрасываем её на ноль,

        if (runLimit < 0) {
            runLimit = Animal.MIN_DISTANCE;
        }

        if (swimLimit < 0) {
            swimLimit = Animal.MIN_DISTANCE;
        }

        // а если передана очень большая дистанция - то ограничиваем её также

        if (runLimit > Animal.MAX_DISTANCE) {
            runLimit = Animal.MAX_DISTANCE;
        }

        if (swimLimit > Animal.MAX_DISTANCE) {
            swimLimit = Animal.MAX_DISTANCE;
        }

        // собственно, сохраняем кличку и ограничения по бегу/плаванию в переменных класса

        this.nickname = nickname;
        this.runLimit = runLimit;
        this.swimLimit = swimLimit;

        // считаем созданных животных

        amimalsCount++; // у нас всегда животное

        // а вот собак и котов отличаем благодаря проверки на принадлежность к классу
        if (this instanceof Dog) {
            dogsCount++;
        } else if (this instanceof Cat) {
            catsCount++;
        }
    }

    /**
     * Выводит информацию о том, сколько пробежало животное, учитывая его физические возможности.
     *
     * @param distance Дистанция, на которую мы отправляем животное
     */
    public void run(int distance)
    {
        if (runLimit == 0) {
            System.out.printf("%s не умеет бегать\n", nickname);
            return;
        }

        if (distance > runLimit) {
            distance = runLimit;
        }

        System.out.printf("%s пробежал %d м.\n", nickname, distance);
    }

    /**
     * Выводит информацию о том, сколько проплыло животное, учитывая его физические возможности.
     *
     * @param distance Дистанция, на которую мы отправляем животное
     */
    public void swim(int distance)
    {
        if (swimLimit == 0) {
            System.out.printf("%s не умеет плавать\n", nickname);
            return;
        }

        if (distance > swimLimit) {
            System.out.printf("%s не сможет проплыть %d м.\n", nickname, distance);
            return;
        }

        System.out.printf("%s проплыл %d м.\n", nickname, distance);
    }

    /**
     * Выводит информацию о том, сколько было создано животных в целом, а также сколько собак и котов
     * в частности.
     */
    public static void getStat()
    {
        System.out.println("Создано животных: " + amimalsCount + ", собак: " + dogsCount + " котов: " + catsCount);
    }
}
