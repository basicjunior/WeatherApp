package ro.mta.se.lab.controller;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import ro.mta.se.lab.model.Logger;
import ro.mta.se.lab.model.WeatherLocation;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JfxRunner.class)

public class WeatherControllerMock {

    WeatherController controller;

    @Mock
    Logger logger;

    ArrayList<WeatherLocation> locations = new ArrayList<WeatherLocation>();


    @BeforeClass
    static public void startTesting()
    {
        System.out.println("Start");
    }

    @Before
    public void setUp() throws Exception {
        logger = mock(Logger.class);
        controller = new WeatherController(locations);

    }

    @Test
    public void APIrequest() throws IOException {
        when(logger.appendMessage("Bucharest")).thenReturn("User searched for information on city: Bucharest");
        when(logger.appendMessage("Paris")).thenReturn("User searched for information on city: Paris");


        assertEquals("User searched for information on city: Bucharest", controller.APIrequest("Bucharest", logger));
        assertEquals("User searched for information on city: Paris", controller.APIrequest("Paris", logger));

        verify(logger,atLeastOnce()).appendMessage("Bucharest");
        verify(logger,never()).appendMessage("Chisinau");

    }
}