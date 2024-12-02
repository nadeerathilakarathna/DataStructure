import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> columnSelector;

    public GUI() {
        frame = new JFrame("CSV Sorting Application");
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton uploadButton = new JButton("Upload CSV");
        uploadButton.setBounds(50, 20, 150, 30);
        frame.add(uploadButton);

        columnSelector = new JComboBox<>();
        columnSelector.setBounds(250, 20, 150, 30);
        frame.add(columnSelector);

        JButton sortButton = new JButton("Sort");
        sortButton.setBounds(450, 20, 150, 30);
        frame.add(sortButton);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 70, 900, 300);
        frame.add(scrollPane);

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    FileHandler.uploadFile(filePath, tableModel, columnSelector);
                }
            }
        });

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerformanceEvaluator.sortAndDisplay(tableModel, columnSelector);
            }
        });

        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
    }

    public void show() {
        frame.setVisible(true);
    }
}
