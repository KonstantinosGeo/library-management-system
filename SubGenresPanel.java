import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SubGenresPanel extends JPanel {

    private LibraryManager manager;
    private JTable table;
    private DefaultTableModel model;

    private JTextField searchField;
    private JLabel statsLabel;

    private JTextField titleField;
    private JTextField mainGenreField;
    private JTextField booksField;
    private JTextField urlField;

    public SubGenresPanel(LibraryManager manager) {

        this.manager = manager;
        setLayout(new BorderLayout());

        // =============================
        // TOP: SEARCH
        // =============================
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        JButton resetBtn = new JButton("Reset");

        top.add(new JLabel("Search SubGenre:"));
        top.add(searchField);
        top.add(searchBtn);
        top.add(resetBtn);

        add(top, BorderLayout.NORTH);

        // =============================
        // TABLE
        // =============================
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Title", "Main Genre", "Books", "URL"
        });

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadSubGenres(manager.getSubGenres());

        // =============================
        // BOTTOM: FORM + BUTTONS
        // =============================
        JPanel bottom = new JPanel(new GridLayout(2, 1));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        titleField = new JTextField(10);
        mainGenreField = new JTextField(10);
        booksField = new JTextField(6);
        urlField = new JTextField(12);

        form.add(new JLabel("Title:"));
        form.add(titleField);

        form.add(new JLabel("Main Genre:"));
        form.add(mainGenreField);

        form.add(new JLabel("Books:"));
        form.add(booksField);

        form.add(new JLabel("URL:"));
        form.add(urlField);

        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(deleteBtn);

        // ===== PDF Export Button =====
        JButton exportPDF = new JButton("Export to PDF");
        buttons.add(exportPDF);

        bottom.add(form);
        bottom.add(buttons);
        JButton exportPDFButton = new JButton("Export to PDF");
        buttons.add(exportPDFButton);

        exportPDFButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("subgenres_report.pdf"));

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                PDFExporter.exportSubGenres(
                        manager.getSubGenres(),
                        chooser.getSelectedFile().getAbsolutePath()
                );

                JOptionPane.showMessageDialog(this, "SubGenres PDF exported successfully!");
            }
        });

        add(bottom, BorderLayout.SOUTH);

        // =============================
        // STATS LABEL
        // =============================
        statsLabel = new JLabel("Stats will appear here.");
        add(statsLabel, BorderLayout.PAGE_END);

        // =============================
        // ACTION LISTENERS
        // =============================

        searchBtn.addActionListener(e -> {
            String key = searchField.getText().trim().toLowerCase();

            List<SubGenre> filtered = new ArrayList<>();
            for (SubGenre sg : manager.getSubGenres()) {
                if (sg.getTitle().toLowerCase().contains(key)) {
                    filtered.add(sg);
                }
            }

            loadSubGenres(filtered);
        });

        resetBtn.addActionListener(e -> {
            searchField.setText("");
            loadSubGenres(manager.getSubGenres());
            clearForm();
        });

        // Table selection → form + stats
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {

                int row = table.getSelectedRow();
                if (row != -1) {
                    titleField.setText(model.getValueAt(row, 0).toString());
                    mainGenreField.setText(model.getValueAt(row, 1).toString());
                    booksField.setText(model.getValueAt(row, 2).toString());
                    urlField.setText(model.getValueAt(row, 3).toString());

                    updateStats(titleField.getText());
                }
            }
        });

        // ADD
        addBtn.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String mainGenre = mainGenreField.getText();
                int bookCount = Integer.parseInt(booksField.getText());
                String url = urlField.getText();

                SubGenre sg = new SubGenre(title, mainGenre, bookCount, url);
                manager.getSubGenres().add(sg);

                loadSubGenres(manager.getSubGenres());
                clearForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid data.");
            }
        });

        // EDIT
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            SubGenre sg = manager.getSubGenres().get(row);

            sg.setTitle(titleField.getText());
            sg.setMainGenre(mainGenreField.getText());
            sg.setNumberOfBooks(Integer.parseInt(booksField.getText()));
            sg.setUrl(urlField.getText());

            loadSubGenres(manager.getSubGenres());
        });

        // DELETE
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            manager.getSubGenres().remove(row);
            loadSubGenres(manager.getSubGenres());
            clearForm();
        });

        // PDF EXPORT
        exportPDF.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("subgenres_report.pdf"));

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                PDFExporter.exportSubGenres(manager.getSubGenres(),
                        chooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this, "PDF Exported Successfully!");
            }
        });
    }

    // =======================================================
    // HELPERS
    // =======================================================
    private void loadSubGenres(List<SubGenre> list) {
        model.setRowCount(0);

        for (SubGenre sg : list) {
            model.addRow(new Object[]{
                    sg.getTitle(),
                    sg.getMainGenre(),
                    sg.getNumberOfBooks(),
                    sg.getUrl()
            });
        }
    }

    private void clearForm() {
        titleField.setText("");
        mainGenreField.setText("");
        booksField.setText("");
        urlField.setText("");
    }

    private void updateStats(String subgenreName) {

        int count = 0;
        double avgPrice = 0;
        double avgRating = 0;
        int ratingCounter = 0;

        for (Book b : manager.getBooks()) {
            if (b.getSubGenre().equalsIgnoreCase(subgenreName)) {

                count++;
                avgPrice += b.getPrice();

                if (b.getRating() > 0) {
                    avgRating += b.getRating();
                    ratingCounter++;
                }
            }
        }

        if (count > 0) avgPrice /= count;
        if (ratingCounter > 0) avgRating /= ratingCounter;

        statsLabel.setText(
                "Books: " + count +
                        " | Avg Price: " + String.format("%.2f", avgPrice) +
                        " | Avg Rating: " + String.format("%.2f", avgRating)
        );
    }
}
