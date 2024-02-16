package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

@Component
public class StatisticsDisplay implements Observer, DisplayElement {
    private String id;
    private String name;
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;
    private float tempSum = 0.0f;
    private int numReadings = 0;

    public StatisticsDisplay(WeatherData weatherData) {
        this.id = "statistics";
        this.name = "Weather Stats";
        subscribe(weatherData);
    }
    @Override
    public void update(float temp, float humidity, float pressure) {
        tempSum += temp;
        numReadings++;

        if (temp > maxTemp) {
            maxTemp = temp;
        }

        if (temp < minTemp) {
            minTemp = temp;
        }

        display();
    }

    public String display() {
        float avgTemp = numReadings > 0 ? tempSum / numReadings : 0;

        return "<html><body>" +
                "<h1>Weather Statistics</h1>" +
                String.format("<p>Average Temperature: %.2f</p>", avgTemp) +
                String.format("<p>Minimum Temperature: %.2f</p>", minTemp) +
                String.format("<p>Maximum Temperature: %.2f</p>", maxTemp) +
                "</body></html>";
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    // Method to subscribe to WeatherData, if not done in the constructor
    public void subscribe(Subject weatherData) {
        weatherData.registerObserver(this);
    }

    // Method to unsubscribe from WeatherData
    public void unsubscribe(Subject weatherData) {
        weatherData.removeObserver(this);
    }
}
