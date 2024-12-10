package sg.edu.nus.iss.vttp5a_ssf_pastAssessment_Aug_22.model;

public class Article {
    private Integer id;
    private Long publishedOn;
    private String title;
    private String url;
    private String imageURl;
    private String body;
    private String tags;
    private String categories;
   
    public Article() {
    }

    public Article(Integer id, Long publishedOn, String title, String url, String imageURl, String body, String tags,
            String categories) {
        this.id = id;
        this.publishedOn = publishedOn;
        this.title = title;
        this.url = url;
        this.imageURl = imageURl;
        this.body = body;
        this.tags = tags;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Long publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    
}
