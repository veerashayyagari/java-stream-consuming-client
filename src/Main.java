import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Main {
   // private static String apiKey = "";

    public static void main(String[] args) {
        try {
        HttpClient client = HttpClient.newBuilder()
                .sslContext(getSslContextWithSkippedValidation()) // by pass SSL validation on localhost, don't do this in prod
                .version(HttpClient.Version.HTTP_2)
                .build();
        Gson gson = new Gson();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://localhost:7094/WeatherForecast"))
                .build();


            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            JsonReader reader = new JsonReader(new InputStreamReader(response.body()));
            reader.beginArray();

            while(reader.hasNext()) {
                WeatherForecast forecast = gson.fromJson(reader, WeatherForecast.class);
                System.out.println(forecast);
                System.out.printf("--- Streaming Weather Forecast: %s --- \n", LocalDateTime.now());
            }

            reader.endArray();
            reader.close();
        } catch (IOException | InterruptedException | NoSuchAlgorithmException | KeyManagementException e){
            e.printStackTrace();
        }
    }

    private  static SSLContext  getSslContextWithSkippedValidation() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        return  sslContext;
    }
}

