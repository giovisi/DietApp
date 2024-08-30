package diet.ui;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.*;

import diet.controller.ControllerNotReadyException;
import diet.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import diet.controller.Controller;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PaneController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private VBox leftPane;

    @FXML
    private FlowPane basePane;
    @FXML
    private TextFlow title;

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField heightField;
    @FXML
    private ComboBox<Integer> ageBox;
    @FXML
    private ComboBox<String> sexBox;
    @FXML
    private TextField weightField;
    @FXML
    private TextField PKRatioField;
    @FXML
    private TextField fatPercentageField;

    @FXML
    private TextField restHoursField;
    @FXML
    private TextField veryLightHoursField;
    @FXML
    private TextField lightHoursField;
    @FXML
    private TextField moderateHoursField;
    @FXML
    private TextField intenseHoursField;

    @FXML
    private ToggleGroup radioGroup;
    @FXML
    private RadioButton hypoRadio;
    @FXML
    private RadioButton normoRadio;
    @FXML
    private RadioButton hyperRadio;

    @FXML
    private Button calculate;

    @FXML
    private Region spacer;

    @FXML
    private GridPane rightPane;
    @FXML
    private Button goBackButton;
    @FXML
    private TextField totalCalories;

    @FXML
    private Label proteinGrams;
    @FXML
    private Label carbohydrateGrams;
    @FXML
    private Label fatGrams;
    @FXML
    private Label proteinCalories;
    @FXML
    private Label carbohydrateCalories;
    @FXML
    private Label fatCalories;
    @FXML
    private Label proteinPercentage;
    @FXML
    private Label carbohydratePercentage;
    @FXML
    private Label fatPercentage;

    @FXML
    private PieChart macrosPieChart;
    @FXML
    private BarChart<String, Number> macrosBarChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Button switchView;


    private Controller controller;

    private static final String textRegExp = "^[a-zA-Z]*$";
    private static final String intRegExp = "^\\d{0,3}$";
    private static final String decimalRegExp = "^\\d+(?:[.,]\\d+)?$";
    private static double TwoDecimalRound(double decimal) {
        return (double) Math.round(decimal * 100) / 100;
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        initializeWindow();

        controller = new Controller();

        initializeAgeBox();

        initializeSexBox();

        initializeRadioGroup();

        rightPane.setVisible(false);
    }

    private void initializeWindow() {
        spacer.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (newScene != null) {
                // Add listener to the window property of the new scene
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        Stage stage = (Stage) newWindow;
                        // Set initial spacer width based on initial stage width
                        adjustSpacerWidth(stage.getWidth());

                        // Add listener to the stage width property
                        stage.widthProperty().addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                adjustSpacerWidth(newValue.doubleValue());
                            }
                        });
                    }
                });
            }
        });
    }
    private void adjustSpacerWidth(double newWidth) {
        if (newWidth > 930) {
            spacer.setPrefWidth((newWidth - 928) / 2);
        } else {
            spacer.setPrefWidth(0);
        }
    }

    private void initializeAgeBox() {
        ageBox.setItems(FXCollections.observableArrayList(IntStream.rangeClosed(18, 100).boxed().collect(Collectors.toList())));
    }
    private void initializeSexBox() {
        sexBox.getItems().addAll(FXCollections
                .observableArrayList(Arrays.stream(Sex.values())
                        .map(Sex::toString).collect(Collectors.toList())));
    }
    private void initializeRadioGroup() {
        radioGroup = new ToggleGroup();

        hypoRadio.setToggleGroup(radioGroup);
        normoRadio.setToggleGroup(radioGroup);
        hyperRadio.setToggleGroup(radioGroup);
    }


    @FXML
    private void buttonHandler(ActionEvent event) {

        if (argumentsAreValid()) {
            String name = nameField.getText();
            String surname = surnameField.getText();
            int age = Integer.parseInt(ageBox.getValue().toString());
            String sex = sexBox.getValue();
            int height = Integer.parseInt(heightField.getText());

            double fatPercentage = Double.parseDouble(fatPercentageField.getText().replace(",", "."));
            int restHours = Integer.parseInt(restHoursField.getText());
            int veryLightActivityHours = Integer.parseInt(veryLightHoursField.getText());
            int lightActivityHours = Integer.parseInt(lightHoursField.getText());
            int moderateActivityHours = Integer.parseInt(moderateHoursField.getText());
            int intenseActivityHours = Integer.parseInt(intenseHoursField.getText());

            double weight = Double.parseDouble(weightField.getText().replace(",", "."));
            double PKRatio = Double.parseDouble(PKRatioField.getText().replace(",", "."));
            DietType dietType = retrieveDietType();

            Client client;
            Activity activity;
            Protein protein;
            Fat fat;
            Carbohydrate carbohydrate;

            try {
                client = new Client(name, surname, Sex.valueOf(sex.toUpperCase()), age, weight, height);
                activity = new Activity(restHours, veryLightActivityHours, lightActivityHours, moderateActivityHours, intenseActivityHours);
                controller.setClient(client);
                controller.setActivity(activity);
                controller.setDiet(PKRatio, fatPercentage, dietType);

                protein = controller.getProtein();
                fat = controller.getFat();
                carbohydrate = controller.getCarbohydrate();

                render(protein, fat, carbohydrate);

                double caloriesSum = protein.kcal + fat.kcal + carbohydrate.kcal;
                totalCalories.setText(TwoDecimalRound(caloriesSum) + " kcal");
            } catch (IllegalArgumentException | ControllerNotReadyException e) {
                Controller.alert(e.getMessage());
            }
        }
    }


    private boolean argumentsAreValid() {
        return nameIsValid() && surnameIsValid() && ageIsValid() && sexIsValid() && heightIsValid() && weightIsValid() && PKRatioIsValid() && fatPercentageIsValid()
                && restHoursIsValid() && veryLightHoursIsValid() && lightHoursIsValid() && moderateHoursIsValid() && intenseHoursIsValid() && radioGroupIsValid();
    }


    private boolean nameIsValid() {
        String nameValue = (nameField.getText() != null) ? nameField.getText() : "";
        return nameIsNotEmptyOrNotifies(nameValue) && namePatternMatchesOrNotifies(nameValue);
    }
    private boolean nameIsNotEmptyOrNotifies(String name) {
        if (!name.isEmpty()) return true;
        Controller.notify("Please enter your name!");
        return false;
    }
    private boolean namePatternMatchesOrNotifies(String name) {
        if (Pattern.matches(textRegExp, name)) return true;
        Controller.notify("Your name must contain only alphabetic characters!");
        return false;
    }


    private boolean surnameIsValid() {
        String surnameValue = (surnameField.getText() != null) ? surnameField.getText() : "";
        return surnameIsNotEmptyOrNotifies(surnameValue) && surnamePatternMatchesOrNotifies(surnameValue);
    }
    private boolean surnameIsNotEmptyOrNotifies(String surname) {
        if (!surname.isEmpty()) return true;
        Controller.notify("Please enter your surname!");
        return false;
    }
    private boolean surnamePatternMatchesOrNotifies(String surname) {
        if (Pattern.matches(textRegExp, surname)) return true;
        Controller.notify("Your surname must contain only alphabetic characters!");
        return false;
    }


    private boolean ageIsValid() {
        return ageIsSafeToRetrieve() && ageIsNotEmptyOrNotifies() && agePatternMatchesOrNotifies();
    }
    private boolean ageIsSafeToRetrieve() {
        String ageValue = "";
        try {
            ageValue = (ageBox.getValue() != null) ? ageBox.getValue().toString() : "";
        } catch (Exception e) {
            Controller.notify("Your age must be an integer number between 18 an 100!");
            return false;
        }
        return true;
    }
    private boolean ageIsNotEmptyOrNotifies() {
        String age  = (ageBox.getValue() != null) ? ageBox.getValue().toString() : "";
        if (!age.isEmpty()) return true;
        Controller.notify("Please select your age!");
        return false;
    }
    private boolean agePatternMatchesOrNotifies() {
        String age  = (ageBox.getValue() != null) ? ageBox.getValue().toString() : "";
        if (Pattern.matches(intRegExp, age)) return true;
        Controller.notify("Your age must be an integer number!");
        return false;
    }


    private boolean sexIsValid() {
        return sexIsSafeToRetrieve() && sexIsNotEmptyOrNotifies();
    }
    private boolean sexIsSafeToRetrieve() {
        String sexValue = "";
        try {
            sexValue = (sexBox.getValue() != null) ? sexBox.getValue() : "";
        } catch (Exception e) {
            Controller.notify("Please select your sex!");
            return false;
        }
        return true;
    }
    private boolean sexIsNotEmptyOrNotifies() {
        String sex  = (sexBox.getValue() != null) ? sexBox.getValue().toString() : "";
        if (!sex.isEmpty()) return true;
        Controller.notify("Please select your sex!");
        return false;
    }


    private boolean heightIsValid() {
        String heightValue = (heightField.getText() != null) ? heightField.getText() : "";
        return heightIsNotEmptyOrNotifies(heightValue) && heightMatchesPatternOrNotifies(heightValue);
    }
    private boolean heightIsNotEmptyOrNotifies(String height) {
        if (!height.isEmpty()) return true;
        Controller.notify("Please enter your height!");
        return false;
    }
    private boolean heightMatchesPatternOrNotifies(String height) {
        if (Pattern.matches(intRegExp, height)) return true;
        Controller.notify("Your height must be an integer number!");
        return false;
    }


    private boolean weightIsValid() {
        String weightValue = (weightField.getText() != null) ? weightField.getText() : "";
        return weightIsNotEmptyOrNotifies(weightValue) && weightMatchesPatternOrNotifies(weightValue);
    }
    private boolean weightIsNotEmptyOrNotifies(String weight) {
        if (!weight.isEmpty()) return true;
        Controller.notify("Please enter your weight!");
        return false;
    }
    private boolean weightMatchesPatternOrNotifies(String weight) {
        if (Pattern.matches(decimalRegExp, weight)) return true;
        Controller.notify("Your weight must be a floating point number!");
        return false;
    }


    private boolean PKRatioIsValid() {
        String PKRatioValue = (PKRatioField.getText() != null) ? PKRatioField.getText() : "";
        return PKRatioIsNotEmptyOrNotifies(PKRatioValue) && PKRatioMatchesPatternOrNotifies(PKRatioValue);
    }
    private boolean PKRatioIsNotEmptyOrNotifies(String PKRatio) {
        if (!PKRatio.isEmpty()) return true;
        Controller.notify("Please enter your protein per kg ratio!");
        return false;
    }
    private boolean PKRatioMatchesPatternOrNotifies(String PKRatio) {
        if (Pattern.matches(decimalRegExp, PKRatio)) return true;
        Controller.notify("Your protein per kg ratio must be a floating point number!");
        return false;
    }


    private boolean fatPercentageIsValid() {
        String fatPercentageValue = (fatPercentageField.getText() != null) ? fatPercentageField.getText() : "";
        return fatIsNotEmptyOrNotifies(fatPercentageValue) && fatMatchesPatternOrNotifies(fatPercentageValue);
    }
    private boolean fatIsNotEmptyOrNotifies(String fatPercentage) {
        if (!fatPercentage.isEmpty()) return true;
        Controller.notify("Please enter your fat percentage!");
        return false;
    }
    private boolean fatMatchesPatternOrNotifies(String fatPercentage) {
        if (Pattern.matches(decimalRegExp, fatPercentage)) return true;
        Controller.notify("Your fat percentage must be a floating point number!");
        return false;
    }


    private boolean restHoursIsValid() {
        String restHoursValue = (restHoursField.getText() != null) ? restHoursField.getText() : "";
        return restHoursIsNotEmptyOrNotifies(restHoursValue) && restHoursMatchesPatternOrNotifies(restHoursValue);
    }
    private boolean restHoursIsNotEmptyOrNotifies(String restHours) {
        if (!restHours.isEmpty()) return true;
        Controller.notify("Please enter your rest hours per day!");
        return false;
    }
    private boolean restHoursMatchesPatternOrNotifies(String restHours) {
        if (Pattern.matches(intRegExp, restHours)) return true;
        Controller.notify("The number of rest hours must be a integer number!");
        return false;
    }


    private boolean veryLightHoursIsValid() {
        String veryLightHoursValue = (veryLightHoursField.getText() != null) ? veryLightHoursField.getText() : "";
        return veryLightHoursIsNotEmptyOrNotifies(veryLightHoursValue) &&
                veryLightHoursMatchesPatternOrNotifies(veryLightHoursValue);
    }
    private boolean veryLightHoursIsNotEmptyOrNotifies(String veryLightHours) {
        if (!veryLightHours.isEmpty()) return true;
        Controller.notify("Please enter your light activity hours per day!");
        return false;
    }
    private boolean veryLightHoursMatchesPatternOrNotifies(String veryLightHours) {
        if (Pattern.matches(intRegExp, veryLightHours)) return true;
        Controller.notify("The number of very light activity hours must be a integer number!");
        return false;
    }


    private boolean lightHoursIsValid() {
        String lightHoursValue = (lightHoursField.getText() != null) ? lightHoursField.getText() : "";
        return lightHoursIsNotEmptyOrNotifies(lightHoursValue) && lightHoursMatchesPatternOrNotifies(lightHoursValue);
    }
    private boolean lightHoursIsNotEmptyOrNotifies(String lightHours) {
        if (!lightHours.isEmpty()) return true;
        Controller.notify("Please enter your light activity hours per day!");
        return false;
    }
    private boolean lightHoursMatchesPatternOrNotifies(String lightHours) {
        if (Pattern.matches(intRegExp, lightHours)) return true;
        Controller.notify("The number of light activity hours must be a integer number!");
        return false;
    }


    private boolean moderateHoursIsValid() {
        String moderateHoursValue = (moderateHoursField.getText() != null) ? moderateHoursField.getText() : "";
        return moderateHoursIsNotEmptyOrNotifies(moderateHoursValue) &&
                moderateHoursIsNotEmptyOrNotifies(moderateHoursValue);
    }
    private boolean moderateHoursIsNotEmptyOrNotifies(String moderateHours) {
        if (!moderateHours.isEmpty()) return true;
        Controller.notify("Please enter your moderate activity hours per day!");
        return false;
    }
    private boolean moderateHoursMatchesPatternOrNotifies(String moderateHours) {
        if (Pattern.matches(intRegExp, moderateHours)) return true;
        Controller.notify("The number of moderate activity hours must be a integer number!");
        return false;
    }


    private boolean intenseHoursIsValid() {
        String intenseHoursValue = (intenseHoursField.getText() != null) ? intenseHoursField.getText() : "";
        return intenseHoursMatchesPatternOrNotifies(intenseHoursValue)
                && intenseHoursIsNotEmptyOrNotifies(intenseHoursValue);
    }
    private boolean intenseHoursIsNotEmptyOrNotifies(String intenseHours) {
        if (!intenseHours.isEmpty()) return true;
        Controller.notify("Please enter your intense activity hours per day!");
        return false;
    }
    private boolean intenseHoursMatchesPatternOrNotifies(String intenseHours) {
        if (Pattern.matches(intRegExp, intenseHours)) return true;
        Controller.notify("The number of moderate activity hours must be a integer number!");
        return false;
    }


    private boolean radioGroupIsValid() {
        return radioGroupSelectedOrNotifies();
    }
    private boolean radioGroupSelectedOrNotifies() {
        if (radioGroup.getSelectedToggle() != null) return true;
        Controller.notify("Please select a diet type!");
        return false;
    }


    private DietType retrieveDietType() {
        RadioButton radioValue = (RadioButton) radioGroup.getSelectedToggle();
        String value = radioValue.getText() + "oric";
        return DietType.valueOf(value.toUpperCase());
    }


    private void render(Protein protein, Fat fat, Carbohydrate carbohydrate) {
        renderProtein(protein);
        renderFat(fat);
        renderCarbohydrate(carbohydrate);

        renderPieChart(protein, fat, carbohydrate);
        renderBarChart(protein, fat, carbohydrate);

        renderPanesVisibility();
    }


    private void renderProtein(Protein protein) {
        proteinGrams.setText(TwoDecimalRound(protein.grams) + " g");
        proteinCalories.setText(TwoDecimalRound(protein.kcal) + " kcal");
        proteinPercentage.setText(TwoDecimalRound(protein.percentage * 100) + " %");
    }
    private void renderFat(Fat fat) {
        fatGrams.setText(TwoDecimalRound(fat.grams) + " g");
        fatCalories.setText(TwoDecimalRound(fat.kcal) + " kcal");
        fatPercentage.setText(TwoDecimalRound(fat.percentage * 100) + " %");
    }
    private void renderCarbohydrate(Carbohydrate carbohydrate) {
        carbohydrateGrams.setText(TwoDecimalRound(carbohydrate.grams) + " g");
        carbohydrateCalories.setText(TwoDecimalRound(carbohydrate.kcal) + " kcal");
        carbohydratePercentage.setText(TwoDecimalRound(carbohydrate.percentage * 100) + " %");
    }

    private void renderPieChart(Protein protein, Fat fat, Carbohydrate carbohydrate) {
        PieChart.Data proteinSclice = new PieChart.Data("Proteins", protein.percentage);
        PieChart.Data fatSclice = new PieChart.Data("Fats", fat.percentage);
        PieChart.Data carbohydrateSclice = new PieChart.Data("Carbohydrates", carbohydrate.percentage);

        macrosPieChart.getData().setAll(proteinSclice, fatSclice, carbohydrateSclice);

        proteinSclice.getNode().setStyle("-fx-pie-color: #DD3333;");
        fatSclice.getNode().setStyle("-fx-pie-color: rgb(0, 128, 0);");
        carbohydrateSclice.getNode().setStyle("-fx-pie-color: #FDD835;");

        adjustPieChartEsthetic();
    }
    private void adjustPieChartEsthetic() {
        macrosPieChart.setLabelsVisible(false);
        macrosPieChart.setLegendVisible(false);
        macrosPieChart.setAnimated(true);
        macrosPieChart.setVisible(true);
    }

    private void renderBarChart(Protein protein, Fat fat, Carbohydrate carbohydrate) {
        XYChart.Series<String, Number> data = new XYChart.Series<String, Number>();
        XYChart.Data<String, Number> data1 = new XYChart.Data<>("Protein", protein.grams);
        data.getData().add(data1);

        XYChart.Data<String, Number> data2 = new XYChart.Data<>("Fat", fat.grams);
        data.getData().add(data2);

        XYChart.Data<String, Number> data3 = new XYChart.Data<>("Carbohydrate", carbohydrate.grams);
        data.getData().add(data3);

        ObservableList<XYChart.Series<String, Number>> observableData =
                FXCollections.observableArrayList(data);
        macrosBarChart.setData(observableData);

        data1.getNode().setStyle(" -fx-bar-fill: #DD3333; ");
        data2.getNode().setStyle(" -fx-bar-fill: rgb(0, 128, 0); ");
        data3.getNode().setStyle(" -fx-bar-fill: #FDD835; ");
        
        adjustBarChartEsthetic();
    }
    private void adjustBarChartEsthetic() {
        macrosBarChart.setLegendVisible(false);
        macrosBarChart.setVisible(false);
        macrosBarChart.setAnimated(true);
    }
    @FXML
    private void renderPanesVisibility() {
        rightPane.setVisible(true);
        adjustPanesVisibility();
        leftPane.setDisable(true);
        title.setStyle(" -fx-opacity: 0.5; ");
    }

    @FXML
    private void adjustPanesVisibility() {
        adjustBasePaneVisibility();
//        adjustRightPaneVisibility();

        adjustPanesAlignment();

        adjustBasePaneLayout();
    }
    @FXML
    private void adjustBasePaneVisibility() {
        AnchorPane.setTopAnchor(basePane, 0.0);
        AnchorPane.setLeftAnchor(basePane, 0.0);
        AnchorPane.setRightAnchor(basePane, 0.0);
        AnchorPane.setBottomAnchor(basePane, 0.0);

        root.requestLayout();
    }
    @FXML
    private void adjustRightPaneVisibility() {
        AnchorPane.setTopAnchor(rightPane, 1000.0);
        AnchorPane.setLeftAnchor(rightPane, 0.0);
        AnchorPane.setRightAnchor(rightPane, 0.0);
        AnchorPane.setBottomAnchor(rightPane, 0.0);

        root.requestLayout();
    }
    @FXML
    private void adjustPanesAlignment() {
        leftPane.setAlignment(Pos.TOP_LEFT);
        rightPane.setAlignment(Pos.TOP_CENTER);
    }
    @FXML
    private void adjustBasePaneLayout() {
        basePane.setLayoutX(100);
        basePane.setLayoutY(0);
    }
    

    @FXML
    private void goBackHandler(ActionEvent event) {
        AnchorPane.setTopAnchor(basePane, null);
        AnchorPane.setLeftAnchor(basePane, null);
        AnchorPane.setRightAnchor(basePane, null);
        AnchorPane.setBottomAnchor(basePane, null);

        root.requestLayout();

        leftPane.setAlignment(Pos.TOP_CENTER);
        rightPane.setAlignment(Pos.TOP_LEFT);

        basePane.setLayoutX(300);
        basePane.setLayoutY(-10);

        rightPane.setVisible(false);
        title.setStyle(" -fx-opacity: 1; ");
        leftPane.setDisable(false);
    }

    @FXML
    private void switchViewHandler(ActionEvent event) {
        macrosPieChart.setVisible(!macrosPieChart.isVisible());
        macrosBarChart.setVisible(!macrosBarChart.isVisible());
    }
}