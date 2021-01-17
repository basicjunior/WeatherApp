package ro.mta.se.lab.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ro.mta.se.lab.controller.WeatherController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mta.se.lab.model.WeatherLocation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @initializeLocations() is used to populate the ChoiceBoxes
 *
 *
 */

public class MainApp extends Application {
    private ObservableList<String> city = FXCollections.observableArrayList();
    private ObservableList<String> country = FXCollections.observableArrayList();

    ArrayList<WeatherLocation> locations = new ArrayList<>();

    public static void main(String[] args)
    {
        launch(args);
    }

    public void initializeLocations() throws IOException{
        BufferedReader buffer=new BufferedReader(new FileReader("src/main/java/ro/mta/se/lab/model/input.txt"));
        String[] words=new String[100];
        String line="";
        line= buffer.readLine();
        while((line=buffer.readLine())!=null)
        {

            words=line.split("[\t ]+");
            WeatherLocation location = new WeatherLocation(words[4],words[1],words[0],words[2],words[3]);
            locations.add(location);
            city.add(words[1]);
            country.add(words[4]);
        }
        buffer.close();
    }


    public void start(Stage primaryStage) throws IOException{
        initializeLocations();
        FXMLLoader loader = new FXMLLoader();

        try{
            loader.setLocation(this.getClass().getResource("/view/WeatherView.fxml"));
            loader.setController(new WeatherController(locations));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
