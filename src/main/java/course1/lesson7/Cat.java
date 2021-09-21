package course1.lesson7;

public class Cat {

    /** Кличка кота */
    private final String name;

    /** Аппетит */
    private final int appetite;

    /** Сытость */
    private boolean satiety = false;

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    /**
     * Метод-симуляция поглощения пищи
     *
     * @param plate Тарелка из которой кот пытается есть.
     */
    public void eat(Plate plate) {

        // если кот сыт или коту мало еды в тарелке, то он не трогает еду
        if (satiety || plate.getFood() < appetite) {
            return;
        }

        // эта проверка избыточна, т.к. выше мы учли момент с порцей, которая меньше аппетита,
        // но всё же проверим, достаточно ли съел кот, чтобы наесться
        satiety = (plate.getFood()-plate.decreaseFood(appetite)) >= appetite;
    }

    /**
     * Метод для того, чтобы сделать кота вновь голодным.
     */
    public void getHungry()
    {
        satiety = false;
    }

    /**
     * Выводит в консоль информацию о текущей сытости кота.
     */
    public void displayInfoAboutSatiety()
    {
        System.out.println(name + " " + (satiety ? "сыт" : "голоден"));
    }
}
