package solver_app.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public final class SolverApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane tango = solver_app.gui.TangoUI.tangoPane();
        BorderPane queens = solver_app.gui.QueensUI.queensBoard();
        StackPane stack = new StackPane(tango, queens);

        Scene scene = new Scene(stack, 1920, 1080);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Q -> queens.toFront();
                case T -> tango.toFront();
            }
        });
        scene.getStylesheets().add("solver-style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}