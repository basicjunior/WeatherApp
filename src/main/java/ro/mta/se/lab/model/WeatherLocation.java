package ro.mta.se.lab.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WeatherLocation {

    StringProperty countryCode;
    StringProperty cityName;
    StringProperty lat;
    StringProperty lon;
    StringProperty ID;


    public String getCountryCode() {
        return countryCode.get();
    }

    public StringProperty countryCodeProperty() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode.set(countryCode);
    }

    public String getCityName() {
        return cityName.get();
    }

    public StringProperty cityNameProperty() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName.set(cityName);
    }

    public String getLat() {
        return lat.get();
    }

    public StringProperty latProperty() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat.set(lat);
    }

    public String getLon() {
        return lon.get();
    }

    public StringProperty lonProperty() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon.set(lon);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public WeatherLocation(String countryCode, String cityName, String ID, String lat, String lon){

        this.countryCode = new SimpleStringProperty(countryCode);
        this.cityName = new SimpleStringProperty(cityName);
        this.ID = new SimpleStringProperty(ID);
        this.lat = new SimpleStringProperty(lat);
        this.lon = new SimpleStringProperty(lon);

    }
}
