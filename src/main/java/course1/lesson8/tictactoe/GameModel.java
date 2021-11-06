package course1.lesson8.tictactoe;

public class GameModel {

    /**
     * Игровое поле
     */
    private static byte[][] map;

    /**
     * Размер игрового поля
     */
    private static final int MAP_SIZE = 3;

    /**
     * Количество крестиков/ноликов для победы
     */
    private static final int DOTS_TO_WIN = 3;

    /**
     * Обозначения колонок на игровом поле
     */
    private static final char[] columnSigns = {'A', 'B', 'C'};

    /**
     * Номера рядов на игровом поле
     */
    private static final char[] rowNumbers = {'1', '2', '3'};

    /**
     * "Стоимость" позиций в игровом поле
     */
    public static final byte EMPTY_POS_COST = 0;
    public static final byte X_POS_COST = 4; // 4 > MAP_SIZE * O_POS_COST
    public static final byte O_POS_COST = 1;

    public GameModel() {
        initMap();
    }

    /**
     * Инициализирует игровое поле размером MAP_SIZE * MAP_SIZE; в начале каждая игровая позиция равна нулю.
     */
    private static void initMap() {
        map = new byte[MAP_SIZE][MAP_SIZE];
    }

    public boolean performUserTurn(char columnSign, int rowNumber) {

        int x = convertRowNumberToX(rowNumber);
        int y = convertColumnSignToY(columnSign);

        if (x < 0 || y < 0 || map[x][y] > GameModel.EMPTY_POS_COST) {
            return false;
        }

        map[x][y] = X_POS_COST;
        return true;
    }

    /**
     * Конвертирует номер ряда в графическом представлении игрового поля в идентификатор ряда в двухмерном массиве.
     * Если полученное число выходит за границы поля, метод вернет -1.
     *
     * @param rowNumber Номер ряда в графическом представлении игрового поля (счёт начинается с 1, идет снизу вверх)
     * @return Идентификатор ряда в двухмерном массиве
     */
    public static int convertRowNumberToX(int rowNumber) {
        int x = MAP_SIZE - rowNumber;
        if (x < 0 || x > 2) {
            return -1; //
        }

        return x;
    }

    /**
     * Конвертирует символ колонки в графическом представлении игрового поля в идентификатор колонки в двухмерном
     * массиве.
     *
     * @param columnSign Символ колонки в графическом представлении игрового поля (a, b, c, слева направо)
     * @return Идентификатор колонки в двухмерном массиве
     */
    public static int convertColumnSignToY(char columnSign) {
        int y;
        switch (columnSign) {
            case 'A':
                y = 0;
                break;
            case 'B':
                y = 1;
                break;
            case 'C':
                y = 2;
                break;
            default:
                return -1;
        }

        return y;
    }

    /**
     * Правила, имеющие приоритет над всеми остальными:
     * <p>
     * Правило 1. Если игрок может немедленно выиграть, он это делает.
     * Правило 2. Если игрок не может немедленно выиграть, но его противник мог бы немедленно выиграть,
     * сделав ход в какую-то клетку, игрок сам делает ход в эту клетку, предотвращая немедленный проигрыш.
     * <p>
     * Далее игровая стратегия выглядит так:
     * <p>
     * Если крестики сделали первый ход в центр, до конца игры ходить в любой угол,
     * а если это невозможно — в любую клетку.
     * <p>
     * Если крестики сделали первый ход в угол, ответить ходом в центр.
     * Следующим ходом занять угол, противоположный первому ходу крестиков,
     * а если это невозможно — пойти на сторону.
     * <p>
     * Если крестики сделали первый ход на сторону, ответить ходом в центр.
     * Если следующий ход крестиков — в угол, занять противоположный угол.
     * Если следующий ход крестиков — на противоположную сторону, пойти в любой угол.
     * Если следующий ход крестиков — на сторону рядом с их первым ходом,
     * пойти в угол рядом с обоими крестиками
     * <p>
     * Из этих правил реализована только последовательность ходов: центр -> углы -> стороны
     *
     * @return Сделал ли компьютер ход (да/нет)
     * @link https://ru.wikipedia.org/wiki/Крестики-нолики#За_нолики
     */
    public String performAiTurn() {

        String pos;

        // проверка, может ли компьютер немедленно выиграть
        pos = goToWinningPosition(O_POS_COST);
        if (!pos.equals("")) {
            return pos;
        }

        // проверка, может ли игрок немедленно выиграть
        pos = goToWinningPosition(X_POS_COST);
        if (!pos.equals("")) {
            return pos;
        }

        int[][] center = {{1, 1}};
        int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        int[][] edges = {{0, 1}, {1, 0}, {1, 2}, {2, 1}};

        // компьютер последовательно пытается занять позиции в центре, в углах и по сторонам игрового поля
        pos = aiTakePositions(center);
        if (!pos.equals("")) {
            return pos;
        }

        pos = aiTakePositions(corners);
        if (!pos.equals("")) {
            return pos;
        }


        pos = aiTakePositions(edges);
        if (!pos.equals("")) {
            return pos;
        }

        return "";
    }

    /**
     * Компьютер проверяет наличие выигрышной позиции на поле (когда в двух ячейках находятся одинаковые символы,
     * а в третья ячейка - пустая), и, если такая позиция есть, делает ход в неё. В случае крестиков это позволяет
     * заблокировать ход игрока (помешать ему выигрыть), в случае ноликов, выиграть.
     *
     * @param cost Проверяемая стоимость фишки
     * @return Позиция клетки, в которую сходил компьютер
     */
    private static String goToWinningPosition(byte cost) {

        int[] winPosition = findWinningPosition(cost);
        int x = winPosition[0], y = winPosition[1];
        if (x != -1 && y != -1 && map[x][y] == 0) {
            map[x][y] = O_POS_COST;
            return "" + columnSigns[y] + rowNumbers[MAP_SIZE - 1 - x];
        }

        return "";
    }

    /**
     * Компьютер проверяет доступные ячейки и занимает первую из переданных
     *
     * @param positions Позиции на игровом поле в виде массива массивов координат {x, y}
     * @return Позиция, в которую сходил компьютер или пустая строка
     */
    private static String aiTakePositions(int[][] positions) {

        for (int[] pos : positions) {
            int x = pos[0], y = pos[1];
            if (map[x][y] == 0) {
                map[x][y] = O_POS_COST;
                return "" + columnSigns[y] + rowNumbers[MAP_SIZE - 1 - x];
            }
        }

        return "";
    }

    /**
     * Проверяет наличие выигрышной комбинации на игровом поле для указанного игрового символа.
     *
     * @param cost Игровой символ (крестик или нолик)
     * @return Наличие выигрышной комбинации на игровом поле.
     */
    public boolean checkWin(byte cost) {

        // если передан символ, отличный от крестика или нолика, возвращаем false
        if (cost != X_POS_COST && cost != O_POS_COST) {
            return false;
        }

        // выигрышная комбинация
        int winCase = DOTS_TO_WIN * cost;

        // переменные для проверки выигрышных комбинаций в диагоналях определяем ДО цикла,
        // переменные для проверки в вертикалях и горизонталях - внутри цикла
        int sumD1 = 0, sumD2 = 0;

        for (int x = 0, sumX = 0, sumY = 0; x < MAP_SIZE; x++, sumX = 0, sumY = 0) {
            for (int y = 0; y < MAP_SIZE; y++) {
                sumX += map[x][y];
                sumY += map[y][x];
            }

            if (sumX == winCase || sumY == winCase) {
                return true;
            }

            sumD1 += map[x][x];
            sumD2 += map[x][MAP_SIZE - 1 - x];
        }

        return sumD1 == winCase || sumD2 == winCase;
    }

    /**
     * Поиск позиции, ход в которую позволит либо предотвратить выигрыш игрока, либо выиграть компьютеру.
     *
     * Если позиция не найдена, вернёт {-1, -1}.
     *
     * @param cost Стоимость фишки (крестика/нолика)
     * @return Координаты позиции
     */
    public static int[] findWinningPosition(byte cost) {

        int[] noWinPosition = {-1, -1};

        // если передан символ, отличный от крестика или нолика, возвращаем false
        if (cost != X_POS_COST && cost != O_POS_COST) {
            return noWinPosition;
        }

        // длина околовыигрышной комбинации
        int winCase = (DOTS_TO_WIN - 1) * cost;

        // переменные для проверки выигрышных комбинаций в вертикалях, горизонталях и диагоналях
        int sumX = 0, sumY = 0, sumD1 = 0, sumD2 = 0;

        // переменные для хранения координат пустой позиции
        int[] emptyPosX = noWinPosition.clone();
        int[] emptyPosY = noWinPosition.clone();
        int[] emptyPosD1 = noWinPosition.clone();
        int[] emptyPosD2 = noWinPosition.clone();

        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                sumX += map[x][y];
                sumY += map[y][x];

                if (map[x][y] == 0) {
                    emptyPosX[0] = x;
                    emptyPosX[1] = y;
                }

                if (map[y][x] == 0) {
                    emptyPosY[0] = y;
                    emptyPosY[1] = x;
                }
            }

            if (sumX == winCase) {
                return emptyPosX;
            }

            if (sumY == winCase) {
                return emptyPosY;
            }

            sumD1 += map[x][x];
            sumD2 += map[x][MAP_SIZE - 1 - x];

            if (map[x][x] == 0) {
                emptyPosD1[0] = x;
                emptyPosD1[1] = x;
            }

            if (map[x][MAP_SIZE - 1 - x] == 0) {
                emptyPosD2[0] = x;
                emptyPosD2[1] = MAP_SIZE - 1 - x;
            }

            sumX = 0;
            sumY = 0;
        }

        if (sumD1 == winCase) {
            return emptyPosD1;
        }

        if (sumD2 == winCase) {
            return emptyPosD2;
        }

        return noWinPosition;
    }

    /**
     * Проверяет наличие на игровом поле пустых позиций.
     *
     * @return Поле заполнено (да/нет)
     */
    public boolean isMapFull() {

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
