package sample;

import javafx.application.Application;

import javafx.event.Event;
import javafx.event.EventHandler;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.net.www.http.HttpClient;

import java.io.*;

import org.apache.http.*;

import javax.net.ssl.HttpsURLConnection;
import java.net.*;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Alexa Polly");
        primaryStage.setScene(new Scene(root, 800, 640));
        primaryStage.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
