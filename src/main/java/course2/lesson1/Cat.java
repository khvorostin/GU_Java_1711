package course2.lesson1;

/**
 * Кот. Участник.
 */
public class Cat implements Actor {

    /**
     * Кличка кота.
     */
    private final String name;

    /**
     * Максимальная дистанция, которую способен пробежать кот.
     */
    private final int distanceLimit;

    /**
     * Максимальная высота, на которую способен прыгнуть кот.
     */
    private final int heightLimit;

    public Cat(String name, int distanceLimit, int heightLimit) {
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
