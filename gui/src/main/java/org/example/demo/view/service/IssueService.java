package org.example.demo.view.service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.demo.view.dto.cards.IssueCardResponseDTO;
import org.example.demo.view.dto.cards.NewIssueCardRequestDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IssueService {
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new IssueService
                    .LocalDateTimeAdapter())
            .create();
    private final HttpClient client =  Authentication.getClient();

    private HttpRequest requestPost(String uri, String json) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type",
                        "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(json))
                .build();
    }

    private HttpRequest requestPost(String uri) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
    }

    private HttpRequest requestGet(String uri) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
    }

    private HttpResponse<String> response(HttpRequest request) throws IOException,
            InterruptedException {
        return client.send(request, HttpResponse
                .BodyHandlers.ofString());
    }

    public IssueCardResponseDTO createIssue(NewIssueCardRequestDTO issue) throws IOException,
            InterruptedException {
        String json = gson.toJson(issue);

        HttpRequest request = requestPost("http://localhost:8080/api/issues", json);
        HttpResponse<String> response = response(request);

        System.out.println("Risposta dal server: " + response.body());

        if (response.statusCode() == 200 || response.statusCode() == 201)
            return gson.fromJson(response.body(),
                    IssueCardResponseDTO.class);
        else
            throw new RuntimeException("Errore. Codice: " +
                    response.statusCode());
    }

    public List<IssueCardResponseDTO> listIssues() throws IOException,
            InterruptedException {
        HttpRequest request = requestGet("http://localhost:8080/api/issues");
        HttpResponse<String> response = response(request);

        System.out.println("Risposta dal server: " + response.body());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            Type listType = new TypeToken<List<IssueCardResponseDTO>>() {}
                    .getType();
            return gson.fromJson(response.body(), listType);
        }
        else
            throw new RuntimeException("Errore. Codice: " +
                    response.statusCode());
    }

    public IssueCardResponseDTO assign(Integer idBug, Integer idUser) throws IOException,
            InterruptedException {
        String url = "http://localhost:8080/api/bugs/" + idBug +
                "/assegna?utenteId=" + idUser;
        HttpRequest request = requestPost(url);
        HttpResponse<String> response = response(request);

        System.out.println("Risposta dal server: " + response.body());

        if (response.statusCode() == 200 ||
                response.statusCode() == 201)
            return gson.fromJson(response.body(),
                    IssueCardResponseDTO.class);
        else
            throw new RuntimeException("Errore. Codice: " +
                    response.statusCode());
    }

    public List<IssueCardResponseDTO> assigned() throws IOException,
    InterruptedException {
        HttpRequest request = requestGet("http://localhost:8080/api/bugs/assegnati");
        HttpResponse<String> response = response(request);

        System.out.println("Risposta dal server: " + response.body());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            Type listType = new TypeToken<List<IssueCardResponseDTO>>() {}
                    .getType();
            return gson.fromJson(response.body(), listType);
        }
        else
            throw new RuntimeException("Errore. Codice: " +
                    response.statusCode());
    }

    public List<IssueCardResponseDTO> searchByKeyword(String keyword, String typology, String priority)
            throws IOException, InterruptedException {
        StringBuilder urlBuilder = new StringBuilder("http://localhost:8080/api/issues/ricerca");
        boolean hasParameter = false;

        if(keyword != null || !keyword.isBlank()) {
            urlBuilder.append("?")
                    .append("parolaChiave=")
                    .append(URLEncoder.encode(keyword,
                            StandardCharsets.UTF_8));
            hasParameter = true;
        }

        if (typology != null || !typology.isBlank()) {
            urlBuilder.append(hasParameter ? "&" : "?")
                    .append("tipo=").append(typology);
            hasParameter = true;
        }

        if (priority != null || !priority.isBlank()) {
            urlBuilder.append(hasParameter ? "&" : "?")
                    .append("priorita=").append(priority);
            hasParameter = true;
        }

        HttpRequest request = requestGet(urlBuilder.toString());
        HttpResponse<String> response = response(request);

        System.out.println("Risposta dal server: " + response.body());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            Type listType = new TypeToken<List<IssueCardResponseDTO>>() {}
                    .getType();
            return gson.fromJson(response.body(), listType);
        }
        else
            throw new RuntimeException("Errore. Codice: " +
                    response.statusCode());
    }

    public List<IssueCardResponseDTO> searchByFilter(String typology,
                                                     String state,
                                                     String priority)
            throws IOException, InterruptedException {
        StringBuilder urlBuilder = new StringBuilder("http://localhost:8080/api/issues/ricerca");
        boolean hasParameter = false;

        if(typology != null && !typology.isBlank()) {
            urlBuilder.append(hasParameter ? "&" : "?")
                    .append("tipo=").append(typology);
            hasParameter = true;
        }

        if (state != null && !state.isBlank()) {
            urlBuilder.append(hasParameter ? "&" : "?")
                    .append("stato=").append(state);
            hasParameter = true;
        }

        if (priority != null && !priority.isBlank()) {
            urlBuilder.append(hasParameter ? "&" : "?")
                    .append("priorita=").append(priority);
            hasParameter = true;
        }

        HttpRequest request = requestGet(urlBuilder.toString());
        HttpResponse<String> response = response(request);

        System.out.println("Risposta dal server: " + response.body());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            Type listType = new TypeToken<List<IssueCardResponseDTO>>() {}
                    .getType();
            return gson.fromJson(response.body(), listType);
        }
        else
            throw new RuntimeException("Errore. Codice: " +
                    response.statusCode());
    }

    public IssueCardResponseDTO close(Integer idIssue) throws IOException,
            InterruptedException {
        String url = "http://localhost:8080/api/issues/" + idIssue + "/chiudi";
        HttpRequest request = requestPost(url);
        HttpResponse<String> response = response(request);

        System.out.println("Risposta dal server: " + response.body());

        if (response.statusCode() == 200 ||
                response.statusCode() == 201)
            return gson.fromJson(response.body(),
                    IssueCardResponseDTO.class);
        else
            throw new RuntimeException("Errore. Codice: " +
                    response.statusCode());
    }

    public Image download(Integer idIssue) {
        try {
            HttpRequest request = requestGet("http://localhost:8080/api/issues/" + idIssue + "/immagine");
            HttpResponse<byte[]> response = client
                    .send(request, HttpResponse.BodyHandlers.ofByteArray());

            System.out.println("--- DEBUG IMMAGINE PER ISSUE ID: " + idIssue + " ---");
            System.out.println("Status code HTTP: " + response.statusCode());

            if (response.body() != null) {
                System.out.println("Numero di byte ricevuti: " + response.body().length);
            } else {
                System.out.println("ATTENZIONE: response.body() è NULL!");
                return null;
            }

            if (response.statusCode() == 200) {
                byte[] imageBytes = response.body();
                if (imageBytes != null && imageBytes.length > 0) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                    return new Image(bis);
                }
            } else if (response.statusCode() == 404) {
                System.out.println("L'issue #" + idIssue + " non ha un'immagine (gestito correttamente).");
            } else {
                System.out.println("Errore inatteso dal server. Status: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Ritorna null se c'è un errore o se l'issue non ha un'immagine
    }

    public IssueCardResponseDTO duplicate(Integer idBug) throws IOException,
            InterruptedException {
        String url = "http://localhost:8080/api/bugs/" + idBug +
                "/duplicato";
        HttpRequest request = requestPost(url);
        HttpResponse<String> response = response(request);

        System.out.println("Risposta dal server: " + response.body());

        if (response.statusCode() == 200 ||
                response.statusCode() == 201)
            return gson.fromJson(response.body(),
                    IssueCardResponseDTO.class);
        else
            throw new RuntimeException("Errore. Codice: " +
                    response.statusCode());
    }

    private class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>,
            JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter formatter = DateTimeFormatter
                .ISO_LOCAL_DATE_TIME;

        @Override
        public JsonElement serialize(LocalDateTime src,
                                     Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(formatter
                    .format(src));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json,
                                         Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDateTime.parse(json
                    .getAsString(), formatter);
        }
    }
}
