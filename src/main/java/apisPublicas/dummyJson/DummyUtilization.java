package apisPublicas.dummyJson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DummyUtilization {
    //fazer 3 metodos get diferentes que usem httpclient
    //formatar o retorno e exibir por console
    //criar classe para serialização do Gson se precisar

    public HttpResponse<String> getResponseAll() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Montar a requisição GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/todos"))
                .GET()
                .build();

        // Enviar a requisição e capturar a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public HttpResponse<String> getResponseRandom() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();

        // Montar a requisição GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/todos/random/3"))
                .GET()
                .build();

        // Enviar a requisição e capturar a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public HttpResponse<String> getResponseLimited() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();

        // Montar a requisição GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/todos?limit=3&skip=30"))
                .GET()
                .build();

        // Enviar a requisição e capturar a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
}

