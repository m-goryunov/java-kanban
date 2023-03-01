package ru.yandex.server;

import ru.yandex.exception.BadRequestException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final String API_TOKEN;
    private final String url;


    public KVTaskClient(String url) {
        this.url = url;
        this.API_TOKEN = getApiToken();
    }


    private String getApiToken() {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create(url+ "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url1)
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new BadRequestException("Ошибка при получении токена. Код: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new BadRequestException("При обработке возникла ошибка.");
        }
    }


    public void put(String key, String json) {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create(url + "/save/" + key + "/?API_TOKEN=" + API_TOKEN);

        try {
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(url1)
                    .POST(body)
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200) {
                throw new BadRequestException("Ошибка при загрузке данных на сервер. Код: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new BadRequestException("При обработке возникла ошибка.");
        }


    }

    public String load(String key) {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create(url + "/load/" + key + "/?API_TOKEN=" + API_TOKEN);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url1)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new BadRequestException("Ошибка при загрузке данных из сервера. Код: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new BadRequestException("При обработке возникла ошибка.");
        }
    }
}







