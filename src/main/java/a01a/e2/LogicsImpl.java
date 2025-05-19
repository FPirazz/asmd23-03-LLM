package a01a.e2;

public class LogicsImpl implements Logics {
	private final Pair<Integer, Integer> pawn;
	private Pair<Integer, Integer> knight;
	private final int size;
	private final PositionGenerator positionGenerator;

	public LogicsImpl(int size) {
		this(size, new RandomPositionGenerator());
	}

	public LogicsImpl(int size, PositionGenerator positionGenerator) {
		this.size = size;
		this.positionGenerator = positionGenerator;
		this.pawn = this.randomEmptyPosition();
		this.knight = this.randomEmptyPosition();
	}

	private Pair<Integer, Integer> randomEmptyPosition() {
		Pair<Integer, Integer> pos = this.positionGenerator.generate(this.size);
		return this.pawn != null && this.pawn.equals(pos) ? randomEmptyPosition() : pos;
	}

	@Override
	public boolean hit(int row, int col) {
		if (row < 0 || col < 0 || row >= this.size || col >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		int x = row - this.knight.getX();
		int y = col - this.knight.getY();
		if (x != 0 && y != 0 && Math.abs(x) + Math.abs(y) == 3) {
			this.knight = new Pair<>(row, col);
			return this.pawn.equals(this.knight);
		}
		return false;
	}

	@Override
	public boolean hasKnight(int row, int col) {
		return this.knight.equals(new Pair<>(row, col));
	}

	@Override
	public boolean hasPawn(int row, int col) {
		return this.pawn.equals(new Pair<>(row, col));
	}
}