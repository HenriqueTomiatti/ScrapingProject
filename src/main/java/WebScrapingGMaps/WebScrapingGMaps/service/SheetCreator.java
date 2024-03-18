package WebScrapingGMaps.WebScrapingGMaps.service;

import WebScrapingGMaps.WebScrapingGMaps.model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SheetCreator {

    public static void creatSheet(ArrayList<Product> products) {
        // criando planilha excel
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Relação");

        Font boldFont = workbook.createFont();

        // definindo celulas:
        CellStyle boldStyle = workbook.createCellStyle();

        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Nome do Estabelecimento");
        cell1.setCellStyle(boldStyle);

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Nota do Estabelecimento");
        cell2.setCellStyle(boldStyle);

        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Rua do Estabelecimento");
        cell3.setCellStyle(boldStyle);

        Cell cell4 = row.createCell(3);
        cell4.setCellValue("Tipo do Estabelecimento");
        cell4.setCellStyle(boldStyle);

        Cell cell5 = row.createCell(4);
        cell5.setCellValue("Contato");
        cell5.setCellStyle(boldStyle);

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);

        if (!products.isEmpty()) {

            int i = 1;
            for (Product product : products) {
                Row rowProduct = sheet.createRow(i);
                Cell celDescriptionName = rowProduct.createCell(0);
                celDescriptionName.setCellValue(product.getName());

                Cell celDescriptionRating = rowProduct.createCell(1);
                celDescriptionRating.setCellValue(product.getRating());

                Cell celDescriptionStreet = rowProduct.createCell(2);
                celDescriptionStreet.setCellValue(product.getStreet());

                Cell celDescriptionType = rowProduct.createCell(3);
                celDescriptionType.setCellValue(product.getType());

                Cell celDescriptionNumber = rowProduct.createCell(4);
                celDescriptionNumber.setCellValue(product.getNumber());
                i++;
            }
        }
        try (FileOutputStream archive = new FileOutputStream("aa.xlsx")) {
            System.out.println("entrou aqui");
            workbook.write(archive);
            System.out.println("criou");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao criar planilha: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
