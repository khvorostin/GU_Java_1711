package course3.lesson7;

/**
 * Первый "тестовый класс". Здесь есть два метода с аннотациями @BeforeSuite и @AfterSuite, а также четыре метода-теста,
 * у двух из которых совпадает приоритет. Все методы просто выводят в консоль имена класса и метода.
 */
@TestSuite
public class MockTest1 implements MockTest {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println(this.getClass().getSimpleName() + " [before suite]");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println(this.getClass().getSimpleName() + " [after suite]");
    }

    @Test(4)
    public void foo() {
        System.out.println(this.getClass().getSimpleName() + ":foo [4]");
    }

    @Test(2)
    public void bar() {
        System.out.println(this.getClass().getSimpleName() + ":bar [2]");
    }

    @Test(1)
    public void baz() {
        System.out.println(this.getClass().getSimpleName() + ":baz [1]");
    }

    @Test(1)
    public void quux() {
        System.out.println(this.getClass().getSimpleName() + ":quux [1]");
    }

}
