import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import org.jdatepicker.impl.*;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
public class Menu0 extends JFrame implements ActionListener {

    SwingMethod sw  = new SwingMethod();

    Color bg = new Color(198,167,216);
    CardLayout cardLayout = new CardLayout();
    CardLayout searchLayout = new CardLayout();
    CardLayout supervisorLayout = new CardLayout();
    JPanel supervisor = new JPanel(supervisorLayout);
    CardLayout managerLayout = new CardLayout();
    JPanel manager = new JPanel(managerLayout);
    JPanel mainPanel;
    JPanel cardPanel = new JPanel(cardLayout);
    JPanel searchCard = new JPanel(searchLayout);

    private  JLabel addLabel(String text){
        JLabel nameLabel = new JLabel(text);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 30));

        return nameLabel;
    }

    private  JTextField addTextField(){
        JTextField field = new JTextField(15);
        return field;
    }

    private void resetPanel(JPanel panel){
        for(Component c : panel.getComponents()){
            if(c instanceof JTextField){
                ((JTextField)c).setText("");
            }
            else if(c instanceof JRadioButton){
                ((JRadioButton)c).setSelected(false);
            }
            else if(c instanceof JSpinner){
                ((JSpinner)c).setValue(new Date());
            }
            else if(c instanceof JPanel){
                resetPanel((JPanel)c);
            }
            else if(c instanceof JLabel){
                if(((JLabel)c).getText().equals("ID") || ((JLabel)c).getText().equals("Name") || ((JLabel)c).getText().equals("Category") || ((JLabel)c).getText().equals("Amount") || ((JLabel)c).getText().equals("Price") || ((JLabel)c).getText().equals("Limit")){
                    ((JLabel) c).setText("");
                }
            }

        }

    }

    private JButton createButton(String text , CardLayout cardLayout , JPanel panel , String label) {
        JButton button = new JButton(text);
        button.addActionListener(e -> {cardLayout.show(panel, label);
            for (Component c : panel.getComponents()) {
                if (label.equals(c.getName())) {
                    resetPanel((JPanel) c);
                    break;
                }

            }
        });
        button.setFocusable(false);
        button.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        button.setBackground(new Color(158,199,232));
        button.setForeground(Color.BLACK);
        return button;
    }

    private JButton returnButton(CardLayout LocalLayout, JPanel GPanel , String name) {
        JButton retButton = new JButton("Return");
        retButton.addActionListener(e ->{for (Component c : GPanel.getComponents()) {
            if (c instanceof JPanel && name.equals(c.getName())) {
                resetPanel((JPanel) c);  // reset all inputs
                break;
            }
        }
            LocalLayout.show(GPanel, name);});
        retButton.setFocusable(false);
        retButton.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        retButton.setBackground(new Color(158,199,232));
        retButton.setForeground(Color.BLACK);
        return retButton;
    }

    private JPanel createScreen(int rows, int up, int bottom, String text, JComponent... components) {

        JLabel label = new JLabel(text);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.BLACK);
        label.setBackground(bg);
        label.setOpaque(true);//

        JPanel UPanel = new JPanel();
        JPanel BPanel = new JPanel();
        JPanel LPanel = new JPanel();
        JPanel RPanel = new JPanel();
        JPanel CPanel = new JPanel(new GridLayout(rows, 1, 0, 30));

        UPanel.setPreferredSize(new Dimension(100, up));
        BPanel.setPreferredSize(new Dimension(100, bottom));
        LPanel.setPreferredSize(new Dimension(400, 100));
        RPanel.setPreferredSize(new Dimension(400, 100));
        CPanel.setPreferredSize(new Dimension(100, 100));

        UPanel.setBackground(bg);
        BPanel.setBackground(bg);
        LPanel.setBackground(bg);
        RPanel.setBackground(bg);
        CPanel.setBackground(bg);

        for (JComponent c : components) {
            CPanel.add(c);
        }
        UPanel.add(label);

        JPanel screen = new JPanel(new BorderLayout());
        screen.add(UPanel, BorderLayout.NORTH);
        screen.add(BPanel, BorderLayout.SOUTH);
        screen.add(LPanel, BorderLayout.WEST);
        screen.add(RPanel, BorderLayout.EAST);
        screen.add(CPanel, BorderLayout.CENTER);

        return screen;
    }

    private JPanel nameSearch() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Name Search");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.white);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);//فراغات
        gbc.fill = GridBagConstraints.HORIZONTAL;//تمدد بشكل عرضي

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Item Name: "), gbc);
        gbc.gridx = 1;
        row++;
        JTextField nameField = addTextField();
        form.add(nameField, gbc);

        panel.add(form, BorderLayout.CENTER);



        JLabel statusLabel1 = new JLabel("ID: ");
        statusLabel1.setFont(new Font("Rom", Font.BOLD, 30));
        statusLabel1.setVerticalAlignment(JLabel.CENTER);
        statusLabel1.setHorizontalAlignment(JLabel.CENTER);
        statusLabel1.setFont(new Font("Rom", Font.PLAIN, 30));
        statusLabel1.setForeground(Color.BLACK);
        statusLabel1.setBackground(bg);
        statusLabel1.setVisible(false);
        gbc.gridx = 0; gbc.gridy = row;
        form.add(statusLabel1, gbc);
        row++;
        JLabel label2 = new JLabel("Name: ");
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setFont(new Font("Rom", Font.PLAIN, 30));
        label2.setForeground(Color.BLACK);
        label2.setBackground(bg);
        label2.setVisible(false);
        gbc.gridx = 0; gbc.gridy = row;
        form.add(label2, gbc);
        row++;
        JLabel label3 = new JLabel("Category:");
        label3.setVerticalAlignment(JLabel.CENTER);
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setFont(new Font("Rom", Font.PLAIN, 30));
        label3.setForeground(Color.BLACK);
        label3.setBackground(bg);
        label3.setVisible(false);
        gbc.gridx = 0; gbc.gridy = row;
        form.add(label3, gbc);
        row++;
        JLabel label4 = new JLabel("Name Search");
        label4.setVerticalAlignment(JLabel.CENTER);
        label4.setHorizontalAlignment(JLabel.CENTER);
        label4.setFont(new Font("Rom", Font.PLAIN, 30));
        label4.setForeground(Color.BLACK);
        label4.setBackground(bg);
        label4.setVisible(false);
        gbc.gridx = 0; gbc.gridy = row;
        form.add(label4, gbc);
        row++;
        JLabel label5 = new JLabel("Name Search");
        label5.setVerticalAlignment(JLabel.CENTER);
        label5.setHorizontalAlignment(JLabel.CENTER);
        label5.setFont(new Font("Rom", Font.PLAIN, 30));
        label5.setForeground(Color.BLACK);
        label5.setBackground(bg);
        label5.setVisible(false);
        gbc.gridx = 0; gbc.gridy = row;
        form.add(label5, gbc);
        row++;
        JLabel label6 = new JLabel("Name Search");
        label6.setVerticalAlignment(JLabel.CENTER);
        label6.setHorizontalAlignment(JLabel.CENTER);
        label6.setFont(new Font("Rom", Font.PLAIN, 30));
        label6.setForeground(Color.BLACK);
        label6.setBackground(bg);
        label6.setVisible(false);
        gbc.gridx = 0; gbc.gridy = row;
        form.add(label6, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();//لحذف الفراغات
            try {

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                        panel,
                        "Please enter a name!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Items item = sw.searchItemsByName(name);

            if (item == null) {
                JOptionPane.showMessageDialog(
                        panel,
                        "Item not found.",
                        "Search Result",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            statusLabel1.setText("ID: "+ String.valueOf(item.getID()));
            statusLabel1.setVisible(true);
            label2.setText("Name: " + item.getName());
            label2.setVisible(true);
                label3.setText("Category: "+item.getCategory());
                label3.setVisible(true);
                label4.setText("Price: "+String.valueOf(item.getPrice()));
                label4.setVisible(true);
                label5.setText("Available: "+String.valueOf(item.getAvamount()));
                label5.setVisible(true);
                label6.setText("Limit: "+String.valueOf(item.getLimit()));
                label6.setVisible(true);
            }catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(
                        panel,
                        ex.getMessage(),
                        "Search Failure",
                        JOptionPane.ERROR_MESSAGE
                );
            }});

        submitButton.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        submitButton.setBackground(new Color(158,199,232));
        submitButton.setForeground(Color.BLACK);
        submitButton.setFocusable(false);

        JButton returnButton = returnButton(searchLayout, searchCard,"searchPanel");

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        buttons.add(returnButton);
        buttons.add(submitButton);

        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private int currentItemIndex = 0; // class-level field
    private List<Items> itemsList = new ArrayList<>(); // class-level field

    private JPanel categorySearch() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Category Search");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.white);
        label.setBackground(bg);
        label.setOpaque(true);
        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        // Category Field
        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Item Category: "), gbc);
        gbc.gridx = 1;
        row++;
        JTextField categoryField = addTextField();
        form.add(categoryField, gbc);

        // Labels for item info
        JLabel statusLabel1 = new JLabel();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();
        JLabel label4 = new JLabel();
        JLabel label5 = new JLabel();
        JLabel label6 = new JLabel();

        for (JLabel l : new JLabel[]{statusLabel1, label2, label3, label4, label5, label6}) {
            l.setFont(new Font("Rom", Font.PLAIN, 30));
            l.setForeground(Color.BLACK);
            l.setVisible(false);
            gbc.gridx = 0; gbc.gridy = row++;
            form.add(l, gbc);
        }

        panel.add(form, BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton submitButton = new JButton("Next Item");
        submitButton.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        submitButton.setBackground(new Color(158,199,232));
        submitButton.setForeground(Color.BLACK);
        submitButton.setFocusable(false);

        JButton returnButton = returnButton(searchLayout, searchCard,"searchPanel");

        buttons.add(returnButton);
        buttons.add(submitButton);
        panel.add(buttons, BorderLayout.SOUTH);

        // ====== ActionListener ======
        submitButton.addActionListener(e -> {
            String category = categoryField.getText().trim();
            if (category.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter a category.");
                return;
            }

            // Load items only if the list is empty or category changed
            if (itemsList.isEmpty()) {
                itemsList = sw.searchItemsByCategory(category);
                currentItemIndex = 0; // reset index
                if (itemsList.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "No items found.");
                    return;
                }
            }

            if (currentItemIndex >= itemsList.size()) {
                JOptionPane.showMessageDialog(panel, "No more items to show.");
                return;
            }

            Items item = itemsList.get(currentItemIndex);

            statusLabel1.setText("ID: " + item.getID());
            label2.setText("Name: " + item.getName());
            label3.setText("Category: " + item.getCategory());
            label4.setText("Price: " + item.getPrice());
            label5.setText("Available: " + item.getAvamount());
            label6.setText("Limit: " + item.getLimit());

            for (JLabel l : new JLabel[]{statusLabel1, label2, label3, label4, label5, label6}) {
                l.setVisible(true);
            }

            currentItemIndex++; // move to next item
        });

        // Reset when return
        returnButton.addActionListener(e -> {
            currentItemIndex = 0;
            itemsList.clear();
            for (JLabel l : new JLabel[]{statusLabel1, label2, label3, label4, label5, label6}) {
                l.setText("");
                l.setVisible(false);
            }
            searchLayout.show(searchCard, "searchPanel");
        });

        return panel;
    }

    private JPanel addPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);

        JLabel title = new JLabel("ADD ITEM", JLabel.CENTER);
        title.setFont(new Font("Rom", Font.PLAIN, 80));
        title.setForeground(Color.BLACK);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("ID:"), gbc);
        gbc.gridx = 1;
        JTextField idField = addTextField();
        form.add(idField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = addTextField();
        form.add(nameField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Category:"), gbc);
        gbc.gridx = 1;
        JTextField categoryField = addTextField();
        form.add(categoryField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Price:"), gbc);
        gbc.gridx = 1;
        JTextField priceField = addTextField();
        form.add(priceField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Available Amount:"), gbc);
        gbc.gridx = 1;
        JTextField amountField = addTextField();
        form.add(amountField, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton add = new JButton("ADD");
        add.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        add.setBackground(new Color(158,199,232));
        add.setForeground(Color.BLACK);
        add.setFocusable(false);
        add.addActionListener(e -> {  try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            String category = categoryField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            int amount = Integer.parseInt(amountField.getText().trim());

            if (name.isEmpty() || category.isEmpty() || price==0 || amount==0) {
                JOptionPane.showMessageDialog(panel,
                        "Non of the fields can be empty!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (price < 0 || amount < 0) {
                JOptionPane.showMessageDialog(panel,
                        "The price or the amount is not valid!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            sw.addNewItem(id, name, category, price, amount);

            JOptionPane.showMessageDialog(panel,
                    "Item added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel,
                    "Please enter valid numeric values for ID, Price, and Amount",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
        });

        JButton returnButton = returnButton(supervisorLayout, supervisor, "storage");

        buttons.add(returnButton);
        buttons.add(add);

        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel editPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);

        JLabel title = new JLabel("EDIT ITEM", JLabel.CENTER);
        title.setFont(new Font("Rom", Font.PLAIN, 80));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = addTextField();
        form.add(nameField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Price:"), gbc);
        gbc.gridx = 1;
        JTextField priceField = addTextField();
        form.add(priceField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Available Amount:"), gbc);
        gbc.gridx = 1;
        JTextField amountField = addTextField();
        form.add(amountField, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton save = new JButton("SAVE");
        save.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        save.addActionListener(e -> {  try {
            String name = nameField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            int amount = Integer.parseInt(amountField.getText().trim());

            if (name.isEmpty() || price == 0 || amount == 0) {
                JOptionPane.showMessageDialog(panel,
                        "Non of the fields can be empty!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (price < 0 || amount < 0) {
                JOptionPane.showMessageDialog(panel,
                        "The price or the amount is not valid!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            sw.changItems(name, price, amount);

            JOptionPane.showMessageDialog(panel,
                    "Item has been Edited!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    panel,
                    "Please enter valid numeric values for price and amount.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    panel,
                    ex.getMessage(),
                    "Edit Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        });
        save.setBackground(new Color(158,199,232));
        save.setForeground(Color.BLACK);
        save.setFocusable(false);

        JButton returnButton = returnButton(supervisorLayout, supervisor, "storage");

        buttons.add(returnButton);
        buttons.add(save);

        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel deletePanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);

        JLabel title = new JLabel("DELETE ITEM", JLabel.CENTER);
        title.setFont(new Font("Rom", Font.PLAIN, 80));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = addTextField();
        form.add(nameField, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton delete = new JButton("DELETE");
        delete.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        delete.addActionListener(e -> { String name = nameField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter a name");
                return;
            }

            try {
                sw.deleteItems(name);
                JOptionPane.showMessageDialog(panel, "Item deleted successfully");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(
                        panel,
                        ex.getMessage(),
                        "Delete Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }});
        delete.setBackground(new Color(158,199,232));
        delete.setForeground(Color.BLACK);
        delete.setFocusable(false);

        JButton returnButton = returnButton(supervisorLayout, supervisor, "storage");

        buttons.add(returnButton);
        buttons.add(delete);

        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel addProductLinePanel(){

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("ADD PRODUCTLINE");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.white);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Line Name: "), gbc);
        gbc.gridx = 1;
        JTextField nameField = addTextField();
        form.add(nameField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Line ID: "), gbc);
        gbc.gridx = 1;
        JTextField idField = addTextField();
        form.add(idField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Line State: "), gbc);
        gbc.gridx = 1;
        JRadioButton active = new JRadioButton("Active");
        active.setActionCommand("active");
        JRadioButton stop = new JRadioButton("Stop");
        stop.setActionCommand("stop");
        JRadioButton maintenance = new JRadioButton("Maintenance");
        maintenance.setActionCommand("maintenance");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(active);
        buttonGroup.add(stop);
        buttonGroup.add(maintenance);
        JPanel statePanel = new JPanel(new GridLayout(1,3,20,20));
        statePanel.add(active);
        statePanel.add(stop);
        statePanel.add(maintenance);
        form.add(statePanel, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Number of Tasks: "), gbc);
        gbc.gridx = 1;
        JTextField numOfTasksField = addTextField();
        form.add(numOfTasksField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Task Number: "), gbc);
        gbc.gridx = 1;
        JTextField taskField = addTextField();
        form.add(taskField, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton add = new JButton("ADD");
        add.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        add.setBackground(new Color(158,199,232));
        add.setForeground(Color.BLACK);
        add.setFocusable(false);
        add.addActionListener(e ->{try {
            int ID = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            int taskNum = Integer.parseInt(taskField.getText().trim());
            int numOfTasks = Integer.parseInt(numOfTasksField.getText().trim());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Line name is required");
                return;
            }
            if (buttonGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(panel, "Please select a line state");
                return;
            }
            String state = buttonGroup.getSelection().getActionCommand();

            if (ID <= 0 || taskNum <= 0 || numOfTasks <= 0) {
                JOptionPane.showMessageDialog(panel, "Numeric values must be greater than 0");
                return;
            }
            sw.addNewProductsLine(ID, name, state, taskNum, numOfTasks);

            JOptionPane.showMessageDialog(panel,
                    "ProductLine added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    panel,
                    "Please enter valid numeric values for price and amount.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }});

        JButton returnButton = returnButton(managerLayout, manager, "managerMenu");

        buttons.add(returnButton);
        buttons.add(add);

        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel editProductLinePanel(){
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("EDIT PRODUCTLINE");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.white);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Line ID: "), gbc);
        gbc.gridx = 1;
        JTextField idField = addTextField();
        form.add(idField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Line's new State: "), gbc);
        gbc.gridx = 1;
        JRadioButton active = new JRadioButton("Active");
        active.setActionCommand("active");
        JRadioButton stop = new JRadioButton("Stop");
        stop.setActionCommand("stop");
        JRadioButton maintenance = new JRadioButton("Maintenance");
        maintenance.setActionCommand("maintenance");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(active);
        buttonGroup.add(stop);
        buttonGroup.add(maintenance);
        JPanel statePanel = new JPanel(new GridLayout(1,3,20,20));
        statePanel.add(active);
        statePanel.add(stop);
        statePanel.add(maintenance);
        form.add(statePanel, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton edit = new JButton("EDIT");
        edit.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        edit.setBackground(new Color(158,199,232));
        edit.setForeground(Color.BLACK);
        edit.setFocusable(false);
        edit.addActionListener(e ->{
                try{
                    int ID = Integer.parseInt(idField.getText().trim());

                    if(ID <= 0){
                        JOptionPane.showMessageDialog(panel, "ID must be greater than 0");
                        return;
                    }

                    if (buttonGroup.getSelection() == null) {
                        JOptionPane.showMessageDialog(panel, "Please select a line state");
                        return;
                    }
                    String state = buttonGroup.getSelection().getActionCommand();

                    sw.changeProductsState(ID, state);

                    JOptionPane.showMessageDialog(panel,
                            "ProductLine Edited successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            panel,
                            "Please enter valid numeric values for price and amount.",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE
                    );
                }});

        JButton returnButton = returnButton(managerLayout, manager, "managerMenu");

        buttons.add(returnButton);
        buttons.add(edit);

        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel showProgressPanel(){
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Progress");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.white);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0;
        form.add(addLabel("Progress: "), gbc);
        gbc.gridx = 1;
        JProgressBar progressBar = new JProgressBar(0,100);
        progressBar.setPreferredSize(new Dimension(400, 40));
        progressBar.setForeground(Color.GREEN);
        progressBar.setBackground(Color.BLACK);
        progressBar.setFont(new Font("Serif", Font.BOLD, 30));
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        form.add(progressBar, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton returnButton = returnButton(managerLayout, manager, "managerMenu");

        buttons.add(returnButton);

        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    } // Problem

    private  JPanel addTaskPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);

        // ====== عنوان الصفحة ======
        JLabel label = new JLabel("ADD TASK");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.white);
        label.setBackground(bg);
        label.setOpaque(true);
        panel.add(label, BorderLayout.NORTH);

        // ====== الفورم ======
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        // Task ID
        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Task ID:"), gbc);
        gbc.gridx = 1;
        JTextField taskIDField = addTextField();
        form.add(taskIDField, gbc);
        row++;

        // Required Product Name
        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Required Product Name:"), gbc);
        gbc.gridx = 1;
        JTextField taskReqProductField = addTextField();
        form.add(taskReqProductField, gbc);
        row++;

        // Required Amount
        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Required Amount:"), gbc);
        gbc.gridx = 1;
        JTextField taskReqAmount = addTextField();
        form.add(taskReqAmount, gbc);
        row++;

        // Agent
        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Agent:"), gbc);
        gbc.gridx = 1;
        JTextField taskAgent = addTextField();
        form.add(taskAgent, gbc);
        row++;

        // ====== Date Pickers ======
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        // Starting Date
        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Starting Date:"), gbc);
        gbc.gridx = 1;
        UtilDateModel startModel = new UtilDateModel();
        startModel.setSelected(true);
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel, p);
        JDatePickerImpl startingDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
        form.add(startingDatePicker, gbc);
        row++;

        // Ending Date
        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Ending Date:"), gbc);
        gbc.gridx = 1;
        UtilDateModel endModel = new UtilDateModel();
        endModel.setSelected(true);
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, p);
        JDatePickerImpl endingDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
        form.add(endingDatePicker, gbc);
        row++;

        // ====== Task Status ======
        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Status:"), gbc);
        gbc.gridx = 1;
        JRadioButton underImp = new JRadioButton("UNDERIMP");
        JRadioButton complete = new JRadioButton("COMPLET");
        JRadioButton cancel = new JRadioButton("CANCEL");

        // Action commands
        underImp.setActionCommand("UNDERIMP");
        complete.setActionCommand("COMPLET");
        cancel.setActionCommand("CANCEL");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(complete);
        buttonGroup.add(cancel);
        buttonGroup.add(underImp);

        JPanel statePanel = new JPanel(new GridLayout(1,3,20,20));
        statePanel.add(complete);
        statePanel.add(cancel);
        statePanel.add(underImp);
        form.add(statePanel, gbc);
        row++;

        // Product Line name
        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Product Line Name:"), gbc);
        gbc.gridx = 1;
        JTextField proLineName = addTextField();
        form.add(proLineName, gbc);
        row++;

        panel.add(form, BorderLayout.CENTER);

        // ====== Buttons ======
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton add = new JButton("ADD");
        add.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        add.setBackground(new Color(158,199,232));
        add.setForeground(Color.BLACK);
        add.setFocusable(false);
        buttons.add(add);

        JButton returnButton = returnButton(supervisorLayout, supervisor, "productLines");
        buttons.add(returnButton);
        panel.add(buttons, BorderLayout.SOUTH);

        // ====== ActionListener للـ ADD ======
        add.addActionListener(e -> {
            try {
                int ID = Integer.parseInt(taskIDField.getText().trim());
                String requireProductName = taskReqProductField.getText().trim();
                int amount = Integer.parseInt(taskReqAmount.getText().trim());
                String agent = taskAgent.getText().trim();
                String productLineName = proLineName.getText().trim();

                Date startDateValue = (Date) startingDatePicker.getModel().getValue();
                Date endDateValue   = (Date) endingDatePicker.getModel().getValue();

                if(requireProductName.isEmpty() || agent.isEmpty() || productLineName.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please fill all text fields.");
                    return;
                }
                if(ID <= 0 || amount <= 0) {
                    JOptionPane.showMessageDialog(panel, "ID and Amount must be greater than 0.");
                    return;
                }
                if(startDateValue == null || endDateValue == null) {
                    JOptionPane.showMessageDialog(panel, "Please select both start and end dates.");
                    return;
                }

                LocalDate startDate = startDateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endDate   = endDateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if(startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
                    JOptionPane.showMessageDialog(panel, "Start date must be before end date.");
                    return;
                }

                if (buttonGroup.getSelection() == null) {
                    JOptionPane.showMessageDialog(panel, "Please select a Task state");
                    return;
                }
                String state = buttonGroup.getSelection().getActionCommand();

                // إضافة المهمة

                sw.addTasks(ID, requireProductName, productLineName, amount, agent, state, startDate, endDate);

                JOptionPane.showMessageDialog(panel, "Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter valid numeric values for ID and Amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private  JPanel deleteTaskPanel(){

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("DELETE TASK");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.white);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("Task ID:"), gbc);
        gbc.gridx = 1;
        JTextField taskIDField = addTextField();
        form.add(taskIDField, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton remove = new JButton("REMOVE");
        remove.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        remove.setBackground(new Color(158,199,232));
        remove.addActionListener(e -> {  try {

            int ID = Integer.parseInt(taskIDField.getText().trim());

            if(ID <= 0) {
                JOptionPane.showMessageDialog(panel, "ID must be greater than 0.");
                return;
            }

            sw.deleteTasks(ID);

            JOptionPane.showMessageDialog(panel,
                    "Task Deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    panel,
                    "Please enter valid numeric values for ID.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    panel,
                    ex.getMessage(),
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }});
        remove.setForeground(Color.WHITE);
        remove.setFocusable(false);

        JButton returnButton = returnButton(supervisorLayout, supervisor, "productLines");

        buttons.add(returnButton);
        buttons.add(remove);

        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel menuPanel() {

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setFocusable(false);
        exitButton.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        exitButton.setBackground(new Color(158,199,232));
        exitButton.setForeground(Color.BLACK);

        return createScreen(3, 250, 250, "Welcome",
                createButton("Supervisor", cardLayout, cardPanel, "supervisorPanel"),
                createButton("Manager", cardLayout, cardPanel, "managerLoginPanel"),
                exitButton);
    }

    private JPanel tasksStatusPanel() {
        String[] columnNames = {"Task ID", "State", "Agent", "Start Date", "End Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };

        JTable tasksTable = new JTable(model);
       // tasksTable.setFillsViewportHeight(true);
        tasksTable.getTableHeader().setResizingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tasksTable);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("PRODUCTLINE TASK");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.BLACK);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        JTextField lineNameField = addTextField();
        form.add(lineNameField, gbc);
        gbc.gridx = 1;
        JButton show = new JButton("Show");
                show.setPreferredSize(new Dimension(85, 30));
                show.setFont(new Font("MV BOLI", Font.ITALIC, 20));
                show.setBackground(new Color(158,199,232));
                show.setForeground(Color.WHITE);
                show.addActionListener(e -> {
                        String lineName = lineNameField.getText().trim();
                        if(lineName.isEmpty()) {
                            JOptionPane.showMessageDialog(panel, "Please enter a line name.");
                        }
                        try {
                            List<Object[]> rows = sw.TasksLine(lineName);
                            model.setRowCount(0);
                            for (Object[] rowe : rows) {
                                model.addRow(rowe);
                            }
                        } catch (RuntimeException ex) {
                            JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }});
                show.setFocusable(false);
        form.add(show, gbc);
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        form.add(scrollPane, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton returnButton = returnButton(supervisorLayout, supervisor, "productLines");
        buttons.add(returnButton);
        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel tasksFilterPanel() {
        String[] columnNames = {"Task ID", "State", "Agent", "Start Date", "End Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };

        JTable tasksTable = new JTable(model);
        // tasksTable.setFillsViewportHeight(true);
        tasksTable.getTableHeader().setResizingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tasksTable);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("TASKS");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.BLACK);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        JRadioButton underImp = new JRadioButton("underImp");
        JRadioButton complete = new JRadioButton("complete");
        JRadioButton all = new JRadioButton("all");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(complete);
        buttonGroup.add(all);
        buttonGroup.add(underImp);
        JPanel statePanel = new JPanel(new GridLayout(1,3,20,20));
        statePanel.add(complete);
        statePanel.add(all);
        statePanel.add(underImp);
        form.add(statePanel, gbc);
        gbc.gridx = 1;
        JButton show = new JButton("Show");
        show.setPreferredSize(new Dimension(85, 30));
        show.setFont(new Font("MV BOLI", Font.ITALIC, 20));
        show.setBackground(new Color(158,199,232));
        show.setForeground(Color.WHITE);
        show.addActionListener(e -> {
            try{
            if(buttonGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(panel, "Please select a state.");
            }
            if(complete.isSelected()) {
                List<Object[]> rows = sw.showActiveTasks();
                model.setRowCount(0);
                for (Object[] rowe : rows) {
                    model.addRow(rowe);
                }
            }
            if(all.isSelected()) {
                List<Object[]> rows = sw.showTasks();
                model.setRowCount(0);
                for (Object[] rowe : rows) {
                    model.addRow(rowe);
                }
            }
            if(underImp.isSelected()) {
                List<Object[]> rows = sw.showUnderImpTasks();
                model.setRowCount(0);
                for (Object[] rowe : rows) {
                    model.addRow(rowe);
                }
            }

            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }});
        show.setFocusable(false);
        form.add(show, gbc);
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        form.add(scrollPane, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton returnButton = returnButton(supervisorLayout, supervisor, "productLines");
        buttons.add(returnButton);
        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel tasksProductsPanel() {
        String[] columnNames = {"Task ID", "State", "Agent", "Start Date", "End Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };

        JTable tasksTable = new JTable(model);
        // tasksTable.setFillsViewportHeight(true);
        tasksTable.getTableHeader().setResizingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tasksTable);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("PRODUCTS TASK");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.BLACK);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        JTextField productNameField = addTextField();
        form.add(productNameField, gbc);
        gbc.gridx = 1;
        JButton show = new JButton("Show");
        show.setPreferredSize(new Dimension(85, 30));
        show.setFont(new Font("MV BOLI", Font.ITALIC, 20));
        show.setBackground(new Color(158,199,232));
        show.setForeground(Color.BLACK);
        show.addActionListener(e -> {
            String productName = productNameField.getText().trim();
            if(productName.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter a line name.");
            }
            try {
                List<Object[]> rows = sw.searchTasksProducts(productName);
                // DefaultTableModel model = (DefaultTableModel) tasksTable.getModel();
                model.setRowCount(0);
                for (Object[] rowe : rows) {
                    model.addRow(rowe);
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }});
        show.setFocusable(false);
        form.add(show, gbc);
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        form.add(scrollPane, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton returnButton = returnButton(supervisorLayout, supervisor, "productLines");
        buttons.add(returnButton);
        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private  JPanel productLineProductsPanel() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("PRODUCT LINE PRODUCT");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.white);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        form.add(addLabel("ProductLine ID: "), gbc);
        gbc.gridx = 1;
        row++;
        JTextField IDField = addTextField();
        form.add(IDField, gbc);

        panel.add(form, BorderLayout.CENTER);



        JLabel statusLabel = new JLabel();
        statusLabel.setFont(new Font("Rom", Font.BOLD, 30));
        statusLabel.setVisible(false);
        gbc.gridx = 0; gbc.gridy = row;
        form.add(statusLabel, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                int ID =Integer.parseInt(IDField.getText().trim());
                if (ID<=0) {
                    JOptionPane.showMessageDialog(
                            panel,
                            "ID must be greater than 0.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                String product = sw.showSpecificProducts(ID);

                if (product == null) {
                    JOptionPane.showMessageDialog(
                            panel,
                            "There is no Products for this productLine !",
                            "Search Result",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                statusLabel.setText("Products: " + product);
                statusLabel.setVisible(true);
            }catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(
                        panel,
                        ex.getMessage(),
                        "Search Failure",
                        JOptionPane.ERROR_MESSAGE
                );
            }});

        submitButton.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        submitButton.setBackground(new Color(158,199,232));
        submitButton.setForeground(Color.BLACK);
        submitButton.setFocusable(false);

        JButton returnButton = returnButton(supervisorLayout, supervisor, "productLines");

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        buttons.add(returnButton);
        buttons.add(submitButton);

        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel showAllProductsPanel() {
        String[] columnNames = {"Line ID", "Line Name", "Products"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };

        JTable tasksTable = new JTable(model);
        tasksTable.getTableHeader().setResizingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tasksTable);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("All Products");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.BLACK);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton showAllButton = new JButton("Show All Products");
        showAllButton.addActionListener(e -> {
            List<Object[]> rows = sw.showAllProductsLine();
            model.setRowCount(0);
            for (Object[] rowe : rows) {
                model.addRow(rowe);
            }
            System.out.println(rows.size());
        });
        showAllButton.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        showAllButton.setBackground(new Color(158,199,232));
        showAllButton.setForeground(Color.BLACK);
        showAllButton.setFocusable(false);



        form.add(scrollPane, gbc);


        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton returnButton = returnButton(supervisorLayout, supervisor, "productLines");
        buttons.add(returnButton);
        buttons.add(showAllButton);
        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel supervisorPanel() {

        JButton saveButton = new JButton("SAVE");
        saveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(supervisor, "The have been saved successfully!");
        WriteInfo writeInfo = new WriteInfo();
            try {
                writeInfo.printInfo();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        saveButton.setFocusable(false);
        saveButton.setFont(new Font("MV BOLI", Font.ITALIC, 30));
        saveButton.setBackground(new Color(158,199,232));
        saveButton.setForeground(Color.BLACK);

        JPanel supervisorMenu = createScreen(3, 250, 250, "",
                createButton("Storage", supervisorLayout,supervisor,"storage"),
                createButton("Product Lines", supervisorLayout,supervisor,"productLines"),
                returnButton(cardLayout,cardPanel ,"menuPanel"));
        supervisorMenu.setName("supervisorMenu");

        JPanel storage = createScreen(6, 120, 80, "Storage",
                createButton("ADD", supervisorLayout, supervisor,"addPanel"),
                createButton("EDIT", supervisorLayout, supervisor, "editPanel"),
                createButton("DELETE", supervisorLayout, supervisor, "deletePanel"),
                saveButton,
                createButton("SEARCH",supervisorLayout, supervisor, "searchCard"),
                returnButton(supervisorLayout, supervisor,"supervisorMenu"));
        storage.setName("storage");

        JPanel productLinePanel = createScreen(8,160,140,"ProductLines",
                createButton("ADD Task",supervisorLayout,supervisor,"addTaskPanel"),
                createButton("DELETE Task",supervisorLayout,supervisor,"deleteTaskPanel"),
                createButton("Lines Tasks",supervisorLayout,supervisor,"taskSearchPanel"),
                createButton("Product Tasks",supervisorLayout,supervisor,"productTasksPanel"),
                createButton("Tasks",supervisorLayout,supervisor,"taskFilterPanel"),
                createButton("Products",supervisorLayout,supervisor,"productLineProductsPanel"),
                createButton("All Products",supervisorLayout,supervisor,"showAllProductsPanel"),
                returnButton(supervisorLayout, supervisor,"supervisorMenu"));
        productLinePanel.setName("productLinePanel");

        JPanel searchPanel = createScreen(3, 250, 250, "Search",
                createButton("BY NAME", searchLayout,searchCard,"nameSearch"),
                createButton("BY CATEGORY",searchLayout,searchCard,"categorySearch"),
                returnButton(supervisorLayout, supervisor, "storage"));
        searchPanel.setName("searchPanel");

        JPanel NSearchPanel = nameSearch();
        NSearchPanel.setName("nameSearch");

        JPanel CSearchPanel = categorySearch();
        CSearchPanel.setName("categorySearch");

        JPanel addPanel = addPanel();
        addPanel.setName("addPanel");

        JPanel editPanel = editPanel();
        editPanel.setName("editPanel");

        JPanel deletePanel = deletePanel();
        deletePanel.setName("deletePanel");

        JPanel addTaskPanel = addTaskPanel();
        addTaskPanel.setName("addTaskPanel");

        JPanel deleteTaskPanel = deleteTaskPanel();
        deleteTaskPanel.setName("deleteTaskPanel");

        JPanel lineTasksPanel = tasksStatusPanel();
        lineTasksPanel.setName("taskSearchPanel");

        JPanel productTasksPanel = tasksProductsPanel();
        productTasksPanel.setName("productTasksPanel");

        JPanel taskFilterPanel = tasksFilterPanel();
        taskFilterPanel.setName("taskFilterPanel");

        JPanel productLineProductsPanel = productLineProductsPanel();
        productLineProductsPanel.setName("productLineProductsPanel");

        JPanel showAllProductsPanel = showAllProductsPanel();
        showAllProductsPanel.setName("showAllProductsPanel");

        searchCard.add(searchPanel,"searchPanel");
        searchCard.add(NSearchPanel,"nameSearch");
        searchCard.add(CSearchPanel,"categorySearch");
        supervisor.add(supervisorMenu,"supervisorMenu");
        supervisor.add(storage, "storage");
        supervisor.add(productLinePanel,"productLines");
        supervisor.add(addTaskPanel,"addTaskPanel");
        supervisor.add(deleteTaskPanel,"deleteTaskPanel");
        supervisor.add(lineTasksPanel,"taskSearchPanel");
        supervisor.add(productTasksPanel,"productTasksPanel");
        supervisor.add(taskFilterPanel,"taskFilterPanel");
        supervisor.add(productLineProductsPanel,"productLineProductsPanel");
        supervisor.add(showAllProductsPanel,"showAllProductsPanel");
        supervisor.add(searchCard, "searchCard");
        supervisor.add(addPanel, "addPanel");
        supervisor.add(deletePanel,"deletePanel");
        supervisor.add(editPanel, "editPanel");
        supervisor.add(deletePanel, "deletePanel");

        return supervisor;
    }

//    private JPanel supervisorLoginPanel() {
//
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBackground(bg);
//
//        JLabel label = new JLabel("Supervisor Login");
//        label.setVerticalAlignment(JLabel.CENTER);
//        label.setHorizontalAlignment(JLabel.CENTER);
//        label.setFont(new Font("Rom", Font.PLAIN, 90));
//        label.setForeground(Color.BLACK);
//        label.setBackground(bg);
//        label.setOpaque(true);
//
//        panel.add(label, BorderLayout.NORTH);
//        panel.setBackground(bg);
//
//                JPanel form = new JPanel(new GridBagLayout());
//                form.setBackground(bg);
//                GridBagConstraints gbc = new GridBagConstraints();
//                gbc.insets = new Insets(10, 10, 10, 10);
//                gbc.fill = GridBagConstraints.HORIZONTAL;
//
//                gbc.gridx = 0; gbc.gridy = 0;
//                form.add(addLabel("UserName: "), gbc);
//
//                gbc.gridx = 1;
//                JTextField usernameField = addTextField();
//                form.add(usernameField, gbc);
//
//                gbc.gridx = 0; gbc.gridy = 1;
//                form.add(addLabel("Password: "), gbc);
//
//                gbc.gridx = 1;
//                JPasswordField passwordField = new JPasswordField(15);
//                form.add(passwordField, gbc);
//
//                gbc.gridx = 0; gbc.gridy = 2;
//                gbc.gridwidth = 2;
//                JButton loginButton = new JButton("Login");
//                loginButton.addActionListener(e ->{ String username = usernameField.getText().trim();
//                    String password = new String(passwordField.getPassword());
//
//                    if (username.isEmpty() || password.isEmpty()) {
//                        JOptionPane.showMessageDialog(form,
//                                "Please enter username and password",
//                                "Error",
//                                JOptionPane.ERROR_MESSAGE);
//                        return;
//                    }
//                    if (username.equals("Bilal") && password.equals("1234")) {
//                       cardLayout.show(cardPanel,"supervisorPanel");
//                    } else {
//                        JOptionPane.showMessageDialog(panel,
//                                "Invalid username or password",
//                                "Login Failed",
//                                JOptionPane.ERROR_MESSAGE);
//                    }});
//                loginButton.setFont(new Font("MV BOLI", Font.ITALIC, 20));
//                loginButton.setBackground(new Color(158,199,232));
//                loginButton.setForeground(Color.BLACK);
//
//        form.add(loginButton, gbc);
//
//        panel.add(form, BorderLayout.CENTER);
//
//        JPanel buttons = new JPanel(new FlowLayout());
//        buttons.setBackground(bg);
//
//        JButton returnButton = returnButton(cardLayout,cardPanel ,"menuPanel");
//
//        buttons.add(returnButton);
//
//        panel.add(buttons, BorderLayout.SOUTH);
//        return panel;
//    }

    private JPanel managerLoginPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);

        JLabel label = new JLabel("Manager Login");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Rom", Font.PLAIN, 90));
        label.setForeground(Color.BLACK);
        label.setBackground(bg);
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.setBackground(bg);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(bg);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(addLabel("UserName: "), gbc);

        gbc.gridx = 1;
        JTextField usernameField = addTextField();
        form.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(addLabel("Password: "), gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        form.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e ->{ String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(form,
                        "Please enter username and password",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (username.equals("Khalil") && password.equals("1234")) {
                cardLayout.show(cardPanel,"managerPanel");
            } else {
                JOptionPane.showMessageDialog(panel,
                        "Invalid username or password",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }});
        loginButton.setFont(new Font("MV BOLI", Font.ITALIC, 20));
        loginButton.setBackground(new Color(158,199,232));
        loginButton.setForeground(Color.BLACK);

        form.add(loginButton, gbc);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(bg);

        JButton returnButton = returnButton(cardLayout,cardPanel ,"menuPanel");

        buttons.add(returnButton);

        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel managerPanel(){

        JPanel managerMenu = createScreen(4,250,250,"Welcome Manager",
                createButton("Add ProductLine",managerLayout,manager,"addProductLinePanel"),
                createButton("Edit ProductLine",managerLayout,manager,"editProductLinePanel"),
                createButton("Show Progress", managerLayout, manager,"showProgressPanel"),
                returnButton(cardLayout,cardPanel ,"menuPanel"));

        JPanel addProLinePanel = addProductLinePanel();
        addProLinePanel.setName("addProductLinePanel");

        JPanel editProductLinePanel = editProductLinePanel();
        editProductLinePanel.setName("editProductLinePanel");

        JPanel showProg = showProgressPanel();
        showProg.setName("showProgressPanel");

        manager.add(managerMenu,"managerMenu");
        manager.add(addProLinePanel,"addProductLinePanel");
        manager.add(editProductLinePanel,"editProductLinePanel");
        manager.add(showProg,"showProgressPanel");
        return manager;
    }

    Menu0() {
        mainPanel = menuPanel();
        cardPanel.add( mainPanel, "menuPanel");
        cardPanel.add(supervisorPanel(), "supervisorPanel");
        cardPanel.add(managerPanel(), "managerPanel");
      //  cardPanel.add(supervisorLoginPanel(),"supervisorLoginPanel");
        cardPanel.add(managerLoginPanel(), "managerLoginPanel");


        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(cardPanel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
