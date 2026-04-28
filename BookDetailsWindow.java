import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BookDetailsWindow extends JFrame {

    public BookDetailsWindow(Book book) {

        setTitle(book.getTitle());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        panel.add(new JLabel("Title: " + book.getTitle()));
        panel.add(new JLabel("Author: " + book.getAuthor()));
        panel.add(new JLabel("Main Genre: " + book.getMainGenre()));
        panel.add(new JLabel("Sub Genre: " + book.getSubGenre()));
        panel.add(new JLabel("Type: " + book.getType()));
        panel.add(new JLabel("Price: " + book.getPrice()));
        panel.add(new JLabel("Rating: " + book.getRating()));
        panel.add(new JLabel("People Rated: " + book.getNumberOfPeopleRated()));

        add(panel, BorderLayout.CENTER);

        JButton openUrlButton = new JButton("Open Amazon Page");
        openUrlButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI(book.getUrl()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error opening URL");
            }
        });

        add(openUrlButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
