import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SolverApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane main = new BorderPane();
        main.setPadding(new Insets(20));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        Grid grid = new Grid();
        int sideLength = grid.sideLength();
        Button[][] cells = new Button[sideLength][sideLength];
        Button[][] verticalEdges = new Button[sideLength][sideLength-1];
        Button[][] horizontalEdges = new Button[sideLength-1][sideLength];
        createCells(gridPane, grid, cells);
        createEdges(gridPane, grid, verticalEdges, EdgeOrientation.VERTICAL);
        createEdges(gridPane, grid, horizontalEdges, EdgeOrientation.HORIZONTAL);

        Button solve = new Button("Solve grid");
        solve.setStyle("-fx-font-size: 24px; -fx-padding: 8 16; -fx-;" +
                "-fx-background-color: slategray; -fx-text-fill: snow");
        solve.setOnAction(event -> {
            Solver.solve(grid);
            for (int row = 0; row < sideLength; ++row) {
                for (int column = 0; column < sideLength; ++column) {
                    cells[row][column].setGraphic(grid.getCell(row, column).getSymbol());
                }
            }
        });

        Button clear = new Button("Clear grid");
        clear.setStyle("-fx-font-size: 24px; -fx-padding: 8 16; -fx-; " +
                "-fx-background-color: slategray; -fx-text-fill: snow");
        clear.setOnAction(event -> {
            for (int row = 0; row < sideLength; ++row) {
                for (int column = 0; column < sideLength; ++column) {
                    cells[row][column].setGraphic(CellType.EMPTY.getSymbol());
                    grid.addCell(CellType.EMPTY, row, column);
                    if (column != sideLength - 1) {
                        verticalEdges[row][column]
                                .setText(EdgeType.NORMAL.getSymbol());
                        grid.addEdge(EdgeType.NORMAL, EdgeOrientation.VERTICAL, row, column);
                    }
                    if (row != sideLength - 1) {
                        horizontalEdges[row][column]
                                .setText(EdgeType.NORMAL.getSymbol());
                        grid.addEdge(EdgeType.NORMAL, EdgeOrientation.HORIZONTAL, row, column);
                    }
                }
            }
        });

        HBox buttonBox = new HBox(40);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(0, 0, 20, 0));
        buttonBox.getChildren().addAll(solve, clear);

        StackPane centerPane = new StackPane(gridPane);
        centerPane.setPadding(new Insets(20));

        Label titleLabel = new Label("LinkedIn Tango Solver");
        titleLabel.setStyle("-fx-font-size: 54px; -fx-font-weight: bold; -fx-text-fill: slategray;");

        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(50, 0, 20, 0)); // Add space below title

        main.setTop(titleBox);
        main.setCenter(centerPane);
        main.setBottom(buttonBox);

        Scene scene = new Scene(main, 800, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static CellType switchType(CellType c) {
        return CellType.values()[(c.ordinal() + 1) % CellType.values().length];
    }

    private static EdgeType switchType(EdgeType e) {
        return EdgeType.values()[(e.ordinal() + 1) % EdgeType.values().length];
    }

    private static void createCells(GridPane gridPane, Grid grid, Button[][] cells) {
        for (int row = 0; row < cells[0].length; ++row) {
            for (int column = 0; column < cells[0].length; ++column) {
                final CellType[] cellType = {CellType.EMPTY};
                Button cell = new Button();
                cell.setGraphic(cellType[0].getSymbol());
                cell.setMinSize(50,50);
                cell.setMaxSize(50,50);
                cell.setStyle("-fx-background-color: white; -fx-border-color: lightgray;");
                int finalColumn = column;
                int finalRow = row;
                cell.setOnAction(event -> {
                    cell.setGraphic(switchType(cellType[0]).getSymbol());
                    cellType[0] = switchType(cellType[0]);
                    grid.addCell(cellType[0], finalRow, finalColumn);
                });
                gridPane.add(cell, column*2, row*2);
                cells[row][column] = cell;
            }
        }
    }

    private static void createEdges(GridPane gridPane, Grid grid,
                                    Button[][] edges, EdgeOrientation orientation) {
        for (int row = 0; row < edges.length; ++row) {
            for (int column = 0; column < edges[0].length; ++column) {
                final EdgeType[] edgeType = {EdgeType.NORMAL};
                Button edge = new Button();
                edge.setText(edgeType[0].getSymbol());
                if (orientation.equals(EdgeOrientation.VERTICAL)) {
                    edge.setMinSize(30,50);
                    edge.setMaxSize(30,50);
                } else if (orientation.equals(EdgeOrientation.HORIZONTAL)) {
                    edge.setMinSize(50,30);
                    edge.setMaxSize(50,30);
                }
                edge.setStyle("-fx-background-color: transparent; -fx-border-color: transparent");
                int finalColumn = column;
                int finalRow = row;
                edge.setOnAction(event -> {
                    edge.setText(switchType(edgeType[0]).getSymbol());
                    edgeType[0] = switchType(edgeType[0]);
                    grid.addEdge(edgeType[0], orientation, finalRow, finalColumn);
                });
                gridPane.add(edge,
                        orientation.equals(EdgeOrientation.HORIZONTAL) ?
                                column*2 : column*2+1,
                        orientation.equals(EdgeOrientation.HORIZONTAL) ?
                                row*2+1 : row*2);
                edges[row][column] = edge;
            }
        }
    }
}