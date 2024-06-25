public class LLCS3 {
	private static final int INVALID_VALUE = -1;

	public static int max3(int a, int b, int c) {
		if (a > b) {
			if (a > c) {
				return a;
			}
		} else if (b > c) {
			return b;
		}

		return c;
	}

	public static int llcs3(String t, String u, String v) {
		int[][][] mem = new int[t.length() + 1][u.length() + 1][v.length() + 1];

		for (int i = 1; i <= t.length(); i++) {
			for (int j = 1; j <= u.length(); j++) {
				for (int k = 1; k <= v.length(); k++) {
					mem[i][j][k] = INVALID_VALUE;
				}
			}
		}

		return llcs3Helper(t, u, v, mem);
	}

	private static int llcs3Helper(String t, String u, String v, int mem[][][]) {
		if (mem[t.length()][u.length()][v.length()] == INVALID_VALUE) {
			if ((t.charAt(0) == u.charAt(0)) && (u.charAt(0) == v.charAt(0))) {
				mem[t.length()][u.length()][v.length()] = 1 + llcs3Helper(
					t.substring(1),
					u.substring(1),
					v.substring(1),
					mem
				);
			} else {
				mem[t.length()][u.length()][v.length()] = max3(
					llcs3Helper(t.substring(1), u, v, mem),
					llcs3Helper(t, u.substring(1), v, mem),
					llcs3Helper(t, u, v.substring(1), mem)
				);
			}
		}

		return mem[t.length()][u.length()][v.length()];
	}
}
