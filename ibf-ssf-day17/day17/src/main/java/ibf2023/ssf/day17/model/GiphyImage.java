package ibf2023.ssf.day17.model;

public class GiphyImage {

    private String title;
    private String url;

    public GiphyImage() {
    }

    public GiphyImage(String title, String url) {
        this.title = title;
        this.url = url;
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

    @Override
    public String toString() {
        return "GiphyImage [title=" + title + ", url=" + url + "]";
    }
}
