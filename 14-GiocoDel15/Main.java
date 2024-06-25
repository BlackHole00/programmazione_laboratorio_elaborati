import puzzleboard.PuzzleBoard;

// La classe main fornisce un entry point per eseguire il gioco
public class Main {
    public static void main() {
        PuzzleBoard board = new PuzzleBoard(4);
        FifteenGame game = new FifteenGame(4);

        while (true) {
            game.applyToPuzzleBoard(board);
            board.display();

            game.moveNumber(board.get());
        }
    }
}
