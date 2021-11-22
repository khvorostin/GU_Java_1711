package course2.lesson1;

/**
 * Беговая дорожка. Препятствие некоторой длины, которое участники должны пробежать.
 */
public class Track implements Plot {

    /**
     * Длина дорожки (м.)
     */
    private final int distance;

    public Track(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean overcome(Actor actor) {
        return actor.run(distance);
    }
}
