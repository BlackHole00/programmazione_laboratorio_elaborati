public class LPS {
    private static String longer(String a, String b) {
        if (a.length() > b.length()) {
            return a;
        }
        return b;
    }

    public static String lpsDP(String s) {
        int n = s.length();
        String[][] mem = new String[n + 1][n + 1];

        for (int k = 0; k <= n; k += 1) {
            for (int i = 0; i <= n - k; i += 1) {
                if (k < 2) {
                    mem[i][k] = s.substring(i, i + k);
                } else if (s.charAt(i) == s.charAt(i + k - 1)) {
                    mem[i][k] = s.charAt(i) + mem[i + 1][k - 2] + s.charAt(i);
                } else {
                    mem[i][k] = longer(
                        mem[i][k - 1],
                        mem[i + 1][k - 1]
                    );
                }
            }
        }

        return mem[0][n];
    }
}
