package ro.mta.se.lab.controller;

import com.eclipsesource.json.*;
import com.sun.scenario.effect.impl.sw.java.JSWInvertMaskPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ro.mta.se.lab.model.WeatherLocation;
import javafx.scene.control.Label;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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
    private TextField temperature;
    @FXML
    private TextField wind;
    @FXML
    private TextField humidity;
    @FXML
    private TextField currentWeather;

    public WeatherController(ArrayList<WeatherLocation> locations) {
        this.locations = locations;
    }

    @FXML
    private void initialize() throws IOException {

        int find = 1;

        CityName.setValue("Choose a city");
        CountryCode.setValue("Choose a Country Code");

        for(int i = 0; i < locations.size(); i++)
        {
                for(int j = 0; j < CountryCode.getItems().size(); j++)
                    if(locations.get(i).getCountryCode().equals(CountryCode.getItems().get(j)))
                        find = 0;
                    if(find == 1)
                        CountryCode.getItems().add(locations.get(i).getCountryCode());
                find = 1;


        }

        CountryCode.setOnAction(event -> {

            CityName.getItems().clear();
            CityName.setValue("Choose city");
            //The above line is important otherwise everytime there is an action it will just keep adding more
            if (CountryCode.getValue() != null) {//This cannot be null but I added because idk what yours will look like
                for(int k = 0; k < locations.size(); k++)
                    if(locations.get(k).getCountryCode().equals(CountryCode.getValue()))
                        CityName.getItems().add(locations.get(k).getCityName());
            }

        });

        CityName.setOnHiding(event -> {
            String name = CityName.getValue();
            //System.out.println(name);
            try {
                APIrequest(name);
            } catch (IOException e) {
                e.printStackTrace();
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
        Integer humidity = obj.get("humidity").asInt();
        JsonObject wObj = Json.parse(res.toString()).asObject().get("wind").asObject();
        Float windSpeed = wObj.get("speed").asFloat();
        String current = "";
        JsonArray items = Json.parse(res.toString()).asObject().get("weather").asArray();
        for (JsonValue item : items)
            current = item.asObject().getString("main", "Unknown");

        temperature.setText(temp.toString());


        System.out.println(urlString);
        System.out.println("humidity" + humidity);
        System.out.println("temperature" + temp);
        System.out.println("windspeed " + windSpeed);
        System.out.println("currentWeather " + current);

    }

}
