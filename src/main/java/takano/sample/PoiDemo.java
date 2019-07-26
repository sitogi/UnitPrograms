package takano.sample;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTXf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PoiDemo {

    private static final Path PATH_XLSX = Paths.get("");
    private static final String SHEET_NAME = "test";
    private static final String CELL_ADDR = "A1:C3";

    public static void main(String[] args) throws IOException, InvalidFormatException {
        writeBook();
        readBook();
    }

    private static void writeBook() throws IOException {
        final XSSFWorkbook book = new XSSFWorkbook();

        final StylesTable stylesTable = book.getStylesSource();

        final XSSFSheet sheet = book.createSheet(SHEET_NAME);

        Row row1 = sheet.createRow(1);
        row1.setHeightInPoints(70);

        Cell cell1_1 = row1.createCell(1);
        cell1_1.setCellValue("Sample");

        XSSFCellStyle existingCellStyle = (XSSFCellStyle) cell1_1.getCellStyle();
        final CTXf ctXf = ((CTXf) existingCellStyle.getCoreXf().copy());

        final int cellXfId = stylesTable.putCellXf(ctXf) - 1;
        final int xfId = (int) ((XSSFCellStyle) cell1_1.getCellStyle()).getStyleXf().getXfId();
        final XSSFCellStyle xssfCellStyle = new XSSFCellStyle(cellXfId, xfId, stylesTable, book.getTheme());

//        final XSSFCellStyle style = book.createCellStyle();
//        style.setBorderTop(BorderStyle.DASH_DOT);
//        style.setBorderBottom(BorderStyle.DASH_DOT);
//        style.setBorderLeft(BorderStyle.DASH_DOT);
//        style.setBorderRight(BorderStyle.DASH_DOT);

//        XSSFCellBorder

        cell1_1.setCellStyle(xssfCellStyle);

        try(FileOutputStream out = new FileOutputStream(PATH_XLSX.toFile())) {
            book.write(out);
        }
    }

    private static void readBook() throws IOException, InvalidFormatException {
        final CellRangeAddress range = CellRangeAddress.valueOf(CELL_ADDR);
        try (Workbook workbook = WorkbookFactory.create(PATH_XLSX.toFile())) {
            final Sheet sheet = workbook.getSheet(SHEET_NAME);
            for (int r = range.getFirstRow(); r <= range.getLastRow(); r++) {
                final Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                for (int c = range.getFirstColumn(); c <= range.getLastColumn(); c++) {
                    final Cell cell = row.getCell(c);
                    if (cell == null) {
                        continue;
                    }
                    System.out.println(cell.getCellStyle().getBorderLeftEnum());
                }
            }
        }
    }
}
