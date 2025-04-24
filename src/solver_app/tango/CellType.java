package solver_app.tango;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Types of cell icons
 */
public enum CellType {
    /**
     * Empty cell
     */
    EMPTY(),
    /**
     * Sun icon or equivalent
     */
    SUN(),
    /**
     * Moon icon or equivalent
     */
    MOON();

    public Circle getSymbol() {
        switch (this) {
            case EMPTY -> {
                return new Circle();
            }
            case SUN -> {
                return new Circle(10, Color.ORANGE);
            }
            case MOON -> {
                return new Circle(10, Color.STEELBLUE);
            }
        }
        return new Circle();
    }
}
