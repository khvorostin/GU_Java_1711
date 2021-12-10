package course3.lesson1;

import java.util.ArrayList;

public class Box {

    private ArrayList<Fruit> fruitsInBox;
    private int fruitsCounter;
    private String fruitName = "";

    /**
     * Добвлят фрукт в ящик
     *
     * @param fruit
     * @return Результат операции: true - фрукт добавлен в ящик, false - нет
     */
    public boolean addOne(Fruit fruit) {
        // определяем название фрукта
        String newFruitName = fruit.getClass().getSimpleName();

        // если коробка пуста, то запоминаем название первого фрукта
        if (0 == fruitsCounter) {
            fruitsInBox = new ArrayList<>();
            fruitName = newFruitName;
        }

        // теперь, если добавляемый фрукт соответствует содержимому,
        // добавляем его и увеличиваем счетчик фруктов
        if (newFruitName.equals(fruitName)) {
            fruitsInBox.add(fruit);
            fruitsCounter++;
            return true;
        }

        return false;
    }

    /**
     * Возвращает один фрукт из ящика.
     *
     * @return
     */
    public Fruit removeOne() {
        if (fruitsCounter == 0) {
            throw new IndexOutOfBoundsException("Current box is empty");
        }

        fruitsCounter--;
        return fruitsInBox.remove(0);
    }

    /**
     * Возвращает название фруктов внутри ящика.
     *
     * @return
     */
    public String getFruitName() {
        return fruitName;
    }

    /**
     * Сравнивает текущий ящик с переданным другим. В качестве сравнения используются названия фруктов и вес.
     *
     * @param otherBox Другой ящик, переданный для сравнения
     * @return Результат сравнения.
     */
    public boolean compare(Box otherBox) {
        // обе коробки пустые -> равны
        if (getWeight() == 0 && otherBox.getWeight() == 0) {
            return true;
        }

        // если какая-то из коробок пуста -> не равны
        if (getWeight() == 0 || otherBox.getWeight() == 0) {
            return false;
        }

        // содержимое ящиков не совпадает -> не равны
        if (!fruitName.equals(otherBox.getFruitName())) {
            return false;
        }

        // содержимое ящиков совпадает и вес фруктов внутри одинаков -> равны
        // если нет -> не равны
        return getWeight() == otherBox.getWeight();
    }

    /**
     * Возвращает число фруктов в ящике.
     *
     * @return
     */
    public int getFruitsCounter() {
        return fruitsCounter;
    }

    /**
     * Рассчитывает и возвращает вес ящика с фруктами
     *
     * @return Вес ящика
     */
    public float getWeight()
    {
        if (fruitsCounter == 0) {
            return 0;
        }

        return fruitsCounter * fruitsInBox.get(0).getWeight();
    }

    /**
     * Пересыпание содержимого текущего ящика в другой ящик
     *
     * @param otherBox Ящик, в который нужно пересыпать содержимое текущего
     * @return Результат пересыпания: true - если удалось пересыпать фрукты, false - если нет
     * (разные фрукты или в текущем ящике ничего нет)
     */
    public boolean shiftTo(Box otherBox) {
        if (fruitName.equals(otherBox.getFruitName()) && fruitsCounter > 0) {
            while (fruitsCounter > 0) {
                otherBox.addOne(removeOne());
            }
            return true;
        }

        return false;
    }
}
