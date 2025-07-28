package apisPublicas.dummyJson;

import Requisicoes.Post;
import com.google.gson.Gson;

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
        client.close();
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
        client.close();
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
        client.close();
        return response;
    }

    public HttpResponse<String> taskPOST(Dummy dummy, Gson gson) throws IOException, InterruptedException{
        String json = gson.toJson(dummy);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/todos/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request2, HttpResponse.BodyHandlers.ofString());
        client.close();
        return response;
    }
    public HttpResponse<String> taskPUT(Dummy dummy, Gson gson, String userID) throws IOException, InterruptedException{
        String json = gson.toJson(dummy);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/todos/" + userID))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request2, HttpResponse.BodyHandlers.ofString());
        client.close();
        return response;
    }

    public HttpResponse<String> taskDelete(String ID) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/todos/" + ID))
                .DELETE()
                .build();
        HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());
        client.close();
        return responseDelete;
    }
}

