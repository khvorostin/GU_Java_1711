package course1.lesson5;

public class HomeWorkApp {

    public static void main(String[] args) {

        Employee[] staff = new Employee[5];
        staff[0] = new Employee("Иванов Агей Венниаминович", "Генеральный директор", "ivanov53@yandex.ru", "89111235423", 300000, 68);
        staff[1] = new Employee("Метронова-Слободская Инга Алексеевна", "Замдиректора по персоналу", "msi@gmail.com", "89114531212", 50000, 34);
        staff[2] = new Employee("Петров Константин Вальерьевич", "Руководитель отдела продаж", "sales@sales.ru", "89999990000", 50000, 32);
        staff[3] = new Employee("Сидоров Кузьма Валентинович", "Ведущий разработчик", "ilovejava@hire.me", "88125434241", 200000, 42);
        staff[4] = new Employee("Эккель Юлия Борисовна", "Секретарь", "zaychonok@mail.ru", "+79212292119", 35000, 21);

        for (Employee employee : staff) {
            if (employee.getAge() > 40) {
                System.out.println(employee.toString());
            }
        }
    }
}
