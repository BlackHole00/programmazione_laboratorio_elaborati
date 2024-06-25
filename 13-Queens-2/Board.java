// Esercizio 13: Queens - part 2

// Francesco Viciguerra - 24/04/2024
// 		(viciguerrafrancesco@gmail.com)
// 		(166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/24-04-24.pdf
// Teachpack: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/code/queens/queens.jar

// La classe Board, basata in quella implementata nell'esercizio 12 permette di
// simulare una scacchiera dove gli unici pezzi sono regine.
//
// Sebbene il protocollo rimanga identico a quello specificato nell'esercizio 
// precedente, questa versione della classe non usa quattro liste, ma salva 
// direttamente le posizioni delle regine nella scacchiera, per poi analizzare
// quali celle sono attaccate
//
// Segue il protocollo della classe:
// 		new Board( int n )           :  costruttore (scacchiera vuota)
// 		size()                       :  int
// 		queensOn()                   :  int
// 		underAttack( int i, int j )  :  boolean
// 		addQueen( int i, int j )     :  Board
// 		arrangement()                :  String
//
// Che corrisponde alle seguenti operazioni
// 		new Board(n)           costruttore della scacchiera n x n vuota;
// 		b.size()               dimensione n della scacchiera b;
// 		b.queensOn()           numero di regine collocate nella scacchiera b;
// 		b.underAttack(i,j)     la posizione <i,j> e' minacciata?
// 		b.addQueen(i,j)        scacchiera che si ottiene dalla configurazione b
// 		                       aggiungendo una nuova regina in posizione <i,j>
// 		                       (si assume che la posizione non sia minacciata);
// 		b.arrangement()        descrizione "esterna" della configurazione
//                             (convenzioni scacchistiche).
public class Board {
	private static final String ROW_SYMBOLS = "123456789ABCDEF";
	private static final String COLUMNS_SYMBOLS = "abcdefghijklmnopqrstuvwxyz";

	private final int size;
	private final int queenNumber;
	private final SList<SList<Integer>> placedQueens;
	private final String arrangement;

	public Board(int size) {
		this.size = size;
		queenNumber = 0;

		arrangement = " ";

		placedQueens = new SList<>();
	}

	private Board(
		int size,
		int queenNumber,
		SList<SList<Integer>> placedQueens,
		String arrangement
	) {
		this.size = size;
		this.queenNumber = queenNumber;
		
		this.arrangement = arrangement;

		this.placedQueens = placedQueens;
	}

	public int size() {
		return size;
	}

	public int queensOn() {
		return queenNumber;
	}

	public String arrangement() {
		return arrangement;
	}

	public boolean underAttack(int i, int j) {
		return placedQueens.contains((queen) -> {
			int queenI = queen.car();
			int queenJ = queen.cdr().car();

			return queenI == i ||
				queenJ == j ||
				i + j == queenI + queenJ ||
				i - j == queenI - queenJ;
		});
	}

	public Board addQueen(int i, int j) {
		return new Board(
			size,
			queenNumber + 1,
			placedQueens.cons(new SList<>(i, new SList<Integer>().cons(j))),
			arrangement + COLUMNS_SYMBOLS.charAt(j - 1) + ROW_SYMBOLS.charAt(i - 1) + " "
		);
	}
}
