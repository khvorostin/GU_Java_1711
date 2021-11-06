package course1.lesson8.tictactoe;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {

    private static final String title = "Noughts and Crosses";

    /**
     * Символы для обозначения крестика и нолика
     */
    private static final String DOT_X = "X";
    private static final String DOT_O = "O";

    /** Флаг активной игры, чтобы блокировать ходы после зваершения игры */
    private boolean isGameActive = true;

    public GameView(GameModel model) {

        setTitle(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 400);

        Font font = new Font("Arial", Font.BOLD, 48);

        Cell[] cells = {
            new Cell('A', 3),
            new Cell('B', 3),
            new Cell('C', 3),
            new Cell('A', 2),
            new Cell('B', 2),
            new Cell('C', 2),
            new Cell('A', 1),
            new Cell('B', 1),
            new Cell('C', 1),
        };

        setLayout(new GridLayout(3, 3));

        for (Cell cell : cells) {
            cell.setFont(font);
            cell.addActionListener(e -> {

                // игра уже закончена - ничего не делаем
                if (!isGameActive) {
                    return;
                }

                // если пользователь сделал ход (сходил в свободную клетку)...
                if (model.performUserTurn(cell.getColumnSign(), cell.getRowNumber())) {

                    // ...ставим значок в клетку
                    cell.setText(DOT_X);
                    if (model.checkWin(GameModel.X_POS_COST)) {
                        setTitle("Игра окончена, вы победили");
                        isGameActive = false;
                        return;
                    }

                    // проверяем поле на заполненность
                    if (model.isMapFull()) {
                        setTitle("Игра окончена, ничья");
                        isGameActive = false;
                        return;
                    }

                    // компьютер ходит
                    String pos = model.performAiTurn();
                    aiTakeCell(pos, cells);
                    if (model.checkWin(GameModel.O_POS_COST)) {
                        setTitle("Игра окончена, компьютер победил");
                        isGameActive = false;
                        return;
                    }

                    if (model.isMapFull()) {
                        setTitle("Игра окончена, ничья");
                        isGameActive = false;
                        return;
                    }
                }
            });
            add(cell);
        }

        setVisible(true);
    }

    private void aiTakeCell(String pos, Cell[] cells) {
        for (Cell cell : cells) {
            if (pos.equals(cell.getCellPosition())) {
                cell.setText(DOT_O);
                return;
            }
        }
    }

    private class Cell extends JButton {

        private final char columnSign;
        private final int rowNumber;

        public Cell(char columnSign, int rowNumber) {
            this.columnSign = columnSign;
            this.rowNumber = rowNumber;
        }

        public char getColumnSign() {
            return columnSign;
        }

        public int getRowNumber() {
            return rowNumber;
        }

        public String getCellPosition() {
            return "" + columnSign + rowNumber;
        }
    }
}
