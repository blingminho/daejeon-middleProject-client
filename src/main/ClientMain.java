
package main;

import java.io.IOException;

import controller.main.LoginPage;
import controller.team.ChatMem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Client Start Main
 * @author Jun
 *
 */
public class ClientMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/main/LoginPage.fxml"));
			Parent root = loader.load();
			
			LoginPage loginPage = loader.getController();
			loginPage.setPrimaryStage(primaryStage);
			
			
			Scene scene = new Scene(root);
			primaryStage.setTitle("TravelMaker 시작");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
