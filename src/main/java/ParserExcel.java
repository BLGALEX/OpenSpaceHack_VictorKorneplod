import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import storing.Record;
import storing.RB;

public class ParserExcel {

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
        sheet.getLastRowNum();
        sheet.getPhysicalNumberOfRows();
        //проходим по всему листу
        int id = 0;
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            cells.next();
            //проходим по всем строкам
            Record record = new Record();
            record.setId(id);
            int count = 0;
            List<String> steps = new ArrayList<>();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                //перебираем все ячейки
                switch (count) {
                    case 0:
                        record.setRequest(cell.getStringCellValue());
                        break;
                    case 1:
                        record.setClarification(cell.getStringCellValue());
                        break;
                    case 2:
                        String rb = cell.getStringCellValue();
                        if (rb.equals("ИБ"))
                            record.setRb(RB.INTERNET);
                        else if (rb.equals("МБ"))
                            record.setRb(RB.MOBILE);
                        break;
                    case 3:
                        record.setQuestion(cell.getStringCellValue());
                        break;
                    default:
                        if (cell != null && cell.getStringCellValue() != null &&
                        !cell.getStringCellValue().isEmpty())
                            steps.add(cell.getStringCellValue());
                        break;
                }
                count++;
            }
            if (record.getQuestion() != null && !record.getQuestion().isEmpty()) {
                record.setSteps(steps);
                record.setnSteps(steps.size());
                result.add(record);
            }
            id++;
        }
        return result;
    }

    public static void main(String[] args) {
        ParserExcel Parser = new ParserExcel();
        File file = new File(".");
        ArrayList<Record> records = parse("src/main/resources/Database.xlsx");
        for (Record record: records)
            System.out.println(record + "\n");
    }
}
