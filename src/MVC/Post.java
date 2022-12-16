package MVC;

public class Post {
    String id;
    Integer num;
    String article;

    public Post(Integer num, String id, String article) {
        this.id = id;
        this.num = num;
        this.article = article;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }


}
