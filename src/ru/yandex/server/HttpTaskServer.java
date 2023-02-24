package ru.yandex.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.model.Task;
import ru.yandex.model.TaskStatus;
import ru.yandex.taskmanager.TaskManager;
import ru.yandex.util.Managers;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private TaskManager taskManager;
    private HttpServer server;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefaultBacked());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        Gson gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::tasksHandler);

    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer taskServer = new HttpTaskServer();
        KVServer KVServer = new KVServer();
        taskServer.start();
        KVServer.start();
    }

    private void tasksHandler(HttpExchange exchange) {
        try {
            Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());
            Gson gson = Managers.getGson();
            String path = exchange.getRequestURI().getPath();

            switch (endpoint) {
                case GET_ALL_TASKS: {

                }
                case GET_HISTORY: {

                }
                case GET_TASKS: {
                    String response = gson.toJson(taskManager.getAllTasks());
                    writeResponse(exchange, response, 200);
                    break;
                }
                case DELETE_TASKS: {

                }
                case DELETE_TASK_BY_ID: {
                    String pathId = path.replaceFirst("/tasks/task/", "");
                    int id = parsePathId(pathId);
                    if (id != -1) {
                        taskManager.deleteTaskById(id);
                        String response = "Задача удалена.";
                        writeResponse(exchange, response, 200);
                    } else {
                        writeResponse(exchange, "Ошибка", 404);
                    }
                    break;
                }
                case GET_TASK_BY_ID: {
                    String pathId = path.replaceFirst("/tasks/task/", "");
                    int id = parsePathId(pathId);
                    if (id != -1) {
                        taskManager.createTask(new Task("Таск1", "Доработать АС", TaskStatus.NEW, null, 60 * 12, LocalDateTime.now().plusDays(2)));
                        String response = gson.toJson(taskManager.getTaskById(id));
                        writeResponse(exchange, response, 200);
                    } else {
                        writeResponse(exchange, "Ошибка", 404);
                    }
                    break;
                }
                case POST_TASK_BY_ID: {

                }
                case GET_SUBTASKS: {
                    String response = gson.toJson(taskManager.getAllSubTasks());
                    writeResponse(exchange, response, 200);
                    break;
                }
                case DELETE_SUBTASKS: {

                }
                case DELETE_SUBTASK_BY_ID: {
                    String pathId = path.replaceFirst("/tasks/subtask/", "");
                    int id = parsePathId(pathId);
                    if (id != -1) {
                        taskManager.deleteSubTaskById(id);
                        String response = "Задача удалена.";
                        writeResponse(exchange, response, 200);
                    } else {
                        writeResponse(exchange, "Ошибка", 404);
                    }
                    break;
                }
                case GET_SUBTASK_BY_ID: {
                    String pathId = path.replaceFirst("/tasks/subtask/", "");
                    int id = parsePathId(pathId);
                    if (id != -1) {
                        String response = gson.toJson(taskManager.getSubTaskById(id));
                        writeResponse(exchange, response, 200);
                    } else {
                        writeResponse(exchange, "Ошибка", 404);
                    }
                    break;
                }
                case POST_SUBTASK_BY_ID: {

                }
                case GET_EPICS: {
                    String response = gson.toJson(taskManager.getAllEpics());
                    writeResponse(exchange, response, 200);
                    break;
                }
                case DELETE_EPICS: {

                }
                case DELETE_EPIC_BY_ID: {
                    String pathId = path.replaceFirst("/tasks/epic/", "");
                    int id = parsePathId(pathId);
                    if (id != -1) {
                        taskManager.deleteEpicById(id);
                        String response = "Задача удалена.";
                        writeResponse(exchange, response, 200);
                    } else {
                        writeResponse(exchange, "Ошибка", 404);
                    }
                    break;

                }
                case GET_EPIC_BY_ID: {
                    String pathId = path.replaceFirst("/tasks/epic/", "");
                    int id = parsePathId(pathId);
                    if (id != -1) {
                        String response = gson.toJson(taskManager.getSubTaskById(id));
                        writeResponse(exchange, response, 200);
                    } else {
                        writeResponse(exchange, "Ошибка", 404);
                    }
                    break;

                }
                case POST_EPIC_BY_ID: {

                }

                default:
                    writeResponse(exchange, "Такого эндпоинта не существует", 404);
                    //exchange.sendResponseHeaders(404, 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }

    }

    private Endpoint getEndpoint(String path, String requestMethod) {

        String[] pathParts = path.split("/");

        switch (requestMethod) {
            case "GET": {
                if (pathParts.length == 2 && pathParts[1].equals("tasks")) {
                    return Endpoint.GET_ALL_TASKS;
                }
                if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("history")) {
                    return Endpoint.GET_HISTORY;
                }
                if (pathParts.length == 3 && pathParts[1].equals("tasks")) {
                    if (pathParts[2].equals("task")) {
                        return Endpoint.GET_TASKS;
                    }
                    if (pathParts[2].equals("subtask")) {
                        return Endpoint.GET_SUBTASKS;
                    }
                    if (pathParts[2].equals("epic")) {
                        return Endpoint.GET_EPICS;
                    }
                }
                if (pathParts.length == 4 && pathParts[1].equals("tasks")) {
                    if (pathParts[2].equals("task")) {
                        return Endpoint.GET_TASK_BY_ID;
                    }
                    if (pathParts[2].equals("subtask")) {
                        return Endpoint.GET_SUBTASK_BY_ID;
                    }
                    if (pathParts[2].equals("epic")) {
                        return Endpoint.GET_EPIC_BY_ID;
                    }
                }
                break;
            }
            case "POST": {
                if (pathParts.length == 4 && pathParts[1].equals("tasks")) {
                    if (pathParts[2].equals("task")) {
                        return Endpoint.POST_TASK_BY_ID;
                    }
                    if (pathParts[2].equals("subtask")) {
                        return Endpoint.POST_SUBTASK_BY_ID;
                    }
                    if (pathParts[2].equals("epic")) {
                        return Endpoint.POST_EPIC_BY_ID;
                    }
                }
                break;
            }
            case "DELETE": {
                if (pathParts.length == 3 && pathParts[1].equals("tasks")) {
                    if (pathParts[2].equals("task")) {
                        return Endpoint.DELETE_TASKS;
                    }
                    if (pathParts[2].equals("subtask")) {
                        return Endpoint.DELETE_SUBTASKS;
                    }
                    if (pathParts[2].equals("epic")) {
                        return Endpoint.DELETE_EPICS;
                    }
                }
                if (pathParts.length == 4 && pathParts[1].equals("tasks")) {
                    if (pathParts[2].equals("task")) {
                        return Endpoint.DELETE_TASK_BY_ID;
                    }
                    if (pathParts[2].equals("subtask")) {
                        return Endpoint.DELETE_SUBTASK_BY_ID;
                    }
                    if (pathParts[2].equals("epic")) {
                        return Endpoint.DELETE_EPIC_BY_ID;
                    }
                }
                break;
            }
        }
        return Endpoint.UNKNOWN;
    }


    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }


    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
        if (responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }

    private int parsePathId(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    enum Endpoint {
        GET_ALL_TASKS, GET_HISTORY,
        GET_TASKS, DELETE_TASKS, DELETE_TASK_BY_ID, GET_TASK_BY_ID, POST_TASK_BY_ID,
        GET_SUBTASKS, DELETE_SUBTASKS, DELETE_SUBTASK_BY_ID, GET_SUBTASK_BY_ID, POST_SUBTASK_BY_ID,
        GET_EPICS, DELETE_EPICS, DELETE_EPIC_BY_ID, GET_EPIC_BY_ID, POST_EPIC_BY_ID,
        UNKNOWN
    }

    /*

    static class TasksHandler implements HttpHandler {
        TaskManager manager = Managers.getDefaultBacked();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

            switch (endpoint) {
                case GET_ALL_TASKS: {
                    break;
                }
                case GET_HISTORY: {
                    break;
                }
                case GET_TASKS: {

                    break;
                }
                case DELETE_TASKS: {
                    break;
                }
                case DELETE_TASK_BY_ID: {
                    break;
                }
                case GET_TASK_BY_ID: {

                    break;
                }
                case POST_TASK_BY_ID: {

                    break;
                }
                case GET_SUBTASKS: {
                    break;
                }
                case DELETE_SUBTASKS: {
                    break;
                }
                case DELETE_SUBTASK_BY_ID: {
                    break;
                }
                case GET_SUBTASK_BY_ID: {
                    break;
                }
                case POST_SUBTASK_BY_ID: {
                    break;
                }
                case GET_EPICS: {
                    break;
                }
                case DELETE_EPICS: {
                    break;
                }
                case DELETE_EPIC_BY_ID: {
                    break;
                }
                case GET_EPIC_BY_ID: {
                    break;
                }
                case POST_EPIC_BY_ID: {
                    break;
                }

                default:
                    //writeResponse(exchange, "Такого эндпоинта не существует", 404);
                    exchange.sendResponseHeaders(404, 0);
            }
        }

        private Endpoint getEndpoint(String requestPath, String requestMethod) {

            */
/*

            Эндпоинты http://localhost8080/tasks/
             GET /tasks/task/
             GET /tasks/task/?id=
             POST /tasks/task/ Body: {task .}
             DELETE /tasks/task/?id= Запросы
             DELETE /tasks/task/
                    /tasks/subtask/
                    /tasks/epic/
             GET /tasks/subtask/epic/?id=
             GET /tasks/history
             GET /tasks/
            *//*

            String[] pathParts = requestPath.split("/");
            switch (requestMethod) {
                case "GET": {
                    if (pathParts.length == 2 && pathParts[1].equals("tasks")) {
                        return Endpoint.GET_ALL_TASKS;
                    }
                    if (pathParts.length == 3 && pathParts[1].equals("tasks") && pathParts[2].equals("history")) {
                        return Endpoint.GET_HISTORY;
                    }
                    if (pathParts.length == 3 && pathParts[1].equals("tasks")) {
                        if (pathParts[2].equals("task")) {
                            return Endpoint.GET_TASKS;
                        }
                        if (pathParts[2].equals("subtask")) {
                            return Endpoint.GET_SUBTASKS;
                        }
                        if (pathParts[2].equals("epic")) {
                            return Endpoint.GET_EPICS;
                        }
                    }
                    if (pathParts.length == 4 && pathParts[1].equals("tasks")) {
                        if (pathParts[2].equals("task")) {
                            return Endpoint.GET_TASK_BY_ID;
                        }
                        if (pathParts[2].equals("subtask")) {
                            return Endpoint.GET_SUBTASK_BY_ID;
                        }
                        if (pathParts[2].equals("epic")) {
                            return Endpoint.GET_EPIC_BY_ID;
                        }
                    }
                    break;
                }
                case "POST": {
                    if (pathParts.length == 4 && pathParts[1].equals("tasks")) {
                        if (pathParts[2].equals("task")) {
                            return Endpoint.POST_TASK_BY_ID;
                        }
                        if (pathParts[2].equals("subtask")) {
                            return Endpoint.POST_SUBTASK_BY_ID;
                        }
                        if (pathParts[2].equals("epic")) {
                            return Endpoint.POST_EPIC_BY_ID;
                        }
                    }
                    break;
                }
                case "DELETE": {
                    if (pathParts.length == 3 && pathParts[1].equals("tasks")) {
                        if (pathParts[2].equals("task")) {
                            return Endpoint.DELETE_TASKS;
                        }
                        if (pathParts[2].equals("subtask")) {
                            return Endpoint.DELETE_SUBTASKS;
                        }
                        if (pathParts[2].equals("epic")) {
                            return Endpoint.DELETE_EPICS;
                        }
                    }
                    if (pathParts.length == 4 && pathParts[1].equals("tasks")) {
                        if (pathParts[2].equals("task")) {
                            return Endpoint.DELETE_TASK_BY_ID;
                        }
                        if (pathParts[2].equals("subtask")) {
                            return Endpoint.DELETE_SUBTASK_BY_ID;
                        }
                        if (pathParts[2].equals("epic")) {
                            return Endpoint.DELETE_EPIC_BY_ID;
                        }
                    }
                    break;
                }
            }
            return Endpoint.UNKNOWN;
        }
    }
*/
}
