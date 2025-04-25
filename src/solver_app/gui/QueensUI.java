package solver_app.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import solver_app.Solver;
import solver_app.queens.QueensBoard;

import java.util.ArrayList;
import java.util.List;


public final class QueensUI {
    private final static int BASE_LENGTH = 420;
    private static final BoardContainer boardContainer = new BoardContainer();
    private QueensUI() {}

    private static class BoardContainer {
        QueensBoard board;
        Button[][] cells;

        void update(int sideLength) {
            board = new QueensBoard(sideLength);
            cells = new Button[sideLength][sideLength];
        }
    }

    public static BorderPane queensBoard() {
        BorderPane main = new BorderPane();
        main.setPadding(new Insets(20));
        main.getStyleClass().add("root-queens");

        BorderPane game = new BorderPane();
        game.setPadding(new Insets(20));
        game.setVisible(false);

        GridPane boardPane = new GridPane();
        boardPane.setAlignment(Pos.TOP_CENTER);
        game.setCenter(boardPane);
        Label titleLabel = new Label("LinkedIn Queens Solver");
        titleLabel.getStyleClass().add("title-label-queens");

        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(50, 0, 20, 0));

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.getStyleClass().add("color-picker");

        // Side length setter
        VBox spinnerBox = new VBox();
        spinnerBox.setPadding(new Insets(80));
        spinnerBox.setAlignment(Pos.TOP_CENTER);

        Label titleSpinner = new Label("Board's side length");
        titleSpinner.getStyleClass().add("title-spinner");
        titleSpinner.setAlignment(Pos.CENTER);

        Spinner<Integer> spinner = new Spinner<>(5, 11, 8);
        spinner.getStyleClass().add("spinner");
        spinner.getEditor().setAlignment(Pos.CENTER);


        Button spinnerButton = new Button("Confirm size");
        spinnerButton.getStyleClass().add("button-queens");
        spinnerButton.setOnAction(event -> {
            boardContainer.update(spinner.getValue());
            createCells(boardPane, boardContainer.board, boardContainer.cells, colorPicker);
            spinnerBox.setVisible(false);
            game.setVisible(true);
        });


        HBox spinnerButtonBox = new HBox(40);
        spinnerButtonBox.setAlignment(Pos.CENTER);
        spinnerButtonBox.setPadding(new Insets(20, 0, 0, 0));
        spinnerButtonBox.getChildren().add(spinnerButton);

        spinnerBox.getChildren().addAll(titleSpinner, spinner, spinnerButtonBox);

        StackPane stack = new StackPane(spinnerBox, game);

        // Main screen buttons
        Button changeSize = new Button("Change size");
        changeSize.getStyleClass().add("button-queens");
        changeSize.setOnAction(event ->
        {
            boardPane.getChildren().clear();
            game.setVisible(false);
            spinnerBox.setVisible(true);
        });

        Button solve = new Button("Solve grid");
        solve.getStyleClass().add("button-queens");
        Label crownLabel = new Label("\uD83D\uDC51"); // Crown emoji
        crownLabel.setStyle("-fx-font-size: 24px;");
        solve.setOnAction(event -> {
            Solver.solve(boardContainer.board);
            System.out.println(boardContainer.board);
            int sideLength = boardContainer.board.sideLength();
            for (int row = 0; row < sideLength; ++row) {
                for (int column = 0; column < sideLength; ++column) {
                    if (boardContainer.board.isOccupied(row, column))
                        boardContainer.cells[row][column].setGraphic(crownLabel);
                }
            }
        });

        Button clear = new Button("Clear grid");
        clear.getStyleClass().add("button-queens");

        VBox gameButtonBox = new VBox(20);
        gameButtonBox.setAlignment(Pos.CENTER);

        HBox firstRow = new HBox(30, colorPicker, solve);
        firstRow.setAlignment(Pos.CENTER);

        HBox secondRow = new HBox(30, changeSize, clear);
        secondRow.setAlignment(Pos.CENTER);

        gameButtonBox.getChildren().addAll(firstRow, secondRow);
        game.setBottom(gameButtonBox);
        BorderPane.setAlignment(gameButtonBox, Pos.CENTER);

        // Main layout
        main.setTop(titleBox);
        main.setCenter(stack);
        return main;
    }

    private static void createCells(GridPane gridPane, QueensBoard board, Button[][] cells,
                                    ColorPicker colorPicker) {
        List<Color> colors = new ArrayList<>();
        colors.add(Color.SNOW);
        int sideLength = cells.length;
        for (int row = 0; row < sideLength; ++row) {
            for (int column = 0; column < sideLength; ++column) {
                Button cell = new Button();
                cell.setMinSize((double) BASE_LENGTH / sideLength,
                        (double) BASE_LENGTH / sideLength);
                cell.setMaxSize((double) BASE_LENGTH / sideLength,
                        (double) BASE_LENGTH / sideLength);
                cell.getStyleClass().add("cell-tango");
                gridPane.add(cell, column, row);
                cells[row][column] = cell;
                int finalRow = row;
                int finalColumn = column;
                cell.setOnAction(event ->
                {
                    if (!colors.contains(colorPicker.getValue())) {
                        colors.add(colorPicker.getValue());
                    }
                    board.addColor(
                            colors.indexOf(colorPicker.getValue()), finalRow, finalColumn);
                    cell.setStyle("-fx-background-color: "
                            + toRGBString(colorPicker.getValue())
                            + ";");
                    System.out.println(board);
                });
            }
        }
    }

    private static String toRGBString(Color color) {
        return String.format("rgb(%d%%, %d%%, %d%%)",
                (int) (color.getRed()*100),
                (int) (color.getGreen()*100),
                (int) (color.getBlue()*100));
    }
}