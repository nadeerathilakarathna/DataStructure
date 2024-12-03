import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class PerformanceEvaluator {

    public static void sortAndDisplay(DefaultTableModel tableModel, JComboBox<String> columnSelector) {
        int selectedColumn = columnSelector.getSelectedIndex();
        if (selectedColumn < 0) {
            JOptionPane.showMessageDialog(null, "Please select a column to sort!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int rowCount = tableModel.getRowCount();
        String[] data = new String[rowCount];

        // Validate if the column is numerical
        for (int i = 0; i < rowCount; i++) {
            Object cellValue = tableModel.getValueAt(i, selectedColumn);
            if (!(cellValue instanceof String)) {
                JOptionPane.showMessageDialog(null, "Selected column is not numerical!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Double.parseDouble((String) cellValue);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Selected column is not numerical!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            data[i] = (String) cellValue;
        }

        String[][] performanceData = new String[5][2];
        String[] columnNames = {"Algorithm", "Duration (ms)"};
        double minTime = Double.MAX_VALUE;
        String bestAlgorithm = "";

        int index = 0;
        for (String algorithm : Arrays.asList("Insertion Sort", "Merge Sort", "Quick Sort", "Shell Sort", "Heap Sort")) {
            String[] tempData = Arrays.copyOf(data, data.length);
            long startTime = System.nanoTime();

            // Run the respective sorting algorithm
            switch (algorithm) {
                case "Insertion Sort" -> SortingAlgorithms.insertionSort(tempData);
                case "Merge Sort" -> SortingAlgorithms.mergeSort(tempData);
                case "Quick Sort" -> SortingAlgorithms.quickSort(tempData, 0, tempData.length - 1);
                case "Shell Sort" -> SortingAlgorithms.shellSort(tempData);
                case "Heap Sort" -> SortingAlgorithms.heapSort(tempData);
            }

            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1_000_000.0;
            performanceData[index][0] = algorithm;
            performanceData[index][1] = String.format("%.4f", duration);

            if (duration < minTime) {
                minTime = duration;
                bestAlgorithm = algorithm;
            }
            index++;
        }

        performanceData = Arrays.copyOf(performanceData, performanceData.length + 2);
        performanceData[5] = new String[]{"", ""};
        performanceData[6] = new String[]{"Best Algorithm", bestAlgorithm};

        JTable resultTable = new JTable(performanceData, columnNames);


        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        JOptionPane.showMessageDialog(null, scrollPane, "Performance Results", JOptionPane.INFORMATION_MESSAGE);
    }
}
