package ibf2023.ssf.day17workshop.model;

import java.io.Serializable;

public class Weather implements Serializable {
    private String city;
    private String icon;
    private String temperature;

    public Weather(){};

    public Weather(String city, String icon) {
        this.city = city;
        this.icon = icon;
    }

    public Weather(String city, String icon, String temperature) {
        this.city = city;
        this.icon = icon;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Weather [city=" + city + ", icon=" + icon + ", temperature=" + temperature + "]";
    }
}
