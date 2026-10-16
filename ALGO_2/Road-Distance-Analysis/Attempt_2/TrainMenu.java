package Attempt_2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TrainMenu extends JFrame {

    private final Graph graph;

    // ---- top controls ----
    private JComboBox<String> startBox;
    private JComboBox<String> endBox;

    // ---- edit panel dropdowns (kept in sync via refreshStationBoxes) ----
    private JComboBox<String> removeStationBox;
    private JComboBox<String> removeRouteFromBox;
    private JComboBox<String> removeRouteToBox;

    // ---- drawing ----
    private final DrawPanel drawPanel;
    private List<String> highlightedPath = new ArrayList<>();
    private String cycleFrom = null, cycleTo = null;
    private final Map<String, Point> positions = new HashMap<>();

    // ---- log ----
    private JTextArea logArea;

    public TrainMenu(Graph graph) {

        this.graph = graph;

        setTitle("Train Network Manager");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(COLOR_BG_MAIN);

        // ===== CENTER: drawing of the network =====
        // initialized first so any listener defined below can safely use it
        drawPanel = new DrawPanel();
        drawPanel.setBackground(COLOR_BG_MAIN);
        add(drawPanel, BorderLayout.CENTER);

        // ===== TOP: path finder =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        topPanel.setBackground(COLOR_BG_PANEL);

        startBox = new JComboBox<>();
        endBox = new JComboBox<>();
        styleField(startBox);
        styleField(endBox);
        refreshStationBoxes();

        JButton findPath = new JButton("Find Shortest Path");
        styleButton(findPath);
        findPath.addActionListener(this::onFindPath);

        JButton clearPathBtn = new JButton("Clear Highlight");
        styleButton(clearPathBtn);
        clearPathBtn.addActionListener(e -> {
            highlightedPath.clear();
            cycleFrom = null;
            cycleTo = null;
            drawPanel.repaint();
        });

        JLabel fromLabel = new JLabel("From:");
        JLabel toLabel = new JLabel("To:");
        fromLabel.setForeground(COLOR_TEXT);
        toLabel.setForeground(COLOR_TEXT);

        topPanel.add(fromLabel);
        topPanel.add(startBox);
        topPanel.add(toLabel);
        topPanel.add(endBox);
        topPanel.add(findPath);
        topPanel.add(clearPathBtn);

        add(topPanel, BorderLayout.NORTH);

        // ===== EAST: control panel (scrollable, in case the panel grows taller than the window) =====
        JScrollPane controlScroll = new JScrollPane(buildControlPanel());
        controlScroll.setBorder(BorderFactory.createEmptyBorder());
        controlScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        controlScroll.getVerticalScrollBar().setUnitIncrement(16);
        controlScroll.getViewport().setBackground(COLOR_BG_PANEL);
        add(controlScroll, BorderLayout.EAST);

        // the remove-station/remove-route dropdowns were just created inside
        // buildControlPanel(), so refresh again now to fill them with existing stations
        refreshStationBoxes();

        // ===== SOUTH: log / results =====
        logArea = new JTextArea(8, 0);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        logArea.setBackground(COLOR_BG_MAIN);
        logArea.setForeground(COLOR_TEXT);
        logArea.setCaretColor(COLOR_TEXT);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDER), "Log / Results"));
        logScroll.getViewport().setBackground(COLOR_BG_MAIN);
        add(logScroll, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);

        log("Ready. " + graph.graph.size() + " station(s) loaded.");
    }

    // ---------------------------------------------------------------
    // Control panel: import/export, add/remove station/route, analysis
    // ---------------------------------------------------------------
    // ---------------------------------------------------------------
    // Unified color palette
    // ---------------------------------------------------------------
    private static final Color COLOR_BG_MAIN     = new Color(0xF5, 0xF3, 0xFA);
    private static final Color COLOR_BG_PANEL     = new Color(0xEA, 0xE6, 0xF5);
    private static final Color COLOR_BG_FIELD     = new Color(0xFB, 0xF0, 0xF6);
    private static final Color COLOR_BORDER       = new Color(0xD3, 0xCC, 0xE8);
    private static final Color COLOR_TEXT         = new Color(0x2B, 0x2F, 0x36);
    private static final Color COLOR_PRIMARY      = new Color(0x6A, 0x5A, 0xA0);
    private static final Color COLOR_PRIMARY_DARK = new Color(0x52, 0x44, 0x80);
    private static final Color COLOR_ACCENT       = new Color(0xE0, 0x6B, 0x8C);
    private static final Color COLOR_EDGE         = new Color(0xA8, 0x9F, 0xC2);

    private void styleButton(AbstractButton b) {
        b.setBackground(COLOR_PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARY_DARK, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    private void styleField(JComponent c) {
        c.setBackground(COLOR_BG_FIELD);
        c.setForeground(COLOR_TEXT);
        if (c instanceof JTextField || c instanceof JComboBox) {
            ((JComponent) c).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_BORDER, 1),
                    BorderFactory.createEmptyBorder(2, 4, 2, 4)
            ));
        }
    }

    private static final int CONTROL_WIDTH = 260;
    private static final int FIELD_HEIGHT = 14;
    private static final int BUTTON_HEIGHT = 14;

    private void sizeField(JComponent c) {
        Dimension d = new Dimension(CONTROL_WIDTH, FIELD_HEIGHT);
        c.setPreferredSize(d);
        c.setMaximumSize(d);
        c.setMinimumSize(d);
        styleField(c);
    }

    private void sizeButton(AbstractButton b) {
        Dimension d = new Dimension(CONTROL_WIDTH, BUTTON_HEIGHT);
        b.setPreferredSize(d);
        b.setMaximumSize(d);
        b.setMinimumSize(d);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        styleButton(b);
    }

    private JPanel buildControlPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(COLOR_BG_PANEL);
        // width is fixed below (after all components are added) so the panel
        // doesn't shrink when wrapped in a JScrollPane

        panel.add(sectionLabel("Import / Export"));

        JButton importBtn = new JButton("Import Network From File");
        sizeButton(importBtn);
        importBtn.addActionListener(this::onImport);
        panel.add(importBtn);
        panel.add(Box.createVerticalStrut(5));

        JButton exportBtn = new JButton("Export Network To File");
        sizeButton(exportBtn);
        exportBtn.addActionListener(this::onExport);
        panel.add(exportBtn);
        panel.add(Box.createVerticalStrut(5));

        JButton drawToFileBtn = new JButton("Read File -> Draw Into Another File");
        sizeButton(drawToFileBtn);
        drawToFileBtn.addActionListener(this::onDrawGraphToFile);
        panel.add(drawToFileBtn);

        panel.add(Box.createVerticalStrut(15));
        panel.add(sectionLabel("Edit Stations"));

        JTextField stationField = new JTextField();
        sizeField(stationField);
        panel.add(labeled("New station name:", stationField));

        JButton addStationBtn = new JButton("Add Station");
        sizeButton(addStationBtn);
        addStationBtn.addActionListener(e -> {
            String name = stationField.getText().trim();
            if (graph.addStation(name)) {
                log("Added station: " + name);
                stationField.setText("");
                refreshStationBoxes();
                drawPanel.repaint();
            } else {
                log("Could not add station (empty name or already exists).");
            }
        });
        panel.add(addStationBtn);
        panel.add(Box.createVerticalStrut(10));

        removeStationBox = new JComboBox<>();
        sizeField(removeStationBox);
        panel.add(labeled("Station to remove:", removeStationBox));

        JButton removeStationBtn = new JButton("Remove Station");
        sizeButton(removeStationBtn);
        removeStationBtn.addActionListener(e -> {
            if (removeStationBox.getSelectedItem() == null) {
                log("No station selected.");
                return;
            }
            String name = removeStationBox.getSelectedItem().toString();
            if (graph.removeStation(name)) {
                log("Removed station: " + name + " (and its routes)");
                refreshStationBoxes();
                drawPanel.repaint();
            } else {
                log("Could not remove station (not found).");
            }
        });
        panel.add(removeStationBtn);

        panel.add(Box.createVerticalStrut(15));
        panel.add(sectionLabel("Edit Routes"));

        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();
        JTextField distField = new JTextField();
        sizeField(fromField);
        sizeField(toField);
        sizeField(distField);
        panel.add(labeled("From:", fromField));
        panel.add(labeled("To:", toField));
        panel.add(labeled("Distance (km):", distField));

        JButton addRouteBtn = new JButton("Add Route");
        sizeButton(addRouteBtn);
        addRouteBtn.addActionListener(e -> {
            try {
                int d = Integer.parseInt(distField.getText().trim());
                boolean ok = graph.addRoute(fromField.getText().trim(), toField.getText().trim(), d);
                if (ok) {
                    log("Added route: " + fromField.getText().trim() + " -> "
                            + toField.getText().trim() + " (" + d + " km)");
                    fromField.setText("");
                    toField.setText("");
                    distField.setText("");
                    drawPanel.repaint();
                } else {
                    log("Could not add route (check station names / duplicate / distance).");
                }
            } catch (NumberFormatException ex) {
                log("Distance must be a whole number.");
            }
        });
        panel.add(addRouteBtn);
        panel.add(Box.createVerticalStrut(10));

        removeRouteFromBox = new JComboBox<>();
        removeRouteToBox = new JComboBox<>();
        sizeField(removeRouteFromBox);
        sizeField(removeRouteToBox);
        panel.add(labeled("Route from:", removeRouteFromBox));
        panel.add(labeled("Route to:", removeRouteToBox));

        JButton removeRouteBtn = new JButton("Remove Route");
        sizeButton(removeRouteBtn);
        removeRouteBtn.addActionListener(e -> {
            if (removeRouteFromBox.getSelectedItem() == null || removeRouteToBox.getSelectedItem() == null) {
                log("No route selected.");
                return;
            }
            String from = removeRouteFromBox.getSelectedItem().toString();
            String to = removeRouteToBox.getSelectedItem().toString();
            boolean ok = graph.removeRoute(from, to);
            if (ok) {
                log("Removed route: " + from + " -> " + to);
                drawPanel.repaint();
            } else {
                log("Could not remove route (not found).");
            }
        });
        panel.add(removeRouteBtn);

        panel.add(Box.createVerticalStrut(15));
        panel.add(sectionLabel("Analysis"));

        JButton cycleBtn = new JButton("Check For Cycle");
        sizeButton(cycleBtn);
        cycleBtn.addActionListener(this::onCheckCycle);
        panel.add(cycleBtn);
        panel.add(Box.createVerticalStrut(5));

        JButton sortBtn = new JButton("Sort Stations By Connections");
        sizeButton(sortBtn);
        sortBtn.addActionListener(this::onSortStations);
        panel.add(sortBtn);

        panel.add(Box.createVerticalGlue());
        // fix the width to 280, keep the natural (full) height so JScrollPane can scroll to it
        Dimension natural = panel.getPreferredSize();
        panel.setPreferredSize(new Dimension(CONTROL_WIDTH + 20, natural.height));

        return panel;
    }

    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        l.setForeground(COLOR_PRIMARY_DARK);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JPanel labeled(String label, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(5, 0));
        Dimension d = new Dimension(CONTROL_WIDTH, FIELD_HEIGHT + 6);
        p.setPreferredSize(d);
        p.setMaximumSize(d);
        p.setMinimumSize(d);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setBackground(COLOR_BG_PANEL);
        JLabel l = new JLabel(label);
        l.setForeground(COLOR_TEXT);
        p.add(l, BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    // ---------------------------------------------------------------
    // Actions
    // ---------------------------------------------------------------

    private void refreshStationBoxes() {
        startBox.removeAllItems();
        endBox.removeAllItems();

        // these three are created later in buildControlPanel(); the very first call
        // (made before that panel exists) just skips them safely
        if (removeStationBox != null) removeStationBox.removeAllItems();
        if (removeRouteFromBox != null) removeRouteFromBox.removeAllItems();
        if (removeRouteToBox != null) removeRouteToBox.removeAllItems();

        List<String> cities = new ArrayList<>(graph.graph.keySet());
        Collections.sort(cities);

        for (String city : cities) {
            startBox.addItem(city);
            endBox.addItem(city);
            if (removeStationBox != null) removeStationBox.addItem(city);
            if (removeRouteFromBox != null) removeRouteFromBox.addItem(city);
            if (removeRouteToBox != null) removeRouteToBox.addItem(city);
        }
    }

    private void onFindPath(ActionEvent e) {
        if (startBox.getSelectedItem() == null || endBox.getSelectedItem() == null) {
            log("No stations available.");
            return;
        }
        String start = startBox.getSelectedItem().toString();
        String end = endBox.getSelectedItem().toString();

        Graph.PathResult result = graph.computeShortestPath(start, end);

        cycleFrom = null;
        cycleTo = null;

        if (result == null) {
            highlightedPath.clear();
            log("No path between " + start + " and " + end + ".");
        } else {
            highlightedPath = result.path;
            StringBuilder sb = new StringBuilder("Shortest path: ");
            for (int i = 0; i < result.path.size(); i++) {
                sb.append(result.path.get(i));
                if (i != result.path.size() - 1) sb.append(" -> ");
            }
            sb.append("  |  Distance: ").append(result.distance).append(" km");
            log(sb.toString());
        }
        drawPanel.repaint();
    }

    private void onImport(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select network file to import");
        int res = chooser.showOpenDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        try {
            graph.importGraph(file.getAbsolutePath());
            log("Imported network from: " + file.getName()
                    + " (" + graph.graph.size() + " station(s))");
            refreshStationBoxes();
            highlightedPath.clear();
            cycleFrom = null;
            cycleTo = null;
            drawPanel.repaint();
        } catch (IOException ex) {
            log("Import failed: " + ex.getMessage());
        }
    }

    private void onExport(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select where to save the network");
        int res = chooser.showSaveDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        try {
            graph.exportGraph(file.getAbsolutePath());
            log("Exported network to: " + file.getName());
        } catch (IOException ex) {
            log("Export failed: " + ex.getMessage());
        }
    }

    // Requirement #3: read the network from one text file, then draw it into another file.
    private void onDrawGraphToFile(ActionEvent e) {
        JFileChooser inChooser = new JFileChooser();
        inChooser.setDialogTitle("Select the INPUT file (network to read)");
        if (inChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File inputFile = inChooser.getSelectedFile();

        JFileChooser outChooser = new JFileChooser();
        outChooser.setDialogTitle("Select the OUTPUT file (where to draw it)");
        if (outChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File outputFile = outChooser.getSelectedFile();

        try {
            graph.drawGraphToFile(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
            log("Read network from " + inputFile.getName() + " and drew it into " + outputFile.getName());
            refreshStationBoxes();
            highlightedPath.clear();
            cycleFrom = null;
            cycleTo = null;
            drawPanel.repaint();
        } catch (IOException ex) {
            log("Operation failed: " + ex.getMessage());
        }
    }

    private void onCheckCycle(ActionEvent e) {
        Graph.CycleResult result = graph.findCycleDetails();

        highlightedPath.clear();

        if (result == null) {
            cycleFrom = null;
            cycleTo = null;
            log("No cycle found in the network.");
        } else {
            cycleFrom = result.closingEdgeFrom;
            cycleTo = result.closingEdgeTo;
            StringBuilder sb = new StringBuilder("Cycle found! Path: ");
            for (int i = 0; i < result.path.size(); i++) {
                sb.append(result.path.get(i));
                if (i != result.path.size() - 1) sb.append(" -> ");
            }
            sb.append(" -> ").append(result.closingEdgeTo)
                    .append("  (closing edge: ").append(result.closingEdgeFrom)
                    .append(" -> ").append(result.closingEdgeTo).append(")");
            log(sb.toString());
        }
        drawPanel.repaint();
    }

    private void onSortStations(ActionEvent e) {
        List<String> sorted = graph.sortedStationsByConnections();
        StringBuilder sb = new StringBuilder("Stations sorted by number of connections:\n");
        for (String city : sorted) {
            sb.append("  ").append(city).append(" -> ")
                    .append(graph.graph.get(city).size()).append(" connection(s)\n");
        }
        logArea.setText(sb.toString());
    }

    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    // ---------------------------------------------------------------
    // Drawing panel
    // ---------------------------------------------------------------
    private class DrawPanel extends JPanel {

        DrawPanel() {
            setDoubleBuffered(true);
        }

        private void computePositions() {
            positions.clear();

            int n = graph.graph.size();
            if (n == 0) return;

            int w = getWidth();
            int h = getHeight();
            int centerX = w / 2;
            int centerY = h / 2;
            int radius = Math.max(50, Math.min(w, h) / 2 - 60);

            List<String> cities = new ArrayList<>(graph.graph.keySet());
            Collections.sort(cities);

            int index = 0;
            for (String city : cities) {
                double angle = (2 * Math.PI * index) / n;
                int x = centerX + (int) (radius * Math.cos(angle));
                int y = centerY + (int) (radius * Math.sin(angle));
                positions.put(city, new Point(x, y));
                index++;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            computePositions();

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(g2.getFont().deriveFont(13f));

            // draw all edges
            for (String city : graph.graph.keySet()) {
                Point p1 = positions.get(city);
                if (p1 == null) continue;

                for (Structure road : graph.graph.get(city)) {
                    Point p2 = positions.get(road.city);
                    if (p2 == null) continue;

                    boolean isCycleEdge = cycleFrom != null
                            && city.equals(cycleFrom) && road.city.equals(cycleTo);

                    if (isCycleEdge) {
                        g2.setColor(COLOR_ACCENT);
                        g2.setStroke(new BasicStroke(3f));
                    } else {
                        g2.setColor(COLOR_EDGE);
                        g2.setStroke(new BasicStroke(1.5f));
                    }

                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);

                    int midX = (p1.x + p2.x) / 2;
                    int midY = (p1.y + p2.y) / 2;
                    g2.setColor(COLOR_TEXT);
                    g2.drawString(road.distance + " km", midX, midY);
                }
            }

            // draw highlighted shortest path on top
            g2.setColor(COLOR_ACCENT);
            g2.setStroke(new BasicStroke(4f));

            for (int i = 0; i < highlightedPath.size() - 1; i++) {
                Point p1 = positions.get(highlightedPath.get(i));
                Point p2 = positions.get(highlightedPath.get(i + 1));
                if (p1 != null && p2 != null) {
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }

            // draw stations on top of everything
            for (Map.Entry<String, Point> entry : positions.entrySet()) {
                String city = entry.getKey();
                Point p = entry.getValue();

                boolean onPath = highlightedPath.contains(city);
                boolean isCycleNode = city.equals(cycleFrom) || city.equals(cycleTo);

                g2.setColor(onPath || isCycleNode ? COLOR_ACCENT : COLOR_PRIMARY);
                g2.fillOval(p.x - 12, p.y - 12, 24, 24);
                g2.setColor(Color.WHITE);
                g2.drawOval(p.x - 12, p.y - 12, 24, 24);

                g2.setColor(COLOR_TEXT);
                g2.drawString(city, p.x + 16, p.y + 4);
            }

            if (graph.graph.isEmpty()) {
                g2.setColor(COLOR_EDGE);
                g2.drawString("No stations yet \u2014 import a network or add stations using the panel on the right.",
                        20, 30);
            }
        }
    }
}
