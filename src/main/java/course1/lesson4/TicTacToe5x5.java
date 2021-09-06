package course1.lesson4;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Протитип игры в крестики-нолики на поле 5 на 5. В текущей версии реализован игровой цикл (вывод игрового поля
 * в консоль, проверка победы игрока/компмьютера, проверка заполненности поля). Логика выборка оптимального хода
 * компьютера в данной версии не реализована (компьютер занимает рандомно выбранное пустое поле).
 *
 * Ход игрока принимается в виде строки, например: A1 или a1 (учитываются как строчные, так и прописные символы)
 *
 * @author Denis Khvorostin <denis.khvorostin@gmail.com>
 * @version 1.0
 */
public class TicTacToe5x5 {

    private static Scanner sc = new Scanner(System.in);
    private static Random random = new Random();

    /**
     * Игровое поле
     */
    private static byte[][] map;

    /**
     * Размер игрового поля
     */
    private static final int MAP_SIZE = 5;

    /**
     * Количество крестиков/ноликов для победы
     */
    private static final int DOTS_TO_WIN = 4;

    /**
     * Обозначения колонок на игровом поле
     */
    private static final char[] columnSigns = {'A', 'B', 'C', 'D', 'E'};

    /**
     * Номера рядов на игровом поле
     */
    private static final char[] rowNumbers = {'1', '2', '3', '4', '5'};

    /**
     * "Стоимость" позиций в игровом поле
     */
    private static final byte EMPTY_POS_COST = 0;
    private static final byte X_POS_COST = 6; // 6 > MAP_SIZE * O_POS_COST
    private static final byte O_POS_COST = 1;

    /**
     * Символы для обозначения пустой позиции, крестика и нолика
     */
    private static final char DOT_EMPTY = '•';
    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';

    public static void main(String[] args) {

        initMap();
        showMap();

        while (true) {

            userTurn();
            showMap();

            if (checkWin(X_POS_COST)) {
                System.out.println("\nВы победили");
                break;
            }

            aiTurn();
            showMap();
            if (checkWin(O_POS_COST)) {
                System.out.println("\nКомпьютер победил");
                break;
            }

            if (isMapFull()) {
                System.out.println("\nИгра окончена. Ничья.");
                break;
            }
        }
    }

    /**
     * Инициализирует игровое поле размером MAP_SIZE * MAP_SIZE; в начале каждая игровая позиция равна нулю.
     */
    private static void initMap() {
        map = new byte[MAP_SIZE][MAP_SIZE];
    }

    /**
     * Выводит игровое поле в консоль. Формируемое поле выглядит так:
     *
     *   5 • • • • •
     *   4 • • • • •
     *   3 • • • • •
     *   2 • • • • •
     *   1 • • • • •
     *     A B C D E
     */
    private static void showMap() {

        // перенос строки, чтобы поле не прилипло к ранее выведенному тексту
        System.out.print("\n");

        for (int i = 0, row = MAP_SIZE; i < MAP_SIZE; i++, row--) {
            System.out.print(row + " "); // номер ряда
            for (int y = 0; y < MAP_SIZE; y++) {
                char sign;
                switch (map[i][y]) {
                    case X_POS_COST:
                        sign = DOT_X;
                        break;
                    case O_POS_COST:
                        sign = DOT_O;
                        break;
                    default:
                        sign = DOT_EMPTY;
                }
                System.out.print(sign + " "); // символ в позиции [i][y]
            }

            System.out.print("\n"); // перенос строки
        }

        // обозначения колонок под игровым полем
        System.out.print(" ");
        for (char columnSign : columnSigns) {
            System.out.print(" " + columnSign);
        }
        System.out.print("\n"); // перенос строки
    }

    private static void userTurn() {

        int x, y;
        String turn;

        do {
            System.out.print("Ваш ход: ");
            turn = sc.next();
            turn = turn.toUpperCase();
        } while (
                turn.length() != 2
                        || (y = Arrays.binarySearch(columnSigns, turn.charAt(0))) < 0
                        || (x = MAP_SIZE - 1 - Arrays.binarySearch(rowNumbers, turn.charAt(1))) < 0
                        || map[x][y] > 0
        );

        map[x][y] = X_POS_COST;
    }

    /**
     * Компьютер ходит рандомно.
     */
    private static void aiTurn() {

        int x, y;

        do {
            x = random.nextInt(MAP_SIZE);
            y = random.nextInt(MAP_SIZE);
        } while (map[x][y] != 0);

        map[x][y] = O_POS_COST;
        System.out.println("\nКомпьютер сделал ход в клетку " + columnSigns[y] + rowNumbers[MAP_SIZE-1-x] + ".");
    }

    /**
     * Проверяет наличие выигрышной комбинации на игровом поле для указанного игрового символа.
     *
     * @param cost "Стоимость" проверяемого символа (крестика или нолика)
     * @return Наличие выигрышной комбинации на игровом поле.
     */
    public static boolean checkWin(byte cost) {

        // если передан символ, отличный от крестика или нолика, возвращаем false
        if (cost != X_POS_COST && cost != O_POS_COST) {
            return false;
        }

        // переменные для проверки в вертикалях и горизонталях опрелеяем внутри цикла

        for (int x = 0, sumX = 0, sumY = 0; x < MAP_SIZE; x++, sumX = 0, sumY = 0) {
            for (int y = 0; y < MAP_SIZE; y++) {

                // если "стоимость" ячейки равна проверяемому значению, то добавляем единицу к сумме подряд идущих ячеек,
                // иначе считаем последовательность разбитой и число подряд идущих ячеек обнуляем

                if (map[x][y] == cost) {
                    sumX++;
                } else if (sumX < DOTS_TO_WIN) { // здесь и далее: проверка нужна, чтобы последнея пустая ячейка не затерла выигрыш
                    sumX = 0;
                }

                if (map[y][x] == cost) {
                    sumY++;
                } else if (sumY < DOTS_TO_WIN) {
                    sumY = 0;
                }
            }

            if (sumX >= DOTS_TO_WIN || sumY >= DOTS_TO_WIN) {
                return true;
            }

            // проверка диагоналей (проверяются все диагонали игрового поля, независимо от их длины)
            // такое решение сделано сознательно: жертвуем лишними циклами в угоду лёгкости изменения
            // размера игрового поля и числа фишек, необходимых для победы

            if (x == 0 && (checkWinInUpDiagonal(cost, x, x) || checkWinInDownDiagonal(cost, x, x))) {
                return true;
            }

            if (x > 0 && (
                    checkWinInUpDiagonal(cost, x, 0)
                            || checkWinInUpDiagonal(cost, MAP_SIZE - 1, x)
                            || checkWinInDownDiagonal(cost, x, 0)
                            || checkWinInDownDiagonal(cost, 0, x)
            )) {
                return true;
            }
        }

        return false;
    }

    /**
     * Проверка наличия четырёх подряд идущих фишек в диагонали, параллельной
     * диагонали [0][0], [1][1], ... [n][n] и начинающейся в позиции [x][y]
     *
     * @param cost ...
     * @param x    Координата X ячейки, в которой начинается диагональ
     * @param y    Координата Y ячейки, в которой начинается диагональ
     * @return В проверяемой диагонали есть выигрышная позиция
     */
    private static boolean checkWinInDownDiagonal(int cost, int x, int y) {

        // число идущих подряд ячеек с одной стоимостью
        int sum = 0;

        do {
            // если "стоимость" ячейки равна проверяемому значению, то добавляем единицу к сумме подряд идущих ячеек,
            // иначе считаем последовательность разбитой и число подряд идущих ячеек обнуляем
            if (map[x][y] == cost) {
                sum++;
            } else if (sum < DOTS_TO_WIN) {
                sum = 0;
            }

            x++;
            y++;
        } while (x < MAP_SIZE && y < MAP_SIZE);

        return sum >= DOTS_TO_WIN;
    }

    /**
     * Проверка наличия четырёх подряд идущих фишек в диагонали, параллельной
     * [n][0], [n-1],[1] ... [0][n] и начинающейся в позиции [х][y]
     *
     * @param cost ...
     * @param x    Координата X ячейки, в которой начинается диагональ
     * @param y    Координата Y ячейки, в которой начинается диагональ
     * @return В проверяемой диагонали есть выигрышная позиция
     */
    private static boolean checkWinInUpDiagonal(int cost, int x, int y) {

        // число идущих подряд ячеек с одной стоимостью
        int sum = 0;

        int fixX = x;

        do {
            // если "стоимость" ячейки равна проверяемому значению, то добавляем единицу к сумме подряд идущих ячеек,
            // иначе считаем последовательность разбитой и число подряд идущих ячеек обнуляем
            if (map[x][y] == cost) {
                sum++;
            } else if (sum < DOTS_TO_WIN) {
                sum = 0;
            }

            x--;
            y++;
        } while (y <= fixX);

        return sum >= DOTS_TO_WIN;
    }

    /**
     * Проверяет наличие на игровом поле пустых позиций.
     *
     * @return Поле заполнено (да/нет)
     */
    private static boolean isMapFull() {

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                if (map[i][y] == EMPTY_POS_COST) {
                    return false;
                }
            }
        }

        return true;
    }
}
