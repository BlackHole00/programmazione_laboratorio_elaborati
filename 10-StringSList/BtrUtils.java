// Esercizio 10: StringSList

// Francesco Viciguerra - 03/04/2024
//   (viciguerrafrancesco@gmail.com)
//   (166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/03-04-24.pdf

// Questo esercizio consiste nel prendere confidenza con java e quindi tradurre
// codice scheme gia'scritto. Non e'necessaria una spiegazione approfondita sul
// funzionamento del codice.
// Risultati alle domande dell'esercizio (viene riportata la console del REPL):
//     > String btr = "+-"; int range = 5;
//     > StringSList btrs = btrRange( btr, n );
//     > btrs.append( btrs.reverse().cdr() ) 
//           "(+-, +., ++, +--, +-., +--, ++, +., +-)"   (String)
//     > btrs.append(btrRange(btrSucc(btrs.listRef(btrs.length()-1)),btrs.length()))
//           "(+-, +., ++, +--, +-., +-+, +.-, +.., +.+, ++-)"   (String)
// Come si puo'notare il codice tradotto funziona correttamente.

public class BtrUtils {
	// Trova il numero successivo a quello fornito come parametro in notazione 
	// BTR. Questa funzione e'stata tradotta da scheme.
	public static String btrSucc(String btr) {
		int n = btr.length();
		char lsb = btr.charAt(n - 1);

		if (n == 1) {
			if (lsb == '+') {
				return "+-";
			} else {
				return "+";
			}
		} else {
			String pre = btr.substring(0, n - 1);

			if (lsb == '+') {
				return btrSucc(pre) + "-";
			} else {
				return pre + (lsb == '-' ? "." : "+");
			}
		}
	}

	// Ritorna la lista dei prossimi n - 1 numeri in notazione btr (il numero 
	// di partenza viene comunque incluso).
	public static StringSList btrRange(String btr, int n) {
		StringSList result = new StringSList().cons(btr);

		if (n < 1) {
			return result;
		}

		String nextBtr = btr;
		for (int i = 1; i < n; i++) {
			nextBtr = btrSucc(nextBtr);
			result = result.append(new StringSList(nextBtr, StringSList.NULL_STRING_SLIST));
		}
		
		return result;
	}
}
