package apisPublicas.jsonPlaceholder;

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

        return response;
    }
}
