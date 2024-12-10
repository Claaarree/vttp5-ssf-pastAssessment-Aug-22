package sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.model;

import java.util.List;

public class ArticleList {
    private List<Article> articlesList;

    public ArticleList(List<Article> articlesList) {
        this.articlesList = articlesList;
    }

    public List<Article> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<Article> articlesList) {
        this.articlesList = articlesList;
    }
}
