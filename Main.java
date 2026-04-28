import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // =========================================================
            // MAIN WINDOW (THE ONLY JFRAME)
            // =========================================================
            JFrame frame = new JFrame("Library Management System");
            frame.setSize(1500, 900);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            // =========================================================
            // LOAD DATA ONLY ONCE — manager shared by all panels
            // =========================================================
            LibraryManager manager = new LibraryManager();

            manager.loadGenresFromCSV("src/sources/Genre_df.csv");
            manager.loadSubGenresFromCSV("src/sources/Sub_Genre_df.csv");
            manager.loadBooksFromCSV("src/sources/Books_df.csv");

            System.out.println("Genres loaded: " + manager.getGenres().size());
            System.out.println("SubGenres loaded: " + manager.getSubGenres().size());
            System.out.println("Books loaded: " + manager.getBooks().size());

            // =========================================================
            // TABBED PANE
            // =========================================================
            JTabbedPane tabs = new JTabbedPane();

            // BOOKS TAB
            JPanel booksPanel = new LibraryGUI(manager);
            tabs.addTab("Books", booksPanel);

            // GENRES TAB
            JPanel genresPanel = new GenresPanel(manager);
            tabs.addTab("Genres", genresPanel);

            // SUBGENRES TAB
            JPanel subGenresPanel = new SubGenresPanel(manager);
            tabs.addTab("SubGenres", subGenresPanel);

            // ADD TABS TO WINDOW
            frame.add(tabs);

            frame.setVisible(true);
        });
    }
}
