package course1.lesson6;

public class Cat extends Animal {

    // Константы физических ограничений по умолчанию. Т.е. столько сможет пробежать и проплыть
    // любой кот, однако на случай тренированных котов и особых пород создаем дополнительный
    // конструктор, который понимает дистанции.

    public static final int RUN_LIMIT = 200;
    public static final int SWIM_LIMIT = 0;

    public Cat(String nickname) {
        this(nickname, RUN_LIMIT, SWIM_LIMIT);
    }

    public Cat(String nickname, int runLimit, int swimLimit) {
        super(nickname, runLimit, swimLimit);
    }
}
