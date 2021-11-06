package course2.lesson1;

/**
 * Интерфейс участника. Фиксирует контракт: каждый актор должен уметь бегать и прыгать, а также сообщать своё имя
 * и физические ограничения (максимально допустимую длину дистанции, которую он может пробежать, и максимально
 * допустимую высоту стены, которую он может перепрыгнуть)
 */
public interface Actor {

    default boolean run(int distance) {

        boolean result;
        String msg = "[" + getName() + "]: ";
        if (distance <= getDistanceLimit()) {
            msg += RunResult.SUCCESS.getMsg();
            result = true;
        } else {
            msg += RunResult.FAILED.getMsg();
            result = false;
        }

        System.out.println(msg + " " + distance + " м.");
        return result;
    }

    default boolean jump(int height) {
        boolean result;
        String msg = "[" + getName() + "]: ";
        if (height <= getHeightLimit()) {
            msg += JumpResult.SUCCESS.getMsg();
            result = true;
        } else {
            msg += JumpResult.FAILED.getMsg();
            result = false;
        }

        System.out.println(msg + " " + height + " м.");
        return result;
    }

    String getName();
    int getDistanceLimit();
    int getHeightLimit();
}
