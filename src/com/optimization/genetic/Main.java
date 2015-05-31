package com.optimization.genetic;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	 
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = (AnchorPane)FXMLLoader.load(getClass().getResource("myView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Genetik Algoritma");
			Image ico = new Image("http://www.kathiresanlab.org/wp-content/uploads/2014/04/icon-nb-biology.png");
			primaryStage.getIcons().add(ico);   
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
}
