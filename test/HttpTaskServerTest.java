public class HttpTaskServerTest {

    /*Отсутствует отдельный класс для тестирования эндпоинтов нашего сервера)
Приведу небольшой кусочек теста, как должно происходить тестирование нашего сервера)
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
Таким образом следует протестировать все маппинги, которые указаны в ТЗ)*/
}
