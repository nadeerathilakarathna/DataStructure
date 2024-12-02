import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

    public static void uploadFile(String filePath, DefaultTableModel tableModel, JComboBox<String> columnSelector) {
        File file = new File(filePath);

        // Check if the file has a .csv extension
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            JOptionPane.showMessageDialog(null,
                    "Invalid file type: " + file.getName() + "\nPlease upload a valid CSV file.",
                    "Invalid File",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext();
            if (headers != null) {
                tableModel.setColumnIdentifiers(headers);
                columnSelector.removeAllItems();
                for (String header : headers) {
                    columnSelector.addItem(header);
                }
            }
            tableModel.setRowCount(0);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                tableModel.addRow(nextLine);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error reading file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (CsvValidationException e) {
            JOptionPane.showMessageDialog(null,
                    "Error validating CSV format: " + e.getMessage(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
