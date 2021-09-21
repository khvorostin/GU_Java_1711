package course1.lesson7;

public class Plate {

    /** Размер тарелки */
    private final static int PLATE_SIZE = 100;

    private int food;

    public Plate(int food) {
        this.food = food;
    }

    public int getFood() {
        return food;
    }

    /**
     * Добавление еды в тарелку, добавить на тарелку больше, чем поместится нельзя.
     *
     * @param food Размер порции
     */
    public void addFood(int food) {
        this.food += Math.min(food, PLATE_SIZE);
    }

    /**
     * Уменьшение еды на тарелке, съесть больше, чем есть - нельзя.
     *
     * @param n На сколько уменьшается еда в тарелке
     * @return Количество еды после уменьшения
     */
    public int decreaseFood(int n) {
        // нельзя съесть больше, чем есть в тарелке
        food -= Math.min(food, n);
        return food;
    }
}
