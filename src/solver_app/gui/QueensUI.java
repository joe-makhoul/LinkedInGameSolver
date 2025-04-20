package solver_app.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import solver_app.tango.CellType;

public final class QueensUI {
    private QueensUI() {}
    public static BorderPane queensBoard() {
        BorderPane main = new BorderPane();
        main.setStyle("-fx-background-color: lavenderblush;");

        GridPane board = new GridPane();
        board.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("LinkedIn Queens Solver");
        titleLabel.setStyle("-fx-font-size: 54px; -fx-font-weight: bold; -fx-text-fill: purple;");

        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets((50), 0, 20, 0));

        /*
        CHOOSE BOARD SIDE LENGTH
         */
        VBox spinnerBox = new VBox();
        spinnerBox.setPadding(new Insets(80));
        spinnerBox.setAlignment(Pos.TOP_CENTER);
        Label titleSpinner = new Label("Board's side length");
        titleSpinner.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: purple;");
        titleSpinner.setAlignment(Pos.CENTER);

        Spinner<Integer> spinner = new Spinner<>(5, 11, 8);
        spinner.setStyle(
                "-fx-border-color: purple; -fx-border-width: 4px; -fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;"
        );
        spinner.getEditor().setStyle(
                "-fx-background-color: snow; -fx-font-size: 28px; -fx-font-weight: bold;" +
                        "-fx-text-fill: purple;"
        );

        spinner.getEditor().setAlignment(Pos.CENTER);

        Button spinnerButton = new Button("Confirm size");
        spinnerButton.setStyle("-fx-font-size: 24px; -fx-padding: 8, 16; " +
                "-fx-background-color: plum; -fx-text-fill: purple");
        spinnerButton.setOnAction(event -> {
            createCells(board, spinner.getValue());
            main.setCenter(board);
        });
        HBox buttonBox = new HBox(40);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        buttonBox.getChildren().add(spinnerButton);
        spinnerBox.getChildren().addAll(titleSpinner, spinner, buttonBox);



        main.setTop(titleBox);
        main.setCenter(spinnerBox);
        return main;
    }


    private static void createCells(GridPane gridPane, int sideLength) {
        for (int row = 0; row < sideLength; ++row) {
            for (int column = 0; column < sideLength; ++column) {
                final CellType[] cellType = {CellType.EMPTY};
                Button cell = new Button();
                cell.setGraphic(cellType[0].getSymbol());
                cell.setMinSize((double) (35 * 12) / sideLength,
                        (double) (35 * 12) /sideLength);
                cell.setMaxSize((double) (35 * 12) / sideLength,
                        (double) (35 * 12) / sideLength);
                cell.setStyle("-fx-background-color: white; -fx-border-color: lightgray;");
                gridPane.add(cell, column*2, row*2);
            }
        }
    }
}
