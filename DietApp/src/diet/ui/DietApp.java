package diet.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import diet.controller.Controller;
import diet.controller.ControllerNotReadyException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class DietApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("DietApp");
        stage.getIcons().add(new Image(Objects.requireNonNull(DietApp.class.getResource("style/D.png")).toString()));

        try {
            AnchorPane layout = (AnchorPane) FXMLLoader.load(new URL(Objects.requireNonNull(DietApp.class.getResource("Pane.fxml")).toExternalForm()));
            Scene scene = new Scene(layout, 930, 680, Paint.valueOf("rgb(245, 245, 255)"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }  catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Main(String[] args) {
        launch(args);
    }
}
