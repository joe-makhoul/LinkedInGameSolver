package solver_app.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SolverApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane tango = solver_app.gui.TangoUI.tangoPane();
        Scene scene = new Scene(tango, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}