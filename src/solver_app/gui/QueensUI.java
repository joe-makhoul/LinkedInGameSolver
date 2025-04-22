package solver_app.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.*;

public final class QueensUI {
    private final static int BASE_LENGTH = 420;
    private QueensUI() {}

    public static BorderPane queensBoard() {
        BorderPane main = new BorderPane();
        main.setStyle("-fx-background-color: #F5F0FF;");  // Soft Lavender

        BorderPane game = new BorderPane();
        game.setPadding(new Insets(20));
        game.setVisible(false);

        GridPane board = new GridPane();
        board.setAlignment(Pos.TOP_CENTER);
        game.setCenter(board);

        Label titleLabel = new Label("LinkedIn Queens Solver");
        titleLabel.setStyle("-fx-font-size: 54px; -fx-font-weight: bold; -fx-text-fill: #6A0DAD;");  // Deep Purple

        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(50, 0, 20, 0));

        VBox spinnerBox = createSideLengthSetter(board, game);
        StackPane stack = new StackPane(spinnerBox, game);

        Button solve = new Button("Solve grid");
        solve.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 8, 16; " +
                "-fx-background-color: #9B59B6; -fx-text-fill: white;");  // Soft Violet

        Button clear = new Button("Clear grid");
        clear.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 8, 16; " +
                "-fx-background-color: #9B59B6; -fx-text-fill: white;");

        HBox gameButtonBox = new HBox(40);
        gameButtonBox.setAlignment(Pos.CENTER);
        gameButtonBox.setPadding(new Insets(0, 0, 20, 0));
        gameButtonBox.getChildren().addAll(solve, clear);
        game.setBottom(gameButtonBox);

        main.setTop(titleBox);
        main.setCenter(stack);
        return main;
    }

    private static VBox createSideLengthSetter(GridPane board, BorderPane game) {
        VBox spinnerBox = new VBox();
        spinnerBox.setPadding(new Insets(80));
        spinnerBox.setAlignment(Pos.TOP_CENTER);

        Label titleSpinner = new Label("Board's side length");
        titleSpinner.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #6A0DAD;");
        titleSpinner.setAlignment(Pos.CENTER);

        Spinner<Integer> spinner = new Spinner<>(5, 11, 8);
        spinner.setStyle(
                "-fx-border-color: #A569BD; -fx-border-width: 4px; -fx-border-radius: 5px;" +  // Medium Purple
                        "-fx-background-radius: 5px;"
        );
        spinner.getEditor().setStyle(
                "-fx-background-color: white; -fx-font-size: 28px; -fx-font-weight: bold;" +
                        "-fx-text-fill: #6A0DAD;"
        );

        Button spinnerButton = new Button("Confirm size");
        spinnerButton.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 8 16; " +
                "-fx-background-color: #9B59B6; -fx-text-fill: white;");
        spinnerButton.setOnAction(event -> {
            createCells(board, spinner.getValue());
            spinnerBox.setVisible(false);
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
                final int[] color = {0};
                Button cell = new Button();
                cell.setMinSize((double) BASE_LENGTH / sideLength,
                        (double) BASE_LENGTH /sideLength);
                cell.setMaxSize((double) BASE_LENGTH / sideLength,
                        (double) BASE_LENGTH / sideLength);
                cell.setStyle("-fx-background-color: white; -fx-border-color: lightgray;");
                gridPane.add(cell, column, row);
            }
        }
    }
}