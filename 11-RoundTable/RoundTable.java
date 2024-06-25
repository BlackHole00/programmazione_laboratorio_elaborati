// Esercizio 11: RoundTable

// Francesco Viciguerra - 10/04/2024
//     (viciguerrafrancesco@gmail.com)
//     (166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/10-04-24.pdf

// In questo esercizio e'stato richiesto di modificare la classe RoundTable in
// modo che la modalita'di "servizio" dei cavalieri sia leggermente modificata.
// Viene riportato il link alla classe originale:
//     https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/pages/java/josephus/more_efficient_implementation/RoundTable.java
// 
// Tale modifica ha reso necessario riuscire a supportare una coppia di 
// cavarieri che servono ad ogni momento dato, al posto di uno solo. Questo
// requisito richiede modifiche sostanziali al funzionamento del metodo 
// passJug(), ma il resto del codice rimane quasi intoccato.
//
// Si nota che l'implementazione del metodo passJug() e'stato scritto in una
// maniera in stile molto imperativa, al fine di evitare di avere lunghe catene 
// di if-else non facilmente leggibili e con codice duplicato.

// Classe RoundTable
// Protocollo:
//     RoundTable tbl = new RoundTable(n);
//     tbl.numberOfKnights();
//     tbl.servingKnights();
//     tbl.serveNeighbour();
//     tbl.passJug();

public class RoundTable {
	// Il numero di cavalieri ancora a tavola
	private final int num;
	// La coppia di cavalieri che stanno attualmente servendo
	private final IntSList serving;
	// Lista dei cavalieri immediatamente successivi
	private final IntSList head;
	// List dei cavalieri rimanenti (rovesciata)
	private final IntSList tail;
  
	public RoundTable( int n ) {
		num = n;
		// Sappiamo gia'che i primi a servire saranno sempre i cavalieri 1 e 2
		serving = new IntSList(1, new IntSList(2, IntSList.NULL_INTLIST));
		head = range(3, n);
		tail = IntSList.NULL_INTLIST;
	}

	private RoundTable( int n, IntSList s, IntSList h, IntSList t ) {
		num = n;
		serving = s;
		head = h;
		tail = t;
	}

	public IntSList servingKnights() {
		return serving;
	}

	public int numberOfKnights() {
		return num;
	}

	public RoundTable serveNeighbour() {
		if ( num < 3 ) {
			return this;
		}
		
		if ( head.isNull() ) {
			IntSList rev = tail.reverse();
			return new RoundTable( num-1, serving, rev.cdr(), IntSList.NULL_INTLIST );
		} else {
			return new RoundTable( num-1, serving, head.cdr(), tail );
		}
	}

	public RoundTable passJug() {
		if ( num < 3 ) {
			return this;
		}

		// Rimetti i cavalieri che hanno servito nella tail
		int servingFirstKnight = serving.car();
		int servingSecondKnight = serving.cdr().car();
		IntSList newTail = tail.cons(servingFirstKnight).cons(servingSecondKnight);

		// L'head necessita di almeno due elementi presenti da cui ottenere una
		// nuova coppia di serving knights
		int headLength = head.length();
		IntSList newHead = head;
		if (headLength < 2) {
			newHead = newTail.reverse();
			newTail = IntSList.NULL_INTLIST;

			// Spostiamo anche l'elemento rimanente nella head se necessario
			if (headLength == 1) {
				newHead = newHead.cons(head.car());
			}
		}
		
		// Genera una nuova coppia di serving knights
		int newServingFirstKnight = newHead.cdr().car();
		int newServingSecondKnight = newHead.car();
		IntSList newServing = new IntSList(
			newServingSecondKnight, 
			new IntSList(newServingFirstKnight, IntSList.NULL_INTLIST)
		);
		newHead = newHead.cdr().cdr();

		return new RoundTable(num, newServing, newHead, newTail);
	}

	// Crea una lista contenente un range di valori
	private static IntSList range( int inf, int sup ) {
		if ( inf > sup ) {
			return IntSList.NULL_INTLIST;
		} else {
			return range( inf+1, sup ).cons( inf );
		}
	}

	@Override
	public String toString() {
		return "" + num + "---" + head.toString() + "---" + tail.toString() + "---" + serving.toString();
	}
}
