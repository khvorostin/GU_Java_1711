package course1.lesson6;

public class HomeWorkApp {

    public static void main(String[] args) {

        // создадим массив животных (несколько собак и несколько котов)

        Animal[] animals = {
            new Dog("Мухтар"),
            new Cat("Гарфилд"),
            new Cat("Гладислав"),
            new Dog("Хатико"),
            new Dog("Цербер"),
        };

        // выведем информацию о том, сколько пробежал/проплыл каждый зверь
        for (Animal animal : animals) {
            animal.run(1000);
            animal.swim(5);
            System.out.println(""); // для наглядности добавим строку между животными
        }

        // статистика созданных животных
        Animal.getStat();
    }
}
