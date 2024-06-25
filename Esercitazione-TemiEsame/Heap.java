public class Heap {
	public static boolean heapCheck(double[] vec) {
		for (int i = 1; i < vec.length / 2; i++) {
			if (vec[i] > vec[2 * i]) {
				return false;
			}
			if ((2 * i + 1 < vec.length) && vec[i] > vec[2 * i + 1]) {
				return false;
			}
		}

		return true;
	}
}
