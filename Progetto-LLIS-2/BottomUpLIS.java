// Progetto: LLIS - parte 2

// Francesco Viciguerra - 08/05/2024
// 		(viciguerrafrancesco@gmail.com)
// 		(166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/08-05-24.pdf

// La classe LLIS calcola la longest increasing subsequence utilizzando la
// tecnica di programmazione dinamica bottom-up, come spiegato a lezione e nel
// pdf fornito. 
// Si nota che parte dell'implementazione e'stata fornita dal professore come 
// esercizio "a completamento". Si nota che anche i commenti del testo originale
// sono stati mantenuti, anche se formattati

public class BottomUpLIS {
	// Length of Longest Increasing Subsequence (LLIS):
	// Programmazione dinamica bottom-up
	public static int llisDP( int[] s ) {
		int n = s.length;
		int[][] mem = new int[n + 1][n + 1];

		// Matrice: valori delle ricorsioni di llisRec
		// relativi a diversi valori degli argomenti
		for ( int j=0; j<=n; j=j+1 ) {
			mem[j][n] = 0;
		}
	
		for ( int i=n-1; i>=0; i=i-1 ) {
			for ( int j=0; j<=n; j=j+1 ) {
				if (j >= i && j != n) {
					continue;
				}

				int t = 0;
				if (j < n) {
					t = s[j];
				}

				if (s[i] <= t) {
					mem[j][i] = mem[j][i + 1];
				} else {
					mem[j][i] = Math.max(
						1 + mem[i][i + 1],
						mem[j][i + 1]
					);
				}
			}
		}

		// ----------------------------------------------------
		//  Inserisci di seguito l'elemento della matrice
		//  il cui valore corrisponde a llis(s) :
		return  mem[n][0];
	}
  
  
	// Longest Increasing Subsequence (LIS):
	// Programmazione dinamica bottom-up
	public static int[] lisDP( int[] s ) {
		int n = s.length;
		int[][] mem = new int[ n+1 ][ n+1 ];
	
		// 1. Matrice: valori delle ricorsioni di llisRec
		//	calcolati esattamente come per llisDP
		for ( int j=0; j<=n; j=j+1 ) {
			mem[j][n] = 0;
		}
	
		// ------------------------------------------------
		//  Replica qui il codice del corpo di llisDP
		//  che registra nella matrice i valori
		//  corrispondenti alle ricorsioni di llisRec
		// ------------------------------------------------
		for ( int i=n-1; i>=0; i=i-1 ) {
			for ( int j=0; j<=n; j=j+1 ) {
				if (j >= i && j != n) {
					continue;
				}

				int t;
				if (j < n) {
					t = s[j];
				} else {
					t = 0;
				}

				if (s[i] <= t) {
					mem[j][i] = mem[j][i + 1];
				} else {
					mem[j][i] = Math.max(
						1 + mem[i][i + 1],
						mem[j][i + 1]
					);
				}
			}
		}
	
	
		// 2. Cammino attraverso la matrice per ricostruire
		//	un esempio di Longest Increasing Subsequence
	
		// ----------------------------------------------------
		//  Inserisci di seguito l'elemento della matrice
		//  il cui valore corrisponde a llis(s) :

		int m = mem[n][0];/* elemento appropriato della matrice */;

		int[] r = new int[ m ];  // per rappresentare una possibile LIS
	
		// ----------------------------------------------------
		//  Introduci e inizializza qui gli indici utili
		//  per seguire un cammino attraverso la matrice e
		//  per assegnare gli elementi della sottosequenza r
		// ----------------------------------------------------
		int i = 0;
		int j = n;
		int x = 0;
		while ( mem[j][i] > 0 ) {
			int t = (j == n) ? 0 : s[j];
			// --------------------------------------------------
			//  Inserisci qui strutture di controllo e comandi
			//  per scegliere e seguire un percorso appropriato
			//  attraverso la matrice in modo da ricostruire in
			//  r una possibile LIS relativa alla sequenza s
			// --------------------------------------------------
			if (s[i] <= t || 1 + mem[i][i + 1] <= mem[j][i + 1]) {
				i = i + 1;
			} else {
				j = i;
				i = i + 1;

				r[x] = s[j];
				x += 1;
			}
 		}
	
		return r;	// = LIS relativa alla sequenza s
	}
}  // class BottomUpLIS
