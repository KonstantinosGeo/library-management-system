public class SubGenre {

    private String title;
    private String mainGenre;
    private int numberOfBooks;
    private String url;

    // Constructor
    public SubGenre(String title, String mainGenre, int numberOfBooks, String url) {
        this.title = title;
        this.mainGenre = mainGenre;
        this.numberOfBooks = numberOfBooks;
        this.url = url;
    }

    // =====================
    // GETTERS
    // =====================

    public String getTitle() {
        return title;
    }

    public String getMainGenre() {
        return mainGenre;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
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

    public void setMainGenre(String mainGenre) {
        this.mainGenre = mainGenre;
    }

    public void setNumberOfBooks(int numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // For debugging
    @Override
    public String toString() {
        return "SubGenre{" +
                "title='" + title + '\'' +
                ", mainGenre='" + mainGenre + '\'' +
                ", numberOfBooks=" + numberOfBooks +
                ", url='" + url + '\'' +
                '}';
    }
}
