package ro.mta.se.lab.controller;

import com.eclipsesource.json.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ro.mta.se.lab.model.Logger;
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

    public String API_arg;

    public WeatherController(ArrayList<WeatherLocation> locations) {
        this.locations = locations;
    }

    @FXML
    private void initialize() throws IOException {

        int find = 1;
        Logger log = new Logger();

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

            API_arg = name;

        });

        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    APIrequest(API_arg, log);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static public String getTempAsInt(Float temp){
        String intTemp = String.valueOf(temp - temp%1);
        //DecimalFormat df = new DecimalFormat("0.#");
        return (intTemp  + " \u2103");
    }

    static public String getWindSpeedAsInt(Float windSpeed){
        String intWind = String.valueOf(windSpeed - windSpeed%1);

        return (intWind + " km/h");
    }

    static public String getHumidityAsInt(Float humidity){
        String intHumidity = String.valueOf(humidity - humidity%1);

        return (intHumidity + " %");
    }

    public void setValues(Float a, Float b, Float c, String d){
        temperature.setText(getTempAsInt(a));
        wind.setText(getWindSpeedAsInt(b));
        humidity.setText(getHumidityAsInt(c));
        currentWeather.setText(d);
    }

    public String APIrequest(String cityName, Logger log) throws IOException {
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
        Float humidity2 = obj.get("humidity").asFloat();
        JsonObject wObj = Json.parse(res.toString()).asObject().get("wind").asObject();
        Float windSpeed = wObj.get("speed").asFloat();
        String current = "";
        JsonArray items = Json.parse(res.toString()).asObject().get("weather").asArray();
        for (JsonValue item : items)
            current = item.asObject().getString("main", "Unknown");

        setValues(temp ,windSpeed,humidity2, current);

        String test = log.appendMessage(cityName);

        return test;
    }

}
