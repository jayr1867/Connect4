import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class JavaFXTemplate extends Application {

	HashMap<String, Scene> sceneMap;
	Button startb, sendb;
	VBox startbox, sendbox;
	BorderPane start, second, gameBoard;
	ListView<String> pStats = new ListView<>();
	GridPane check;

	String winnerT;
	Text win;

	Client clientConn = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Project #3");
		sceneMap = new HashMap<>();

		start = new BorderPane();
		start.setStyle("-fx-background-image: url(client.gif);");
		Text click = new Text("Welcome to Connect4!");
		click.setFont(Font.font("CopperPlate", FontWeight.BOLD, 20));
		click.setFill(Paint.valueOf("#ffffff"));
		Text ipadd = new Text("IP: ");
		Text porrt = new Text("Port: ");
		ipadd.setFont(Font.font("CopperPlate", FontWeight.BOLD, 15));
		ipadd.setFill(Paint.valueOf("#ffffff"));
		porrt.setFont(Font.font("CopperPlate", FontWeight.BOLD, 15));
		porrt.setFill(Paint.valueOf("#ffffff"));
		TextField ip = new TextField();
		ip.setPromptText("IP Address");
		ip.setFocusTraversable(false);
		TextField por = new TextField();
		por.setPromptText("Port Number");
		por.setFocusTraversable(false);
		GridPane info = new GridPane();
		info.add(ipadd, 0, 0);
		info.add(ip, 1, 0);
		info.add(porrt, 0, 1);
		info.add(por, 1, 1);

		info.setAlignment(Pos.CENTER);

		startb = new Button("Start");
		startb.setFocusTraversable(false);
		startbox = new VBox(click, info, startb);
		startbox.setSpacing(10);
		startbox.setAlignment(Pos.CENTER);
		start.setCenter(startbox);


		startb.setOnAction(e -> {

			clientConn = new Client(data -> {
				Platform.runLater(() -> {pStats.getItems().clear();
					pStats.getItems().add(data.toString());
					BorderPane checker = (BorderPane) primaryStage.getScene().getRoot();
					GridPane c = (GridPane) checker.getCenter();


					if (!clientConn.clientInfo.first) {
						GameButton test = getB(primaryStage, clientConn.clientInfo.pPlays);

						if (clientConn.clientInfo.p1Turn) {
							test.setStyle("-fx-pref-width: 50px; -fx-pref-height: 50px; " +
									"-fx-background-color: MediumAquaMarine");
//
							test.setDisable(true);

							test.used = true;
							if (Objects.equals(primaryStage.getTitle(), "PLAYER 2")) {
								for (Node n:c.getChildren()) {
									if (!((GameButton) n).used) {
										n.setDisable(true);
									}
								}
							} else {
								for (Node n:c.getChildren()) {
									if (!((GameButton) n).used) {
										n.setDisable(false);
									}
								}
							}
						}

						try {
							if (clientConn.clientInfo.p2Turn) {
								test.setStyle("-fx-pref-width: 50px; -fx-pref-height: 50px; " +
										"-fx-background-color: Crimson");
								test.setDisable(true);
								test.used = true;

								if (Objects.equals(primaryStage.getTitle(), "PLAYER 1")) {
									for (Node n : c.getChildren()) {
										if (!((GameButton) n).used) {
											n.setDisable(true);
										}
									}
								} else {
									for (Node n : c.getChildren()) {
										if (!((GameButton) n).used) {
											n.setDisable(false);
										}
									}
								}
							}
						} catch(NullPointerException npe) {}

						if (clientConn.clientInfo.wP1 || clientConn.clientInfo.wP2) {
							test.setOnAction(el -> {primaryStage.setScene(sceneMap.get("Res"));});
							clientConn.send(clientConn.clientInfo);
						}
					}




					if (!clientConn.clientInfo.has2players) {
						c.setDisable(true);
					}

					if (clientConn.clientInfo.has2players && clientConn.clientInfo.p1Turn) {
						c.setDisable(false);
					}

				});
				}, ip.getText(), por.getText());
				clientConn.start();



					primaryStage.setScene(sceneMap.get("P2"));
					if (clientConn.clientInfo.has2players) {
						primaryStage.setTitle("PLAYER 2");
					} else {
						primaryStage.setTitle("PLAYER 1");
					}

					clientConn.send(clientConn.clientInfo);

		});


		BorderPane result = new BorderPane();

		Button restart = new Button("Restart");
		Button quit = new Button("Quit");
		quit.setOnAction(e -> {System.exit(0);});

		HBox buts = new HBox(restart, quit);
		buts.setSpacing(10);
		buts.setAlignment(Pos.CENTER);
		win = new Text("Winner?");
		VBox all = new VBox(win, buts);
		all.setAlignment(Pos.CENTER);
		all.setSpacing(10);

		all.setStyle("-fx-background-image: url(bg1.jpeg)");

		result.setCenter(all);






		sceneMap.put("P2", gameBoard());
		sceneMap.put("Res", new Scene(result, 700, 700));


	     Scene scene = new Scene(start, 500,500);
			primaryStage.setScene(scene);
			primaryStage.show();
	} // end of Start.


	public GameButton getB (Stage stage, String co_ord) {
		BorderPane bp = (BorderPane) stage.getScene().getRoot();
		GridPane gp = (GridPane) bp.getCenter();
		GameButton ret = null;
		ObservableList<Node> buts = gp.getChildren();
		String deli = "[(, )]+";
		String[] out = co_ord.split(deli);
		int i = 0;
		int row = 0;
		int col = 0;

		try {
			row = Integer.parseInt(out[1]);
			col = Integer.parseInt(out[2]);
			int j = 0;
			while ( j < buts.size()) {
				ret = (GameButton) buts.get(j);
				if (ret.row == row && ret.col == col) {
					int y = 6 - row;
					while (y > 0 && ret.col == col) {
						GameButton ter = (GameButton) buts.get(j);
						if (!ter.used) {
							ret = ter;
							y = 6 - ret.row;
							j++;
						} else if (ter.used) {
							break;
						}
					}
					break;
				}
				j++;
			}

		} catch(NumberFormatException n) {
			return null;
		}

		clientConn.clientInfo.pPlays = "(" + ret.row + ", " + ret.row + ")";

		return ret;
	}

	public GridPane setGameBoard() {
		GridPane temp = new GridPane();
		temp.setAlignment(Pos.CENTER);
		temp.setHgap(10);
		temp.setVgap(10);
		GameButton temp1;
		for (int i = 1; i < 8; i++) {
			for (int j = 1; j < 7; j++) {
				temp1 = new GameButton(i, j);
				temp1.setStyle("-fx-pref-width: 50px; -fx-pref-height: 50px; " +
						"-fx-background-color: AliceBlue");
				temp.add(temp1, i, j);
				temp1.setOnAction(connect4Button);
			}
		}

		return temp;
	}


	public Scene gameBoard() {
		gameBoard = new BorderPane();
		check = setGameBoard();
		check.setAlignment(Pos.CENTER_LEFT);
		check.setPadding(new Insets(0,0,0,70));
		gameBoard.setCenter(check);
		gameBoard.setPrefSize(500, 500);
		gameBoard.setStyle("-fx-background-image: url(bg1.jpeg);");


		pStats.setPrefHeight(100);
		GridPane players = new GridPane();
		Text p1 = new Text("Player One: ");
		p1.setFont(Font.font("CopperPlate", FontWeight.BOLD, 20));
		Text p2 = new Text("Player Two: ");
		p2.setFont(Font.font("CopperPlate", FontWeight.BOLD, 20));
		p1.setFill(Paint.valueOf("#FDF5E6"));
		p2.setFill(Paint.valueOf("#FDF5E6"));
		Button p1b = new Button();
		p1b.setDisable(true);
		p1b.setStyle("-fx-background-color: Crimson; -fx-background-radius: 50px; -fx-pref-height: 50; -fx-pref-width: 50");
		Button p2b = new Button();
		p2b.setDisable(true);
		p2b.setStyle("-fx-background-color: MediumAquaMarine; -fx-background-radius: 50px; -fx-pref-height: 50; -fx-pref-width: 50");
		players.add(p1, 0, 0);
		players.add(p1b, 1, 0);
		players.add(p2, 0, 1);
		players.add(p2b, 1, 1);
		players.setAlignment(Pos.CENTER);
		players.setVgap(10);
		VBox stats = new VBox(pStats, players);
		stats.setSpacing(10);
		stats.setAlignment(Pos.CENTER_RIGHT);
		stats.setPadding(new Insets(0, 70, 0, 35));
		gameBoard.setRight(stats);


		return new Scene(gameBoard, 800, 500);
	}

	public Scene winner() {
		BorderPane result = new BorderPane();
		Text win = new Text(clientConn.clientInfo.pPlays);
		Button restart = new Button("Restart");
		Button quit = new Button("Quit");

		HBox buts = new HBox(restart, quit);
		buts.setSpacing(10);
		buts.setAlignment(Pos.CENTER);
		VBox all = new VBox(win, buts);
		all.setAlignment(Pos.CENTER);
		all.setSpacing(10);

		all.setStyle("-fx-background-image: url(bg1.jpeg)");

		result.setCenter(all);

		return new Scene(all, 700, 700);
	}

	EventHandler<ActionEvent> connect4Button = new EventHandler<>() {
		@Override
		public void handle(ActionEvent actionEvent) {
			GameButton temp = (GameButton)actionEvent.getSource();
			String cord = "(" + temp.row.toString() +
					", " + temp.col.toString() + ")";

			GameButton temp1 = getB((Stage) temp.getScene().getWindow(), cord);
			clientConn.clientInfo.pPlays = "(" + temp1.row.toString() +
					", " + temp1.col.toString() + ")";

			if (clientConn.clientInfo.wP1 || clientConn.clientInfo.wP2) {
				Stage sg = (Stage) temp.getScene().getWindow();
				win .setText(clientConn.clientInfo.pPlays);
				sg.setScene(sceneMap.get("Res"));
				sg.setTitle("Winner");
			}

			clientConn.send(clientConn.clientInfo);
		}
	};
}





