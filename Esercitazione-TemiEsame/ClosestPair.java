public class ClosestPair {
	public static double[] closestPair(double[] vec) {
		double[] bestPair = new double[2];
		double best = Double.MAX_VALUE;

		for (int i = 0; i < vec.length; i++) {
			for (int j = i + 1; j < vec.length; j++) {
				if (best > Math.abs(vec[i] - vec[j])) {
					bestPair[0] = vec[i];
					bestPair[1] = vec[j];

					best = Math.abs(vec[i] - vec[j]);
				}
			}
		}

		return bestPair;
	}
}
