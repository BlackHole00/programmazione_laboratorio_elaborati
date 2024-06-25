public class Board {
	private SList<Integer> attackedRows;
	// ...

	public Board(int n) {}
	public void addQueen(int i, int j) {}
	public void removeQueen(int i, int j) {}
	public int size() {return 0;}
	public int queensOn() {return 0;}
	public String arrangement() {return "";}

	public boolean isFreeRow(int i) {
		SList<Integer> rows = attackedRows;
		while (!rows.isNull()) {
			int value = rows.car();
			if (value == i) {
				return false;
			}

			rows = rows.cons();
		}

		return true;
	}

	public void addQueen(String s) {
		char charRow = s.charAt(0);
		char charLine = s.charAt(1);

		int column = (int)(charColumn) - (int)('a');
		int row = (int)(charRow) - (int)('1');

		if (column < 0 || column > 7 || row < 0 || row > 7) {
			return;
		}

		addQueen(column, row);
	}
}
