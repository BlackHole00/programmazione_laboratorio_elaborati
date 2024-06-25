public class LIS {
	private static int UNKNOWN = -1;

	private static int llisMem(double[] s) {
		int n = s.length;
		double[] v = s.clone();
		Arrays.sort(v);

		int[][] mem = new int[n + 1][n + 1];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				mem[i][j] = UNKNOWN;
			}
		}

		llcsRec(s, v, mem);
	}

	private static int llcsRec(double[] u, double[] v, int i, int j, int[][] mem) {
		if (mem[i][j] == UNKNOWN) {
			// Caso base gia'inserito nella matrice
			if (u[i] == v[j]) {
				mem[i][j] = 1 + llcsRec(u, v, i + 1, j + 1);
			} else {
				mem[i][j] = Math.max(
					llcsRec(u, v, i + 1, j),
					llcsRec(u, v, i, j + 1)
				);
			}
		}

		return mem[i][j];
	}
}
