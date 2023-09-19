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

public class ExcelReader {

    FileInputStream fileInputStream;
    XSSFWorkbook xssfWorkbook;
    XSSFSheet xssfSheet;
    ArrayList<String> arrayList = new ArrayList<>();

    public static void main(String[] args) {
        ExcelReader excelReader = new ExcelReader();
        System.out.println(excelReader.getData("login"));
        HashMap<String,Object> jsonHashMap= new HashMap<>();
        jsonHashMap.put("firstName","vidyasagar");
        jsonHashMap.put("lastName","ramiredd");

        System.out.println(jsonHashMap);
    }

    public ArrayList<String> getData(String testCaseName) {
        try {
            fileInputStream = new FileInputStream("src/main/resources/testdata/TestData.xlsx");
            xssfWorkbook = new XSSFWorkbook(fileInputStream);
            int countOfSheets = xssfWorkbook.getNumberOfSheets();
            System.out.println(countOfSheets);

            for (int i = 0; i < countOfSheets; i++) {
                if (xssfWorkbook.getSheetName(i).equalsIgnoreCase("testdata")) {
                    xssfSheet = xssfWorkbook.getSheetAt(i);
                    Iterator<Row> rows = xssfSheet.iterator();
                    Row firstRow = rows.next();

                    Iterator<Cell> cells = firstRow.cellIterator();
                    int k = 0, column = 0;
                    while (cells.hasNext()) {
                        Cell cellvalue = cells.next();
                        if (cellvalue.getStringCellValue().equalsIgnoreCase("Testcases")) {
                            column = k;
                        }
                        k++;
                    }
                    System.out.println(column);

                    while (rows.hasNext()) {
                        Row r = rows.next();
                        if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
                            Iterator<Cell> cell = r.cellIterator();
                            while (cell.hasNext()) {
                                Cell c = cell.next();
                                if (c.getCellType() == CellType.STRING) {
                                    arrayList.add(c.getStringCellValue());
                                } else if (c.getCellType()==CellType.NUMERIC){
                                    arrayList.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
