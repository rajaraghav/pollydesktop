package sample;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Controller {

    @FXML
    private BorderPane border;
    HashMap<String,String> map = new LinkedHashMap<>();
    ComboBox<String> dropDownVoices = initCombo();
    ListView<HBox> listView = new ListView<>();
    TextArea textBox;


    void refreshScene() throws InterruptedException, IOException {
        Thread.sleep(5000);
        System.out.println("Awake now fetch");
        loadData();
    }
    void playSound(String uri)
    {
        System.out.println("clicked"+uri);
        Media sound = new Media(uri);
        MediaPlayer mp =new MediaPlayer(sound);
        mp.play();
    }
    public void  performPostCall(String requestURL,
                                 HashMap<String, String> postDataParams) {

        URL url;
        try {
            url = new URL(requestURL);
            JSONObject data = getPostDataString(postDataParams);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            System.out.println(data);
            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            writer.write(data.toString().getBytes());

            Integer responseCode = conn.getResponseCode();
            BufferedReader bufferedReader;

            // Creates a reader buffer
            if (responseCode > 199 && responseCode < 300) {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            // To receive the response
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();

            // Prints the response
            System.out.println(content.toString());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private JSONObject getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String, String> entry : params.entrySet()){
            jsonObject.put(entry.getKey(),entry.getValue());
        }

        return jsonObject;
    }
    String getData() throws IOException {
        URL yahoo = new URL("https://opcb6awum8.execute-api.us-east-1.amazonaws.com/prod?postId=*");
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;
        String res="";
        while ((inputLine = in.readLine()) != null)
            res+=inputLine;
        in.close();
        System.out.println(res);
        return res;
    }
    public EventHandler<Event> createSolButtonHandler(String uri)
    {
        EventHandler btnClickHandler = new EventHandler<Event>(){

            @Override
            public void handle(Event event) {
                playSound(uri);
            }
        };
        return btnClickHandler;
    }
    public EventHandler<Event> quitButtonHandler()
    {
        EventHandler btnClickHandler = new EventHandler<Event>(){

            @Override
            public void handle(Event event) {
                Platform.exit();
            }

        };
        return btnClickHandler;
    }
    public EventHandler<Event> postButtonHandler()
    {
        EventHandler btnClickHandler = new EventHandler<Event>(){

            @Override
            public void handle(Event event) {
                HashMap<String,String> tem = new HashMap<>();
                String voice  = dropDownVoices.getValue();
                String text = textBox.getText();
                tem.put("voice",map.get(voice));
                tem.put("text",text);
                System.out.println(voice+" "+text);
                performPostCall("https://opcb6awum8.execute-api.us-east-1.amazonaws.com/prod",tem);
                try {
                    refreshScene();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        return btnClickHandler;
    }
    public void initMap()
    {
        map.put("Ivy [English - American]","Ivy");
        map.put("Joanna [English - American]","Joanna");
        map.put("Joey [English - American]","Joey");
        map.put("Justin [English - American]","Justin");
        map.put("Kendra [English - American]","Kendra");
        map.put("Kimberly [English - American]","Kimberly");
        map.put("Salli [English - American]","Salli");
        map.put("Nicole [English - Australian]","Nicole");
        map.put("Russell [English - Australian]","Russell");
        map.put("Emma [English - British]","Emma");
        map.put("Brian [English - British]","Brian");
        map.put("Amy [English - British]","Amy");
        map.put("Raveena [English - Indian]","Raveena");
        map.put("Geraint [English - Welsh]","Geraint");
        map.put("Ricardo [Brazilian Portuguese]","Ricardo");
        map.put("Vitoria [Brazilian Portuguese]","Vitoria");
        map.put("Lotte [Dutch]","Lotte");
        map.put("Ruben [Dutch]","Ruben");
        map.put("Mathieu [French]","Mathieu");
        map.put("Celine [French]","Celine");
        map.put("Chantal [Canadian French]","Chantal");
        map.put("Marlene [German]","Marlene");
        map.put("Dora [Icelandic]","Dora");
        map.put("Karl [Icelandic]","Karl");
        map.put("Carla [Italian]","Carla");
        map.put("Giorgio [Italian]","Giorgio");
        map.put("Mizuki [Japanese]","Mizuki");
        map.put("Liv [Norwegian]","Liv");
        map.put("Maja [Polish]","Maja");
        map.put("Jan [Polish]","Jan");
        map.put("Ewa [Polish]","Ewa");
        map.put("Cristiano [Portuquese]","Cristiano");
        map.put("Ines [Portuquese]","Ines");
        map.put("Carmen [Romanian]","Carmen");
        map.put("Maxim [Russian]","Maxim");
        map.put("Tatyana [Russian]","Tatyana");
        map.put("Enrique [Spanish]","Enrique");
        map.put("Penelope [US Spanish]","Penelope");
        map.put("Miguel [US Spanish]","Miguel");
        map.put("Conchita [Castilian Spanish]","Conchita");
        map.put("Astrid [Swedish]","Astrid");
        map.put("Filiz [Turkish]","Filiz");
        map.put("Gwyneth [Welsh]","Gwyneth");

    };
    ComboBox<String> initCombo()
    {
        ComboBox<String > cb = new ComboBox<>();
        for(Map.Entry<String,String> en:map.entrySet())
        {
            cb.getItems().add(en.getKey());
        }
        cb.setPromptText("Select Voice");
        return cb;
    }
    String clipText(String s)
    {
        if(s.length()>40)
        {
            s = s.substring(0,40)+"...";
        }
        return "\""+s+"\"";
    }
    public void loadData() throws IOException {
        JSONArray arr = new JSONArray(getData());
        listView.getItems().clear();
        for(int i=0;i<arr.length();i++)
        {
            final JSONObject jsonObject= arr.getJSONObject(i);
            HBox hb = new HBox();
            hb.setSpacing(150);
            hb.setAlignment(Pos.CENTER);
            Text voice = new Text(jsonObject.getString("voice"));
            voice.getStyleClass().add("white_text");
            voice.setTextAlignment(TextAlignment.LEFT);
            Text text = new Text(clipText(jsonObject.getString("text")));
            text.getStyleClass().add("white_text");
            Node btn = new Button("Play");
            btn.getStyleClass().add("playButton");
            btn.addEventHandler(MouseEvent.MOUSE_CLICKED, createSolButtonHandler(jsonObject.getString("url")));
            hb.getChildren().add(voice);
            hb.getChildren().add(text);
            hb.getChildren().add(btn);

            listView.getItems().add(hb);
        }
        listView.refresh();
    }
    public void initialize() throws IOException {
        VBox vBox = new VBox(20);
        vBox.getStyleClass().add("vbox");
        border.setCenter(vBox);
        loadData();
        vBox.getChildren().add(listView);
        HBox postBox = new HBox();
        textBox = new TextArea();
        textBox.getStyleClass().add("textField");
        textBox.setPromptText("Enter Text");
        initMap();
        dropDownVoices = initCombo();
        Node postButton = new Button("Say It");
        VBox quitPostBox = new VBox(20);
        postButton.getStyleClass().add("postButton");
        Button quitButton = new Button("quit");
        quitPostBox.getChildren().add(postButton);
        quitPostBox.getChildren().add(quitButton);
        postBox.getChildren().add(dropDownVoices);
        postBox.getChildren().add(textBox);
        postBox.getChildren().add(quitPostBox);
        postBox.setSpacing(40);

        postButton.addEventHandler(MouseEvent.MOUSE_CLICKED,postButtonHandler());
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED,quitButtonHandler());
        vBox.getChildren().add(postBox);
        }


}


