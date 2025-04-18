/**
 * Class that contains the solver's algorithm
 */
public final class Solver {
    private Solver() {}

    /**
     * Fills the grid
     * @param grid (Grid) grid to be solved
     * @return true if the grid is solved
     */
    public static boolean solve(Grid grid) {
        int sideLength = grid.sideLength();
        for (int row = 0; row < sideLength; ++row) {
            for (int column = 0; column < sideLength; ++column) {
                if (grid.isEmpty(row, column)) {
                    for (CellType type : new CellType[]{CellType.SUN, CellType.MOON}) {
                        grid.addCell(type, row, column);
                        if (grid.isValidRow(row, false)
                                && grid.isValidColumn(column, false)) {
                            if (solve(grid)) {
                                return true;
                            }
                        }
                        grid.addCell(CellType.EMPTY, row, column);
                    }
                    return false;
                }
            }
        }
        return grid.isValidGrid();
    }
}
