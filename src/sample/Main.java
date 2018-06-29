package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;

import static com.ea.async.Async.await;

public class Main extends Application {



        String getData() throws IOException {
            URL yahoo = new URL("https://opcb6awum8.execute-api.us-east-1.amazonaws.com/prod?postId=*");
            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            String res = "";
            while ((inputLine = in.readLine()) != null)
                res+=inputLine;
            in.close();
            System.out.println(res);
        return res;
        }

    @Override
    public void start(Stage primaryStage) throws Exception{
        JSONArray arr = new JSONArray(getData());
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox(20);
        borderPane.setCenter(vBox);
        primaryStage.setTitle("Aarus");
        primaryStage.setScene(new Scene(borderPane, 800, 640));
        ListView<HBox> listView = new ListView<>();
        for(int i=0;i<arr.length();i++)
        {

            HBox hb = new HBox();
            Node voice = new Text(arr.getJSONObject(i).getString("voice"));
            Node text = new Text(arr.getJSONObject(i).getString("text"));
            Node btn = new Button("Play");
            EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            };
//            btn.setOnMouseClicked();
            hb.getChildren().add(voice);
            hb.getChildren().add(text);
            hb.getChildren().add(btn);
            listView.getItems().add(hb);
        }
        vBox.getChildren().add(listView);
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        launch(args);

    }
}
