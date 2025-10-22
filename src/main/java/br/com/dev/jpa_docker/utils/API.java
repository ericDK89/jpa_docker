package br.com.dev.jpa_docker.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class API {

  public HttpResponse<String> execute(String uri) {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(uri))
          .timeout(Duration.ofMinutes(2))
          .header("Content-Type", "application/json")
          .GET()
          .build();

      return client.send(request, BodyHandlers.ofString());

    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
