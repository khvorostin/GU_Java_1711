package course2.lesson1;

/**
 * Возможные результаты преодоления стены.
 */
public enum JumpResult {

    SUCCESS("Успешно перепрыгнул"),
    FAILED("Не смог перепрыгнуть");

    private final String msg;

    JumpResult(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
