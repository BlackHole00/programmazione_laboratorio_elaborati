public class MostFrequentChar {
	public static char mostFrequentChar(char[] v) {
		int[] charFreqs = new int[128];

		for (int i = 0; i < v.length; i++) {
			charFreqs[v[i]] += 1;
		}

		char bestChar = ' ';
		int bestCharFreq = 0;
		for (int i = 0; i < charFreqs.length; i++) {
			if (charFreqs[i] > bestCharFreq) {
				bestChar = (char)(i);
				bestCharFreq = charFreqs[i];
			}
		}

		return bestChar;
	}
}
