import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.PriorityQueue;

public class Menu extends JFrame implements ActionListener {


    static final Color BG_MAIN   = new Color(0xF5F0EB);   // warm off-white
    static final Color BG_SIDEBAR= new Color(0x2C3E50);   // deep navy
    static final Color BG_CARD   = new Color(0xFFFFFF);   // white cards
    static final Color ACCENT    = new Color(0xE67E22);   // warm amber
    static final Color ACCENT2   = new Color(0x27AE60);   // soft green (confirm)
    static final Color TEXT_DARK = new Color(0x1A1A2E);   // near-black
    static final Color TEXT_SIDE = new Color(0xECF0F1);   // sidebar text
    static final Color TEXT_MUTED= new Color(0x7F8C8D);   // muted labels
    static final Color HEADER_BG = new Color(0x34495E);   // table header
    static final Color ROW_ALT   = new Color(0xFAF6F2);   // alternating row

    BST bst = new BST();
    BorrowedBook B = new BorrowedBook(bst);

    CardLayout cl = new CardLayout();
    JPanel cardPanel = new JPanel(cl);
    JTextField returnDateField;

    JPanel booksPanel;
    JPanel borrowBooksPanel;
    JPanel addNewBooksPanel;
    JPanel editBooksPanel;
    JPanel deleteBooksPanel;
    JPanel returnBooksPanel;
    JPanel studentsPanel;
    JPanel editStudentsPanel;
    JPanel statusPanel;
    JPanel waitListPanel;

    DefaultTableModel model;
    DefaultTableModel booksModel;
    DefaultTableModel studentModel;
    DefaultTableModel waitListModel;


    private JLabel addLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(TEXT_DARK);
        return label;
    }

    private JLabel pageTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 48));
        label.setForeground(TEXT_DARK);
        label.setBorder(new EmptyBorder(20, 30, 10, 0));
        return label;
    }

    private JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFocusable(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 40));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    private JTextField makeField() {
        JTextField f = new JTextField(28);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        f.setBackground(Color.WHITE);
        f.setForeground(TEXT_DARK);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xCCC5BB), 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        return f;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setForeground(TEXT_DARK);
        table.setBackground(BG_CARD);
        table.setGridColor(new Color(0xE8E0D8));
        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();
        header.setBackground(HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 36));
        header.setResizingAllowed(false);
    }

    private void resetPanel(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JTextField) {
                if (c != returnDateField) ((JTextField) c).setText("");
            } else if (c instanceof JRadioButton) {
                ((JRadioButton) c).setSelected(false);
            } else if (c instanceof JPanel) {
                resetPanel((JPanel) c);
            }
        }
    }


    private JButton sidebarBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFocusable(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setBackground(BG_SIDEBAR);
        btn.setForeground(TEXT_SIDE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(10, 20, 10, 10));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(ACCENT);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(BG_SIDEBAR);
            }
        });
        return btn;
    }


    JPanel booksPanel() {
        String[] cols = {"Title", "Author", "ISBN", "Total", "Available"};
        booksModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(booksModel);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_MAIN);

        JTextField searchField = makeField();
        JButton searchBtn = makeButton("Search", ACCENT);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridwidth = 3; c.weightx = 0; c.weighty = 0;
        c.gridx = 0; c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        panel.add(pageTitle("Book Catalog"), c);

        c.gridwidth = 1; c.weightx = 1;
        c.gridx = 0; c.gridy = 1;
        c.insets = new Insets(0, 20, 10, 0);
        panel.add(new JLabel(), c);
        c.weightx = 0; c.gridx = 1; c.gridy = 1;
        panel.add(searchBtn, c);
        c.gridx = 2; c.gridy = 1;
        panel.add(searchField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1; c.weighty = 1; c.gridwidth = 3;
        c.gridx = 0; c.gridy = 2;
        c.insets = new Insets(0, 20, 20, 20);
        panel.add(scroll, c);

        searchBtn.addActionListener(e -> {
            try {
                int isbn = Integer.parseInt(searchField.getText());
                booksModel.setRowCount(0);
                Node cur = bst.search(isbn);
                if (cur == null) {
                    JOptionPane.showMessageDialog(null, "No book found");
                } else {
                    booksModel.addRow(new Object[]{cur.title, cur.author, cur.value, cur.totalCopies, cur.copies});
                }
            } catch (Exception ignored) {}
        });
        return panel;
    }


    JPanel addNewBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        panel.add(pageTitle("Add New Book"), BorderLayout.NORTH);

        JTextField nameField   = makeField();
        JTextField isbnField   = makeField();
        JTextField authorField = makeField();
        JTextField numberField = makeField();
        JButton confirmBtn = makeButton("Confirm", ACCENT2);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG_MAIN);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 10, 8, 10);
        c.anchor = GridBagConstraints.WEST;

        addFormRow(center, c, 0, "Title:",    nameField);
        addFormRow(center, c, 1, "ISBN:",     isbnField);
        addFormRow(center, c, 2, "Author:",   authorField);
        addFormRow(center, c, 3, "Copies:", numberField);
        c.gridx = 1; c.gridy = 4; center.add(confirmBtn, c);

        panel.add(center, BorderLayout.CENTER);

        confirmBtn.addActionListener(e -> {
            try {
                String title  = nameField.getText().trim();
                int isbn      = Integer.parseInt(isbnField.getText().trim());
                String author = authorField.getText().trim();
                int qty       = Integer.parseInt(numberField.getText().trim());
                if (title.isEmpty() || author.isEmpty() || isbn <= 0 || qty <= 0) {
                    JOptionPane.showMessageDialog(panel, "Please fill all fields correctly"); return;
                }
                if (bst.search(isbn) != null) {
                    JOptionPane.showMessageDialog(panel, "Book already exists"); return;
                }
                bst.insert(isbn, qty, author, title);
                JOptionPane.showMessageDialog(panel, "Book Added");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid ISBN or Quantity");
            }
        });
        return panel;
    }


    JPanel editBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        panel.add(pageTitle("Edit Book"), BorderLayout.NORTH);

        JTextField isbnField   = makeField();
        JTextField copiesField = makeField();
        JButton confirmBtn = makeButton("Confirm", ACCENT2);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG_MAIN);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 10, 8, 10);
        c.anchor = GridBagConstraints.WEST;

        addFormRow(center, c, 0, "ISBN:",             isbnField);
        addFormRow(center, c, 1, "Number of Copies:", copiesField);
        c.gridx = 1; c.gridy = 2; center.add(confirmBtn, c);

        panel.add(center, BorderLayout.CENTER);

        confirmBtn.addActionListener(e -> {
            try {
                int isbn   = Integer.parseInt(isbnField.getText().trim());
                int copies = Integer.parseInt(copiesField.getText().trim());
                Node n = bst.search(isbn);
                if (n == null) { JOptionPane.showMessageDialog(panel, "Wrong ISBN"); return; }
                n.totalCopies = copies;
                n.copies = copies;
                JOptionPane.showMessageDialog(panel, "Copies updated to " + copies);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input");
            }
        });
        return panel;
    }


    JPanel deleteBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        panel.add(pageTitle("Delete Book"), BorderLayout.NORTH);

        JTextField isbnField = makeField();
        JButton confirmBtn = makeButton("Delete", new Color(0xC0392B));

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG_MAIN);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 10, 8, 10);
        c.anchor = GridBagConstraints.WEST;

        addFormRow(center, c, 0, "ISBN:", isbnField);
        c.gridx = 1; c.gridy = 1; center.add(confirmBtn, c);

        panel.add(center, BorderLayout.CENTER);

        confirmBtn.addActionListener(e -> {
            try {
                int isbn = Integer.parseInt(isbnField.getText().trim());
                Node n = bst.search(isbn);
                if (n == null) { JOptionPane.showMessageDialog(panel, "Wrong ISBN"); return; }
                bst.delete(isbn);
                JOptionPane.showMessageDialog(panel, "Book deleted");
            } catch (NumberFormatException ex) {}
        });
        return panel;
    }


    JPanel borrowBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        panel.add(pageTitle("Borrow Book"), BorderLayout.NORTH);

        JTextField nameField = makeField();
        JTextField ageField  = makeField();
        JTextField isbnField = makeField();

        returnDateField = makeField();
        returnDateField.setEditable(false);
        returnDateField.setText(LocalDate.now().plusDays(7).toString());

        JRadioButton grad    = new JRadioButton("Graduated");
        JRadioButton notGrad = new JRadioButton("Not Graduated");
        grad.setBackground(BG_MAIN);
        notGrad.setBackground(BG_MAIN);
        grad.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        notGrad.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        ButtonGroup bg = new ButtonGroup();
        bg.add(grad); bg.add(notGrad);

        JButton confirmBtn = makeButton("Confirm", ACCENT2);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG_MAIN);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 10, 8, 10);
        c.anchor = GridBagConstraints.WEST;

        addFormRow(center, c, 0, "Student Name:", nameField);
        addFormRow(center, c, 1, "Student Age:",  ageField);

        c.gridx = 0; c.gridy = 2; center.add(addLabel("Status:"), c);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        radioPanel.setBackground(BG_MAIN);
        radioPanel.add(grad); radioPanel.add(notGrad);
        c.gridx = 1; center.add(radioPanel, c);

        addFormRow(center, c, 3, "ISBN:",        isbnField);
        addFormRow(center, c, 4, "Return Date:", returnDateField);
        c.gridx = 1; c.gridy = 5; center.add(confirmBtn, c);

        panel.add(center, BorderLayout.CENTER);

        confirmBtn.addActionListener(e -> {
            try {
                String name  = nameField.getText().trim();
                int age      = Integer.parseInt(ageField.getText().trim());
                int isbn     = Integer.parseInt(isbnField.getText().trim());
                String rDate = returnDateField.getText().trim();
                boolean graduated = grad.isSelected();

                if (name.isEmpty() || age <= 0 || isbn <= 0) {
                    JOptionPane.showMessageDialog(panel, "Please fill all fields"); return;
                }
                int state = B.borrowedBookDate(name, age, graduated, isbn, rDate);
                String[] msgs = {"", "Book not found!", "Added to wait list", "Limit reached!", "Book Borrowed Successfully"};
                JOptionPane.showMessageDialog(panel, msgs[state]);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input!");
            }
        });
        return panel;
    }


    JPanel returnBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        panel.add(pageTitle("Return Book"), BorderLayout.NORTH);

        JTextField nameField = makeField();
        JTextField isbnField = makeField();
        JButton confirmBtn = makeButton("Confirm", ACCENT2);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG_MAIN);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 10, 8, 10);
        c.anchor = GridBagConstraints.WEST;

        addFormRow(center, c, 0, "Student Name:", nameField);
        addFormRow(center, c, 1, "ISBN:",         isbnField);
        c.gridx = 1; c.gridy = 2; center.add(confirmBtn, c);

        panel.add(center, BorderLayout.CENTER);

        confirmBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                int isbn    = Integer.parseInt(isbnField.getText().trim());
                if (name.isEmpty() || isbn <= 0) {
                    JOptionPane.showMessageDialog(panel, "Please fill all fields"); return;
                }
                int state = B.returnBook(name, isbn);
                if (state == 1) JOptionPane.showMessageDialog(panel, "Book not found!");
                if (state == 2) JOptionPane.showMessageDialog(panel, "ERROR!");
                if (state == 3) JOptionPane.showMessageDialog(panel, "Thank you for returning the book");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input!");
            }
        });
        return panel;
    }


    JPanel studentsPanel() {
        String[] cols = {"Student Name", "Borrow Date", "Return Date"};
        studentModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(studentModel);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_MAIN);

        JTextField searchField = makeField();
        JButton searchBtn = makeButton("Search", ACCENT);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridwidth = 3; c.weightx = 0; c.weighty = 0;
        c.gridx = 0; c.gridy = 0;
        panel.add(pageTitle("Students"), c);

        c.gridwidth = 1; c.weightx = 1;
        c.gridx = 0; c.gridy = 1; c.insets = new Insets(0, 20, 10, 0);
        panel.add(new JLabel(), c);
        c.weightx = 0; c.gridx = 1; panel.add(searchBtn, c);
        c.gridx = 2; panel.add(searchField, c);

        c.fill = GridBagConstraints.BOTH; c.weightx = 1; c.weighty = 1; c.gridwidth = 3;
        c.gridx = 0; c.gridy = 2; c.insets = new Insets(0, 20, 20, 20);
        panel.add(scroll, c);

        searchBtn.addActionListener(e -> {
            String name = searchField.getText().trim();
            for (Borrowed b : B.borrowedList) {
                if (b.name.equals(name)) {
                    studentModel.setRowCount(0);
                    studentModel.addRow(new Object[]{b.name, b.Date, b.returnTime});
                    break;
                }
            }
        });
        return panel;
    }


    JPanel editStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        panel.add(pageTitle("Update Record"), BorderLayout.NORTH);

        JTextField oldNameField   = makeField();
        JTextField newNameField   = makeField();
        JTextField wrongIsbnField = makeField();
        JTextField isbnField      = makeField();
        JButton confirmBtn = makeButton("Confirm", ACCENT2);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG_MAIN);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 10, 8, 10);
        c.anchor = GridBagConstraints.WEST;

        addFormRow(center, c, 0, "Old Name:", oldNameField);
        addFormRow(center, c, 1, "New Name:", newNameField);
        addFormRow(center, c, 2, "Old ISBN:", wrongIsbnField);
        addFormRow(center, c, 3, "New ISBN:", isbnField);
        c.gridx = 1; c.gridy = 4; center.add(confirmBtn, c);

        panel.add(center, BorderLayout.CENTER);

        confirmBtn.addActionListener(e -> {
            try {
                String oldName = oldNameField.getText().trim();
                String newName = newNameField.getText().trim();
                int wrongIsbn  = Integer.parseInt(wrongIsbnField.getText().trim());
                int isbn       = Integer.parseInt(isbnField.getText().trim());

                if (oldName.isEmpty() || newName.isEmpty() || wrongIsbn <= 0 || isbn <= 0) {
                    JOptionPane.showMessageDialog(panel, "Please fill all fields"); return;
                }
                int n = B.updates(wrongIsbn, isbn, oldName, newName);
                if (n == 0) JOptionPane.showMessageDialog(panel, "Update failed!");
                if (n == 1) JOptionPane.showMessageDialog(panel, "Update successful");
                if (n == 2) JOptionPane.showMessageDialog(panel, "Record not found");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid ISBN");
            }
        });
        return panel;
    }


    JPanel statusPanel() {
        String[] cols = {"Author", "Book Title"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_MAIN);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridwidth = 3; c.weightx = 0; c.weighty = 0;
        c.gridx = 0; c.gridy = 0;
        panel.add(pageTitle("Top Rated"), c);

        c.weightx = 1; c.weighty = 1; c.gridwidth = 3;
        c.gridx = 0; c.gridy = 1; c.insets = new Insets(0, 20, 20, 20);
        panel.add(scroll, c);
        return panel;
    }


    JPanel showBorrowedPanel() {
        String[] cols = {"Name", "Age", "Graduated", "ISBN", "Return Dates", "Borrow Date"};
        DefaultTableModel borrowedModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(borrowedModel);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_MAIN);

        JButton refreshBtn = makeButton("Show All", ACCENT);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridwidth = 3; c.weightx = 0; c.weighty = 0;
        c.gridx = 0; c.gridy = 0;
        panel.add(pageTitle("Borrowed List"), c);

        c.gridwidth = 1; c.weightx = 1;
        c.gridx = 0; c.gridy = 1; c.insets = new Insets(0, 20, 10, 0);
        panel.add(new JLabel(), c);
        c.weightx = 0; c.gridx = 1; panel.add(refreshBtn, c);

        c.fill = GridBagConstraints.BOTH; c.weightx = 1; c.weighty = 1; c.gridwidth = 3;
        c.gridx = 0; c.gridy = 2; c.insets = new Insets(0, 20, 20, 20);
        panel.add(scroll, c);

        refreshBtn.addActionListener(e -> {
            borrowedModel.setRowCount(0);
            for (Borrowed b : B.borrowedList) {
                borrowedModel.addRow(new Object[]{
                        b.name, b.age,
                        b.graduated ? "Yes" : "No",
                        b.bookId.toString(),
                        b.returnTime.toString(),
                        b.Date != null ? b.Date.toString() : "N/A"
                });
            }
        });
        return panel;
    }


    JPanel waitListPanel() {
        String[] cols = {"Name", "Age", "Graduated", "Arrival Order"};
        waitListModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(waitListModel);
        styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_CARD);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_MAIN);

        JTextField isbnField = makeField();
        JButton searchBtn = makeButton("Search", ACCENT);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridwidth = 3; c.weightx = 0; c.weighty = 0;
        c.gridx = 0; c.gridy = 0;
        panel.add(pageTitle("Wait List"), c);

        c.gridwidth = 1; c.weightx = 1;
        c.gridx = 0; c.gridy = 1; c.insets = new Insets(0, 20, 10, 0);
        panel.add(new JLabel(), c);
        c.weightx = 0; c.gridx = 1; panel.add(searchBtn, c);
        c.gridx = 2; panel.add(isbnField, c);

        c.fill = GridBagConstraints.BOTH; c.weightx = 1; c.weighty = 1; c.gridwidth = 3;
        c.gridx = 0; c.gridy = 2; c.insets = new Insets(0, 20, 20, 20);
        panel.add(scroll, c);

        searchBtn.addActionListener(e -> {
            try {
                int isbn = Integer.parseInt(isbnField.getText().trim());
                Node n = bst.search(isbn);
                waitListModel.setRowCount(0);
                if (n == null) {
                    JOptionPane.showMessageDialog(null, "Book not found!"); return;
                }
                PriorityQueue<Students> temp = new PriorityQueue<>(n.queue);
                while (!temp.isEmpty()) {
                    Students s = temp.poll();
                    waitListModel.addRow(new Object[]{s.name, s.age, s.graduated ? "Yes" : "No", s.arrivalOrder});
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid ISBN!");
            }
        });
        return panel;
    }


    JPanel listPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BG_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(210, 0));
        // right border separates sidebar from content
        sidebar.setBorder(new MatteBorder(0, 0, 0, 3, ACCENT));


        JLabel menuTitle = new JLabel("  Library");
        menuTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        menuTitle.setForeground(ACCENT);
        menuTitle.setBorder(new EmptyBorder(20, 15, 5, 0));
        menuTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(menuTitle);


        sidebar.add(makeDivider());


        sidebar.add(sectionLabel("BOOKS"));
        JButton booksBtn      = sidebarBtn("  Book Catalog");
        JButton addBookBtn    = sidebarBtn("  Add New Book");
        JButton editBookBtn   = sidebarBtn("  Edit Book");
        JButton deleteBookBtn = sidebarBtn("  Delete Book");
        JButton borrowBtn     = sidebarBtn("  Borrow Book");
        JButton returnBtn     = sidebarBtn("  Return Book");

        sidebar.add(booksBtn);
        sidebar.add(addBookBtn);
        sidebar.add(editBookBtn);
        sidebar.add(deleteBookBtn);
        sidebar.add(borrowBtn);
        sidebar.add(returnBtn);

        sidebar.add(makeDivider());


        sidebar.add(sectionLabel("STUDENTS"));
        JButton studentsBtn   = sidebarBtn("  Students");
        JButton updateBtn     = sidebarBtn("  Update Record");
        JButton borrowedBtn   = sidebarBtn("  Borrowed List");
        JButton waitListBtn   = sidebarBtn("  Wait List");

        sidebar.add(studentsBtn);
        sidebar.add(updateBtn);
        sidebar.add(borrowedBtn);
        sidebar.add(waitListBtn);

        sidebar.add(makeDivider());

        // Section: Reports
        sidebar.add(sectionLabel("REPORTS"));
        JButton statusBtn = sidebarBtn("  Top Rated");
        sidebar.add(statusBtn);

        sidebar.add(Box.createVerticalGlue());

        // ── Action Listeners ──
        booksBtn.addActionListener(e -> {
            booksModel.setRowCount(0);
            List<Node> rows = bst.showBooks();
            for (Node n : rows)
                booksModel.addRow(new Object[]{n.title, n.author, n.value, n.totalCopies, n.copies});
            cl.show(cardPanel, "books");
        });
        addBookBtn.addActionListener(e -> {
            resetPanel(addNewBooksPanel); cl.show(cardPanel, "addNewBooks");
        });
        editBookBtn.addActionListener(e -> {
            resetPanel(editBooksPanel); cl.show(cardPanel, "editBooks");
        });
        deleteBookBtn.addActionListener(e -> {
            resetPanel(deleteBooksPanel); cl.show(cardPanel, "deleteBooks");
        });
        borrowBtn.addActionListener(e -> {
            resetPanel(borrowBooksPanel); cl.show(cardPanel, "borrowBooks");
        });
        returnBtn.addActionListener(e -> {
            resetPanel(returnBooksPanel); cl.show(cardPanel, "returnBooks");
        });
        studentsBtn.addActionListener(e -> {
            studentModel.setRowCount(0);
            for (Borrowed s : B.borrowedList)
                studentModel.addRow(new Object[]{s.name, s.Date, s.returnTime});
            cl.show(cardPanel, "students");
        });
        updateBtn.addActionListener(e -> {
            resetPanel(editStudentsPanel); cl.show(cardPanel, "editStudents");
        });
        borrowedBtn.addActionListener(e -> cl.show(cardPanel, "showBorrowed"));
        waitListBtn.addActionListener(e -> {
            waitListModel.setRowCount(0); cl.show(cardPanel, "waitList");
        });
        statusBtn.addActionListener(e -> {
            try {
                model.setRowCount(0);
                for (Node n : B.famousRate())
                    model.addRow(new Object[]{n.author, n.title});
                cl.show(cardPanel, "status");
            } catch (Exception ignored) {}
        });

        return sidebar;
    }

    private JSeparator makeDivider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0x3D5166));
        sep.setBackground(new Color(0x3D5166));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setBorder(new EmptyBorder(4, 0, 4, 0));
        return sep;
    }

    private JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel("  " + text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(new Color(0x95A5A6));
        lbl.setBorder(new EmptyBorder(10, 10, 2, 0));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }


    private void addFormRow(JPanel panel, GridBagConstraints c, int row, String label, JComponent field) {
        c.gridx = 0; c.gridy = row; panel.add(addLabel(label), c);
        c.gridx = 1; panel.add(field, c);
    }

    JPanel libraryPanel() {
        JPanel libraryPanel = new JPanel(new BorderLayout());
        libraryPanel.setBackground(BG_MAIN);

        cardPanel.setBackground(BG_MAIN);

        booksPanel       = booksPanel();
        addNewBooksPanel = addNewBooksPanel();
        editBooksPanel   = editBooksPanel();
        deleteBooksPanel = deleteBooksPanel();
        borrowBooksPanel = borrowBooksPanel();
        returnBooksPanel = returnBooksPanel();
        studentsPanel    = studentsPanel();
        editStudentsPanel= editStudentPanel();
        statusPanel      = statusPanel();
        waitListPanel    = waitListPanel();

        JPanel showBorrowedPanel = showBorrowedPanel();
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("C:\\Users\\ASUS\\OneDrive\\Desktop\\محاضرات\\WhatsApp Image 2026-06-28 at 4.23.16 PM.jpeg");
                g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setBackground(BG_MAIN);

        cardPanel.add(mainPanel,         "main");
        cardPanel.add(booksPanel,        "books");
        cardPanel.add(addNewBooksPanel,  "addNewBooks");
        cardPanel.add(editBooksPanel,    "editBooks");
        cardPanel.add(deleteBooksPanel,  "deleteBooks");
        cardPanel.add(borrowBooksPanel,  "borrowBooks");
        cardPanel.add(returnBooksPanel,  "returnBooks");
        cardPanel.add(editStudentsPanel, "editStudents");
        cardPanel.add(studentsPanel,     "students");
        cardPanel.add(statusPanel,       "status");
        cardPanel.add(showBorrowedPanel, "showBorrowed");
        cardPanel.add(waitListPanel,     "waitList");

        libraryPanel.add(listPanel(),  BorderLayout.WEST);
        libraryPanel.add(cardPanel,    BorderLayout.CENTER);
        return libraryPanel;
    }


    Menu() {
        bst.insert(1, 2, "Ahmed",      "OrWell");
        bst.insert(2, 5, "George",     "Pride");
        bst.insert(3, 4, "William",    "The Old Man");
        bst.insert(4, 6, "Charles",    "Harry Potter");
        bst.insert(5, 7, "Jane",       "Rich Dad");
        bst.insert(6, 8, "Dostoevsky", "Poor Dad");
        bst.insert(7, 9, "Alan",       "America");


        this.add(libraryPanel());
        this.setTitle("Library Management System");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}