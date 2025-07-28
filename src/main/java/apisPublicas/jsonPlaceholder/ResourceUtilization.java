package apisPublicas.jsonPlaceholder;

import apisPublicas.dummyJson.Dummy;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ResourceUtilization {
    public HttpResponse<String> getResource() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Montar a requisição GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                .GET()
                .build();

        // Enviar a requisição e capturar a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        client.close();
        return response;
    }
    public HttpResponse<String> getAllResources() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Montar a requisição GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                .GET()
                .build();

        // Enviar a requisição e capturar a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        client.close();
        return response;
    }
    public HttpResponse<String> getFilteredResource() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Montar a requisição GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts?userId=1"))
                .GET()
                .build();

        // Enviar a requisição e capturar a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        client.close();
        return response;
    }

    public HttpResponse<String> jsonPost(Resource resource, Gson gson) throws IOException, InterruptedException{
        String json = gson.toJson(resource);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        client.close();
        return response;
    }
    public HttpResponse<String> jsonPut(Resource resource, Gson gson, String jsonID) throws IOException, InterruptedException{
        String json = gson.toJson(resource);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/" + jsonID))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        client.close();
        return response;
    }
    public HttpResponse<String> jsonDele(String jsonID) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/" + jsonID))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        client.close();
        return response;
    }
}
