package course2.lesson1;

/**
 * Робот. Участник.
 */
public class Robot implements Actor {

    /**
     * Название робота.
     */
    private final String name;

    /**
     * Максимальная дистанция, которую способен пробежать робот.
     */
    private final int distanceLimit;

    /**
     * Максимальная высота, на которую способен прыгнуть робот.
     */
    private final int heightLimit;

    public Robot(String name, int distanceLimit, int heightLimit) {
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
