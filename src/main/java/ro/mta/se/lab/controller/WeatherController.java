package ro.mta.se.lab.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import ro.mta.se.lab.model.WeatherLocation;
import javafx.scene.control.Label;
import java.awt.*;
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
    private Label temperature;
    @FXML
    private Label lat;
    @FXML
    private Label lon;
    @FXML
    private Label currentWeather;

    public WeatherController(ArrayList<WeatherLocation> locations) {
        this.locations = locations;
    }

    @FXML
    private void initialize(){

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

    }

}
