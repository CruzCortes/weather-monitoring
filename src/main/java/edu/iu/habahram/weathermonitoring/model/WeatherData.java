package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class WeatherData implements Subject{
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        this.observers = new ArrayList<>();
        startMeasuring();
    }

    private void startMeasuring() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            // Generate realistic temperature, humidity, and pressure values.
            // For example:
            float newTemp = getRandomTemperature();
            float newHumidity = getRandomHumidity();
            float newPressure = getRandomPressure();

            setMeasurements(newTemp, newHumidity, newPressure);
        }, 0, 3, TimeUnit.SECONDS);
    }

    private float getRandomTemperature() {
        // Return a realistic temperature value.
        // For example, between -10.0°C (14°F) and 40.0°C (104°F):
        return -10.0f + (float)(Math.random() * 50.0);
    }

    private float getRandomHumidity() {
        // Return a realistic humidity value.
        // For example, between 0% and 100%:
        return (float)(Math.random() * 100.0);
    }

    private float getRandomPressure() {
        // Return a realistic pressure value.
        // For example, between 980 hPa and 1050 hPa:
        return 980.0f + (float)(Math.random() * 70.0);
    }

    Runnable sensorsChanged = () -> {
        setMeasurements((float) Math.random(),
                (float) Math.random(),
                (float) Math.random());
    };

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
       int i = observers.indexOf(observer);
       if(i >= 0) {
           observers.remove(observer);
       }
    }

    @Override
    public void notifyObservers() {
       for(Observer observer : observers) {
           observer.update(temperature, humidity, pressure);
       }
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    public void measurementChanged() {
        notifyObservers();
    }

    public  void setMeasurements(float temperature,
                                 float humidity,
                                 float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementChanged();
    }
}
