package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main extends Application {


    String getData() throws IOException {
        URL yahoo = new URL("https://opcb6awum8.execute-api.us-east-1.amazonaws.com/prod?postId=*");
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine="";

        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
        return inputLine;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        String res = getData();
        System.out.println(res);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        launch(args);

    }
}
