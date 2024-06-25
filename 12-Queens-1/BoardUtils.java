// Esercizio 12: Queens - part 1

// Francesco Viciguerra - 17/04/2024
// 		(viciguerrafrancesco@gmail.com)
// 		(166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/17-04-24.pdf

// Classe BoardUtils, implementata per testare facilmente la classe board
// Questa classe e'la classe Queens fornita dal professore e rinominata.

public class BoardUtils {
	// I. Numero di soluzioni:
	//
	// Il numero di modi diversi in cui si possono disporre n regine
	// 		numberOfSolutions( n )
	//
	// in una scacchiera n x n e' dato dal numero di modi diversi in
	// cui si puo' completare la disposizione delle regine a partire
	// da una scacchiera n x n inizialmente vuota
	// 		numberOfCompletions( new Board(n) )
	//
	public static int numberOfSolutions( int n ) {
		return numberOfCompletions( new Board(n) );
	}
  
	// Il numero di modi in cui si puo' completare la disposizione
	// a partire da una scacchiera b parzialmente configurata
	//
	// 		numberOfCompletions( b )   : int
	//
	// dove k regine (0 <= k < n) sono collocate nelle prime k righe
	// di b, si puo' determinare a partire dalle configurazioni
	// che si ottengono aggiungendo una regina nella riga k+1 in tutti
	// i modi possibili (nelle posizioni che non sono gia' minacciate)
	//
	//   	for ( int j=1; j<=n; j=j+1 ) {
	//     		if ( !b.underAttack(i,j) ) { ... b.addQueen(i,j) ... }
	//   	}
	//
	// calcolando ricorsivamente per ciascuna di queste il numero
	// di modi in cui si puo' completare la disposizione
	//
	//   	numberOfCompletions( b.addQueen(i,j) )
	//
	// e sommando i valori che ne risultano
	//
	//   	count = count + numberOfCompletions( ... )
	//
	// Se invece la scacchiera rappresenta una soluzione (q == n)
	// c'e' un solo modo (banale) di completare la disposizione:
	// lasciare le cose come stanno!
	//
	private static int numberOfCompletions( Board b ) {
		int n = b.size();
		int q = b.queensOn();
	
		if ( q == n ) {
			return 1;
		}

		int i = q + 1;
		int count = 0;
	
		for ( int j=1; j<=n; j=j+1 ) {
			if ( !b.underAttack(i,j) ) {
				count = count + numberOfCompletions( b.addQueen(i,j) );
			}
		}
		return count;
	}
  
	public static void main( String args[] ) {
		int n = Integer.parseInt( args[0] );
		System.out.println( numberOfSolutions(n) );
	}
}
