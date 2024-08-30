package diet.ui;


import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MockDietApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("DietApp");
        stage.getIcons().add(new Image(Objects.requireNonNull(DietApp.class.getResource("style/D.png")).toString()));

        try {
            //URL fxmlResource = DietApp.class.getResource("Pane.fxml");

            //MainPane mainPanel = new MainPane(controller);
            FXMLLoader loader = new FXMLLoader(DietApp.class.getResource("Pane.fxml"));
            AnchorPane layout = loader.load();

            TextField name = (TextField) loader.getNamespace().get("nameField");
            name.setText("Giovanni");
            TextField surname = (TextField) loader.getNamespace().get("surnameField");
            surname.setText("Visi");
            ComboBox<Integer> age = (ComboBox<Integer>) loader.getNamespace().get("ageBox");
            age.setValue(23);
            ComboBox<String> sex = (ComboBox<String>) loader.getNamespace().get("sexBox");
            sex.setValue("Male");
            TextField height = (TextField) loader.getNamespace().get("heightField");
            height.setText("175");
            TextField weight = (TextField) loader.getNamespace().get("weightField");
            weight.setText("76");
            TextField PKRatio = (TextField) loader.getNamespace().get("PKRatioField");
            PKRatio.setText("2");
            TextField fatPercentage = (TextField) loader.getNamespace().get("fatPercentageField");
            fatPercentage.setText("25");

            TextField restHours = (TextField) loader.getNamespace().get("restHoursField");
            restHours.setText("10");
            TextField veryLightHours = (TextField) loader.getNamespace().get("veryLightHoursField");
            veryLightHours.setText("12");
            TextField lightHours = (TextField) loader.getNamespace().get("lightHoursField");
            lightHours.setText("1");
            TextField moderateHours = (TextField) loader.getNamespace().get("moderateHoursField");
            moderateHours.setText("0");
            TextField intenseHours = (TextField) loader.getNamespace().get("intenseHoursField");
            intenseHours.setText("1");

            RadioButton dietType = (RadioButton) loader.getNamespace().get("hypoRadio");
            dietType.setSelected(true);


            Scene scene = new Scene(layout, 930, 680, Paint.valueOf("rgb(245, 245, 255)"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        }  catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Main(String[] args) {
        for (String s : args) { System.out.println(s); }
        System.out.println("args length is: " + args.length);
//        args.toString().concat("--module-path \"C:\\Program Files\\Java\\javafx-sdk-22.0.1\\lib\" --add-modules javafx.controls,javafx.fxml");
        launch(args);
    }
}