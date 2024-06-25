public class Manhattan {
	public static int commonStretches(String u, String v) {
		int counter = 0;
		int[] uPos = new int[2];
		int[] vPos = new int[2];

		for (int i = 0; i < u.length(); i++) {
			char uMove = u.charAt(i);
			char vMove = v.charAt(i);

			uPos[uMove == '1' ? 1 : 0]++;
			vPos[vMove == '1' ? 1 : 0]++;

			if (uMove == vMove && uPos[0] == vPos[0] && uPos[1] == vPos[1]) {
				counter++;
			}
		}

		return counter;
	}
}
