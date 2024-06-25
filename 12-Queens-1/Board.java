// Esercizio 12: Queens - part 1

// Francesco Viciguerra - 17/04/2024
// 		(viciguerrafrancesco@gmail.com)
// 		(166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/17-04-24.pdf

// La classe Board, basata in quella implementata in classe permette di simulare
// una scacchiera dove gli unici pezzi sono regine.
//
// Sebbene il protocollo rimanga identico a quello specificato in lezione, 
// questa versione della classe non usa predicati, ma salva direttamente tutte
// le posizioni attaccate, che possono poi essere controllate direttamente.
// Questo permette di avere una migliore gestione della memoria
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
	private static final String COLUMNS_SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private final int size;
	private final int queenNumber;
	private final SList<Integer> attackedRows;
	private final SList<Integer> attackedColumns;
	private final SList<Integer> attackedAscendingDiagonals;
	private final SList<Integer> attackedDescendingDiagonals;
	private final String arrangement;

	public Board(int size) {
		this.size = size;
		queenNumber = 0;

		arrangement = " ";

		attackedRows = new SList<Integer>();
		attackedColumns = new SList<Integer>();
		attackedAscendingDiagonals = new SList<Integer>();
		attackedDescendingDiagonals = new SList<Integer>();
	}

	private Board(
		int size,
		int queenNumber,
		SList<Integer> attackedRows,
		SList<Integer> attackedColumns,
		SList<Integer> attackedAscendingDiagonals,
		SList<Integer> attackedDescendingDiagonals,
		String arrangement
	) {
		this.size = size;
		this.queenNumber = queenNumber;
		
		this.attackedRows = attackedRows;
		this.attackedColumns = attackedColumns;
		this.attackedAscendingDiagonals = attackedAscendingDiagonals;
		this.attackedDescendingDiagonals = attackedDescendingDiagonals;

		this.arrangement = arrangement;
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
		return attackedRows.contains(i) ||
			attackedColumns.contains(j) ||
			attackedAscendingDiagonals.contains(i + j) ||
			attackedDescendingDiagonals.contains(i - j);
	}

	public Board addQueen(int i, int j) {
		return new Board(
			size,
			queenNumber + 1,
			attackedRows.cons(i),
			attackedColumns.cons(j),
			attackedAscendingDiagonals.cons(i + j),
			attackedDescendingDiagonals.cons(i - j),
			arrangement + COLUMNS_SYMBOLS.charAt(j - 1) + ROW_SYMBOLS.charAt(i - 1) + " "
		);
	}
}
