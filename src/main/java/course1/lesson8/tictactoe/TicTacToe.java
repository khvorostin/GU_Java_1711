package course1.lesson8.tictactoe;

public class TicTacToe {

    public static void main(String[] args) {
        GameModel game = new GameModel();
        new GameView(game);
    }
}
