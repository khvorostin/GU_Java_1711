package course2.lesson1;

/**
 * Человек. Участник.
 */
public class Human implements Actor {

    /**
     * Имя человека.
     */
    private final String name;

    /**
     * Максимальная дистанция, которую способен пробежать человек.
     */
    private final int distanceLimit;

    /**
     * Максимальная высота, на которую способен прыгнуть человек.
     */
    private final int heightLimit;

    public Human(String name, int distanceLimit, int heightLimit) {
        this.name = name;
        this.distanceLimit = distanceLimit;
        this.heightLimit = heightLimit;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDistanceLimit() {
        return distanceLimit;
    }

    @Override
    public int getHeightLimit() {
        return heightLimit;
    }
}
