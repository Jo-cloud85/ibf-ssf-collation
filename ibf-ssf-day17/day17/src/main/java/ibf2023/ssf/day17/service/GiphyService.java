package ibf2023.ssf.day17.service;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2023.ssf.day17.model.GiphyImage;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class GiphyService {

    public static final String SEARCH_URL="https://api.giphy.com/v1/gifs/search";

    @Value("${giphy.key}")
    private String apiKey;
    
    public List<GiphyImage> search(String term) {

        String url = UriComponentsBuilder
                        .fromUriString(SEARCH_URL)
                        .queryParam("api_key", apiKey)
                        .queryParam("q", term)
                        .queryParam("limit",5)
                        .toUriString();
        
        System.out.printf(">>>>> url %s\n", url);

        // Make the GET request
        RequestEntity<Void> req = RequestEntity.get(url).build();

        // Make the call
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return List.of();
        }

        System.out.printf("Status code: %d\n", resp.getStatusCode().value());
        System.out.printf("Payload: %s\n", resp.getBody());

        List<GiphyImage> imageslist = new LinkedList<>();

        // Process the body
        JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
        JsonObject giphyResp = reader.readObject();
        JsonArray data = giphyResp.getJsonArray("data");

        for (int i=0; i<data.size(); i++) {
            // Convert the element to Json object
            JsonObject elem = data.get(i).asJsonObject();
            JsonObject images = elem.getJsonObject("images");
            JsonObject fixedWidth = images.getJsonObject("fixed_width");
            String imgUrl = fixedWidth.getString("url");
            GiphyImage giphyImage = new GiphyImage(elem.getString("title"), imgUrl);
            imageslist.add(giphyImage);
        }

        return imageslist;
    }
}
