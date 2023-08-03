public class WeatherForecast {
    private final String date;
    private final int temperatureF;
    private final int temperatureC;
    private final String summary;

    public WeatherForecast(String date, int temperatureF, int temperatureC, String summary) {
        this.date = date;
        this.temperatureC = temperatureC;
        this.temperatureF = temperatureF;
        this.summary = summary;
    }

    public String getDate(){
        return  this.date;
    }

    public int getTemperatureF(){
        return  this.temperatureF;
    }

    public int getTemperatureC(){
        return  this.temperatureC;
    }

    public String getSummary(){
        return  this.summary;
    }

    public String toString(){
        return  summary + " On " + date + ":" + " Temperature(Celsius): " + temperatureC + " Temperature(Fahrenheit): " + temperatureF;
    }
}
