import apisPublicas.dummyJson.Dummy;
import apisPublicas.dummyJson.DummyUtilization;
import apisPublicas.dummyJson.Todos;
import apisPublicas.jsonPlaceholder.Resource;
import apisPublicas.jsonPlaceholder.ResourceUtilization;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Criar o cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Montar a requisição GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://viacep.com.br/ws/85530000/json/"))
                .GET()
                .build();

        // Enviar a requisição e capturar a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        //converte json em objeto endereco
        Endereco endereco = gson.fromJson(response.body(), Endereco.class);

        System.out.println("Cidade: " + endereco.getLocalidade());
        System.out.println("Estado: " + endereco.getUf());
        System.out.println("Rua: " + endereco.getLogradouro());

        Scanner teclado = new Scanner(System.in);
        String cep;
        System.out.println("Digite um CEP: ");
        cep = teclado.nextLine();

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("https://viacep.com.br/ws/" + cep + "/json/"))
                .GET()
                .build();

        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        Endereco endereco2 = gson.fromJson(response2.body(), Endereco.class);

        System.out.println("\n--- Dados do CEP digitado ---");
        System.out.println("CEP: " + endereco2.getCep());
        System.out.println("Logradouro: " + endereco2.getLogradouro());
        System.out.println("Complemento: " + endereco2.getComplemento());
        System.out.println("Unidade: " + endereco2.getUnidade());
        System.out.println("Bairro: " + endereco2.getBairro());
        System.out.println("Localidade: " + endereco2.getLocalidade());
        System.out.println("UF: " + endereco2.getUf());
        System.out.println("Estado: " + endereco2.getEstado());
        System.out.println("Região: " + endereco2.getRegiao());
        System.out.println("IBGE: " + endereco2.getIbge());
        System.out.println("GIA: " + endereco2.getGia());
        System.out.println("DDD: " + endereco2.getDdd());
        System.out.println("SIAFI: " + endereco2.getSiafi());

        DummyUtilization dummyUtil = new DummyUtilization();

        System.out.println("\n--- TODOS List ---");
        HttpResponse<String> response3 = dummyUtil.getResponseAll();
        Todos listaTodos = gson.fromJson(response3.body(), Todos.class);

        for (Dummy d : listaTodos.getTodos()) {
            System.out.println("\nID do todo: " + d.getId());
            System.out.println("Todo: " + d.getTodo());
            System.out.println("Status do todo: " + d.getCompleted());
            System.out.println("ID do usuário: " + d.getUserId());
        }

        System.out.println("\n--- TODOS Random ---");

        HttpResponse<String> response4 = dummyUtil.getResponseRandom();
        Type tipoLista = new TypeToken<List<Dummy>>() {}.getType();
        List<Dummy> listaDummy = gson.fromJson(response4.body(), tipoLista);

        for(Dummy d2 : listaDummy){
            System.out.println("\nID do todo: " + d2.getId());
            System.out.println("Todo: " + d2.getTodo());
            System.out.println("Status do todo: " + d2.getCompleted());
            System.out.println("ID do usuário: " + d2.getUserId());
        }

        System.out.println("\n--- TODOS Limited ---");
        HttpResponse<String> response5 = dummyUtil.getResponseLimited();
        Todos listaTodosLimited = gson.fromJson(response5.body(), Todos.class);

        for (Dummy d3 : listaTodosLimited.getTodos()) {
            System.out.println("\nID do todo: " + d3.getId());
            System.out.println("Todo: " + d3.getTodo());
            System.out.println("Status do todo: " + d3.getCompleted());
            System.out.println("ID do usuário: " + d3.getUserId());
        }
        ResourceUtilization resUtil = new ResourceUtilization();

        System.out.println("\n--- Resource ---");

        HttpResponse<String> response6 = resUtil.getResource();
        Resource resource = gson.fromJson(response6.body(), Resource.class);

        System.out.println("ID do usuário: " + resource.getUserId());
        System.out.println("ID do recurso: " + resource.getId());
        System.out.println("Título do recurso: " + resource.getTitle());
        System.out.println("Body do recurso: " + resource.getBody());

        System.out.println("\n--- All Resources ---");

        HttpResponse<String> response7 = resUtil.getAllResources();
        Type listType = new TypeToken<List<Resource>>() {}.getType();
        List<Resource> listaResources = gson.fromJson(response7.body(), listType);

        for(Resource rs : listaResources){
            System.out.println("\nID do usuário: " + rs.getUserId());
            System.out.println("ID do recurso: " + rs.getId());
            System.out.println("Título do recurso: " + rs.getTitle());
            System.out.println("Body do recurso: " + rs.getBody());
        }

        System.out.println("\n--- Filtered Resources by UserID---");
        HttpResponse<String> response8 = resUtil.getFilteredResource();
        Type listType2 = new TypeToken<List<Resource>>() {}.getType();
        List<Resource> listFilterRes = gson.fromJson(response8.body(), listType2);

        for(Resource rs : listFilterRes){
            System.out.println("\nID do usuário: " + rs.getUserId());
            System.out.println("ID do recurso: " + rs.getId());
            System.out.println("Título do recurso: " + rs.getTitle());
            System.out.println("Body do recurso: " + rs.getBody());
        }
    }
}
