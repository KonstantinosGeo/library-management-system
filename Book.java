public class Book {

    // 1. Ιδιωτικά πεδία (χαρακτηριστικά βιβλίου)
    private String title;
    private String author;
    private String mainGenre;
    private String subGenre;
    private String type;          // paperback, Kindle, audiobook, hardcover
    private double price;
    private double rating;
    private int numberOfPeopleRated;
    private String url;

    // 2. Constructor (χτίζει ένα Book με όλα τα πεδία)
    public Book(String title,
                String author,
                String mainGenre,
                String subGenre,
                String type,
                double price,
                double rating,
                int numberOfPeopleRated,
                String url) {

        this.title = title;
        this.author = author;
        this.mainGenre = mainGenre;
        this.subGenre = subGenre;
        this.type = type;
        this.price = price;
        this.rating = rating;
        this.numberOfPeopleRated = numberOfPeopleRated;
        this.url = url;
    }

    // 3. Getters (μόνο διάβασμα)
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getMainGenre() {
        return mainGenre;
    }

    public String getSubGenre() {
        return subGenre;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public int getNumberOfPeopleRated() {
        return numberOfPeopleRated;
    }

    public String getUrl() {
        return url;
    }

    // 4. Setters (αλλαγή τιμών)
    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setNumberOfPeopleRated(int numberOfPeopleRated) {
        this.numberOfPeopleRated = numberOfPeopleRated;
    }

    // 5. toString() - βοηθάει να τυπώνουμε το βιβλίο εύκολα
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", mainGenre='" + mainGenre + '\'' +
                ", subGenre='" + subGenre + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", numberOfPeopleRated=" + numberOfPeopleRated +
                '}';
    }
}
