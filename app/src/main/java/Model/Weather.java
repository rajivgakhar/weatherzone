package Model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather {
    private String city;

    public String getCity() {
        return city;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getDate() {
        long unixSeconds = Long.parseLong(this.date);
        Date date = new java.util.Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("E, MMM dd");
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

    public String getTime() {
        long unixSeconds = Long.parseLong(this.date);
        Date date = new java.util.Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getDay() {
        long unixSeconds = Long.parseLong(this.date);
        Date date = new java.util.Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("E");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getDateV2() {
        long unixSeconds = Long.parseLong(this.date);
        Date date = new java.util.Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getMaxTemp() {
        int tempformat = (int) (Double.parseDouble(maxTemp) - 273.15);
        return String.valueOf(tempformat);
    }

    public Weather(String date, String temp, String icon) {
        this.date = date;
        this.temp = temp;
        this.icon = icon;
    }

    public Weather(String date, String temp, String icon, String lng, String lat) {
        this.date = date;
        this.temp = temp;
        this.icon = icon;
        this.lng = lng;
        this.lat = lat;
    }
    public Weather(String date, String temp, String icon,String descrip, String humid, String pressure,String windSpeed) {
        this.date = date;
        this.temp = temp;
        this.icon = icon;
        this.description=descrip;
        this.humidity = humid;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
    }

    public String getMinTemp() {

        int tempformat = (int) (Double.parseDouble(minTemp) - 273.15);
        return String.valueOf(tempformat);
    }

    public String getTemp() {
        int tempformat = (int) (Double.parseDouble(temp) - 273.15);
        return String.valueOf(tempformat);
    }

    public String getPressure() {
        return pressure;
    }

    public String getVisibility() {
        int distance = Integer.parseInt(visibility) / 1000;
        return String.valueOf(distance);
    }

    public String getCountry() {
        return country;
    }

    public String getSunrise() {
        long unixSeconds = Long.parseLong(this.sunrise);
        Date date = new java.util.Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getSunset() {
        long unixSeconds = Long.parseLong(this.sunset);
        Date date = new java.util.Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    private String lng;
    private String lat;
    private String date;
    private String maxTemp, minTemp, temp, pressure, humidity, main, description, icon, windSpeed, visibility, country, sunrise, sunset;

    public Weather() {
    }

    public Weather(String city, String lng, String lat, String date, String maxTemp, String minTemp, String temp, String pressure, String humidity, String main, String description, String icon, String windSpeed, String visibility, String country, String sunrise, String sunset) {
        this.city = city;
        this.lng = lng;
        this.lat = lat;
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.windSpeed = windSpeed;
        this.visibility = visibility;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Weather(String city, String lng, String lat, String date, String maxTemp, String minTemp, String temp, String pressure, String humidity, String main, String description, String icon, String windSpeed) {
        this.city = city;
        this.lng = lng;
        this.lat = lat;
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.windSpeed = windSpeed;
    }
}
