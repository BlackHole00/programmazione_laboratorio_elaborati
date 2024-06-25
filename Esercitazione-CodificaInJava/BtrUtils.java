public class BtrUtils {
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
}
