package course1.lesson6;

public class Dog extends Animal {

    // Константы физических ограничений по умолчанию. Т.е. столько сможет пробежать и проплыть
    // любая собака, однако на случай тренированных собак и особых пород создаем дополнительный
    // конструктор, который понимает дистанции.

    public static final int RUN_LIMIT = 500;
    public static final int SWIM_LIMIT = 10;

    public Dog(String nickname) {
        this(nickname, RUN_LIMIT, SWIM_LIMIT);
    }

    public Dog(String nickname, int runLimit, int swimLimit) {
        super(nickname, runLimit, swimLimit);
    }
}
