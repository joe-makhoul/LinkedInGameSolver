package solver_app;

import solver_app.queens.QueensBoard;
import solver_app.tango.CellType;
import solver_app.tango.TangoGrid;

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
    public static boolean solve(TangoGrid grid) {
        int sideLength = grid.sideLength();
        for (int row = 0; row < sideLength; ++row) {
            for (int column = 0; column < sideLength; ++column) {
                if (grid.isEmpty(row, column)) {
                    for (CellType type : new CellType[]{CellType.SUN, CellType.MOON}) {
                        grid.addCell(type, row, column);
                        if (grid.isValidRow(row, false)
                                && grid.isValidColumn(column, false)) {
                            if (solve(grid)) return true;
                        }
                        grid.addCell(CellType.EMPTY, row, column);
                    }
                    return false;
                }
            }
        }
        return grid.isValidGrid();
    }

    public static void solve(QueensBoard board) {
        solveByColor(board, 1);
    }

    private static boolean solveByColor(QueensBoard board, int color) {
        int sideLength = board.sideLength();
        if (color > sideLength) {
            return board.isValidBoard();
        }
        for (int[] coord : board.getCoordsByColor().get(color)) {
            int row = coord[0];
            int column = coord[1];
            if (!board.isOccupied(row, column)) {
                if (board.isValidPlacement(row, column)) {
                    board.addQueen(row, column);
                    if (solveByColor(board, color+1)) return true;
                    board.removeQueen(row, column);
                }
            }
        }
        return false;
    }
}
