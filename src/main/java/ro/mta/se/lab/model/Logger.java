package ro.mta.se.lab.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class created for future editing
 */

public class Logger {
    private String message;

    public String appendMessage(String cityName) throws IOException {
        FileWriter fw = new FileWriter("src/main/java/ro/mta/se/lab/model/logger.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(this.message + cityName);
        bw.newLine();
        bw.close();

        return (this.message + cityName);
}

    public Logger() {
        this.message = "User searched for information on city: ";
    }
}
