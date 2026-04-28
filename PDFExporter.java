import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;

public class PDFExporter {

    // ==========================================================
    // EXPORT BOOKS
    // ==========================================================
    public static void exportBooks(List<Book> books, String filePath) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph(
                    "Books Report",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)
            ));
            document.add(new Paragraph("Total books: " + books.size()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);

            addHeader(table, "Title");
            addHeader(table, "Author");
            addHeader(table, "Main Genre");
            addHeader(table, "Sub Genre");
            addHeader(table, "Type");
            addHeader(table, "Price");
            addHeader(table, "Rating");
            addHeader(table, "Rated");

            for (Book b : books) {
                table.addCell(b.getTitle());
                table.addCell(b.getAuthor());
                table.addCell(b.getMainGenre());
                table.addCell(b.getSubGenre());
                table.addCell(b.getType());
                table.addCell(String.valueOf(b.getPrice()));
                table.addCell(String.valueOf(b.getRating()));
                table.addCell(String.valueOf(b.getNumberOfPeopleRated()));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================================
    // EXPORT GENRES
    // ==========================================================
    public static void exportGenres(List<Genre> genres, String filePath) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph(
                    "Genres Report",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)
            ));
            document.add(new Paragraph("Total genres: " + genres.size()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            addHeader(table, "Title");
            addHeader(table, "Number of SubGenres");
            addHeader(table, "URL");

            for (Genre g : genres) {
                table.addCell(g.getTitle());
                table.addCell(String.valueOf(g.getNumberOfSubGenres()));
                table.addCell(g.getUrl());
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================================
    // EXPORT SUBGENRES
    // ==========================================================
    public static void exportSubGenres(List<SubGenre> subGenres, String filePath) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph(
                    "SubGenres Report",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)
            ));
            document.add(new Paragraph("Total subgenres: " + subGenres.size()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            addHeader(table, "Title");
            addHeader(table, "Main Genre");
            addHeader(table, "Number of Books");
            addHeader(table, "URL");

            for (SubGenre sg : subGenres) {
                table.addCell(sg.getTitle());
                table.addCell(sg.getMainGenre());
                table.addCell(String.valueOf(sg.getNumberOfBooks()));
                table.addCell(sg.getUrl());
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================================================
    // HEADER CELL HELPER
    // ==========================================================
    private static void addHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(
                new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12))
        );
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
    }
}
