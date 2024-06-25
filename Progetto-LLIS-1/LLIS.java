// Progetto: LLIS - parte 1

// Francesco Viciguerra - 02/05/2024
// 		(viciguerrafrancesco@gmail.com)
// 		(166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/02-05-24.pdf

// La classe LLIS calcola la longest increasing subsequence utilizzando la
// tecnica di programmazione dinamica top-down, come spiegato a lezione e nel
// pdf fornito.
//
// Si nota che il punto 1 non e'stato riportato. Segue quindi la soluzione del
// punto 2.
//
// Notiamo che, a differenza della soluzione iniziale fornita nella consegna,
// i parametri della funzione llisRec sono indici del vettore s. Tuttavia il
// valore t, nella prima chiamata ricorsiva deve fare riferimento ad un valore,
// percio'viene passato il parametro UNKNOWN, che verra'prontamente interpretato
// dalla funzione llisRec().

public class LLIS {
	private static int UNKNOWN = -1;

	public static int llis(int[] s) {
		int[][] mem = new int[s.length + 1][s.length + 1];
		for (int x = 0; x < mem.length; x++) {
			for (int y = 0; y < (mem.length - 1); y++) {
				mem[y][x] = UNKNOWN;
			}
		}

		for (int x = 0; x < mem.length; x++) {
			mem[s.length][x] = 0;
		}

		return llisRec(s, 0, UNKNOWN, mem);
	}

	private static int llisRec(int[] s, int i, int t, int[][] mem) {
		if (mem[i][t + 1] != UNKNOWN) {
			return mem[i][t + 1];
		}

		int result = UNKNOWN;
		int tValue = UNKNOWN;
		
		if (t == UNKNOWN) {
			tValue = 0;
		} else {
			tValue = s[t];
		}
		
		if (s[i] <= tValue) {
			result = llisRec(s, i + 1, t, mem);
		} else {
			result = Math.max(
				1 + llisRec(s, i + 1, i, mem),
				llisRec(s, i + 1, t, mem)
			);
		}

		mem[i][t + 1] = result;
		return result;
	}
}
