package br.com.dev.jpa_docker.services;

import br.com.dev.jpa_docker.models.House;
import br.com.dev.jpa_docker.utils.API;
import java.net.http.HttpResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class PopulateHouses {

  @Autowired
  API api;

  public List<House> execute() {
    final String uri = "https://potterapi-fedeperin.vercel.app/en/houses";
    HttpResponse<String> response = api.execute(uri);

    TypeReference<List<House>> typeReference = new TypeReference<List<House>>() {
    };
    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper.readValue(response.body(), typeReference);
  }
}
