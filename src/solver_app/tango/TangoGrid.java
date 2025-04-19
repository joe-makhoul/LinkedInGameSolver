package solver_app.tango;

import java.util.Arrays;

/**
 * Represents the game's grid
 */
public final class TangoGrid {
    private final CellType[] cells;
    private final EdgeType[] horizontalEdges;
    private final EdgeType[] verticalEdges;
    private final int sideLength;

    /**
     * Creates square grid of default side length 6
     */
    public TangoGrid() {
        this(6);
    }

    /**
     * Creates square grid of given side length
     * @param sideLength (int) the grid's side length
     * @throws IllegalArgumentException for odd or negative side length
     */
    public TangoGrid(int sideLength) {
        if (sideLength < 0 || sideLength % 2 != 0) {
            throw new IllegalArgumentException("Size should be positive and even");
        }
        this.sideLength = sideLength;
        cells = new CellType[sideLength * sideLength];
        Arrays.fill(cells, CellType.EMPTY);
        horizontalEdges = new EdgeType[sideLength*(sideLength - 1)];
        Arrays.fill(horizontalEdges, EdgeType.NORMAL);
        verticalEdges = new EdgeType[sideLength*(sideLength - 1)];
        Arrays.fill(verticalEdges, EdgeType.NORMAL);
    }

    /**
     * Returns the length of the grid's side
     * @return the length of the grid's side
     */
    public int sideLength() {
        return sideLength;
    }

    /**
     * Checks if a cell is empty
     * @param row (int) row index (0 included to side length excluded)
     * @param column (int) column index (0 included to side length excluded)
     * @return true if the cell of given coordinates is empty
     * @throws IndexOutOfBoundsException if row or column index is out of bounds
     */
    public boolean isEmpty(int row, int column) {
        if (row < 0 || row >= sideLength || column < 0 || column >= sideLength)
            throw new IndexOutOfBoundsException();
        return cells[row * sideLength + column].equals(CellType.EMPTY);
    }

    public CellType getCell(int row, int column) {
        return cells[row * sideLength + column];
    }

    /**
     * Adds a cell to the grid
     * @param type (solver_app.tango.CellType) cell's type (EMPTY, SUN, MOON)
     * @param row (int) row index (0 included to side length excluded)
     * @param column (int) column index (0 included to side length excluded)
     * @return the current instance of the grid
     * @throws IndexOutOfBoundsException if row or column index is out of bounds
     */
    public TangoGrid addCell(CellType type, int row, int column) {
        if (row < 0 || row >= sideLength || column < 0 || column >= sideLength)
            throw new IndexOutOfBoundsException();
        cells[row * sideLength + column] = type;
        return this;
    }

    /**
     * Adds an edge to the grid
     * @param type (solver_app.tango.EdgeType) edge's type (NORMAL, EQUALS, CROSS)
     * @param orientation (solver_app.tango.EdgeOrientation) edge's orientation (HORIZONTAL or VERTICAL)
     * @param row (int) index of the row to its left or top
     *            (0 included to side length - 1 excluded)
     * @param column (int) index of the column to its left or top
     *               (0 included to side length - 1excluded)
     * @return the current instance of the grid
     * @throws IllegalArgumentException if row and column indices correspond to an outer edge
     * @throws IndexOutOfBoundsException if row or column index is out of bounds
     */
    public TangoGrid addEdge(EdgeType type, EdgeOrientation orientation, int row, int column) {
        if ((orientation.equals(EdgeOrientation.HORIZONTAL) && row == sideLength - 1)
        || (orientation.equals(EdgeOrientation.VERTICAL) && column == sideLength - 1))
            throw new IllegalArgumentException("Outer edge type cannot be changed");
        if (row < 0 || row >= sideLength || column < 0 || column >= sideLength)
            throw new IndexOutOfBoundsException();
        if (orientation.equals(EdgeOrientation.HORIZONTAL)) {
            horizontalEdges[column * (sideLength - 1) + row] = type;
        } else if (orientation.equals(EdgeOrientation.VERTICAL)) {
            verticalEdges[row * (sideLength - 1) + column] = type;
        }
        return this;
    }

    private boolean hasValidCount(CellType[] cells, boolean strict) {
        int sunCount = 0, moonCount = 0;
        for (CellType type : cells) {
            if (type.equals(CellType.SUN)) ++sunCount;
            else if (type.equals(CellType.MOON)) ++moonCount;
            else if (strict) return false;
        }
        int limit = cells.length/2;
        if (sunCount > limit || moonCount > limit) return false;
        return !strict || (sunCount == limit && moonCount == limit);
    }

    private boolean hasValidPositions(CellType[] cells, EdgeType[] edges, boolean strict) {
        int succeeding = 1;
        for (int i = 1; i < cells.length; ++i) {
            if (!strict & (cells[i] == CellType.EMPTY || cells[i-1] == CellType.EMPTY)) continue;
            if (cells[i].equals(cells[i-1])) succeeding += 1;
            else succeeding = 1;
            if (succeeding >= cells.length/2) return false;

            if (edges[i-1].equals(EdgeType.EQUALS) & cells[i] != cells[i-1]
                    || edges[i-1].equals(EdgeType.CROSS) && cells[i] == cells[i-1]) return false;
        }
        return true;
    }

    /**
     * Determines if row is valid
     * @param row (int) row index
     * @param strict (boolean) true if finding empty cells returns false
     * @return true if row is valid
     */
    public boolean isValidRow(int row, boolean strict) {
        if (row < 0 || row >= sideLength)
            throw new IndexOutOfBoundsException();
        CellType[] rowCells = Arrays.copyOfRange(cells,
                row * sideLength,
                (row+1) * sideLength);
        EdgeType[] rowEdges = Arrays.copyOfRange(verticalEdges,
                row * (sideLength-1),
                (row+1) * (sideLength-1));
        return hasValidCount(rowCells, strict)
                && hasValidPositions(rowCells, rowEdges, strict);
    }

    /**
     * Determines if column is valid
     * @param column (int) column index
     * @param strict (boolean) true if finding empty cells returns false
     * @return true if column is valid
     */
    public boolean isValidColumn(int column, boolean strict) {
        CellType[] columnCells = new CellType[sideLength];
        EdgeType[] columnEdges = Arrays.copyOfRange(horizontalEdges,
                column * (sideLength-1),
                (column+1) * (sideLength-1));
        for (int i = 0; i < sideLength; ++i) {
            columnCells[i] = cells[i * sideLength + column];
        }
        return hasValidCount(columnCells, strict)
                && hasValidPositions(columnCells, columnEdges, strict);
    }

    /**
     * Determines if the grid is valid, indicating a win
     * @return true if the grid is valid, indicating a win
     */
    public boolean isValidGrid() {
        for (int row = 0; row < sideLength; ++row) {
            if (!isValidRow(row, true)) return false;
        }
        for (int column = 0; column < sideLength; ++column) {
            if (!isValidColumn(column, true)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < sideLength; ++row) {
            for (int column = 0; column < sideLength; ++column) {
                sb.append(String.format("%5s", cells[row * sideLength + column]));
                if (column != sideLength - 1)
                    sb.append(verticalEdges[row * (sideLength-1) + column]
                            .getSymbol());
            }
            sb.append("\n");
            if (row != sideLength - 1) {
                for (int column = 0; column < sideLength; ++column) {
                    sb.append(String.format("  %s   ", horizontalEdges[column * (sideLength-1) + row]
                            .getSymbol()));
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof TangoGrid
                && sideLength == ((TangoGrid) that).sideLength
                && Arrays.equals(cells, ((TangoGrid) that).cells)
                && Arrays.equals(horizontalEdges, ((TangoGrid) that).horizontalEdges)
                && Arrays.equals(verticalEdges, ((TangoGrid) that).verticalEdges);
    }
}
