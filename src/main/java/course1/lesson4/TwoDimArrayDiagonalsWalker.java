package course1.lesson4;

/**
 * Прототип класса, который будет выполнять вспомогательные операции при работе
 * с двухмерными массивами. Пока что здесь реализован обход всех дагоналей
 * массива размера 5x5. На основе кода этого класса была реализована проверка
 * победы из 4 фишек в игровом поле 5x5.
 *
 * @author Denis Khvorostin <denis.khvorostin@gmail.com>
 * @version 0.1
 */
public class TwoDimArrayDiagonalsWalker {

    public static void main(String[] args) {

        int[][] matrix = new int[5][5];

        for (int i = 0, matrixLength = matrix.length; i < matrixLength; i++) {
            if (i == 0) {
                walkUpDiagonal(i, i);
                walkDownDiagonal(i, i, matrixLength);
            } else {
                walkUpDiagonal(i, 0);
                walkUpDiagonal(matrixLength-1, i);
                walkDownDiagonal(i, 0, matrixLength);
                walkDownDiagonal(0, i, matrixLength);
            }
        }
    }

    /**
     * Обход диагоналей, параллельных диагонали [0][0], [1][1], ... [n][n],
     * включая саму эту диагональ.
     *
     * @param x Координата X ячейки, в которой начинается диагональ
     * @param y Координата Y ячейки, в которой начинается диагональ
     * @param matrixLength Ширина массива
     */
    private static void walkDownDiagonal(int x, int y, int matrixLength)
    {
        do {
            System.out.print(x + "," + y + " ");
            x++;
            y++;
        } while (x < matrixLength && y < matrixLength);
        System.out.print("\n");
    }

    /**
     * Обход всех диагоналей, параллельных диагонали [n][0], [n-1],[1] ... [0][n],
     * включая саму эту диагональ
     *
     * @param x Координата X ячейки, в которой начинается диагональ
     * @param y Координата Y ячейки, в которой начинается диагональ
     */
    private static void walkUpDiagonal(int x, int y)
    {
        int fixX = x;

        do {
            System.out.print(x + "," + y + " ");
            x--;
            y++;
        } while (y <= fixX);
        System.out.print("\n");
    }
}
