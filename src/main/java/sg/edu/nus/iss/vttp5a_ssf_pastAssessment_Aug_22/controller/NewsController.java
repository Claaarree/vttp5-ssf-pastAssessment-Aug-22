package sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.model.Article;
import sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.model.ArticleList;
import sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.service.NewsService;

@Controller
@RequestMapping("/articles")
public class NewsController {
    
    @Autowired
    NewsService newsService;

    @GetMapping(produces = "text/html")
    public String getAllArticles(Model model, HttpSession session) {
        List<Article> articlesList = newsService.getAllArticles();
        ArticleList articleListObject = new ArticleList(articlesList);
        model.addAttribute("articles", articlesList);
        session.setAttribute("articles", articleListObject);
        return "articles";
    }

    @PostMapping
    public String handleSaveArticles(@RequestBody MultiValueMap<String, String> articlesToSave, HttpSession session) {

        // List<String> savedArticles = articlesToSave.get("articlesToSave");
        // then continue to process the data in the List
        
        newsService.saveArticles(articlesToSave.keySet(), (ArticleList)session.getAttribute("articles"));
        // System.out.println(articlesToSave.values());
        // value is just "on"
        return "redirect:/articles";
    }
}
