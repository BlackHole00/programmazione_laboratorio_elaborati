public class Simmetrica {
	public static boolean isSymmetric(int matrix[][]) {
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 0; j < i; j++) {
				if (matrix[i][j] != matrix[j][i]) {
					return false;
				}
			}
		}

		return true;
	}

	public static void test() {
		int[][] mat1 = new int[][]{
			{1, 2, 3, 4},
			{2, 1, 2, 3},
			{3, 2, 1, 2},
			{4, 3, 2, 1}
		};
		int[][] mat2 = new int[][]{
			{1, 2, 3, 4},
			{2, 1, 2, 3},
			{3, 2, 1, 2},
			{1, 2, 3, 4}
		};

		System.out.println(isSymmetric(mat1));
		System.out.println(isSymmetric(mat2));
	}
}
