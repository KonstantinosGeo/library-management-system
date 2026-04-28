import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LibraryGUI extends JPanel {

    private LibraryManager manager;
    private JTable booksTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<String> genreCombo;
    private JComboBox<String> subGenreCombo;

    private JLabel infoLabel;

    private JTextField formTitleField;
    private JTextField formAuthorField;
    private JTextField formMainGenreField;
    private JTextField formSubGenreField;
    private JTextField formTypeField;
    private JTextField formPriceField;
    private JTextField formRatingField;
    private JTextField formRatedField;
    private JTextField formUrlField;

    public LibraryGUI(LibraryManager manager) {

        this.manager = manager;
        setLayout(new BorderLayout());

        // ---------------------------------------------------------
        // TOP PANEL — SEARCH + FILTERS + SORT
        // ---------------------------------------------------------
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton resetButton = new JButton("Reset");
        JButton sortPriceButton = new JButton("Sort by Price");
        JButton sortRatingButton = new JButton("Sort by Rating");

        genreCombo = new JComboBox<>();
        subGenreCombo = new JComboBox<>();
        populateGenreCombo();
        populateSubGenreCombo();

        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(resetButton);

        topPanel.add(new JLabel("Genre:"));
        topPanel.add(genreCombo);

        topPanel.add(new JLabel("SubGenre:"));
        topPanel.add(subGenreCombo);

        topPanel.add(sortPriceButton);
        topPanel.add(sortRatingButton);

        add(topPanel, BorderLayout.NORTH);

        // ---------------------------------------------------------
        // CENTER TABLE
        // ---------------------------------------------------------
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
                "Title", "Author", "Main Genre", "Sub Genre",
                "Type", "Price", "Rating", "Rated"
        });

        booksTable = new JTable(tableModel);
        add(new JScrollPane(booksTable), BorderLayout.CENTER);

        // ---------------------------------------------------------
        // SOUTH PANEL — FORM + BUTTONS
        // ---------------------------------------------------------
        JPanel southPanel = new JPanel(new BorderLayout());
        infoLabel = new JLabel();
        southPanel.add(infoLabel, BorderLayout.NORTH);

        // FORM
        JPanel formPanel = new JPanel(new GridLayout(2, 1));
        JPanel fieldsRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttonsRow = new JPanel(new FlowLayout(FlowLayout.CENTER));

        formTitleField = new JTextField(10);
        formAuthorField = new JTextField(10);
        formMainGenreField = new JTextField(10);
        formSubGenreField = new JTextField(10);
        formTypeField = new JTextField(8);
        formPriceField = new JTextField(6);
        formRatingField = new JTextField(4);
        formRatedField = new JTextField(6);
        formUrlField = new JTextField(15);

        fieldsRow.add(new JLabel("Title:"));   fieldsRow.add(formTitleField);
        fieldsRow.add(new JLabel("Author:"));  fieldsRow.add(formAuthorField);
        fieldsRow.add(new JLabel("MainGenre:")); fieldsRow.add(formMainGenreField);
        fieldsRow.add(new JLabel("SubGenre:")); fieldsRow.add(formSubGenreField);
        fieldsRow.add(new JLabel("Type:"));    fieldsRow.add(formTypeField);
        fieldsRow.add(new JLabel("Price:"));   fieldsRow.add(formPriceField);
        fieldsRow.add(new JLabel("Rating:"));  fieldsRow.add(formRatingField);
        fieldsRow.add(new JLabel("Rated:"));   fieldsRow.add(formRatedField);
        fieldsRow.add(new JLabel("URL:"));     fieldsRow.add(formUrlField);

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit Selected");
        JButton deleteButton = new JButton("Delete Selected");

        JButton exportPDFButton = new JButton("Export to PDF");
        buttonsRow.add(exportPDFButton);

        buttonsRow.add(addButton);
        buttonsRow.add(editButton);
        buttonsRow.add(deleteButton);

        formPanel.add(fieldsRow);
        formPanel.add(buttonsRow);
        southPanel.add(formPanel, BorderLayout.CENTER);

        add(southPanel, BorderLayout.SOUTH);

        loadBooksToTable(manager.getBooks());

        // ---------------------------------------------------------
        // ACTION LISTENERS
        // ---------------------------------------------------------
        searchButton.addActionListener(e -> applyFiltersAndSearch());

        resetButton.addActionListener(e -> {
            searchField.setText("");
            genreCombo.setSelectedIndex(0);
            subGenreCombo.setSelectedIndex(0);
            loadBooksToTable(manager.getBooks());
            clearFormFields();
        });

        genreCombo.addActionListener(e -> applyFiltersAndSearch());
        subGenreCombo.addActionListener(e -> applyFiltersAndSearch());

        sortPriceButton.addActionListener(e -> {
            manager.sortBooksByPrice();
            applyFiltersAndSearch();
        });

        sortRatingButton.addActionListener(e -> {
            manager.sortBooksByRating();
            applyFiltersAndSearch();
        });

        booksTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && booksTable.getSelectedRow() != -1)
                fillFormFromRow(booksTable.getSelectedRow());
        });

        addButton.addActionListener(e -> {
            Book b = buildBookFromForm();
            if (b != null) {
                manager.addBook(b);
                applyFiltersAndSearch();
                clearFormFields();
            }
        });

        editButton.addActionListener(e -> editSelectedBook());
        deleteButton.addActionListener(e -> deleteSelectedBook());

        // ---------------------------------------------------------
        // EXPORT PDF  — CORRECT POSITION
        // ---------------------------------------------------------
        exportPDFButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("books_report.pdf"));

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                PDFExporter.exportBooks(
                        manager.getBooks(),
                        chooser.getSelectedFile().getAbsolutePath()
                );
                JOptionPane.showMessageDialog(this, "PDF exported successfully!");
            }
        });
    }

    // =====================================================================
    // HELPER METHODS
    // =====================================================================

    private void fillFormFromRow(int row) {
        formTitleField.setText(valueAt(row, 0));
        formAuthorField.setText(valueAt(row, 1));
        formMainGenreField.setText(valueAt(row, 2));
        formSubGenreField.setText(valueAt(row, 3));
        formTypeField.setText(valueAt(row, 4));
        formPriceField.setText(valueAt(row, 5));
        formRatingField.setText(valueAt(row, 6));
        formRatedField.setText(valueAt(row, 7));

        Book b = findBookByTitleAuthor(valueAt(row, 0), valueAt(row, 1));
        if (b != null) formUrlField.setText(b.getUrl());
    }

    private void editSelectedBook() {
        int row = booksTable.getSelectedRow();
        if (row == -1) return;

        Book updated = buildBookFromForm();
        if (updated == null) return;

        Book original = findBookByTitleAuthor(valueAt(row, 0), valueAt(row, 1));
        manager.updateBook(manager.getBooks().indexOf(original), updated);

        applyFiltersAndSearch();
    }

    private void deleteSelectedBook() {
        int row = booksTable.getSelectedRow();
        if (row == -1) return;

        Book b = findBookByTitleAuthor(valueAt(row, 0), valueAt(row, 1));
        manager.deleteBook(b);

        applyFiltersAndSearch();
        clearFormFields();
    }

    private Book buildBookFromForm() {
        try {
            return new Book(
                    formTitleField.getText(),
                    formAuthorField.getText(),
                    formMainGenreField.getText(),
                    formSubGenreField.getText(),
                    formTypeField.getText(),
                    Double.parseDouble(formPriceField.getText()),
                    Double.parseDouble(formRatingField.getText()),
                    Integer.parseInt(formRatedField.getText()),
                    formUrlField.getText()
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid data: " + ex.getMessage());
            return null;
        }
    }

    private void clearFormFields() {
        formTitleField.setText("");
        formAuthorField.setText("");
        formMainGenreField.setText("");
        formSubGenreField.setText("");
        formTypeField.setText("");
        formPriceField.setText("");
        formRatingField.setText("");
        formRatedField.setText("");
        formUrlField.setText("");
    }

    private void populateGenreCombo() {
        Set<String> set = new LinkedHashSet<>();
        set.add("All Genres");
        for (Genre g : manager.getGenres()) set.add(g.getTitle());
        for (String s : set) genreCombo.addItem(s);
    }

    private void populateSubGenreCombo() {
        Set<String> set = new LinkedHashSet<>();
        set.add("All SubGenres");
        for (SubGenre sg : manager.getSubGenres()) set.add(sg.getTitle());
        for (String s : set) subGenreCombo.addItem(s);
    }

    private void applyFiltersAndSearch() {
        String keyword = searchField.getText().trim().toLowerCase();
        String genreFilter = (String) genreCombo.getSelectedItem();
        String subFilter = (String) subGenreCombo.getSelectedItem();

        List<Book> filtered = new ArrayList<>();

        for (Book b : manager.getBooks()) {

            if (!"All Genres".equals(genreFilter) &&
                    !b.getMainGenre().equalsIgnoreCase(genreFilter))
                continue;

            if (!"All SubGenres".equals(subFilter) &&
                    !b.getSubGenre().equalsIgnoreCase(subFilter))
                continue;

            if (!keyword.isEmpty() &&
                    !b.getTitle().toLowerCase().contains(keyword) &&
                    !b.getAuthor().toLowerCase().contains(keyword))
                continue;

            filtered.add(b);
        }

        loadBooksToTable(filtered);
    }

    private void loadBooksToTable(List<Book> list) {
        tableModel.setRowCount(0);

        for (Book b : list) {
            tableModel.addRow(new Object[]{
                    b.getTitle(),
                    b.getAuthor(),
                    b.getMainGenre(),
                    b.getSubGenre(),
                    b.getType(),
                    b.getPrice(),
                    b.getRating(),
                    b.getNumberOfPeopleRated()
            });
        }

        infoLabel.setText("Showing: " + list.size() +
                " | Total: " + manager.getBooks().size());
    }

    private String valueAt(int row, int col) {
        return tableModel.getValueAt(row, col).toString();
    }

    private Book findBookByTitleAuthor(String title, String author) {
        for (Book b : manager.getBooks()) {
            if (b.getTitle().equals(title) && b.getAuthor().equals(author))
                return b;
        }
        return null;
    }
}
