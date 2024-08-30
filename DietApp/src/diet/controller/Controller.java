package diet.controller;

import diet.model.Activity;
import diet.model.Calories;
import diet.model.Carbohydrate;
import diet.model.Client;
import diet.model.Diet;
import diet.model.DietType;
import diet.model.Fat;
import diet.model.Protein;
import diet.ui.DietApp;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;


public class Controller {
	private Client client;
	private Activity activity;
	private Diet diet;
	private static MyTotalCaloriesCalculator caloriesCalculator = new MyTotalCaloriesCalculator(new HarrisBenedictBMRCalculator());
	
	public static void alert(String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initStyle(StageStyle.TRANSPARENT);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

		ImageView dIcon = RetrieveDIcon();
		stage.getIcons().add(dIcon.getImage());

		ImageView errorIcon = RetrieveAlertIcon("E");
		alert.setGraphic(errorIcon);

		SetAlertType(alert);

		// Create a StackPane to hold the dialog pane
		StackPane root = GetRootWithStyle(alert);

		// Create a new scene with the root and apply it to the stage
		Scene scene = new Scene(root, -1, -1);
		scene.setFill(null); // Ensure the scene fill is transparent
		stage.setScene(scene);

		SetUpAlert(alert, contentMessage);
	}
	public static void notify(String contentMessage) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initStyle(StageStyle.TRANSPARENT);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

		ImageView dIcon = RetrieveDIcon();
		stage.getIcons().add(dIcon.getImage());

		ImageView errorIcon = RetrieveAlertIcon("E");
		alert.setGraphic(errorIcon);

		SetAlertType(alert);

		// Create a StackPane to hold the dialog pane
		StackPane root = GetRootWithStyle(alert);

		// Create a new scene with the root and apply it to the stage
		Scene scene = new Scene(root, -1, -1);
		scene.setFill(null); // Ensure the scene fill is transparent
		stage.setScene(scene);

		SetUpAlert(alert, contentMessage);
	}

	private static ImageView RetrieveDIcon() {
		ImageView icon = new ImageView(Objects.requireNonNull(DietApp.class.getResource("style/D.png")).toExternalForm());
		icon.setFitWidth(48);
		icon.setFitHeight(48);
		return icon;
	}
	private static ImageView RetrieveAlertIcon(String type) {
		String path = (type.equals("E")) ? "style/Error.png" : "style/Alert.png";
		ImageView icon = new ImageView(Objects.requireNonNull(DietApp.class.getResource(path)).toExternalForm());
		icon.setFitWidth(48);
		icon.setFitHeight(48);
		return icon;
	}
	private static void SetAlertType(Alert alert) {
		alert.getButtonTypes().setAll(ButtonType.OK);
		alert.getDialogPane().lookupButton(ButtonType.OK).getStyleClass().add("ok-button");
		alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(DietApp.class.getResource("style/style.css")).toExternalForm());
		alert.getDialogPane().getStyleClass().add("custom-dialog-pane");
	}
	private static StackPane GetRootWithStyle(Alert alert) {
		StackPane root = new StackPane(alert.getDialogPane());
		root.setPadding(new Insets(20));
		root.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-border-radius: 4px; -fx-background-radius: 4px;");
		return root;
	}
	private static void SetUpAlert(Alert alert, String contentMessage) {
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	
	public Controller() {
		client = null;
		activity = null;
		diet = null;
	}

	public void setClient(Client client) {
		if (client == null) throw new IllegalArgumentException("Client is null!");
		this.client = client;
	}
	public void setActivity(Activity activity) {
		if (client == null) throw new IllegalArgumentException("Activity is null!");
		this.activity = activity;
	}
	public void setDiet(double PKratio, double fatPercentage, DietType type) 
			throws ControllerNotReadyException, IllegalArgumentException {
		if (client == null || activity == null) throw new ControllerNotReadyException("Client or activity are missing!");
		diet = new Diet(client, activity, PKratio, fatPercentage, caloriesCalculator.CalculateTotalCalories(client, activity), type);
	}
	
	
	public double CalculateBasicCalories() throws ControllerNotReadyException {
		if (client == null) throw new ControllerNotReadyException("Client is missing!");
		return caloriesCalculator.CalculateBasicCalories(client);
	}
	public double CalculateTotalCalories() throws ControllerNotReadyException {
		if (client == null || activity == null) throw new ControllerNotReadyException("Client or activity is missing!");
		return caloriesCalculator.CalculateTotalCalories(client, activity);
	}
	public double CalculateActivityCalories() throws ControllerNotReadyException {
		if (client == null || activity == null) throw new ControllerNotReadyException("Client or activity are missing!");
		return caloriesCalculator.CalculateActivityCalories(client, activity);
	}
	
	
	public Protein getProtein() throws ControllerNotReadyException {
		if (diet == null) throw new ControllerNotReadyException("Diet is missing!");
		Protein protein = new Protein();
		protein.grams = client.getWeight() * diet.getProteinKiloRatio();
		protein.kcal = protein.grams * Calories.PROTEIN.calorieValue();
		protein.percentage = protein.kcal / caloriesCalculator.CalculateTotalCalories(client, activity);
		return protein;
	}
	public Fat getFat() throws ControllerNotReadyException {
		if (diet == null) throw new ControllerNotReadyException("Diet is missing!");
		Fat fat = new Fat();
		fat.percentage = diet.getFatPercentage() / 100;
		fat.kcal = diet.getDiet() * fat.percentage;
		fat.grams = fat.kcal / Calories.FAT.calorieValue();
		return fat;
	}
	public Carbohydrate getCarbohydrate() throws ControllerNotReadyException {
		if (diet == null) throw new ControllerNotReadyException("Diet is missing!");
		Carbohydrate carbohydrate = new Carbohydrate();
		Protein protein = getProtein();
		carbohydrate.percentage = 1 - diet.getFatPercentage() / 100 - protein.percentage;
		carbohydrate.kcal = diet.getDiet() * carbohydrate.percentage;
		carbohydrate.grams = carbohydrate.kcal / Calories.CARBOHYDRATE.calorieValue();
		return carbohydrate;
	}
	
}