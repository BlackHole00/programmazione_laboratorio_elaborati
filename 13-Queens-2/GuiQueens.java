// Esercizio 13: Queens - part 2

// Francesco Viciguerra - 24/04/2024
//         (viciguerrafrancesco@gmail.com)
//         (166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/24-04-24.pdf
// Teachpack: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/code/queens/queens.jar

import queens.ChessboardView;

// La classe GuiQueens fornisce una visualizzazione del gioco delle 8 regine,
// utilizzando il teachpack fornito dal professore
public class GuiQueens {
    private static final int BOARD_SIZE = 8;

    public static void showAllSolutions() {
        SList<Board> solutions = BoardUtils.solutions(BOARD_SIZE);

        ChessboardView view = new ChessboardView(BOARD_SIZE);
        while (!solutions.isNull()) {
            Board currentSolution = solutions.car();
            view.setQueens(currentSolution.arrangement());

            solutions = solutions.cdr().append(new SList<Board>().cons(currentSolution));
        }
    }
}
