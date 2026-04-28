public class Genre {

    private String title;
    private int numberOfSubGenres;
    private String url;

    // Constructor
    public Genre(String title, int numberOfSubGenres, String url) {
        this.title = title;
        this.numberOfSubGenres = numberOfSubGenres;
        this.url = url;
    }

    // =====================
    // GETTERS
    // =====================

    public String getTitle() {
        return title;
    }

    public int getNumberOfSubGenres() {
        return numberOfSubGenres;
    }

    public String getUrl() {
        return url;
    }

    // =====================
    // SETTERS
    // =====================

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumberOfSubGenres(int numberOfSubGenres) {
        this.numberOfSubGenres = numberOfSubGenres;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // For debugging
    @Override
    public String toString() {
        return "Genre{" +
                "title='" + title + '\'' +
                ", numberOfSubGenres=" + numberOfSubGenres +
                ", url='" + url + '\'' +
                '}';
    }
}
