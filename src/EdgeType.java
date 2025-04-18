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

    public String toSymbol(EdgeOrientation orientation) {
        return switch (this) {
            case NORMAL -> orientation == EdgeOrientation.HORIZONTAL ? "-" : "|";
            case EQUALS -> "=";
            case CROSS -> "×";
        };
    }
}
