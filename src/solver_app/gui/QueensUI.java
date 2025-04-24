package solver_app.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.*;


public final class QueensUI {
    private final static int BASE_LENGTH = 420;
    private QueensUI() {}

    public static BorderPane queensBoard() {
        BorderPane main = new BorderPane();
        main.setPadding(new Insets(20));
        main.getStyleClass().add("root-queens");

        BorderPane game = new BorderPane();
        game.setPadding(new Insets(20));
        game.setVisible(false);

        GridPane board = new GridPane();
        board.setAlignment(Pos.TOP_CENTER);
        game.setCenter(board);

        Label titleLabel = new Label("LinkedIn Queens Solver");
        titleLabel.getStyleClass().add("title-label-queens");

        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(50, 0, 20, 0));

        VBox spinnerBox = createSideLengthSetter(board, game);
        StackPane stack = new StackPane(spinnerBox, game);

        ColorPicker pickColor = new ColorPicker();
        pickColor.getStyleClass().add("color-picker");



        Button changeSize = new Button("Change size");
        changeSize.getStyleClass().add("button-queens");
        changeSize.setOnAction(event ->
        {
            board.getChildren().clear();
            game.setVisible(false);
            spinnerBox.setVisible(true);
        });

        Button solve = new Button("Solve grid");
        solve.getStyleClass().add("button-queens");

        Button clear = new Button("Clear grid");
        clear.getStyleClass().add("button-queens");

        VBox gameButtonBox = new VBox(20);
        gameButtonBox.setAlignment(Pos.CENTER);

        HBox firstRow = new HBox(30, pickColor, solve);
        firstRow.setAlignment(Pos.CENTER);

        HBox secondRow = new HBox(30, changeSize, clear);
        secondRow.setAlignment(Pos.CENTER);

        gameButtonBox.getChildren().addAll(firstRow, secondRow);
        game.setBottom(gameButtonBox);
        BorderPane.setAlignment(gameButtonBox, Pos.CENTER);

        main.setTop(titleBox);
        main.setCenter(stack);
        return main;
    }

    private static VBox createSideLengthSetter(GridPane board, BorderPane game) {
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
            createCells(board, spinner.getValue());
            game.setVisible(true);
        });

        HBox spinnerButtonBox = new HBox(40);
        spinnerButtonBox.setAlignment(Pos.CENTER);
        spinnerButtonBox.setPadding(new Insets(20, 0, 0, 0));
        spinnerButtonBox.getChildren().add(spinnerButton);

        spinnerBox.getChildren().addAll(titleSpinner, spinner, spinnerButtonBox);
        return spinnerBox;
    }

    private static void createCells(GridPane gridPane, int sideLength) {
        for (int row = 0; row < sideLength; ++row) {
            for (int column = 0; column < sideLength; ++column) {
                Button cell = new Button();
                cell.setMinSize((double) BASE_LENGTH / sideLength,
                        (double) BASE_LENGTH / sideLength);
                cell.setMaxSize((double) BASE_LENGTH / sideLength,
                        (double) BASE_LENGTH / sideLength);
                cell.getStyleClass().add("cell-tango");
                gridPane.add(cell, column, row);
            }
        }
    }
}