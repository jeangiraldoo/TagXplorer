package io.github.jeangiraldoo.tagxplorer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        Parent root = fxmlLoader.load();
        Screen screen = Screen.getPrimary();
        Rectangle2D rectangle = screen.getVisualBounds();
        double screenWidth = rectangle.getWidth();
        double screenHeight = rectangle.getHeight();
        double finalScreenWidth = screenWidth  * 0.80;
        double finalScreenHeight = screenHeight * 0.80;

        Controller controller = fxmlLoader.getController();
        controller.setScreenWidth(screenWidth);
        controller.setScreenHeight(screenHeight);
        controller.initializeUI();

        Scene scene = new Scene(root, finalScreenWidth, finalScreenHeight);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        FileSystemManager manager = new FileSystemManager();
        File[] files = manager.getDirectoryFiles(manager.getCurrentPath());

        controller.updateFiles(files);

        stage.setTitle("TagXplorer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}