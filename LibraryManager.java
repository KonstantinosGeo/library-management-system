import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {

    // ===============================
    // LISTS FOR ALL DATA
    // ===============================
    private List<Book> books = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private List<SubGenre> subGenres = new ArrayList<>();

    // Path for saving books
    private String booksFilePath = "";   // will be set from Main


    // ===============================
    // GETTERS
    // ===============================
    public List<Book> getBooks() { return books; }
    public List<Genre> getGenres() { return genres; }
    public List<SubGenre> getSubGenres() { return subGenres; }

    public void setBooksFilePath(String path) {
        this.booksFilePath = path;
    }

    public String getBooksFilePath() {
        return booksFilePath;
    }


    // ============================================================
    // SAFE PARSING HELPERS
    // ============================================================
    private int safeParseInt(String s) {
        try {
            s = s.replace("\"", "").trim();
            s = s.replace(",", "");
            if (s.contains(".")) {
                return (int) Double.parseDouble(s);
            }
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    private double safeParseDouble(String s) {
        try {
            s = s.replace("\"", "").trim();
            s = s.replace("₹", "").replace(",", "");
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0.0;
        }
    }


    // ============================================================
    // REGEX SPLIT FUNCTION FOR CSV WITH QUOTES
    // ============================================================
    private String[] splitCSV(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    }


    // ============================================================
    // LOAD GENRES FROM CSV
    // ============================================================
    public void loadGenresFromCSV(String path) {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = splitCSV(line);
                if (parts.length < 3) continue;

                String title = parts[0].replace("\"", "").trim();
                int number = safeParseInt(parts[1]);
                String url = parts[2].replace("\"", "").trim();

                genres.add(new Genre(title, number, url));
            }

            System.out.println("Genres loaded: " + genres.size());

        } catch (Exception e) {
            System.out.println("Error loading genres: " + e.getMessage());
        }
    }


    // ============================================================
    // LOAD SUBGENRES FROM CSV
    // ============================================================
    public void loadSubGenresFromCSV(String path) {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = splitCSV(line);
                if (parts.length < 4) continue;

                String title = parts[0].replace("\"", "").trim();
                String mainGenre = parts[1].replace("\"", "").trim();
                int numberOfBooks = safeParseInt(parts[2]);
                String url = parts[3].replace("\"", "").trim();

                subGenres.add(new SubGenre(title, mainGenre, numberOfBooks, url));
            }

            System.out.println("SubGenres loaded: " + subGenres.size());

        } catch (Exception e) {
            System.out.println("Error loading subgenres: " + e.getMessage());
        }
    }


    // ============================================================
    // LOAD BOOKS FROM CSV
    // ============================================================
    public void loadBooksFromCSV(String path) {

        this.booksFilePath = path;  // store it for saving later

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = splitCSV(line);
                if (parts.length < 10) continue;

                String title = parts[1].replace("\"", "").trim();
                String author = parts[2].replace("\"", "").trim();
                String mainGenre = parts[3].replace("\"", "").trim();
                String subGenre = parts[4].replace("\"", "").trim();
                String type = parts[5].replace("\"", "").trim();

                double price = safeParseDouble(parts[6]);
                double rating = safeParseDouble(parts[7]);
                int rated = safeParseInt(parts[8]);
                String url = parts[9].replace("\"", "").trim();

                books.add(new Book(
                        title, author, mainGenre, subGenre,
                        type, price, rating, rated, url
                ));
            }

            System.out.println("Books loaded: " + books.size());

        } catch (Exception e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }


    // ============================================================
    // SAVE BOOKS TO CSV  (AUTO-SAVE)
    // ============================================================
    public void saveBooksToCSV() {
        if (booksFilePath == null || booksFilePath.isEmpty()) {
            System.out.println("No file path assigned for saving.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new File(booksFilePath))) {

            pw.println("Index,Title,Author,Main Genre,Sub Genre,Type,Price,Rating,No. Rated,URL");

            int index = 1;
            for (Book b : books) {
                pw.println(
                        index + "," +
                                b.getTitle() + "," +
                                b.getAuthor() + "," +
                                b.getMainGenre() + "," +
                                b.getSubGenre() + "," +
                                b.getType() + "," +
                                b.getPrice() + "," +
                                b.getRating() + "," +
                                b.getNumberOfPeopleRated() + "," +
                                b.getUrl()
                );
                index++;
            }

            System.out.println("Books saved successfully!");

        } catch (Exception e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }


    // ============================================================
    // CRUD WITH AUTO-SAVE
    // ============================================================
    public void addBook(Book b) {
        books.add(b);
        saveBooksToCSV();
    }

    public void deleteBook(Book b) {
        books.remove(b);
        saveBooksToCSV();
    }

    public void updateBook(int index, Book updated) {
        books.set(index, updated);
        saveBooksToCSV();
    }


    // ============================================================
    // SEARCH
    // ============================================================
    public List<Book> searchBooks(String keyword) {
        List<Book> results = new ArrayList<>();

        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    b.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(b);
            }
        }
        return results;
    }


    // ============================================================
    // FILTER
    // ============================================================
    public List<Book> filterByGenre(String genre) {
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            if (b.getMainGenre().equalsIgnoreCase(genre)) {
                results.add(b);
            }
        }
        return results;
    }

    public List<Book> filterBySubGenre(String subgenre) {
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            if (b.getSubGenre().equalsIgnoreCase(subgenre)) {
                results.add(b);
            }
        }
        return results;
    }


    // ============================================================
    // SORTING
    // ============================================================
    public void sortBooksByPrice() {
        books.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
    }

    public void sortBooksByRating() {
        books.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
    }
}
