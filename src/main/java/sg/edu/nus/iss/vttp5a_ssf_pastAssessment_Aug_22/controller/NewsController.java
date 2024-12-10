package sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.model.Article;
import sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.service.NewsService;

@Controller
@RequestMapping("/news")
public class NewsController {
    
    @Autowired
    NewsService newsService;

    @ResponseBody
    @GetMapping
    public List<Article> getAllArticles() {
        return newsService.getAllArticles();
    }
}
