package ro.mta.se.lab.controller;

import org.junit.*;

import static org.junit.Assert.*;

public class WeatherControllerTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("\nBefore test");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("After test\n");
    }

    @BeforeClass
    static public void init(){
        System.out.println("Started testing, brace for impact!");
    }

    @AfterClass
    static public void _final(){
        System.out.println("Testing done!");
    }

    @Test
    public void getTempAsInt() {
        assertEquals("5.0 \u2103", WeatherController.getTempAsInt((float) 5.24));
    }

    @Test
    public void getWindSpeedAsInt() {
        assertEquals("23.0 km/h", WeatherController.getWindSpeedAsInt((float) 23.754));
    }

    @Test
    public void getHumidityAsInt() {
        assertEquals("75.0 %", WeatherController.getHumidityAsInt((float)75.292));
    }
}