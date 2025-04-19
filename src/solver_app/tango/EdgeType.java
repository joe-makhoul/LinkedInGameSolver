package solver_app.tango;

/**
 * Types of grid edges
 */
public enum EdgeType {
    /**
     * Normal edge
     */
    NORMAL,
    /**
     * Edge with equal sign
     */
    EQUALS,
    /**
     * Edge with a cross
     */
    CROSS;

    public String getSymbol() {
        return switch (this) {
            case NORMAL -> "";
            case EQUALS -> "=";
            case CROSS -> "×";
        };
    }
}
