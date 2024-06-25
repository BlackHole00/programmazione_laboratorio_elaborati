// Esercizio 14: Gioco del 15

// Francesco Viciguerra - 15/05/2024
// 		(viciguerrafrancesco@gmail.com)
// 		(166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/15-05-24.pdf
// Teachpack: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/code/fifteen_puzzle/puzzleboard.jar

import java.util.Random;

import puzzleboard.PuzzleBoard;

// Classe FifteenGame
// 
// Questa classe permette di simulare il gioco del 15. Segue il protocollo base
// specificato nel pdf, ma con qualche aggiunta:
//     new FifteenGame(int boardSize)
//     boolean canTesselMove(int x, int y)
//     void moveTessel(int x, int y)
//     boolean isOrdered()
//     void moveNumber(int n)
//     void applyToPuzzleBoard(PuzzleBoard board)
// Si noti la funzione applyToPuzzleBoard() aggiunta per comodita' e per 
// interfacciarsi col teachpack fornito
//
// Si nota che per l'implementazione e'stato utilizzato il costrutto enum, che
// non e'stato spiegato a lezione. Il suo utilizzo permette tuttavia di poter
// implementare il gioco in un metodo ordinato e leggibile (l'alternativa 
// sarebbe stata l'utilizzare interi e costanti). Si nota anche che e'stato
// preferito l'utilizzo della classe Random (proveniente da java.util) al posto
// di Math.random(), in quanto piu'comoda.

public class FifteenGame {
    private static final Random rand = new Random();
    private enum TesselMoveDirection {
        NORTH,
        SOUTH,
        EAST,
        WEST,
        INVALID,
    }

    public static final int EMPTY_TESSEL = 0;
    public static final int INVALID_TESSEL = -1;

    private final int boardSize;
    private final int[][] board;

    public FifteenGame(int boardSize) {
        this.boardSize = boardSize;
        board = new int[boardSize][boardSize];

        populateBoard();
    }

    // Comunica se un tessel identificato dalla sua posizione nella griglia puo'
    // essere spostato in una qualsiasi direzione
    public boolean canTesselMove(int x, int y) {
        return getTesselMoveDirection(x, y) != TesselMoveDirection.INVALID;
    }

    // Muove un tessel identificato dalla sua posizione nella griglia. Siccome
    // un tessel, se si puo'spostare, puo'essere mosso in una unica direzione,
    // non e'necessario specificare una direzione di movimento.
    // Nel caso che le coordinate siano errate o il tessel modificato non sia
    // spostabile, la funzione non applica nessuna modifica al gioco.
    public void moveTessel(int x, int y) {
        if (x >= boardSize || y >= boardSize) {
            // Invalid position
            return;
        }

        TesselMoveDirection moveDirection = getTesselMoveDirection(x, y);

        int prevTessel = EMPTY_TESSEL;
        switch (moveDirection) {
            case NORTH: {
                // muovi tutti i tessel a nord ed inserisci il tessello vuoto
                // nella posizione di partenza
                for (int i = y; i >= 0; i -= 1) {
                    int temp = board[i][x];
                    board[i][x] = prevTessel;
                    prevTessel = temp;

                    if (temp == EMPTY_TESSEL) {
                        break;
                    }
                }
                break;
            }
            case SOUTH: {
                // muovi tutti i tessel a sud ed inserisci il tessello vuoto
                // nella posizione di partenza
                for (int i = y; i < boardSize; i += 1) {
                    int temp = board[i][x];
                    board[i][x] = prevTessel;
                    prevTessel = temp;

                    if (temp == EMPTY_TESSEL) {
                        break;
                    }
                }
                break;
            }
            case WEST: {
                // muovi tutti i tessel ad ovest ed inserisci il tessello vuoto
                // nella posizione di partenza
                for (int j = x; j >= 0; j -= 1) {
                    int temp = board[y][j];
                    board[y][j] = prevTessel;
                    prevTessel = temp;

                    if (temp == EMPTY_TESSEL) {
                        break;
                    }
                }
                break;
            }
            case EAST: {
                // muovi tutti i tessel ad est ed inserisci il tessello vuoto
                // nella posizione di partenza
                for (int j = x; j < boardSize; j += 1) {
                    int temp = board[y][j];
                    board[y][j] = prevTessel;
                    prevTessel = temp;

                    if (temp == EMPTY_TESSEL) {
                        break;
                    }
                }
                break;
            }
            case INVALID: {
                break;
            }
        }
    }

    // Dice se la board e'ordinata. Si nota che per board ordinata si intende
    // anche che il tessello vuoto sia nell'ultima posizione
    public boolean isOrdered() {
        int prevTessel = INVALID_TESSEL;
        for (int y = 0; y < boardSize; y += 1) {
            for (int x = 0; x < boardSize; x += 1) {
                if (board[y][x] == EMPTY_TESSEL) {
                    continue;
                }

                if (board[y][x] < prevTessel) {
                    return false;
                }

                prevTessel = board[y][x];
            }
        }

        return (board[boardSize - 1][boardSize - 1] == EMPTY_TESSEL);
    }

    // Muove un tessello identificato dal suo numero. Se il numero specificato
    // non e'valido o se il tassello non esiste, la funzione non apportera'
    // modifiche alla board
    public void moveNumber(int n) {
        for (int y = 0; y < boardSize; y += 1) {
            for (int x = 0; x < boardSize; x += 1) {
                if (board[y][x] != n) {
                    continue;
                }

                moveTessel(x, y);
                return;
            }
        }
    }
    
    // Applica lo stato attuale del gioco del 15 alla puzzle board implementata
    // nel teachpack in modo da mostrare una visualizzazione
    public void applyToPuzzleBoard(PuzzleBoard puzzleboard) {
        for (int y = 0; y < boardSize; y += 1) {
            for (int x = 0; x < boardSize; x += 1) {
                puzzleboard.setNumber(y + 1, x + 1, board[y][x]);
            }
        }
    }

    @Override
    public String toString() {
        String result = "Board {";

        for (int y = 0; y < boardSize; y += 1) {
            result += " { ";
            for (int x = 0; x < boardSize; x += 1) {
                result += board[y][x];
                result += " ";
            }
            result += "}";
        }

        return result;
    }

    // Ritorna la direzione nella quale un tessello puo'muoversi. Ritorna 
    // INVALID se le coordinate del tessel specificate non sono valide o se
    // quest'ultimo non puo'essere spostato
    private TesselMoveDirection getTesselMoveDirection(int x, int y) {
        if (x > boardSize || y > boardSize || board[y][x] == EMPTY_TESSEL) {
            return TesselMoveDirection.INVALID;
        }

        // Check for North
        for (int i = y; i >= 0; i -= 1) {
            if (board[i][x] == EMPTY_TESSEL) {
                return TesselMoveDirection.NORTH;
            }
        }

        // Check for South
        for (int i = y; i < boardSize; i += 1) {
            if (board[i][x] == EMPTY_TESSEL) {
                return TesselMoveDirection.SOUTH;
            }
        }

        // Check for West
        for (int j = x; j >= 0; j -= 1) {
            if (board[y][j] == EMPTY_TESSEL) {
                return TesselMoveDirection.WEST;
            }
        }

        // Check for East
        for (int j = x; j < boardSize; j += 1) {
            if (board[y][j] == EMPTY_TESSEL) {
                return TesselMoveDirection.EAST;
            }
        }

        return TesselMoveDirection.INVALID;
    }

    // Ritorna la posizione in coordinate (x, y) di un tessello identificato
    // da un numero. Ritorna null se tale tessello non esiste
    private int[] positionOfTessel(int tessel) {
        for (int y = 0; y < boardSize; y += 1) {
            for (int x = 0; x < boardSize; x += 1) {
                if (board[y][x] == tessel) {
                    return new int[]{x, y};
                }
            }
        }

        return null;
    }

    // Controlla se la configurazione attuale de gioco del 15 e'risolvibile.
    // Si nota che il gioco del 15 e'risolvibile se:
    //    - Se boardSize e'dispari ed il numero di inversioni nella board e'
    //        pari
    //    - Se boardSize e'pari ed il numero di inversioni e':
    //        - dispari e il tessello vuoto e'in una riga pari contando dalla
    //            fine della board
    //        - pari ed il tessello vuoto e'in una riga pari contando dalla fine
    //            della board
    private boolean isSolvable() {
        int inversions = countInversions();

        if (boardSize % 2 == 1) {
            return inversions % 2 == 0;
        } else {
            int emptyTesselRow = positionOfTessel(EMPTY_TESSEL)[1];
            boolean emptyTesselEven = (boardSize - emptyTesselRow) % 2 == 0;

            if (!emptyTesselEven) {
                return inversions % 2 == 0;
            } else {
                return inversions % 2 == 1;
            }
        }
    }

    // Conta le inversioni presenti nella matrice
    private int countInversions() {
        int inversions = 0;
        for (int i = 0; i < boardSize * boardSize - 1; i++) {
            for (int j = i + 1; j < boardSize * boardSize; j++) {
                int ix = i % boardSize;
                int iy = i / boardSize;

                int jx = j % boardSize;
                int jy = j / boardSize;

                if (board[iy][ix] != 0 && board[jy][jx] != 0 && board[iy][ix] > board[jy][jx]) {
                    inversions++;
                }
            }
        }

        return inversions;
    }    

    // Randomicamente mischia una board
    private void shuffleBoard() {
        int shuffles = rand.nextInt(boardSize * 2, boardSize * boardSize);
        for (int i = 0; i < shuffles; i += 1) {
            int x = i % boardSize;
            int y = (i / boardSize) % boardSize;

            int target = rand.nextInt(i + 1, boardSize * boardSize);
            int targetX = target % boardSize;
            int targetY = target / boardSize;

            int temp = board[y][x];
            board[y][x] = board[targetY][targetX];
            board[targetY][targetX] = temp;
        }
    }

    // Mischia la board con una configurazione sicuramente risolvibile
    private void populateBoard() {
        for (int i = 0; i < boardSize * boardSize; i += 1) {
            int x = i % boardSize;
            int y = i / boardSize;

            board[y][x] = i;
        }

        do {
            shuffleBoard();
        } while(!isSolvable());
    }
}
