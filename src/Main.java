import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Main {
   private final static String apiKey = "<your hl api key>";

    public static void main(String[] args) {

        ChatRequest req = new ChatRequest("ELI5 GenAI", "OpenAi", "gpt-3.5-turbo-16k");

        try {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(req);

        HttpRequest request = HttpRequest.newBuilder()
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .header("Content-Type", "application/json")
                .header("x-hl-api-key", apiKey)
                .uri(URI.create("https://infinity-hl.azurewebsites.net/app/conversations/5e66fa1d-c95b-4337-8bc6-52f5eff58a6b/continue"))
                .build();


            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            JsonReader reader = new JsonReader(new InputStreamReader(response.body()));
            reader.beginArray();

            while(reader.hasNext()) {
                JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
                System.out.println(obj);
                System.out.printf("--- Streaming Chat Response: %s --- \n", LocalDateTime.now());
            }

            reader.endArray();
            reader.close();
        } catch (IOException | InterruptedException e){
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

