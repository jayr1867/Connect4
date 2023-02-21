import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;

public class JavaFXTemplate extends Application {


	Server connect;
	HashMap<String, Scene> sceneMap;
	BorderPane start;
	VBox startbt;
	Button sbutton, restart;
	TextField port;
	ListView<String> list1;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		list1 = new ListView<>();
		sceneMap = new HashMap<>();
		// TODO Auto-generated method stub
		primaryStage.setTitle("Server Starter");

		start = new BorderPane();
		start.setStyle("-fx-background-image: url(server.gif);");
		Text stext = new Text("Click Start");
		stext.setFill(Paint.valueOf("#1E90FF"));
		stext.setFont(Font.font("Copperplate", FontWeight.BOLD, 20));
		port = new TextField();
		sbutton = new Button("Start");
		sbutton.setStyle("-fx-background-color: white");
		Text enter_port = new Text("Port: ");
		enter_port.setFill(Paint.valueOf("#1E90FF"));
		enter_port.setFont(Font.font("Copperplate", FontWeight.BOLD, 15));
		HBox input = new HBox(enter_port, port);
		input.setAlignment(Pos.CENTER);
		input.setSpacing(5);
		startbt = new VBox(stext, input, sbutton);
		startbt.setSpacing(10);
		startbt.setPadding(new Insets(15));
		startbt.setAlignment(Pos.BOTTOM_CENTER);
		start.setCenter(startbt);

		sbutton.setOnAction(e -> {connect = new Server(data -> {
				Platform.runLater(() -> {
					list1.getItems().add(data.toString());
				});
			}, port.getText());
//				port.clear();
					primaryStage.setScene(sceneMap.get("server"));
					primaryStage.setTitle("SERVER");
		});

				


		BorderPane server = new BorderPane();
		restart = new Button("Restart");
//		VBox scene2 = new VBox(list1, restart);
//		scene2.setSpacing(50);
//		scene2.setAlignment(Pos.CENTER);
		server.setCenter(list1);
		VBox but = new VBox(restart);
		but.setAlignment(Pos.BOTTOM_CENTER);
		but.setPadding(new Insets(35, 0, 0, 0));
		server.setBottom(but);
		server.setStyle("-fx-background-color: dodgerblue");
		server.setPadding(new Insets(50));

		restart.setOnAction(e -> {port.clear();
			primaryStage.setScene(sceneMap.get("start"));
			primaryStage.setTitle("Server Starter");
			connect.end();
			stext.setText("Select different port...");
			list1.getItems().clear();
		});

		Scene serverScene = new Scene(server, 500, 500);
		Scene scene = new Scene(start, 480,360);

		sceneMap.put("server", serverScene);
		sceneMap.put("start", scene);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
