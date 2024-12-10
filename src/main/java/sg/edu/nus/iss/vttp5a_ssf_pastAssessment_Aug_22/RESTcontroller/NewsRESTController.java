package sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.RESTcontroller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.service.NewsService;

@RestController
@RequestMapping
public class NewsRESTController {
    
    @Autowired
    NewsService newsService;

    @GetMapping("/news/{id}")
    public ResponseEntity<String> getSavedArticle(@PathVariable String id) {
        JsonObject error = Json.createObjectBuilder()
                .add("error", "Cannot find news article " + id)
                .build();

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        Optional<String> articleFound = Optional.ofNullable(newsService.getSavedArticleFromRedis(id));
        
        return articleFound.map(s -> new ResponseEntity<>(s, headers, HttpStatusCode.valueOf(200)))
                .orElse(new ResponseEntity<>(error.toString(), headers, HttpStatusCode.valueOf(404)));
    }
}
