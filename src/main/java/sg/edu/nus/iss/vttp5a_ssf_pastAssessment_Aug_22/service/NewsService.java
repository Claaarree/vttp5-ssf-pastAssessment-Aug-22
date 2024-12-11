package sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.model.Article;
import sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.model.ArticleList;
import sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.repo.HashRepo;
import sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.utility.Utility;

@Service
public class NewsService {

    @Autowired
    HashRepo newsRepo;

    @Value("${crypto.news.api.key}")
    private String apiKey;
    
    RestTemplate template = new RestTemplate();
    
    public List<Article> getAllArticles() {
        RequestEntity<Void> req = RequestEntity
                .get(Utility.cryptoNewsUrl)
                .header("Authorization", "Bearer " + apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<String> res = template.exchange(req, String.class);

        String articles = res.getBody();

        JsonReader jReader = Json.createReader(new StringReader(articles));
        JsonObject jObject = jReader.readObject();
        JsonArray data = jObject.getJsonArray("Data");
        List<Article> articlesList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JsonObject news = data.getJsonObject(i);
            Integer id = news.getInt("ID");
            Long publishedOnLong = news.getJsonNumber("PUBLISHED_ON").longValueExact();
            Date publishedOnDate = new Date(publishedOnLong * 1000);
            String title = news.getString("TITLE");
            String url = news. getString("URL");
            String imageUrl = news.getString("IMAGE_URL");
            String body = news.getString("BODY");
            String tags = news.getString("KEYWORDS");

            JsonArray categoryData = news.getJsonArray("CATEGORY_DATA");
            StringBuilder sb = new StringBuilder("");

            for (int j = 0; j < categoryData.size(); j++) {
                JsonObject cat = categoryData.getJsonObject(j);
                String category = cat.getString("CATEGORY");
                sb.append(category + "|");
            }
            sb.deleteCharAt(sb.lastIndexOf("|"));
            String categories = sb.toString();

            Article a = new Article(id, publishedOnDate, title, url, imageUrl, body, tags, categories);
            articlesList.add(a);
        }

        return articlesList;
    }

    public void saveArticles(Set<String> articlesToSave, ArticleList articles) {
        List<Article> articlesList = articles.getArticlesList();
        
        for (String id : articlesToSave) {
            for (Article a : articlesList) {
                if (String.valueOf(a.getId()).equals(id)) {
                    addToRedis(a);
                }
            }
        }

    }

    public void addToRedis(Article a) {
        JsonObject jObject = Json.createObjectBuilder()
                .add("ID", a.getId())
                .add("PUBLISHED_ON", a.getPublishedOn().getTime()/1000)
                .add("TITLE", a.getTitle())
                .add("URL", a.getUrl())
                .add("IMAGE_URL", a.getImageUrl())
                .add("BODY", a.getBody())
                .add("TAGS", a.getTags())
                .add("CATEGORIES", a.getCategories())
                .build();

        newsRepo.addToHash(Utility.articlesRedisKey, String.valueOf(a.getId()), jObject.toString());
    }

    public String getSavedArticleFromRedis(String id) {
        Set<String> articlesSaved = newsRepo.getAllFields(Utility.articlesRedisKey);

        String articleFound = null;

        for(String articleID: articlesSaved) {
            if (articleID.equals(id)) {
                String articleToReturn = newsRepo.getFieldValue(Utility.articlesRedisKey, articleID);
                articleFound = articleToReturn;
            }
        }
        return articleFound;
    }
}
 