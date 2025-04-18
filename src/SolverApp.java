import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SolverApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();
        Grid grid = new Grid();
        int sideLength = grid.sideLength();
        Button[][] cells = new Button[sideLength][sideLength];
        for (int row = 0; row < sideLength; ++row) {
            for (int column = 0; column < sideLength; ++column) {
                final CellType[] cellType = {CellType.EMPTY};
                Button cell = new Button();
                cell.setGraphic(cellType[0].getSymbol());
                cell.setMinSize(50,50);
                int finalColumn = column;
                int finalRow = row;
                cell.setOnAction(event -> {
                    cell.setGraphic(switchType(cellType[0]).getSymbol());
                    cellType[0] = switchType(cellType[0]);
                    grid.addCell(cellType[0], finalRow, finalColumn);
                });
                gridPane.add(cell, column, row);
                cells[row][column] = cell;
            }
        }
        Button solve = new Button("Solve grid");
        solve.setOnAction(event -> {
            Solver.solve(grid);
            for (int row = 0; row < sideLength; ++row) {
                for (int column = 0; column < sideLength; ++column) {
                    System.out.printf("Grid[%d][%d] = %s\n", row, column, grid.getCell(row, column));
                    cells[row][column].setGraphic(grid.getCell(row, column).getSymbol());
                }
            }
        });

        Button clear = new Button("Clear grid");
        clear.setOnAction(event -> {
            for (int row = 0; row < sideLength; ++row) {
                for (int column = 0; column < sideLength; ++column) {
                    cells[row][column].setGraphic(CellType.EMPTY.getSymbol());
                    grid.addCell(CellType.EMPTY, row, column);
                }
            }
        });
        HBox buttons = new HBox(solve, clear);
        VBox vbox = new VBox(gridPane, buttons);
        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static CellType switchType(CellType c) {
        return CellType.values()[(c.ordinal() + 1) % CellType.values().length];
    }
}