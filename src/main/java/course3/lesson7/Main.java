package course3.lesson7;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // есть три тестовых класса, запускаем их в цикле

        for (int i = 1; i <= 3; i++) {
            try {
                Class< ? > testClass = Class.forName("course3.lesson7.MockTest" + i);
                start(testClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void start(Class< ? > testClass) {

        // Проверка переданного класса на то, что он тестовый. По идее, должна быть какая-то проверка, которая
        // исключит возможность передачи какого попало класса. Наличие аннотации @TestSuite, конечно, не решает
        // проблемы, но хотя бы обозначает её.

        if (testClass.getDeclaredAnnotation(TestSuite.class) == null) {
            throw new RuntimeException("Class without @TestSuite annotation");
        }

        System.out.println("== Начало выполнения тестов из класса " + testClass.getSimpleName());

        // Пробегаемся по списку методов класса, используя Reflection API.
        // Методы с аннотациями @BeforeSuite и @AfterSuite сохраняем в строковых переменных (такие методы могут быть
        // только по 1 на класс-тест, поэтому используем переменные-строки), методы с аннотацией @Test складываем
        // в словарь имен, где ключом будет название метода, а значением - его приоритет.

        String beforeSuiteMethodName = null;
        String afterSuiteMethodName = null;

        Map<String, Integer> methodNames = new LinkedHashMap<>();

        Method[] methods = testClass.getDeclaredMethods();
        for (Method o : methods) {

            if (o.getDeclaredAnnotation(BeforeSuite.class) != null) {
                if (beforeSuiteMethodName != null) {
                    throw new RuntimeException("Where must be only one @BeforeSuite annotation in class");
                }

                beforeSuiteMethodName = o.getName();
                continue;
            }

            if (o.getDeclaredAnnotation(AfterSuite.class) != null) {
                if (afterSuiteMethodName != null) {
                    throw new RuntimeException("Where must be only one @AfterSuite annotation in class");
                }

                afterSuiteMethodName = o.getName();
                continue;
            }

            if (o.getDeclaredAnnotation(Test.class) != null) {
                methodNames.put(o.getName(), o.getDeclaredAnnotation(Test.class).value());
            }
        }

        try {

            // Получаем инстанс переданного класса
            Constructor< ? > constructor = testClass.getConstructor();
            MockTest mt = (MockTest) constructor.newInstance();

            // если есть метод с аннотацией @BeforeSuite, выполняем его
            if (beforeSuiteMethodName != null) {
                Method bs = testClass.getDeclaredMethod(beforeSuiteMethodName);
                bs.invoke(mt);
            }

            // Выполняем методы-тесты в порядке, соответствующем приоритету (для этого сортируем значения)

            List< Map.Entry<String, Integer> > list = new ArrayList<>(methodNames.entrySet());
            list.sort(Map.Entry.comparingByValue());

            for (Map.Entry<String, Integer> entry : list) {
                Method t = testClass.getDeclaredMethod(entry.getKey());
                t.invoke(mt);
            }

            // если есть метод с аннотацией @AfterSuite, выполняем его
            if (afterSuiteMethodName != null) {
                Method as = testClass.getDeclaredMethod(afterSuiteMethodName);
                as.invoke(mt);
            }

        } catch (NoSuchMethodException|InstantiationException|IllegalAccessException|InvocationTargetException e) {
            e.getStackTrace();
        }

        System.out.println("== Завершение выполнения тестов из класса " + testClass.getSimpleName());
    }
}
