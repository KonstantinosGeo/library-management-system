import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GenresPanel extends JPanel {

    private LibraryManager manager;
    private JTable table;
    private DefaultTableModel model;

    private JTextField searchField;
    private JLabel statsLabel;

    private JTextField titleField;
    private JTextField subsField;
    private JTextField urlField;

    public GenresPanel(LibraryManager manager) {

        this.manager = manager;
        setLayout(new BorderLayout());

        // =============================
        // TOP: SEARCH + CONTROLS
        // =============================
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        JButton resetBtn = new JButton("Reset");

        top.add(new JLabel("Search Genre:"));
        top.add(searchField);
        top.add(searchBtn);
        top.add(resetBtn);

        add(top, BorderLayout.NORTH);

        // =============================
        // TABLE
        // =============================
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Title", "SubGenres", "URL"});

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadGenres(manager.getGenres());

        // =============================
        // BOTTOM: FORM + CRUD + PDF
        // =============================
        JPanel bottom = new JPanel(new GridLayout(2, 1));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        titleField = new JTextField(10);
        subsField = new JTextField(5);
        urlField = new JTextField(15);

        form.add(new JLabel("Title:"));
        form.add(titleField);

        form.add(new JLabel("SubGenres:"));
        form.add(subsField);

        form.add(new JLabel("URL:"));
        form.add(urlField);

        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        // ADD buttons
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
            chooser.setSelectedFile(new java.io.File("genres_report.pdf"));

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                PDFExporter.exportGenres(
                        manager.getGenres(),
                        chooser.getSelectedFile().getAbsolutePath()
                );

                JOptionPane.showMessageDialog(this, "Genres PDF exported successfully!");
            }
        });

        add(bottom, BorderLayout.SOUTH);

        // =============================
        // STATS LABEL
        // =============================
        statsLabel = new JLabel("Stats will appear here");
        add(statsLabel, BorderLayout.PAGE_END);

        // =============================
        // ACTION LISTENERS
        // =============================
        searchBtn.addActionListener(e -> {
            String key = searchField.getText().trim().toLowerCase();

            List<Genre> filtered = new ArrayList<>();
            for (Genre g : manager.getGenres()) {
                if (g.getTitle().toLowerCase().contains(key)) {
                    filtered.add(g);
                }
            }
            loadGenres(filtered);
        });

        resetBtn.addActionListener(e -> {
            searchField.setText("");
            loadGenres(manager.getGenres());
            clearForm();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row != -1) {

                    titleField.setText(model.getValueAt(row, 0).toString());
                    subsField.setText(model.getValueAt(row, 1).toString());
                    urlField.setText(model.getValueAt(row, 2).toString());

                    updateStats(titleField.getText());
                }
            }
        });

        addBtn.addActionListener(e -> {
            try {
                String title = titleField.getText();
                int subs = Integer.parseInt(subsField.getText());
                String url = urlField.getText();

                Genre g = new Genre(title, subs, url);
                manager.getGenres().add(g);

                loadGenres(manager.getGenres());
                clearForm();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Inputs");
            }
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            Genre g = manager.getGenres().get(row);

            g.setTitle(titleField.getText());
            g.setNumberOfSubGenres(Integer.parseInt(subsField.getText()));
            g.setUrl(urlField.getText());

            loadGenres(manager.getGenres());
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            manager.getGenres().remove(row);
            loadGenres(manager.getGenres());
            clearForm();
        });

        // =============================
        // PDF EXPORT
        // =============================
        exportPDF.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("genres_report.pdf"));

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

                PDFExporter.exportGenres(
                        manager.getGenres(),
                        chooser.getSelectedFile().getAbsolutePath()
                );

                JOptionPane.showMessageDialog(this,
                        "PDF Exported Successfully!");
            }
        });
    }

    // =======================================================
    // HELPERS
    // =======================================================

    private void loadGenres(List<Genre> list) {
        model.setRowCount(0);

        for (Genre g : list) {
            model.addRow(new Object[]{
                    g.getTitle(),
                    g.getNumberOfSubGenres(),
                    g.getUrl()
            });
        }
    }

    private void clearForm() {
        titleField.setText("");
        subsField.setText("");
        urlField.setText("");
    }

    private void updateStats(String genreName) {

        int count = 0;
        double avgPrice = 0;
        double avgRating = 0;
        int ratingCount = 0;

        for (Book b : manager.getBooks()) {
            if (b.getMainGenre().equalsIgnoreCase(genreName)) {

                count++;
                avgPrice += b.getPrice();

                if (b.getRating() > 0) {
                    avgRating += b.getRating();
                    ratingCount++;
                }
            }
        }

        if (count > 0) avgPrice /= count;
        if (ratingCount > 0) avgRating /= ratingCount;

        statsLabel.setText(
                "Books: " + count +
                        " | Avg Price: " + String.format("%.2f", avgPrice) +
                        " | Avg Rating: " + String.format("%.2f", avgRating)
        );
    }
}
