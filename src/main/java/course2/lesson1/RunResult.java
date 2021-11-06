package course2.lesson1;

/**
 * Возможные результаты преодоления беговой дорожки.
 */
public enum RunResult {

    SUCCESS("Успешно пробежал"),
    FAILED("Не смог пробежать");

    private final String msg;

    RunResult(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
