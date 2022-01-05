package course3.lesson7;

/**
 * Второй "тестовый класс". Здесь опущен метод с аннотацией @BeforeSuite, а у методов xyzzy() и plugh() приоритеты
 * выставлены в обратном порядке относительно очереди в файле.
 */
@TestSuite
public class MockTest2 implements MockTest {

    @AfterSuite
    public void afterSuite() {
        System.out.println(this.getClass().getSimpleName() + " [after suite]");
    }

    @Test(9)
    public void xyzzy() {
        System.out.println(this.getClass().getSimpleName() + ":xyzzy [9]");
    }

    @Test(1)
    public void plugh() {
        System.out.println(this.getClass().getSimpleName() + ":plugh [1]");
    }
}
