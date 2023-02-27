package ru.yandex.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private String url;
    private final String API_TOKEN;

    public KVTaskClient(String url) {
        this.url = url;
        this.API_TOKEN = getApiToken();
    }


    private String getApiToken() {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8078/register");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(".getAPI_TOKEN() -> Ваш токен: " + response.body());
            return response.body();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void put(String key, String json) {
        //POST /save/<ключ>?API_TOKEN=
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create(url + "/save/" + key + "/?API_TOKEN=" + API_TOKEN);

        try {
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(url1)
                    .POST(body)
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    public String load(String key) {
        //GET /load/<ключ>?API_TOKEN=
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create(url + "/load/" + key + "/?API_TOKEN=" + API_TOKEN);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url1)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}






