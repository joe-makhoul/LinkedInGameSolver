package solver_app.queens;

import java.util.Arrays;

public final class QueensBoard {
    private final int sideLength;
    private final int[] colors;
    private final boolean[] queens;

    public QueensBoard(int sideLength) {
        this.sideLength = sideLength;
        this.colors = new int[sideLength * sideLength];
        Arrays.fill(colors, 0);
        this.queens = new boolean[sideLength * sideLength];
        Arrays.fill(queens, false);
    }

    public int sideLength() {
        return sideLength;
    }

    public boolean isOccupied(int row, int column) {
        return queens[row * sideLength + column];
    }


    public void addColor(int i, int row, int column) {
        colors[row * sideLength + column] = i;
    }

    public void addQueen(int row, int column) {
        queens[row * sideLength + column] = true;
    }

    public void removeQueen(int row, int column) {
        queens[row * sideLength + column] = false;
    }

    public boolean isValidPlacement(int row, int column) {
        return isValidForColor(row, column) && isValidForSurroundings(row, column)
                && isValidForRow(row) && isValidForColumn(column);
    }

    public int queenCount() {
        int count = 0;
        for (boolean q : queens) {
            if (q) count++;
        }
        return count;
    }

    public boolean isValidBoard() {
        if (Arrays.stream(colors).distinct().count() != sideLength) return false;
        if (Arrays.stream(colors).filter(c -> c == 0).findAny().isPresent()) return false;
        if (queenCount() != sideLength) return false;
        return true;
    }

    private boolean isValidForColor(int row, int column) {
        int color = colors[row * sideLength + column];
        if (color == 0) return false;

        for (int r = 0; r < sideLength; ++r) {
            for (int c = 0; c < sideLength; ++c) {
                if ((r != row || c != column) &&
                        queens[r * sideLength + c] &&
                        colors[r * sideLength + c] == color) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidForSurroundings(int row, int column) {
        if (column != sideLength - 1
                && isOccupied(row, column + 1)) return false;
        if (column != 0
                && isOccupied(row, column - 1)) return false;
        if (row != 0) {
            if (isOccupied(row - 1, column)) return false;
            if (column != sideLength - 1
                    && isOccupied(row - 1, column + 1)) return false;
            if (column != 0 &&
                    isOccupied(row - 1, column - 1)) return false;
        }
        if (row != sideLength - 1) {
            if (isOccupied(row + 1, column)) return false;
            if (column != sideLength - 1
                    && isOccupied(row + 1, column + 1)) return false;
            if (column != 0
                    && isOccupied(row + 1, column - 1)) return false;
        }
        return true;
    }

    private boolean isValidForRow(int row) {
        for (int column = 0; column < sideLength; ++column) {
            if (queens[row * sideLength + column]) return false;
        }
        return true;
    }

    private boolean isValidForColumn(int column) {
        for (int row = 0; row < sideLength; ++row) {
            if (queens[row * sideLength + column]) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < sideLength; ++row) {
            for (int column = 0; column < sideLength; ++column) {
                sb.append(queens[row * sideLength + column] ?
                        "X" : colors[row * sideLength + column]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

