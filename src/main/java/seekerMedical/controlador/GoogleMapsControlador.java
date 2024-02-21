package seekerMedical.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GoogleMapsControlador {

    private final String apiKey = "AIzaSyC11776nnpYQTWenID5bQgLBGVNu1RZb9M"; // API Key
    @GetMapping("/buscar_lugares_maps")
    public String searchPlace(@RequestParam("query") String query) {
        String apiUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + query + "&key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);
        return response;
    }
}