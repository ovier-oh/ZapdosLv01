package com.ovier;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class CSVFolderMerger {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File folder = chooser.getSelectedFile();
                try {
                    mergeCSVFiles(folder);
                    JOptionPane.showMessageDialog(null, "Archivos combinados exitosamente en merged.csv");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error al combinar archivos: " + e.getMessage());
                }
            }
        });
    }

    private static void mergeCSVFiles(File folder) throws IOException {
        File[] csvFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            throw new IOException("No se encontraron archivos CSV en la carpeta.");
        }

        File mergedFile = new File(folder, "merged.csv");
        try (CSVWriter writer = new CSVWriter(new FileWriter(mergedFile))) {
            boolean firstFile = true;

            for (File file : csvFiles) {
                try (CSVReader reader = new CSVReader(new FileReader(file))) {
                    List<String[]> rows = reader.readAll();
                    if (!rows.isEmpty()) {
                        if (firstFile) {
                            writer.writeAll(rows); // Escribe encabezado
                            firstFile = false;
                        } else {
                            writer.writeAll(rows.subList(1, rows.size())); // Omite encabezado
                        }
                    }
                } catch (CsvException e) {
                    System.err.println("Error leyendo archivo CSV: " + file.getName() + " - " + e.getMessage());
                }
            }
        }
    }
}
