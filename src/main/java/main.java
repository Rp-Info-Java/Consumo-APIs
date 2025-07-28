import Requisicoes.Post;
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
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //Código para testar e consumir APIs públicas
        //Estruturação básica para consumir uma API
        System.out.println("--Exemplo do funcionamento do HttpRequest--\n");
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

        //Exercicio 1: Ler o CEP digitado  -------------------------------------------------------------
        String cep;
        System.out.println("Digite um CEP: ");
        cep = lerOpcao();

        if (cep.length() == 8) {
            imprimeCEP(cep, client, gson);
        } else {
            System.out.println("O CEP digitado não possui a quantia correta de caracteres.\n");
        }

        //Exercicio 2 -- Utilização do GET das APIs escolhidas -------------------------------------
        DummyUtilization dummyUtil = new DummyUtilization();

        System.out.println("\n--- TODOS List ---"); //TODOS é o nome do atributo de Tarefas
        HttpResponse<String> response3 = dummyUtil.getResponseAll();
        Todos listaTodos = gson.fromJson(response3.body(), Todos.class);

        listDummy(listaTodos);

        System.out.println("\n--- TODOS Random ---");
        HttpResponse<String> response4 = dummyUtil.getResponseRandom();
        Type tipoLista = new TypeToken<List<Dummy>>() {
        }.getType();
        List<Dummy> listaDummy = gson.fromJson(response4.body(), tipoLista);

        //Utilizamos Type pois o que está englobando os atributos no postman é um vetor (está entre colchetes)
        listDummyList(listaDummy);

        System.out.println("\n--- TODOS Limited ---");
        HttpResponse<String> response5 = dummyUtil.getResponseLimited();
        Todos listaTodosLimited = gson.fromJson(response5.body(), Todos.class);

        listDummy(listaTodosLimited);

        //Segunda API ---------------------------
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
        Type listType = new TypeToken<List<Resource>>() {
        }.getType();
        List<Resource> listaResources = gson.fromJson(response7.body(), listType);

        listRes(listaResources);

        System.out.println("\n--- Filtered Resources by UserID---");
        HttpResponse<String> response8 = resUtil.getFilteredResource();
        List<Resource> listFilterRes = gson.fromJson(response8.body(), listType);

        listRes(listFilterRes);

        //Desenvolvimento dos métodos CRUD - POST - PUT - DELETE
        //-------------------------------------------------------------------------
        Post novoPost = new Post();
        novoPost.setUserId(1);
        novoPost.setTitle("Aprendendo API em Java");
        novoPost.setBody("Hoje aprendi como fazer POST!");

        String json = gson.toJson(novoPost);
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client = HttpClient.newHttpClient();
        HttpResponse<String> response9 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nStatus: " + response9.statusCode());
        System.out.println("Resposta: " + response9.body());

        novoPost.setTitle("Título Atualizado");
        novoPost.setBody("Conteúdo atualizado do post.");
        json = gson.toJson(novoPost);

        HttpRequest requestPut = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> responsePut = client.send(requestPut, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status do PUT: " + responsePut.statusCode());
        System.out.println("Resposta PUT: " + responsePut.body());

        HttpRequest requestDelete = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                .DELETE()
                .build();
        HttpResponse<String> responseDelete = client.send(requestDelete, HttpResponse.BodyHandlers.ofString());

        client.close();
        System.out.println("Status do DELETE: " + responseDelete.statusCode());
        System.out.println("Resposta DELETE: " + responseDelete.body());
        //---------------------------------------------------------------------------------------------

        Dummy dummy = new Dummy();
        int opcao = 0;

        System.out.println("\n--- DUMMY POST ---");
        alterDummy(dummy, opcao);
        HttpResponse<String> responseDumPost = dummyUtil.taskPOST(dummy, gson);
        System.out.println("Status da requisição: " + responseDumPost.statusCode());
        System.out.println("Body da resposta: " + responseDumPost.body());

        System.out.println("\n--- DUMMY PUT ---");
        alterDummy(dummy,opcao);
        HttpResponse<String> responseDumPut = dummyUtil.taskPUT(dummy, gson, dummy.getUserId());
        System.out.println("Status da requisição: " + responseDumPut.statusCode());
        System.out.println("Body da requisição: " + responseDumPut.body());

        System.out.println("\n--- DUMMY DELETE ---");
        System.out.println("Digite o ID da TASK que será deletada: ");
        HttpResponse<String> responseDumDele = dummyUtil.taskDelete(lerOpcao());

        System.out.println("Status DELETE: " + responseDumDele.statusCode());
        System.out.println("Resposta DELETE: " + responseDumDele.body());

        //------------------------------------------------------------------------

        System.out.println("\n--- JSONPLACEHOLDER POST ---");
        alterJson(resource);
        HttpResponse<String> responseJsonPost = resUtil.jsonPost(resource, gson);

        System.out.println("\nStatus da requisião: " + responseJsonPost.statusCode());
        System.out.println("Resposta POST: " + responseJsonPost.body());

        System.out.println("\n--- JSONPLACEHOLDER PUT ---");
        alterJson(resource);
        HttpResponse<String> responseJsonPut = resUtil.jsonPut(resource, gson, resource.getId());

        System.out.println("\nStatus da requisição: " + responseJsonPut.statusCode());
        System.out.println("Resposta PUT: " + responseJsonPut.body());

        System.out.println("\n--- JSONPLACEHOLDER DELETE ---");
        System.out.println("Digite o ID do placeholder que será deletado: ");
        HttpResponse<String> responseJsonDele = resUtil.jsonDele(lerOpcao());

        System.out.println("\nStatus da requisição: " + responseJsonDele.statusCode());
        System.out.println("Resposta DELETE: " + responseJsonDele.body());
    }

    private static String lerOpcao() {
        Scanner teclado = new Scanner(System.in);
        try {
            return teclado.nextLine();
        } catch (Exception e) {
            throw new InputMismatchException("Erro de digitação: " + e.getMessage());
        }
    }

    private static int lerOpcaoInt() {
        Scanner teclado = new Scanner(System.in);
        try {
            return teclado.nextInt();
        } catch (Exception e) {
            throw new InputMismatchException("Erro de digitação: " + e.getMessage());
        }
    }

    private static void imprimeCEP(String cep, HttpClient client, Gson gson) throws IOException, InterruptedException {
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
    }

    private static void listDummy(Todos listaTodos) {
        for (Dummy d : listaTodos.getTodos()) {
            System.out.println("\nID do todo: " + d.getId());
            System.out.println("Todo: " + d.getTodo());
            System.out.println("Status do todo: " + d.getCompleted());
            System.out.println("ID do usuário: " + d.getUserId());
        }
    }

    private static void listDummyList(List<Dummy> listaDummy) {
        for (Dummy d2 : listaDummy) {
            System.out.println("\nID do todo: " + d2.getId());
            System.out.println("Todo: " + d2.getTodo());
            System.out.println("Status do todo: " + d2.getCompleted());
            System.out.println("ID do usuário: " + d2.getUserId());
        }
    }

    private static void listRes(List<Resource> listaResources) {
        for (Resource rs : listaResources) {
            System.out.println("\nID do usuário: " + rs.getUserId());
            System.out.println("ID do recurso: " + rs.getId());
            System.out.println("Título do recurso: " + rs.getTitle());
            System.out.println("Body do recurso: " + rs.getBody());
        }
    }

    private static void alterDummy(Dummy dummy, int opcao){
        System.out.println("Digite a TASK (todo) que você deseja adicionar: ");
        dummy.setTodo(lerOpcao());

        while (opcao < 1 || opcao > 2) {
            System.out.println("""
                    Qual o Status da TASK?
                    1- TRUE (completada)
                    2- FALSE (não foi completada)
                    """);
            System.out.println("Digite uma opção: ");
            opcao = lerOpcaoInt();
            if (opcao == 1) {
                dummy.setCompleted("true");
            } else if (opcao == 2) {
                dummy.setCompleted("false");
            } else {
                System.out.println("A opção selecionada está incorreta!\n");
            }
        }

        System.out.println("Digite o ID do usuário: ");
        dummy.setUserId(lerOpcao());
    }
    private static void alterJson(Resource resource){
        System.out.println("Digite o título do placeholder: ");
        resource.setTitle(lerOpcao());

        System.out.println("Digite o body do placeholder: ");
        resource.setBody(lerOpcao());

        System.out.println("Digite o ID do usuário: ");
        resource.setUserId(lerOpcao());
    }
}
