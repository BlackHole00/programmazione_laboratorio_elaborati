// Esercizio 11: RoundTable

// Francesco Viciguerra - 10/04/2024
//     (viciguerrafrancesco@gmail.com)
//     (166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/10-04-24.pdf

// Questa classe helper permette di testare facilmente la classe RoundTable
public class RoundTableHelper {
	// Simula il passaggio della caraffa fino ad arrivare ad una situazione di
	// "stallo". Viene ritornata la lista di cavalieri che devono ancora 
	// servirsi, ovvero gli ultimi due rimanenti.
	public static IntSList josephus(int n) {
		RoundTable table = new RoundTable(n);

		while (table.numberOfKnights() > 2) {
			table = table.serveNeighbour();
			table = table.passJug();
		}

		return table.servingKnights();
	}
}
