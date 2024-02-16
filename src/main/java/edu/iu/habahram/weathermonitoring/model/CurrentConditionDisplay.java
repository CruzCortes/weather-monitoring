package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

@Component
public class CurrentConditionDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private float pressure;

    private Subject weatherData;

    public CurrentConditionDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public String display() {
        return String.format("<div style=\"background-image: url(/images/sky.webp); " +
                "height: 400px; width: 647.2px; display:flex;flex-wrap:wrap;" +
                "justify-content:center;align-content:center;\">" +
                "<section>" +
                "<label>Temperature: %.2f</label><br />" +
                "<label>Humidity: %.2f</label><br />" +
                "<label>Pressure: %.2f</label>" +
                "</section></div>", temperature, humidity, pressure);
    }

    @Override
    public String name() {
        return "Current Condition Display";
    }

    @Override
    public String id() {
        return "current-condition";
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }
}
