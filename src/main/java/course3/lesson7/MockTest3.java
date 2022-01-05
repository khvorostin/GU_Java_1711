package course3.lesson7;

/**
 * Третий "тестовый класс". Здесь два метода помечены аннотацией @BeforeSuite, что должно вызвать RuntimeException.
 */
@TestSuite
public class MockTest3 implements MockTest {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println(this.getClass().getSimpleName() + " [before suite]");
    }

    @BeforeSuite
    public void anotherBeforeSuite() {
        System.out.println(this.getClass().getSimpleName() + " [second before suite]");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println(this.getClass().getSimpleName() + " [after suite]");
    }

    @Test(1)
    public void spam() {
        System.out.println(this.getClass().getSimpleName() + ":spam [1]");
    }

    @Test(2)
    public void eggs() {
        System.out.println(this.getClass().getSimpleName() + ":eggs [2]");
    }

}
