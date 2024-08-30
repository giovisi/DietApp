package diet.ui;

import diet.controller.Controller;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class MainPane extends BorderPane {
	
	private Controller controller;
	
	public MainPane(Controller controller) {
		this.controller = controller;
		
		VBox leftBox = new VBox(10);
		Text title = new Text();
		title.setText("Diet App");
		Text description = new Text("Please enter your data:");
		leftBox.getChildren().addAll(title, description);
		Label label = new Label();		
		HBox miniBox = new HBox();
		
		label.setText("Name: ");
		TextField name = new TextField();
		miniBox.getChildren().addAll(label, name);
		leftBox.getChildren().add(miniBox);
		
		
		label = new Label("Surname: ");
		TextField surname = new TextField();
		miniBox = new HBox(label, surname);
		leftBox.getChildren().add(miniBox);
		
		label = new Label("Age");
		
		
		
		this.setLeft(leftBox);

	}

	private void myHandler(Object obj) {
		
		
	}	
}
