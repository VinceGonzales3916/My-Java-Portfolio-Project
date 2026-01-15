package MyLibrarySystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LibraryCRUD {

    // Main window
    JFrame frame;

    // Text fields for input
    JTextField txtID, txtTitle, txtAuthor, txtDate;

    // Table to display book data
    JTable table;

    // Table model
    DefaultTableModel model;

    // Table sorter
    TableRowSorter<DefaultTableModel> sorter;

    // Main data storage
    ArrayList<Book> books = new ArrayList<>();

    // Program entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryCRUD().createUI());
    }

    // Build the UI
    private void createUI() {

        // Main frame
        frame = new JFrame("Library Management System");
        frame.setSize(850, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Input panel
        JPanel topPanel = new JPanel(new GridLayout(2, 4, 10, 5));

        // Labels
        topPanel.add(new JLabel("Book ID:"));
        topPanel.add(new JLabel("Book Title:"));
        topPanel.add(new JLabel("Book Author(s):"));
        topPanel.add(new JLabel("Publication Date (yyyy-MM-dd):"));

        // Text fields
        txtID = new JTextField();
        txtTitle = new JTextField();
        txtAuthor = new JTextField();
        txtDate = new JTextField();

        // Add text fields to panel
        topPanel.add(txtID);
        topPanel.add(txtTitle);
        topPanel.add(txtAuthor);
        topPanel.add(txtDate);

        // Table columns
        String[] columns = {"Book ID", "Title", "Author(s)", "Publication Date"};

        // Table model
        model = new DefaultTableModel(columns, 0);

        // Table
        table = new JTable(model);

        // Sorter
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Sorting for ID and Title (case-insensitive)
        sorter.setComparator(0, (Object o1, Object o2) -> o1.toString().compareToIgnoreCase(o2.toString()));
        sorter.setComparator(1, (Object o1, Object o2) -> o1.toString().compareToIgnoreCase(o2.toString()));

        // Sorting for date
        sorter.setComparator(3, (Object o1, Object o2) -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                return sdf.parse(o1.toString()).compareTo(sdf.parse(o2.toString()));
            } catch (Exception e) {
                return 0;
            }
        });

     // Set default multi-column sort: ID → Title → Date
        sorter.setSortKeys(Arrays.asList(
            new RowSorter.SortKey(0, SortOrder.ASCENDING),
            new RowSorter.SortKey(1, SortOrder.ASCENDING),
            new RowSorter.SortKey(3, SortOrder.ASCENDING)
        ));


        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(table);

        // Button panel
        JPanel bottomPanel = new JPanel();
        JButton btnCreate = new JButton("Create");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        bottomPanel.add(btnCreate);
        bottomPanel.add(btnUpdate);
        bottomPanel.add(btnDelete);

        // Add panels to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Attach events
        createEvent(btnCreate, btnUpdate, btnDelete);

        // Show frame
        frame.setVisible(true);
    }

    // Handle all CRUD events
    private void createEvent(JButton btnCreate, JButton btnUpdate, JButton btnDelete) {

        // Date formatter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        // CREATE button
        btnCreate.addActionListener(e -> {

            // Validate date
            try {
                sdf.parse(txtDate.getText().trim());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date format (yyyy-MM-dd)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Empty field check
            if (txtID.getText().trim().isEmpty() ||
                    txtTitle.getText().trim().isEmpty() ||
                    txtAuthor.getText().trim().isEmpty() ||
                    txtDate.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            // Duplicate ID + Title check
            for (Book b : books) {
                if (b.getId().getId().equalsIgnoreCase(txtID.getText().trim()) &&
                    b.getTitle().getTitle().equalsIgnoreCase(txtTitle.getText().trim()) &&
                    !b.getAuthors().getAuthors().equalsIgnoreCase(txtAuthor.getText().trim())) {
                    continue; // Same ID+Title but different author is allowed
                }
                if (b.getId().getId().equalsIgnoreCase(txtID.getText().trim()) &&
                    b.getTitle().getTitle().equalsIgnoreCase(txtTitle.getText().trim()) &&
                    b.getAuthors().getAuthors().equalsIgnoreCase(txtAuthor.getText().trim())) {
                    JOptionPane.showMessageDialog(frame, "Duplicate book found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Create book object
            Book book = new Book(
                new BookID(txtID.getText().trim()),
                new Title(txtTitle.getText().trim()),
                new Authors(txtAuthor.getText().trim()),
                new PublishedDate(txtDate.getText().trim())
            );

            // Add to list
            books.add(book);

            // Add to table
            model.addRow(new Object[]{
                book.getId().getId(),
                book.getTitle().getTitle(),
                book.getAuthors().getAuthors(),
                book.getPublishedDate().getDate()
            });

            // Sort table
            sorter.sort();

            // Clear fields
            txtID.setText("");
            txtTitle.setText("");
            txtAuthor.setText("");
            txtDate.setText("");
        });

        // UPDATE button
        btnUpdate.addActionListener(e -> {

            int viewRow = table.getSelectedRow();
            if (viewRow < 0) {
                JOptionPane.showMessageDialog(frame, "Select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int row = table.convertRowIndexToModel(viewRow);
            Book book = books.get(row);

            // Duplicate check for update
            for (Book b : books) {
                if (b == book) continue; // Skip current row
                if (b.getId().getId().equalsIgnoreCase(txtID.getText().trim()) &&
                    b.getTitle().getTitle().equalsIgnoreCase(txtTitle.getText().trim()) &&
                    b.getAuthors().getAuthors().equalsIgnoreCase(txtAuthor.getText().trim())) {
                    JOptionPane.showMessageDialog(frame, "Duplicate book found!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Update book
            book.getId().setId(txtID.getText().trim());
            book.getTitle().setTitle(txtTitle.getText().trim());
            book.getAuthors().setAuthors(txtAuthor.getText().trim());
            book.getPublishedDate().setDate(txtDate.getText().trim());

            // Update table
            model.setValueAt(book.getId().getId(), row, 0);
            model.setValueAt(book.getTitle().getTitle(), row, 1);
            model.setValueAt(book.getAuthors().getAuthors(), row, 2);
            model.setValueAt(book.getPublishedDate().getDate(), row, 3);

            // Re-sort
            sorter.sort();
        });

        // DELETE button
        btnDelete.addActionListener(e -> {

            int viewRow = table.getSelectedRow();
            if (viewRow < 0) {
                JOptionPane.showMessageDialog(frame, "Select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int row = table.convertRowIndexToModel(viewRow);

            // Remove from list and table
            books.remove(row);
            model.removeRow(row);

            // Clear fields
            txtID.setText("");
            txtTitle.setText("");
            txtAuthor.setText("");
            txtDate.setText("");
        });

        // TABLE row click event
        table.getSelectionModel().addListSelectionListener(e -> {

            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int row = table.convertRowIndexToModel(viewRow);
                Book book = books.get(row);

                txtID.setText(book.getId().getId());
                txtTitle.setText(book.getTitle().getTitle());
                txtAuthor.setText(book.getAuthors().getAuthors());
                txtDate.setText(book.getPublishedDate().getDate());
            }
        });
    }
}
