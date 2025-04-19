package shortestpath.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class ExcelFileHandler{
    private int currentRow;
    private Sheet dataSheet;
    private Workbook workbook;

    public ExcelFileHandler(String fileName)
    {
        workbook = new XSSFWorkbook();
        dataSheet = workbook.createSheet(fileName);
        currentRow = 0;
    }

    private <T> void setCellValue(Cell cell, T value) {
        if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue()); // Stores as a Double
        } else {
            cell.setCellValue(value.toString()); // Stores as a String
        }
    }

    public <T> void setRow(T data[]) {
        Row row = dataSheet.createRow(currentRow++);

        final int size = data.length;

        for(int i = 0 ; i<size ; ++i)
        {
            setCellValue(row.createCell(i), data[i]);
        }
    }

    public void saveToFile() {
        final String dataSheetName = dataSheet.getSheetName();
        try (FileOutputStream fileOut = new FileOutputStream(dataSheetName + ".xlsx")) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Excel file saved: " + dataSheetName + ".xlsx");
        } catch (IOException e) {
            System.err.println("Error saving Excel file: " + e.getMessage());
        }
    }


}


