import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import storing.Record;

public class Parser_ExcelToJava {
    public static ArrayList<Record> parse(String fileName) {
        //инициализируем потоки
        ArrayList<Record> result = new ArrayList<>();
        InputStream inputStream = null;
        XSSFWorkbook workBook = null;
        try {
            inputStream = new FileInputStream(fileName);
            workBook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //разбираем первый лист входного файла на объектную модель
        XSSFSheet sheet = workBook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();//
        it.next();
        //проходим по всему листу
        int id = 0;
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            cells.next();
            //проходим по всем строкам
            Record record = new Record();
            int count = 0;
            while (cells.hasNext()) {
                Cell cell = cells.next();
                //перебираем все ячейки
                switch (count) {
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                count++;
            }
            result.add(record);
            id++;
        }
        return result;
    }

    public static void main(String[] args) {
    }
}
