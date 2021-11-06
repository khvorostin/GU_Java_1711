package course2.lesson1;

/**
 * Стена. Препятствие, которое участники должны перепрыгнуть.
 */
public class Barrier implements Plot {

    /**
     * Высота стены (м.)
     */
    private final int height;

    public Barrier(int height) {
        this.height = height;
    }

    @Override
    public boolean overcome(Actor actor) {
        return actor.jump(height);
    }
}
