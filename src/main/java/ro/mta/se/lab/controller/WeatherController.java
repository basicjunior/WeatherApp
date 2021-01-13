package ro.mta.se.lab.controller;

import com.eclipsesource.json.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ro.mta.se.lab.model.WeatherLocation;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class WeatherController {



    private ArrayList<WeatherLocation> locations;

    private ObservableList<String> city;
    private ObservableList<String> country;

    @FXML
    private ChoiceBox<String> CountryCode = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> CityName = new ChoiceBox<>();

    @FXML
    private Button search;


    @FXML
    private TextField temperature;
    @FXML
    private TextField wind;
    @FXML
    private TextField humidity;
    @FXML
    private TextField currentWeather;

    public String APIarg;
    public WeatherController(ArrayList<WeatherLocation> locations) {
        this.locations = locations;
    }

    @FXML
    private void initialize() throws IOException {

        int find = 1;

        CityName.setValue("Choose a city");
        CountryCode.setValue("Choose a Country Code");

        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < CountryCode.getItems().size(); j++)
                if (locations.get(i).getCountryCode().equals(CountryCode.getItems().get(j)))
                    find = 0;
            if (find == 1)
                CountryCode.getItems().add(locations.get(i).getCountryCode());
            find = 1;


        }

        CountryCode.setOnAction(event -> {

            CityName.getItems().clear();
            CityName.setValue("Choose city");
            //The above line is important otherwise everytime there is an action it will just keep adding more
            if (CountryCode.getValue() != null) {//This cannot be null but I added because idk what yours will look like
                for (int k = 0; k < locations.size(); k++)
                    if (locations.get(k).getCountryCode().equals(CountryCode.getValue()))
                        CityName.getItems().add(locations.get(k).getCityName());
            }

        });

        CityName.setOnHiding(event -> {
            String name = CityName.getValue();
            String name2 = CountryCode.getValue();

            if (name.equals("Choose city") || name2.equals("Choose a Country Code"))
                return;

            APIarg = name;

        });

        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    APIrequest(APIarg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void APIrequest(String cityName) throws IOException {
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric"
                + "&appid=7b3f7e6b8294c79eac5273ac52cfc673";

        StringBuilder res = new StringBuilder();
        URL url;

        url = new URL(urlString);
        URLConnection conn = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null)
            res.append(line);
        rd.close();

        JsonObject obj = Json.parse(res.toString()).asObject().get("main").asObject();
        Float temp = obj.get("temp").asFloat();
        Integer humidity2 = obj.get("humidity").asInt();
        JsonObject wObj = Json.parse(res.toString()).asObject().get("wind").asObject();
        Float windSpeed = wObj.get("speed").asFloat();
        String current = "";
        JsonArray items = Json.parse(res.toString()).asObject().get("weather").asArray();
        for (JsonValue item : items)
            current = item.asObject().getString("main", "Unknown");


        temperature.setText(temp.toString());
        wind.setText(windSpeed.toString());
        humidity.setText(humidity2.toString());
        currentWeather.setText(current);

        FileWriter fw = new FileWriter("src/main/java/ro/mta/se/lab/model/logger.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write("User searched for informations on city: " + cityName);
        bw.newLine();
        bw.close();


    }

}
