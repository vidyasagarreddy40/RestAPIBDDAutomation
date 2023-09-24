package DataReader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class ExcelReader {

    FileInputStream fileInputStream;
    XSSFWorkbook xssfWorkbook;
    XSSFSheet xssfSheet;
    ArrayList<String> arrayList = new ArrayList<>();

    public static void main(String[] args) {
        ExcelReader excelReader = new ExcelReader();
        System.out.println(excelReader.getData("login"));
        HashMap<String, Object> jsonHashMap = new HashMap<>();
        jsonHashMap.put("firstName", "vidyasagar");
        jsonHashMap.put("lastName", "ramiredd");

        System.out.println(jsonHashMap);
    }

    public ArrayList<String> getData(String testScenario) {
        try {
            HashMap<String, Object> hs = new LinkedHashMap<>();
            fileInputStream = new FileInputStream("src/main/resources/testdata/TestData.xlsx");
            xssfWorkbook = new XSSFWorkbook(fileInputStream);

            int countOfSheets = xssfWorkbook.getNumberOfSheets();

            System.out.println(countOfSheets);

            for (int i = 0; i < countOfSheets; i++) {
                if (xssfWorkbook.getSheetName(i).equalsIgnoreCase("addplace")) {
                    xssfSheet = xssfWorkbook.getSheetAt(i);
                    Iterator<Row> rows = xssfSheet.iterator();
                    Row firstRow = rows.next();

                    Iterator<Cell> cells = firstRow.cellIterator();
                    int k = 0, column = 0;
                    while (cells.hasNext()) {
                        Cell cellvalue = cells.next();
                        if (cellvalue.getStringCellValue().equalsIgnoreCase("TestScenario")) {
                            column = k;
                        }

                        k++;

                    }
                    System.out.println(column);

                    Iterator<Cell> firstRowCells = firstRow.cellIterator();
                    while (firstRowCells.hasNext()) {

                        while (rows.hasNext()) {
                            Row r = rows.next();
                            if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testScenario)) {
                                Iterator<Cell> cell = r.cellIterator();
                                while (cell.hasNext()) {
                                    Cell firstRowCellValue = firstRowCells.next();
                                    Cell c = cell.next();
                                    if (c.getCellType() == CellType.STRING) {
                                        if (!firstRowCellValue.getStringCellValue().equalsIgnoreCase("TestScenario")) {
                                            hs.put(firstRowCellValue.getStringCellValue(), c.getStringCellValue());
                                        }
                                        arrayList.add(c.getStringCellValue());
                                    } else if (c.getCellType() == CellType.NUMERIC) {
                                        hs.put(firstRowCellValue.getStringCellValue(), (int) c.getNumericCellValue());
                                        arrayList.add(NumberToTextConverter.toText((int) c.getNumericCellValue()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(hs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
