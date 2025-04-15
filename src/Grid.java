import java.util.Arrays;

/**
 * Represents the game's grid
 */
public class Grid {
    private final CellType[] cells;
    private final EdgeType[] horizontalEdges;
    private final EdgeType[] verticalEdges;
    private final int sideLength;

    /**
     * Creates square grid of default side length 6
     */
    public Grid() {
        this(6);
    }

    /**
     * Creates square grid of given side length
     * @param sideLength (int) the grid's side length
     * @throws IllegalArgumentException for odd or negative side length
     */
    public Grid(int sideLength) {
        if (sideLength < 0 || sideLength % 2 != 0) {
            throw new IllegalArgumentException("Size should be positive and even");
        }
        this.sideLength = sideLength;
        cells = new CellType[sideLength * sideLength];
        Arrays.fill(cells, CellType.EMPTY);
        horizontalEdges = new EdgeType[(sideLength - 1)*(sideLength - 1)];
        Arrays.fill(horizontalEdges, EdgeType.NORMAL);
        verticalEdges = new EdgeType[(sideLength - 1)*(sideLength - 1)];
        Arrays.fill(verticalEdges, EdgeType.NORMAL);
    }

    /**
     * Adds a cell to the grid
     * @param type (CellType) cell's type (EMPTY, SUN, MOON)
     * @param row (int) row index (0 included to side length excluded)
     * @param column (int) column index (0 included to side length excluded)
     * @return the current instance of the grid
     * @throws IndexOutOfBoundsException if row or column index is out of bounds
     */
    public Grid addCell(CellType type, int row, int column) {
        if (row < 0 || row >= sideLength || column < 0 || column >= sideLength)
            throw new IndexOutOfBoundsException();
        cells[row * sideLength + column] = type;
        return this;
    }

    /**
     * Adds an edge to the grid
     * @param type (EdgeType) edge's type (NORMAL, EQUALS, CROSS)
     * @param row (int) index of the row to its left or top
     *            (0 included to side length - 1 excluded)
     * @param column (int) index of the column to its left or top
     *               (0 included to side length - 1excluded)
     * @return the current instance of the grid
     * @throws IllegalArgumentException if row and column indices correspond to an outer edge
     * @throws IndexOutOfBoundsException if row or column index is out of bounds
     */
    public Grid addEdge(EdgeType type, EdgeOrientation orientation, int row, int column) {
        if (row == sideLength - 1 || column == sideLength - 1)
            throw new IllegalArgumentException("Outer edge type cannot be changed");
        if (row < 0 || row >= sideLength || column < 0 || column >= sideLength)
            throw new IndexOutOfBoundsException();
        if (orientation.equals(EdgeOrientation.HORIZONTAL)) {
            horizontalEdges[row * (sideLength - 1) + column] = type;
        } else {
            verticalEdges[row * (sideLength - 1) + column] = type;
        }
        return this;
    }

    /**
     * Determines if row is valid
     * @param row (int) row index
     * @return true if row is valid
     */
    public boolean isValidRow(int row) {
        if (row < 0 || row >= sideLength)
            throw new IndexOutOfBoundsException();
        CellType[] rowCells = Arrays.copyOfRange(cells,
                row * sideLength,
                (row+1) * sideLength);
        EdgeType[] rowEdges = Arrays.copyOfRange(verticalEdges,
                row * (sideLength-1),
                (row+1) * (sideLength-1));
        return hasValidCount(rowCells) && hasValidPositions(rowCells, rowEdges);
    }

    /**
     * Determines if column is valid
     * @param column (int) column index
     * @return true if column is valid
     */
    public boolean isValidColumn(int column) {
        CellType[] columnCells = new CellType[sideLength];
        EdgeType[] columnEdges = new EdgeType[sideLength - 1];
        for (int i = 0; i < sideLength; ++i) {
            columnCells[i] = cells[i * sideLength + column];
            columnEdges[i] = horizontalEdges[i * (sideLength-1) + column];
        }
        return hasValidCount(columnCells) && hasValidPositions(columnCells, columnEdges);
    }

    /**
     * Determines if the grid is valid, indicating a win
     * @return true if the grid is valid, indicating a win
     */
    public boolean isValidGrid() {
        for (int row = 0; row < sideLength; ++row) {
            if (!isValidRow(row)) return false;
        }
        for (int column = 0; column < sideLength; ++column) {
            if (!isValidColumn(column)) return false;
        }
        return true;
    }

    private boolean hasValidCount(CellType[] cells) {
        int sunCount = 0, moonCount = 0;
        for (CellType type : cells) {
            if (type.equals(CellType.SUN)) ++sunCount;
            else if (type.equals(CellType.MOON)) ++moonCount;
        }
        return sunCount == moonCount;
    }

    private boolean hasValidPositions(CellType[] cells, EdgeType[] edges) {
        int succeeding = 1;
        for (int i = 1; i < cells.length; ++i) {
            if (cells[i].equals(cells[i-1])) succeeding += 1;
            else succeeding = 1;
            if (succeeding >= 3) return false;

            if (edges[i-1].equals(EdgeType.EQUALS) & cells[i] != cells[i-1]
            || edges[i-1].equals(EdgeType.CROSS) && cells[i] == cells[i-1]) return false;
        }
        return true;
    }
}
