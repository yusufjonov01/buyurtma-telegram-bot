package gvs.service;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gvs.dto.OrderItem;
import gvs.dto.ProductItem;
import gvs.enums.OrderItemType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class CreateDocument {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthDateFormat = new SimpleDateFormat("MM.yyyy");
    SimpleDateFormat dayDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public File orderPdfDocument(OrderItem orderItem) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4, 40, 40, 30, 10);

        File file = new File("order.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter.getInstance(document, outputStream);

        document.open();

        BaseFont bfMedium = BaseFont.createFont("Montserrat-Medium.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont bfLight = BaseFont.createFont("Montserrat-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont bfExtraLight = BaseFont.createFont("Montserrat-ExtraLight.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont bfRegular = BaseFont.createFont("Montserrat-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont bfSemiBold = BaseFont.createFont("Montserrat-SemiBold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontHeader = new Font(bfSemiBold, 20, Font.BOLD);
        Font fontTitle = new Font(bfRegular, 15, Font.BOLD);
        Font fontPrice = new Font(bfRegular, 13, Font.BOLD);
        Font fontTitleHeader = new Font(bfMedium, 18, Font.BOLD);
        Font fontRight = new Font(bfLight, 13, Font.NORMAL);
        Font fontLeft = new Font(bfExtraLight, 13, Font.NORMAL);

        //  BUYURTMALAR SHARTNPOMASI PARAGRAFI

        Paragraph orderParagraph = new Paragraph("\nБ у й у р т м а  -  1\n\n", fontHeader);
        orderParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(orderParagraph);

        // BUYURTMACHI MA'LUMOTLARI

        float[] numPoinTable = {250f, 400f};
        PdfPTable pdfOrderUserTable = new PdfPTable(numPoinTable);
        pdfOrderUserTable.setWidthPercentage(100);

        PdfPCell pdfPCellOrderUserTitle = new PdfPCell(new Paragraph("Б у й у р т м а ч и: \n ", fontTitle));
        pdfPCellOrderUserTitle.setBorder(0);
        pdfPCellOrderUserTitle.setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPCellOrderUserTitle.setPaddingTop(6);


        PdfPCell pdfPCellName = new PdfPCell(new Paragraph(""));
        pdfPCellName.setBorder(0);
        pdfPCellName.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell pdfPCellOrderUsernameLeft = new PdfPCell(new Paragraph("Username:\n", fontLeft));
        pdfPCellOrderUsernameLeft.setBorder(0);
        pdfPCellOrderUsernameLeft.setPaddingLeft(10);
        pdfPCellOrderUsernameLeft.setPaddingTop(6);

        PdfPCell pdfPCellOrderUsernameRight = new PdfPCell(new Paragraph(orderItem.getUsername(), fontRight));
        pdfPCellOrderUsernameRight.setBorder(0);
        pdfPCellOrderUsernameRight.setPaddingTop(6);
        pdfPCellOrderUsernameRight.setHorizontalAlignment(Element.ALIGN_RIGHT);


        PdfPCell pdfPCellOrderUserNameLeft = new PdfPCell(new Paragraph("ФИШ: ", fontLeft));
        pdfPCellOrderUserNameLeft.setBorder(0);
        pdfPCellOrderUserNameLeft.setPaddingTop(6);
        pdfPCellOrderUserNameLeft.setPaddingLeft(10);

        PdfPCell pdfPCellOrderUserNameRight = new PdfPCell(new Paragraph(orderItem.getUserName(), fontRight));
        pdfPCellOrderUserNameRight.setBorder(0);
        pdfPCellOrderUserNameRight.setPaddingTop(6);
        pdfPCellOrderUserNameRight.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPCell pdfPCellOrderUserPhoneLeft = new PdfPCell(new Paragraph("Телефон рақам: ", fontLeft));
        pdfPCellOrderUserPhoneLeft.setBorder(0);
        pdfPCellOrderUserPhoneLeft.setPaddingTop(6);
        pdfPCellOrderUserPhoneLeft.setPaddingLeft(10);

        PdfPCell pdfPCellOrderUserPhoneRight = new PdfPCell(new Paragraph(orderItem.getPhoneNumber(), fontRight));
        pdfPCellOrderUserPhoneRight.setBorder(0);
        pdfPCellOrderUserPhoneRight.setPaddingTop(6);
        pdfPCellOrderUserPhoneRight.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPCell pdfPCellOrderDateL = new PdfPCell(new Paragraph("Буюртма қилинган вақти: ", fontLeft));
        pdfPCellOrderDateL.setBorder(0);
        pdfPCellOrderDateL.setPaddingTop(6);
        pdfPCellOrderDateL.setPaddingLeft(10);

        PdfPCell pdfPCellOrderDateR = new PdfPCell(new Paragraph(simpleDateFormat.format(orderItem.getDate()), fontRight));
        pdfPCellOrderDateR.setBorder(0);
        pdfPCellOrderDateR.setPaddingTop(6);
        pdfPCellOrderDateR.setHorizontalAlignment(Element.ALIGN_RIGHT);

        pdfOrderUserTable.addCell(pdfPCellOrderUserTitle);
        pdfOrderUserTable.addCell(pdfPCellName);
        pdfOrderUserTable.addCell(pdfPCellOrderUserNameLeft);
        pdfOrderUserTable.addCell(pdfPCellOrderUserNameRight);
        pdfOrderUserTable.addCell(pdfPCellOrderUsernameLeft);
        pdfOrderUserTable.addCell(pdfPCellOrderUsernameRight);
        pdfOrderUserTable.addCell(pdfPCellOrderUserPhoneLeft);
        pdfOrderUserTable.addCell(pdfPCellOrderUserPhoneRight);
        pdfOrderUserTable.addCell(pdfPCellOrderDateL);
        pdfOrderUserTable.addCell(pdfPCellOrderDateR);

        document.add(pdfOrderUserTable);

        //  BUYURTMALAR RO'YHATI PARAGRAFI

        Paragraph orderListParagraph = new Paragraph("\nБ у й у р т м а л а р   р ў й ҳ а т и\n\n", fontTitleHeader);
        orderListParagraph.setAlignment(Element.ALIGN_CENTER);
        orderListParagraph.setPaddingTop(6);
        document.add(orderListParagraph);

        int count = 1;
        Double allPrice = 0.00;
        Double allAmount = 0.00;

        for (ProductItem productItem : orderItem.getProductItems()) {
            PdfPTable pdfOrderUserTable2 = new PdfPTable(numPoinTable);
            pdfOrderUserTable2.setWidthPercentage(100);

            PdfPCell pdfPCellOrderList1L = new PdfPCell(new Paragraph(count + ". " + productItem.getName() +
                    "(" + productItem.getKeyName() + "):\n", fontTitle));
            pdfPCellOrderList1L.setBorder(0);
            pdfPCellOrderList1L.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPCellOrderList1L.setPaddingTop(6);

            PdfPCell pdfPCellOrderList1R = new PdfPCell(new Paragraph(""));
            pdfPCellOrderList1R.setBorder(0);
            pdfPCellOrderList1R.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell pdfPCellOrderList1CL = new PdfPCell(new Paragraph("Миқдори:\n", fontLeft));
            pdfPCellOrderList1CL.setBorder(0);
            pdfPCellOrderList1CL.setPaddingLeft(10);
            pdfPCellOrderList1CL.setPaddingTop(6);

            PdfPCell pdfPCellOrderList1CR = new PdfPCell(new Paragraph(String.format("%,.0f", productItem.getCount()) + " каробка", fontRight));
            pdfPCellOrderList1CR.setBorder(0);
            pdfPCellOrderList1CR.setPaddingTop(6);
            pdfPCellOrderList1CR.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell pdfPCellOrderList1PL = new PdfPCell(new Paragraph("Нархи:\n", fontLeft));
            pdfPCellOrderList1PL.setBorder(0);
            pdfPCellOrderList1PL.setPaddingLeft(10);
            pdfPCellOrderList1PL.setPaddingTop(6);

            PdfPCell pdfPCellOrderList1PR = new PdfPCell(new Paragraph(String.format("%,.2f", productItem.getPrice()) + " сўм", fontRight));
            pdfPCellOrderList1PR.setBorder(0);
            pdfPCellOrderList1PR.setPaddingTop(6);
            pdfPCellOrderList1PR.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell pdfPCellOrderList1APL = new PdfPCell(new Paragraph("Умумий:\n", fontPrice));
            pdfPCellOrderList1APL.setBorder(0);
            pdfPCellOrderList1APL.setPaddingLeft(10);
            pdfPCellOrderList1APL.setPaddingTop(6);

            PdfPCell pdfPCellOrderList1APR = new PdfPCell(new Paragraph(String.format("%,.2f", productItem.getAllPrice()) + " сўм", fontPrice));
            pdfPCellOrderList1APR.setBorder(0);
            pdfPCellOrderList1APR.setPaddingTop(6);
            pdfPCellOrderList1APR.setHorizontalAlignment(Element.ALIGN_RIGHT);

            pdfOrderUserTable2.addCell(pdfPCellOrderList1L);
            pdfOrderUserTable2.addCell(pdfPCellOrderList1R);
            pdfOrderUserTable2.addCell(pdfPCellOrderList1CL);
            pdfOrderUserTable2.addCell(pdfPCellOrderList1CR);
            pdfOrderUserTable2.addCell(pdfPCellOrderList1PL);
            pdfOrderUserTable2.addCell(pdfPCellOrderList1PR);
            pdfOrderUserTable2.addCell(pdfPCellOrderList1APL);
            pdfOrderUserTable2.addCell(pdfPCellOrderList1APR);

            document.add(pdfOrderUserTable2);

            allPrice += productItem.getAllPrice();
            allAmount += productItem.getAllAmount();

        }

        Paragraph orderListBorder = new Paragraph("" +
                "\n******************************" +
                "******************************************************************************" +
                "\n\n", fontLeft);
        orderListBorder.setAlignment(Element.ALIGN_CENTER);
        orderListBorder.setPaddingTop(6);
        document.add(orderListBorder);

        PdfPTable pdfOrderUserTable3 = new PdfPTable(numPoinTable);
        pdfOrderUserTable3.setWidthPercentage(100);


        PdfPCell orderListAllAmount = new PdfPCell(new Paragraph("\nБуюртма миқдори:", fontTitle));
        orderListAllAmount.setBorder(0);
        orderListAllAmount.setPaddingLeft(10);
        orderListAllAmount.setPaddingTop(5);

        PdfPCell orderListAllAmountR = new PdfPCell(new Paragraph(String.format("%,.0f", allAmount) + " каробка", fontTitle));
        orderListAllAmountR.setBorder(0);
        orderListAllAmountR.setPaddingTop(15);
        orderListAllAmountR.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPCell orderListAllPrice = new PdfPCell(new Paragraph("\nБуюртма нархи:", fontTitle));
        orderListAllPrice.setBorder(0);
        orderListAllPrice.setPaddingLeft(10);
        orderListAllPrice.setPaddingTop(5);

        PdfPCell orderListAllPriceR = new PdfPCell(new Paragraph(String.format("%,.2f", allPrice) + " сўм", fontTitle));
        orderListAllPriceR.setBorder(0);
        orderListAllPriceR.setPaddingTop(15);
        orderListAllPriceR.setHorizontalAlignment(Element.ALIGN_RIGHT);


        pdfOrderUserTable3.addCell(orderListAllAmount);
        pdfOrderUserTable3.addCell(orderListAllAmountR);
        pdfOrderUserTable3.addCell(orderListAllPrice);
        pdfOrderUserTable3.addCell(orderListAllPriceR);

        document.add(pdfOrderUserTable3);

        document.close();
        outputStream.close();
        return file;
    }

    public File orderXlsDocument(OrderItem orderItem) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        Cell cell2 = row.createCell(1);
        Cell cell3 = row.createCell(2);
        Cell cell4 = row.createCell(3);
        Cell cell5 = row.createCell(4);

        sheet.setColumnWidth(0, 2957);
        sheet.setColumnWidth(1, 2957);
        sheet.setColumnWidth(2, 2185);
        sheet.setColumnWidth(3, 2185);
        sheet.setColumnWidth(4, 2185);

        Row orderDateRow = sheet.createRow(1);
        Cell orderReqDateCell = orderDateRow.createCell(0);
        orderReqDateCell.setCellValue("Буюртма санаси: " + simpleDateFormat.format(orderItem.getDate()));

        Row orderBranchRow = sheet.createRow(2);
        Cell orderBranchCell = orderBranchRow.createCell(0);
        orderBranchCell.setCellValue("Филиал: ");

        Row orderNakladRow = sheet.createRow(3);
        Cell orderNakladCell = orderNakladRow.createCell(0);
        orderNakladCell.setCellValue(" ");

        Row orderAdminRow = sheet.createRow(4);
        Cell orderAdminCell = orderAdminRow.createCell(0);
        orderAdminCell.setCellValue("Савдо вакили: Мамажонов Мухаммадали тел. номер: +998975010023 ");

        Row orderUserRow = sheet.createRow(5);
        Cell orderUserCell = orderUserRow.createCell(0);
        orderUserCell.setCellValue("Қабул қилувчи: ______________________" + "тел. номер: +" + orderItem.getPhoneNumber());

        Row orderIdNumberRow = sheet.createRow(6);
        Cell orderIdNumberCell = orderIdNumberRow.createCell(0);
        orderIdNumberCell.setCellValue("Буюртма ИД си: ");

        Row orderUserAddresRow = sheet.createRow(7);
        Cell orderUserAddresCell = orderUserAddresRow.createCell(0);
        orderUserAddresCell.setCellValue("Манзил: ");

        Row orderDeliveryDateRow = sheet.createRow(8);
        Cell orderDeliveryDateCell = orderDeliveryDateRow.createCell(0);
        orderDeliveryDateCell.setCellValue("Юкларни жўнатиш муддати: ");

        Row orderCashTypeRow = sheet.createRow(9);
        Cell orderCashTypeCell = orderCashTypeRow.createCell(0);
        orderCashTypeCell.setCellValue("Тўлов тури номи:   ");

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 4));

        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);
        orderAdminCell.setCellStyle(cs);
        orderUserCell.setCellStyle(cs);

        // START PRODUCT LIST TABLE

        //START TABLE THEAD CONTENT
        CellStyle tableThStyle = workbook.createCellStyle();
        tableThStyle.setWrapText(true);
        tableThStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        tableThStyle.setAlignment(HorizontalAlignment.CENTER);
        tableThStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        tableThStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName("sans-serif");
        font.setBold(true);
        // Applying font to the style
        tableThStyle.setFont(font);

        tableThStyle.setBorderTop(BorderStyle.THIN);
        tableThStyle.setBorderLeft(BorderStyle.THIN);
        tableThStyle.setBorderRight(BorderStyle.THIN);
        tableThStyle.setBorderBottom(BorderStyle.THIN);
        tableThStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        Row tableRow = sheet.createRow(10);

        Cell tableProductThCell = tableRow.createCell(0);
        tableProductThCell.setCellValue("Маҳсулотлар ");
        sheet.addMergedRegion(new CellRangeAddress(10, 10, 0, 1));


        Cell tableProductTh1Cell = tableRow.createCell(1);
        Cell tableProductBlokThCell = tableRow.createCell(2);
        tableProductBlokThCell.setCellValue("Миқдори");

        Cell tableProductSumThCell = tableRow.createCell(3);
        tableProductSumThCell.setCellValue("Нархи");

        Cell tableProductAllSumThCell = tableRow.createCell(4);
        tableProductAllSumThCell.setCellValue("Миқдор чегирмалар / ставкалар");


        tableProductTh1Cell.setCellStyle(tableThStyle);
        tableProductThCell.setCellStyle(tableThStyle);
        tableProductAllSumThCell.setCellStyle(tableThStyle);
        tableProductBlokThCell.setCellStyle(tableThStyle);
        tableProductSumThCell.setCellStyle(tableThStyle);

        tableRow.setHeightInPoints((4 * sheet.getDefaultRowHeightInPoints()));

        //FINISH TABLE THEAD CONTENT

        int rowNum = 11;
        double allAmount = 0.00;
        double allPrice = 0.00;
        org.apache.poi.ss.usermodel.Font tableTdfont = workbook.createFont();
        for (ProductItem productItem : orderItem.getProductItems()) {
            int blokCount = 0;
            double count = 0.00;

            if (productItem.getCount() > (productItem.getAllAmount() / productItem.getAmount())) {
                blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
                count = productItem.getCount() % (productItem.getAllAmount() / productItem.getAmount());
            } else if (productItem.getCount() == (productItem.getAllAmount() / productItem.getAmount())) {
                blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
            } else if (productItem.getCount() < (productItem.getAllAmount() / productItem.getAmount())) {
                count = productItem.getCount();
            }

            Row tableTd = sheet.createRow(rowNum);
            Cell tableProductCell = tableTd.createCell(0);
            tableProductCell.setCellValue(productItem.getName());
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
            Cell tableProduct1Cell = tableTd.createCell(1);

            Cell tableProductBlok1Cell = tableTd.createCell(2);
            tableProductBlok1Cell.setCellValue((blokCount > 0 ? blokCount + " кор. \n" : "") +
                    (count > 0 ? String.format("%,.0f", count) + " дона" : ""));

            Cell tableProductSumCell = tableTd.createCell(3);
            tableProductSumCell.setCellValue(String.format("%,.2f", productItem.getPrice()));

            Cell tableProductAllSumCell = tableTd.createCell(4);
            tableProductAllSumCell.setCellValue(String.format("%,.2f", productItem.getTotalPrice()));

            CellStyle tableTdStyle = workbook.createCellStyle();
            tableTdStyle.setAlignment(HorizontalAlignment.RIGHT);
            tableTdStyle.setVerticalAlignment(VerticalAlignment.TOP);
            tableTdStyle.setBorderTop(BorderStyle.THIN);
            tableTdStyle.setBorderLeft(BorderStyle.THIN);
            tableTdStyle.setBorderRight(BorderStyle.THIN);
            tableTdStyle.setBorderBottom(BorderStyle.THIN);
            tableTdStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableTdStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableTdStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableTdStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

            CellStyle tableProductTdStyle = workbook.createCellStyle();
            tableProductTdStyle.setAlignment(HorizontalAlignment.LEFT);
            tableProductTdStyle.setVerticalAlignment(VerticalAlignment.TOP);
            tableProductTdStyle.setBorderTop(BorderStyle.THIN);
            tableProductTdStyle.setBorderLeft(BorderStyle.THIN);
            tableProductTdStyle.setBorderRight(BorderStyle.THIN);
            tableProductTdStyle.setBorderBottom(BorderStyle.THIN);
            tableProductTdStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableProductTdStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableProductTdStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableProductTdStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableProductCell.setCellStyle(tableProductTdStyle);
            tableProduct1Cell.setCellStyle(tableProductTdStyle);


            tableTdfont.setFontHeightInPoints((short) 8);
            tableTdfont.setFontName("sans-serif");

            tableTdStyle.setFont(tableTdfont);
            tableProductTdStyle.setFont(tableTdfont);

            tableProductBlok1Cell.setCellStyle(tableTdStyle);
            tableProductSumCell.setCellStyle(tableTdStyle);
            tableProductAllSumCell.setCellStyle(tableTdStyle);
            allAmount += productItem.getCount();
            allPrice += productItem.getTotalPrice();
            rowNum++;
        }


        // END PRODUCT LIST TABLE

        Row tableBottomRow = sheet.createRow(rowNum);

        // Table bottom itogo cell
        Cell tableBottomCell = tableBottomRow.createCell(0);
        tableBottomCell.setCellValue("Жами");
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 2, 0, 0));

        Cell tableBottomAmountCell = tableBottomRow.createCell(1);
        tableBottomAmountCell.setCellValue("Умумий вес: ");

        Cell tableBottomAllAmountCell = tableBottomRow.createCell(2);
        tableBottomAllAmountCell.setCellValue(String.format("%,.2f", allAmount));

        Cell tableBottomAllPriceCell = tableBottomRow.createCell(3);
        Cell tableBottomAllPriceCell1 = tableBottomRow.createCell(4);
        tableBottomAllPriceCell.setCellValue(String.format("%,.2f", allPrice));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 3, 4));


        CellStyle tableItogoCS = workbook.createCellStyle();
        tableItogoCS.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        tableItogoCS.setAlignment(HorizontalAlignment.RIGHT);
        tableItogoCS.setVerticalAlignment(VerticalAlignment.CENTER);
        tableItogoCS.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        tableItogoCS.setFont(font);
        tableItogoCS.setBorderTop(BorderStyle.THIN);
        tableItogoCS.setBorderLeft(BorderStyle.THIN);
        tableItogoCS.setBorderRight(BorderStyle.THIN);
        tableItogoCS.setBorderBottom(BorderStyle.THIN);
        tableItogoCS.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableItogoCS.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableItogoCS.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableItogoCS.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        tableBottomCell.setCellStyle(tableItogoCS);
        tableBottomAmountCell.setCellStyle(tableItogoCS);
        tableBottomAllAmountCell.setCellStyle(tableItogoCS);
        tableBottomAllPriceCell.setCellStyle(tableItogoCS);
        tableBottomAllPriceCell1.setCellStyle(tableItogoCS);


        Row tableBottom2Row = sheet.createRow(rowNum + 1);

        // Table bottom itogo cell
        Cell tableBottom1Cell1 = tableBottom2Row.createCell(0);
        Cell tableBottom2Cell = tableBottom2Row.createCell(1);
        Cell tableBottom2Cell1 = tableBottom2Row.createCell(2);
        tableBottom2Cell.setCellValue("Олдинги қарз");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 1, 2));
        tableBottom1Cell1.setCellStyle(tableItogoCS);
        tableBottom2Cell.setCellStyle(tableItogoCS);
        tableBottom2Cell1.setCellStyle(tableItogoCS);


        Cell tableBottomAllPriceBot2Cell = tableBottom2Row.createCell(3);
        Cell tableBottomAllPriceBot2Cell1 = tableBottom2Row.createCell(4);
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 3, 4));
        tableBottomAllPriceBot2Cell.setCellStyle(tableItogoCS);
        tableBottomAllPriceBot2Cell1.setCellStyle(tableItogoCS);


        Row tableBottom3Row = sheet.createRow(rowNum + 2);

        // Table bottom itogo cell
        Cell tableBottom3Cell1 = tableBottom3Row.createCell(0);
        Cell tableBottom3Cell = tableBottom3Row.createCell(1);
        Cell tableBottom3Cell2 = tableBottom3Row.createCell(2);
        tableBottom3Cell.setCellValue("Умумий қарз");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 2, rowNum + 2, 1, 2));
        tableBottom3Cell1.setCellStyle(tableItogoCS);
        tableBottom3Cell2.setCellStyle(tableItogoCS);
        tableBottom3Cell.setCellStyle(tableItogoCS);


        Cell tableBottomAllPrice2Cell = tableBottom3Row.createCell(3);
        tableBottomAllPrice2Cell.setCellValue(String.format("%,.0f", allPrice));
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 2, rowNum + 2, 3, 4));
        tableBottomAllPrice2Cell.setCellStyle(tableItogoCS);

        Cell tableBottomAllPrice2Cell1 = tableBottom3Row.createCell(4);
        tableBottomAllPrice2Cell1.setCellStyle(tableItogoCS);
        //FINISH TABLE BOTTOM


        Row bottomRow = sheet.createRow(rowNum + 3);

        Cell bottomCell = bottomRow.createCell(0);
        bottomCell.setCellValue("Қайта баҳолаш: 0");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 3, rowNum + 3, 0, 2));

        Cell bottomRCell = bottomRow.createCell(3);
        bottomRCell.setCellValue("Тўлов: " + String.format("%,.0f", allPrice));
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 3, rowNum + 3, 3, 4));


        CellStyle bottomCS = workbook.createCellStyle();
        bottomCS.setAlignment(HorizontalAlignment.LEFT);
        bottomCS.setFont(font);
        bottomCell.setCellStyle(bottomCS);

        CellStyle bottomRCS = workbook.createCellStyle();
        bottomRCS.setAlignment(HorizontalAlignment.RIGHT);
        bottomRCS.setFont(font);
        bottomRCell.setCellStyle(bottomRCS);


        Row bottom2Row = sheet.createRow(rowNum + 4);

        Cell bottom2Cell = bottom2Row.createCell(0);
        bottom2Cell.setCellValue("Товар топширувчи:");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 4, rowNum + 4, 0, 2));

        Cell bottomR2Cell = bottom2Row.createCell(3);
        bottomR2Cell.setCellValue("Қабул қилувчи:");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 4, rowNum + 4, 3, 4));


        CellStyle bottom2CS = workbook.createCellStyle();
        bottom2CS.setFont(tableTdfont);
        bottom2Cell.setCellStyle(bottom2CS);
        bottomR2Cell.setCellStyle(bottom2CS);


        File file = new File("order.xls");
        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        return file;
    }

    public File orderXlsBySold(OrderItem orderItem) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        Cell cell2 = row.createCell(1);
        Cell cell3 = row.createCell(2);
        Cell cell4 = row.createCell(3);
        Cell cell5 = row.createCell(4);

        sheet.setColumnWidth(0, 2957);
        sheet.setColumnWidth(1, 2957);
        sheet.setColumnWidth(2, 2185);
        sheet.setColumnWidth(3, 2185);
        sheet.setColumnWidth(4, 2185);

        Row orderDateRow = sheet.createRow(1);
        Cell orderReqDateCell = orderDateRow.createCell(0);
        orderReqDateCell.setCellValue("Буюртма санаси: " + simpleDateFormat.format(orderItem.getDate()));

        Row orderBranchRow = sheet.createRow(2);
        Cell orderBranchCell = orderBranchRow.createCell(0);
        orderBranchCell.setCellValue("Филиал: ");

        Row orderNakladRow = sheet.createRow(3);
        Cell orderNakladCell = orderNakladRow.createCell(0);
        orderNakladCell.setCellValue(" ");

        Row orderAdminRow = sheet.createRow(4);
        Cell orderAdminCell = orderAdminRow.createCell(0);
        orderAdminCell.setCellValue("Савдо вакили: Мамажонов Мухаммадали тел. номер: +998975010023 ");

        Row orderUserRow = sheet.createRow(5);
        Cell orderUserCell = orderUserRow.createCell(0);
        orderUserCell.setCellValue("Қабул қилувчи: ______________________" + "тел. номер: +" + orderItem.getPhoneNumber());

        Row orderIdNumberRow = sheet.createRow(6);
        Cell orderIdNumberCell = orderIdNumberRow.createCell(0);
        orderIdNumberCell.setCellValue("Буюртма ИД си: ");

        Row orderUserAddresRow = sheet.createRow(7);
        Cell orderUserAddresCell = orderUserAddresRow.createCell(0);
        orderUserAddresCell.setCellValue("Манзил: ");

        Row orderDeliveryDateRow = sheet.createRow(8);
        Cell orderDeliveryDateCell = orderDeliveryDateRow.createCell(0);
        orderDeliveryDateCell.setCellValue("Юкларни жўнатиш муддати: ");

        Row orderCashTypeRow = sheet.createRow(9);
        Cell orderCashTypeCell = orderCashTypeRow.createCell(0);
        orderCashTypeCell.setCellValue("Тўлов тури номи:   ");

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 4));

        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);
        orderAdminCell.setCellStyle(cs);
        orderUserCell.setCellStyle(cs);

        // START PRODUCT LIST TABLE

        //START TABLE THEAD CONTENT
        CellStyle tableThStyle = workbook.createCellStyle();
        tableThStyle.setWrapText(true);
        tableThStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        tableThStyle.setAlignment(HorizontalAlignment.CENTER);
        tableThStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        tableThStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName("sans-serif");
        font.setBold(true);
        // Applying font to the style
        tableThStyle.setFont(font);

        tableThStyle.setBorderTop(BorderStyle.THIN);
        tableThStyle.setBorderLeft(BorderStyle.THIN);
        tableThStyle.setBorderRight(BorderStyle.THIN);
        tableThStyle.setBorderBottom(BorderStyle.THIN);
        tableThStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        Row tableRow = sheet.createRow(10);

        Cell tableProductThCell = tableRow.createCell(0);
        tableProductThCell.setCellValue("Маҳсулотлар ");
        sheet.addMergedRegion(new CellRangeAddress(10, 10, 0, 1));


        Cell tableProductTh1Cell = tableRow.createCell(1);
        Cell tableProductBlokThCell = tableRow.createCell(2);
        tableProductBlokThCell.setCellValue("Миқдори");

        Cell tableProductSumThCell = tableRow.createCell(3);
        tableProductSumThCell.setCellValue("Нархи");

        Cell tableProductAllSumThCell = tableRow.createCell(4);
        tableProductAllSumThCell.setCellValue("Миқдор чегирмалар / ставкалар");


        tableProductTh1Cell.setCellStyle(tableThStyle);
        tableProductThCell.setCellStyle(tableThStyle);
        tableProductAllSumThCell.setCellStyle(tableThStyle);
        tableProductBlokThCell.setCellStyle(tableThStyle);
        tableProductSumThCell.setCellStyle(tableThStyle);

        tableRow.setHeightInPoints((4 * sheet.getDefaultRowHeightInPoints()));

        //FINISH TABLE THEAD CONTENT

        int rowNum = 11;
        double allAmount = 0.00;
        double allPrice = 0.00;
        org.apache.poi.ss.usermodel.Font tableTdfont = workbook.createFont();
        for (ProductItem productItem : orderItem.getProductItems()) {
            int blokCount = 0;
            double count = 0.00;

            if (productItem.getCount() > (productItem.getAllAmount() / productItem.getAmount())) {
                blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
                count = productItem.getCount() % (productItem.getAllAmount() / productItem.getAmount());
            } else if (productItem.getCount() == (productItem.getAllAmount() / productItem.getAmount())) {
                blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
            } else if (productItem.getCount() < (productItem.getAllAmount() / productItem.getAmount())) {
                count = productItem.getCount();
            }

            Row tableTd = sheet.createRow(rowNum);
            Cell tableProductCell = tableTd.createCell(0);
            tableProductCell.setCellValue(productItem.getName());
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
            Cell tableProduct1Cell = tableTd.createCell(1);

            Cell tableProductBlok1Cell = tableTd.createCell(2);
            tableProductBlok1Cell.setCellValue((blokCount > 0 ? blokCount + " кор. \n" : "") +
                    (count > 0 ? String.format("%,.0f", count) + " дона" : ""));

            Cell tableProductSumCell = tableTd.createCell(3);
            tableProductSumCell.setCellValue(String.format("%,.2f", productItem.getPrice()));

            Cell tableProductAllSumCell = tableTd.createCell(4);
            tableProductAllSumCell.setCellValue(String.format("%,.2f", productItem.getTotalPrice()));

            CellStyle tableTdStyle = workbook.createCellStyle();
            tableTdStyle.setAlignment(HorizontalAlignment.RIGHT);
            tableTdStyle.setVerticalAlignment(VerticalAlignment.TOP);
            tableTdStyle.setBorderTop(BorderStyle.THIN);
            tableTdStyle.setBorderLeft(BorderStyle.THIN);
            tableTdStyle.setBorderRight(BorderStyle.THIN);
            tableTdStyle.setBorderBottom(BorderStyle.THIN);
            tableTdStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableTdStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableTdStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableTdStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

            CellStyle tableProductTdStyle = workbook.createCellStyle();
            tableProductTdStyle.setAlignment(HorizontalAlignment.LEFT);
            tableProductTdStyle.setVerticalAlignment(VerticalAlignment.TOP);
            tableProductTdStyle.setBorderTop(BorderStyle.THIN);
            tableProductTdStyle.setBorderLeft(BorderStyle.THIN);
            tableProductTdStyle.setBorderRight(BorderStyle.THIN);
            tableProductTdStyle.setBorderBottom(BorderStyle.THIN);
            tableProductTdStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableProductTdStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableProductTdStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableProductTdStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            tableProductCell.setCellStyle(tableProductTdStyle);
            tableProduct1Cell.setCellStyle(tableProductTdStyle);


            tableTdfont.setFontHeightInPoints((short) 8);
            tableTdfont.setFontName("sans-serif");

            tableTdStyle.setFont(tableTdfont);
            tableProductTdStyle.setFont(tableTdfont);

            tableProductBlok1Cell.setCellStyle(tableTdStyle);
            tableProductSumCell.setCellStyle(tableTdStyle);
            tableProductAllSumCell.setCellStyle(tableTdStyle);
            allAmount += productItem.getCount();
            allPrice += productItem.getTotalPrice();
            rowNum++;
        }


        // END PRODUCT LIST TABLE

        Row tableBottomRow = sheet.createRow(rowNum);

        // Table bottom itogo cell
        Cell tableBottomCell = tableBottomRow.createCell(0);
        tableBottomCell.setCellValue("Жами");
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + 2, 0, 0));

        Cell tableBottomAmountCell = tableBottomRow.createCell(1);
        tableBottomAmountCell.setCellValue("Умумий вес: ");

        Cell tableBottomAllAmountCell = tableBottomRow.createCell(2);
        tableBottomAllAmountCell.setCellValue(String.format("%,.2f", allAmount));

        Cell tableBottomAllPriceCell = tableBottomRow.createCell(3);
        Cell tableBottomAllPriceCell1 = tableBottomRow.createCell(4);
        tableBottomAllPriceCell.setCellValue(String.format("%,.2f", allPrice));
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 3, 4));


        CellStyle tableItogoCS = workbook.createCellStyle();
        tableItogoCS.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        tableItogoCS.setAlignment(HorizontalAlignment.RIGHT);
        tableItogoCS.setVerticalAlignment(VerticalAlignment.CENTER);
        tableItogoCS.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        tableItogoCS.setFont(font);
        tableItogoCS.setBorderTop(BorderStyle.THIN);
        tableItogoCS.setBorderLeft(BorderStyle.THIN);
        tableItogoCS.setBorderRight(BorderStyle.THIN);
        tableItogoCS.setBorderBottom(BorderStyle.THIN);
        tableItogoCS.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableItogoCS.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableItogoCS.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableItogoCS.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        tableBottomCell.setCellStyle(tableItogoCS);
        tableBottomAmountCell.setCellStyle(tableItogoCS);
        tableBottomAllAmountCell.setCellStyle(tableItogoCS);
        tableBottomAllPriceCell.setCellStyle(tableItogoCS);
        tableBottomAllPriceCell1.setCellStyle(tableItogoCS);


        Row tableBottom2Row = sheet.createRow(rowNum + 1);

        // Table bottom itogo cell
        Cell tableBottom1Cell1 = tableBottom2Row.createCell(0);
        Cell tableBottom2Cell = tableBottom2Row.createCell(1);
        Cell tableBottom2Cell1 = tableBottom2Row.createCell(2);
        tableBottom2Cell.setCellValue("Олдинги қарз");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 1, 2));
        tableBottom1Cell1.setCellStyle(tableItogoCS);
        tableBottom2Cell.setCellStyle(tableItogoCS);
        tableBottom2Cell1.setCellStyle(tableItogoCS);


        Cell tableBottomAllPriceBot2Cell = tableBottom2Row.createCell(3);
        Cell tableBottomAllPriceBot2Cell1 = tableBottom2Row.createCell(4);
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 3, 4));
        tableBottomAllPriceBot2Cell.setCellStyle(tableItogoCS);
        tableBottomAllPriceBot2Cell1.setCellStyle(tableItogoCS);


        Row tableBottom3Row = sheet.createRow(rowNum + 2);

        // Table bottom itogo cell
        Cell tableBottom3Cell1 = tableBottom3Row.createCell(0);
        Cell tableBottom3Cell = tableBottom3Row.createCell(1);
        Cell tableBottom3Cell2 = tableBottom3Row.createCell(2);
        tableBottom3Cell.setCellValue("Умумий қарз");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 2, rowNum + 2, 1, 2));
        tableBottom3Cell1.setCellStyle(tableItogoCS);
        tableBottom3Cell2.setCellStyle(tableItogoCS);
        tableBottom3Cell.setCellStyle(tableItogoCS);


        Cell tableBottomAllPrice2Cell = tableBottom3Row.createCell(3);
        tableBottomAllPrice2Cell.setCellValue(String.format("%,.0f", allPrice));
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 2, rowNum + 2, 3, 4));
        tableBottomAllPrice2Cell.setCellStyle(tableItogoCS);

        Cell tableBottomAllPrice2Cell1 = tableBottom3Row.createCell(4);
        tableBottomAllPrice2Cell1.setCellStyle(tableItogoCS);
        //FINISH TABLE BOTTOM


        Row bottomRow = sheet.createRow(rowNum + 3);

        Cell bottomCell = bottomRow.createCell(0);
        bottomCell.setCellValue("Қайта баҳолаш: 0");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 3, rowNum + 3, 0, 2));

        Cell bottomRCell = bottomRow.createCell(3);
        bottomRCell.setCellValue("Тўлов: " + String.format("%,.0f", allPrice));
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 3, rowNum + 3, 3, 4));


        CellStyle bottomCS = workbook.createCellStyle();
        bottomCS.setAlignment(HorizontalAlignment.LEFT);
        bottomCS.setFont(font);
        bottomCell.setCellStyle(bottomCS);

        CellStyle bottomRCS = workbook.createCellStyle();
        bottomRCS.setAlignment(HorizontalAlignment.RIGHT);
        bottomRCS.setFont(font);
        bottomRCell.setCellStyle(bottomRCS);


        Row bottom2Row = sheet.createRow(rowNum + 4);

        Cell bottom2Cell = bottom2Row.createCell(0);
        bottom2Cell.setCellValue("Товар топширувчи:");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 4, rowNum + 4, 0, 2));

        Cell bottomR2Cell = bottom2Row.createCell(3);
        bottomR2Cell.setCellValue("Қабул қилувчи:");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 4, rowNum + 4, 3, 4));


        CellStyle bottom2CS = workbook.createCellStyle();
        bottom2CS.setFont(tableTdfont);
        bottom2Cell.setCellStyle(bottom2CS);
        bottomR2Cell.setCellStyle(bottom2CS);


        File file = new File("order.xls");
        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        return file;
    }

    public List<ProductItem> productAddByFile(String documentPath) throws IOException {

        URL website = new URL(documentPath);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream("addProduct.xls");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);


        Workbook workbook = WorkbookFactory.create(new FileInputStream("addProduct.xls"));

        Sheet sheet = workbook.getSheetAt(0);
        int totalRows = sheet.getPhysicalNumberOfRows();

        Map<String, Integer> map = new HashMap<String, Integer>(); //Create map
        Row row = sheet.getRow(0); //Get first row
        short minColX = row.getFirstCellNum();
        short maxColX = row.getLastCellNum();
        for (short col = minColX; col < maxColX; col++) {
            Cell cell = row.getCell(col);
            map.put(cell.getStringCellValue(), cell.getColumnIndex());
        }
        List<ProductItem> productItemList = new ArrayList<>();
        for (int i = 1; i < totalRows; i++) {
            Row dataRow = sheet.getRow(i);
            int idxForColumn1 = map.get("Маҳсулот номи"); //get the column index for the column with header name = "Column1"
            int idxForColumn2 = map.get("Коди"); //get the column index for the column with header name = "Column2"
            int idxForColumn3 = map.get("Бўлими\n" +
                    "(sweet, drink, diaper)"); //get the column index for the column with header name = "Column3"
            int idxForColumn4 = map.get("Нархи"); //get the column index for the column with header name = "Column3"
            int idxForColumn5 = map.get("Каробкадаги нархи"); //get the column index for the column with header name = "Column3"
            int idxForColumn6 = map.get("Миқдори"); //get the column index for the column with header name = "Column3"
            int idxForColumn7 = map.get("Миқдор тури"); //get the column index for the column with header name = "Column3"
            int idxForColumn8 = map.get("Каробкадаги миқдори"); //get the column index for the column with header name = "Column3"
            int idxForColumn9 = map.get("Сотиш тури\n" +
                    "(dona, blok, all)"); //get the column index for the column with header name = "Column3"
            int idxForColumn10 = map.get("Сони\n" +
                    "(Каробкада)"); //get the column index for the column with header name = "Column3"
            Cell productName = dataRow.getCell(idxForColumn1); //Get the cells for each of the indexes
            Cell code = dataRow.getCell(idxForColumn2);
            Cell category = dataRow.getCell(idxForColumn3);
            Cell price = dataRow.getCell(idxForColumn4);
            Cell allPrice = dataRow.getCell(idxForColumn5);
            Cell amount = dataRow.getCell(idxForColumn6);
            Cell weightType = dataRow.getCell(idxForColumn7);
            Cell allAmount = dataRow.getCell(idxForColumn8);
            Cell sellType = dataRow.getCell(idxForColumn9);
            Cell count = dataRow.getCell(idxForColumn10);

            ProductItem productItem = new ProductItem();
            productItem.setName(productName.getStringCellValue());
            productItem.setKeyName(code.toString());
            productItem.setCategoryName(category.getStringCellValue());
            productItem.setWeightType(weightType.getStringCellValue());
            productItem.setCount(count.getNumericCellValue() * (allAmount.getNumericCellValue() / amount.getNumericCellValue()));
            productItem.setPrice(Double.parseDouble(price.toString()));
            productItem.setAllPrice(Double.parseDouble(allPrice.toString()));
            productItem.setAmount(amount.getNumericCellValue());
            productItem.setAllAmount(allAmount.getNumericCellValue());
            productItem.setActive(true);
            productItem.setPhotoId("AgACAgIAAxkBAAIttV7zM3NASAtQyhFpsNGOE9JYwBUpAAL5sDEbqjihSwUdKlU1XMkSTLLvkS4AAwEAAwIAA3gAA4OEAwABGgQ");
            productItem.setProductSellType(sellType.getStringCellValue());
            productItemList.add(productItem);

        }
        return productItemList;

    }

    public File reportOrderByProduct(List<ProductItem> productList) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet();

        // START PRODUCT LIST TABLE

        //START TABLE THEAD CONTENT
        CellStyle tableThStyle = workbook.createCellStyle();
        tableThStyle.setWrapText(true);
        tableThStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        tableThStyle.setAlignment(HorizontalAlignment.CENTER);
        tableThStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        tableThStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName("sans-serif");
        font.setBold(true);
        // Applying font to the style
        tableThStyle.setFont(font);

        tableThStyle.setBorderTop(BorderStyle.THIN);
        tableThStyle.setBorderLeft(BorderStyle.THIN);
        tableThStyle.setBorderRight(BorderStyle.THIN);
        tableThStyle.setBorderBottom(BorderStyle.THIN);
        tableThStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        Row tableRow = sheet.createRow(0);

        Cell tableProductTrCell = tableRow.createCell(0);
        tableProductTrCell.setCellValue("Т/р");

        Cell tableProductThCell = tableRow.createCell(1);
        Cell tableProductThCellR = tableRow.createCell(2);
        tableProductThCell.setCellValue("Маҳсулотлар ");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));

        Cell tableProductKeyCell = tableRow.createCell(3);
        tableProductKeyCell.setCellValue("Маҳсулот коди");

        Cell tableProductCategoryCell = tableRow.createCell(4);
        tableProductCategoryCell.setCellValue("Маҳсулот тури");


        Cell tableProductAmountThCell = tableRow.createCell(5);
        tableProductAmountThCell.setCellValue("Миқдори");

        Cell tableProductSumThCell = tableRow.createCell(6);
        tableProductSumThCell.setCellValue("Нархи");

        Cell tableProductAllSumThCell = tableRow.createCell(7);
        tableProductAllSumThCell.setCellValue("Умумий нарх");


        tableProductThCellR.setCellStyle(tableThStyle);
        tableProductCategoryCell.setCellStyle(tableThStyle);
        tableProductKeyCell.setCellStyle(tableThStyle);
        tableProductTrCell.setCellStyle(tableThStyle);
        tableProductThCell.setCellStyle(tableThStyle);
        tableProductAllSumThCell.setCellStyle(tableThStyle);
        tableProductAmountThCell.setCellStyle(tableThStyle);
        tableProductSumThCell.setCellStyle(tableThStyle);

        tableRow.setHeightInPoints((4 * sheet.getDefaultRowHeightInPoints()));
//            END ALL TOP
        int rowNum = 1;
        double allAmount = 0.00;
        double allPrice = 0.00;
        org.apache.poi.ss.usermodel.Font tableTdfont = workbook.createFont();
        for (ProductItem productItem : productList) {
            int blokCount = 0;
            double count = 0.00;

            if (productItem.getCount() > (productItem.getAllAmount() / productItem.getAmount())) {
                blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
                count = productItem.getCount() % (productItem.getAllAmount() / productItem.getAmount());
            } else if (productItem.getCount() == (productItem.getAllAmount() / productItem.getAmount())) {
                blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
            } else if (productItem.getCount() < (productItem.getAllAmount() / productItem.getAmount())) {
                count = productItem.getCount();
            }
            String categoryName = "";
            switch (productItem.getCategoryName()) {
                case "sweet":
                    categoryName = "Печене";
                    break;
                case "drink":
                    categoryName = " ичимлик";
                    break;
                case "diaper":
                    categoryName = "таглик";
                    break;
            }
            //START PRODUCT LIST
            Row tableConRow = sheet.createRow(rowNum);

            Cell tableConProductTrCell = tableConRow.createCell(0);
            tableConProductTrCell.setCellValue(rowNum);

            Cell tableConProductThCell = tableConRow.createCell(1);
            Cell tableConProductThCellR = tableConRow.createCell(2);
            tableConProductThCell.setCellValue(productItem.getName());
            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 2));

            Cell tableConProductKeyCell = tableConRow.createCell(3);
            tableConProductKeyCell.setCellValue(productItem.getKeyName());

            Cell tableConProductCategoryCell = tableConRow.createCell(4);
            tableConProductCategoryCell.setCellValue(categoryName);


            Cell tableConProductAmountThCell = tableConRow.createCell(5);
            tableConProductAmountThCell.setCellValue((blokCount > 0 ? blokCount + " кор. \n" : "") +
                    (count > 0 ? String.format("%,.0f", count) + " дона" : ""));

            Cell tableConProductSumThCell = tableConRow.createCell(6);
            tableConProductSumThCell.setCellValue(String.format("%,.2f", productItem.getPrice()));

            Cell tableConProductAllSumThCell = tableConRow.createCell(7);
            tableConProductAllSumThCell.setCellValue(String.format("%,.2f", productItem.getTotalPrice()));
            rowNum++;
            //END PRODUCT LIST
            allPrice += productItem.getTotalPrice();
        }


        //  START ALL BOTTOM
        Row tableRBottomRow = sheet.createRow(rowNum + 1);

        Cell tableBotProductTrCell = tableRBottomRow.createCell(0);
        tableBotProductTrCell.setCellValue("Жами");

        Cell tableBotProductThCell = tableRBottomRow.createCell(1);
        Cell tableBotProductThCellR = tableRBottomRow.createCell(2);
        tableBotProductThCell.setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 1, 2));

        Cell tableBotProductKeyCell = tableRBottomRow.createCell(3);
        tableBotProductKeyCell.setCellValue("");

        Cell tableBotProductCategoryCell = tableRBottomRow.createCell(4);
        tableBotProductCategoryCell.setCellValue("");


        Cell tableBotProductAmountThCell = tableRBottomRow.createCell(5);
        tableBotProductAmountThCell.setCellValue("");

        Cell tableBotProductSumThCell = tableRBottomRow.createCell(6);
        tableBotProductSumThCell.setCellValue("");

        Cell tableBotProductAllSumThCell = tableRBottomRow.createCell(7);
        tableBotProductAllSumThCell.setCellValue(String.format("%,.2f", allPrice));


        tableBotProductThCellR.setCellStyle(tableThStyle);
        tableBotProductCategoryCell.setCellStyle(tableThStyle);
        tableBotProductKeyCell.setCellStyle(tableThStyle);
        tableBotProductTrCell.setCellStyle(tableThStyle);
        tableBotProductThCell.setCellStyle(tableThStyle);
        tableBotProductAllSumThCell.setCellStyle(tableThStyle);
        tableBotProductAmountThCell.setCellStyle(tableThStyle);
        tableBotProductSumThCell.setCellStyle(tableThStyle);

        File file = new File("reportByOrderProduct.xls");
        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        return file;
    }

    public File reportByProduct(List<ProductItem> productItems) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        //START TABLE THEAD CONTENT
        CellStyle tableThStyle = workbook.createCellStyle();
        tableThStyle.setWrapText(true);
        tableThStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        tableThStyle.setAlignment(HorizontalAlignment.CENTER);
        tableThStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        tableThStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName("sans-serif");
        font.setBold(true);
        // Applying font to the style
        tableThStyle.setFont(font);

        tableThStyle.setBorderTop(BorderStyle.THIN);
        tableThStyle.setBorderLeft(BorderStyle.THIN);
        tableThStyle.setBorderRight(BorderStyle.THIN);
        tableThStyle.setBorderBottom(BorderStyle.THIN);
        tableThStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        Row tableRow = sheet.createRow(0);

        Cell tableOrderUserTrCell = tableRow.createCell(0);
        Cell tableOrderUserTrCell1 = tableRow.createCell(1);
        tableOrderUserTrCell.setCellValue("Маҳсулот");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

        Cell tableProductThCell = tableRow.createCell(2);
        tableProductThCell.setCellValue("Маҳсулот коди ");

        Cell tableProductKeyCell = tableRow.createCell(3);
        tableProductKeyCell.setCellValue("Маҳсулот сони");

        Cell tableProductCategoryThCell = tableRow.createCell(4);
        tableProductCategoryThCell.setCellValue("Маҳсулот бўлими");

        Cell tableProductAmountThCell = tableRow.createCell(5);
        tableProductAmountThCell.setCellValue("Миқдори");

        Cell tableProductAllAmountThCell = tableRow.createCell(6);
        tableProductAllAmountThCell.setCellValue("Умумий миқдори");

        Cell tableProductSumThCell = tableRow.createCell(7);
        tableProductSumThCell.setCellValue("Нархи");

        Cell tableProductAllSumThCell = tableRow.createCell(8);
        tableProductAllSumThCell.setCellValue("Умумий нарх");

        tableProductCategoryThCell.setCellStyle(tableThStyle);
        tableOrderUserTrCell.setCellStyle(tableThStyle);
        tableOrderUserTrCell1.setCellStyle(tableThStyle);
        tableProductAllAmountThCell.setCellStyle(tableThStyle);
        tableProductKeyCell.setCellStyle(tableThStyle);
        tableOrderUserTrCell1.setCellStyle(tableThStyle);
        tableProductThCell.setCellStyle(tableThStyle);
        tableProductAllSumThCell.setCellStyle(tableThStyle);
        tableProductAmountThCell.setCellStyle(tableThStyle);
        tableProductSumThCell.setCellStyle(tableThStyle);

        tableRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
        int rowNum = 1;
        for (ProductItem productItem : productItems) {

            Row tableConRow1 = sheet.createRow(rowNum);
            Cell tableConProductThCell1 = tableConRow1.createCell(0);
            Cell tableConProductThCellR1 = tableConRow1.createCell(1);
            tableConProductThCell1.setCellValue(productItem.getName());
            String categoryName = "";
            switch (productItem.getCategoryName()) {
                case "sweet":
                    categoryName = "Печене";
                    break;
                case "drink":
                    categoryName = " ичимлик";
                    break;
                case "diaper":
                    categoryName = "таглик";
                    break;
            }
            int blokCount = 0;
            double count = 0.00;

            if (productItem.getCount() > (productItem.getAllAmount() / productItem.getAmount())) {
                blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
                count = productItem.getCount() % (productItem.getAllAmount() / productItem.getAmount());
            } else if (productItem.getCount() == (productItem.getAllAmount() / productItem.getAmount())) {
                blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
            } else if (productItem.getCount() < (productItem.getAllAmount() / productItem.getAmount())) {
                count = productItem.getCount();
            }
            Cell tableConProductThCell = tableConRow1.createCell(2);
            tableConProductThCell.setCellValue(productItem.getKeyName());

            Cell tableConProductAmountThCell = tableConRow1.createCell(3);
            tableConProductAmountThCell.setCellValue((blokCount > 0 ? blokCount + " кор. \n" : "") +
                    (count > 0 ? String.format("%,.0f", count) + " дона" : ""));

            Cell tableConProductCategoryCell = tableConRow1.createCell(4);
            tableConProductCategoryCell.setCellValue(categoryName);

            Cell tableConProductAmountCell = tableConRow1.createCell(5);
            tableConProductAmountCell.setCellValue(String.format("%,.2f", productItem.getAmount()));

            Cell tableConProductAllAmountCell = tableConRow1.createCell(6);
            tableConProductAllAmountCell.setCellValue(String.format("%,.2f", productItem.getAllAmount()));

            Cell tableConProductSumThCell = tableConRow1.createCell(7);
            tableConProductSumThCell.setCellValue(String.format("%,.2f", productItem.getPrice()));

            Cell tableConProductAllSumThCell = tableConRow1.createCell(8);
            tableConProductAllSumThCell.setCellValue(String.format("%,.2f", productItem.getTotalPrice()));

            rowNum++;
        }
        File file = new File("reportByProduct.xls");
        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        return file;
    }

    public File reportOrderByUser(List<OrderItem> orderItems, String dateFormat, String date) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        // START PRODUCT LIST TABLE

        //START TABLE THEAD CONTENT
        CellStyle tableThStyle = workbook.createCellStyle();
        tableThStyle.setWrapText(true);
        tableThStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        tableThStyle.setAlignment(HorizontalAlignment.CENTER);
        tableThStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        tableThStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setFontName("sans-serif");
        font.setBold(true);
        // Applying font to the style
        tableThStyle.setFont(font);

        tableThStyle.setBorderTop(BorderStyle.THIN);
        tableThStyle.setBorderLeft(BorderStyle.THIN);
        tableThStyle.setBorderRight(BorderStyle.THIN);
        tableThStyle.setBorderBottom(BorderStyle.THIN);
        tableThStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        tableThStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        Row tableRow = sheet.createRow(0);

        Cell tableOrderUserTrCell = tableRow.createCell(0);
        Cell tableOrderUserTrCell1 = tableRow.createCell(1);
        tableOrderUserTrCell.setCellValue("Мижоз");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

        Cell tableProductThCell = tableRow.createCell(2);
        Cell tableProductThCellR = tableRow.createCell(3);
        tableProductThCell.setCellValue("Маҳсулотлар ");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));

        Cell tableProductKeyCell = tableRow.createCell(4);
        tableProductKeyCell.setCellValue("Маҳсулот коди");

        Cell tableProductCategoryThCell = tableRow.createCell(5);
        tableProductCategoryThCell.setCellValue("Маҳсулот бўлими");

        Cell tableProductAmountThCell = tableRow.createCell(6);
        tableProductAmountThCell.setCellValue("Миқдори");

        Cell tableProductSumThCell = tableRow.createCell(7);
        tableProductSumThCell.setCellValue("Нархи");

        Cell tableProductAllSumThCell = tableRow.createCell(8);
        tableProductAllSumThCell.setCellValue("Умумий нарх");

        tableProductCategoryThCell.setCellStyle(tableThStyle);
        tableOrderUserTrCell.setCellStyle(tableThStyle);
        tableOrderUserTrCell1.setCellStyle(tableThStyle);
        tableProductThCellR.setCellStyle(tableThStyle);
        tableProductKeyCell.setCellStyle(tableThStyle);
        tableOrderUserTrCell1.setCellStyle(tableThStyle);
        tableProductThCell.setCellStyle(tableThStyle);
        tableProductAllSumThCell.setCellStyle(tableThStyle);
        tableProductAmountThCell.setCellStyle(tableThStyle);
        tableProductSumThCell.setCellStyle(tableThStyle);

        tableRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));

        //            END ALL TOP
        int rowNum = 1;
        int allProduct = 0;
        double allOrderPrice = 0;
        int rowNum2 = 1;
        for (OrderItem orderItem : orderItems) {

            Row tableConRow = sheet.createRow(rowNum);
            CellStyle tableOrderThStyle = workbook.createCellStyle();
            Cell tableConProductTrCell = tableConRow.createCell(0);
            Cell tableConProductTrCell1 = tableConRow.createCell(1);

            if (orderItem.getOrderItemType().equals(OrderItemType.FINISHED)) {
                boolean dateFormatB = false;
                if (dateFormat.length() > 0) {
                    switch (dateFormat) {
                        case "year":
                            dateFormatB = date.equals(yearDateFormat.format(orderItem.getDate()));
                            break;
                        case "month":
                            dateFormatB = date.equals(monthDateFormat.format(orderItem.getDate()));
                            break;
                        case "day":
                            dateFormatB = date.equals(dayDateFormat.format(orderItem.getDate()));
                            break;
                    }
                } else {
                    dateFormatB = true;
                }
                if (dateFormatB) {
                    //START PRODUCT LIST

                    double allPrice = 0.00;
                    for (ProductItem productItem : orderItem.getProductItems()) {

                        Row tableConRow1 = sheet.createRow(rowNum2);
                        Cell tableConProductThCell1 = tableConRow1.createCell(0);
                        Cell tableConProductThCellR1 = tableConRow1.createCell(1);
                        String categoryName = "";
                        switch (productItem.getCategoryName()) {
                            case "sweet":
                                categoryName = "Печене";
                                break;
                            case "drink":
                                categoryName = " ичимлик";
                                break;
                            case "diaper":
                                categoryName = "таглик";
                                break;
                        }
                        int blokCount = 0;
                        double count = 0.00;

                        if (productItem.getCount() > (productItem.getAllAmount() / productItem.getAmount())) {
                            blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
                            count = productItem.getCount() % (productItem.getAllAmount() / productItem.getAmount());
                        } else if (productItem.getCount() == (productItem.getAllAmount() / productItem.getAmount())) {
                            blokCount = (int) (productItem.getCount() / (productItem.getAllAmount() / productItem.getAmount()));
                        } else if (productItem.getCount() < (productItem.getAllAmount() / productItem.getAmount())) {
                            count = productItem.getCount();
                        }
                        Cell tableConProductThCell = tableConRow1.createCell(2);
                        Cell tableConProductThCellR = tableConRow1.createCell(3);
                        tableConProductThCell.setCellValue(productItem.getName());
                        sheet.addMergedRegion(new CellRangeAddress(rowNum2, rowNum2, 2, 3));

                        Cell tableConProductKeyCell = tableConRow1.createCell(4);
                        tableConProductKeyCell.setCellValue(productItem.getKeyName());

                        Cell tableConProductCategoryCell = tableConRow1.createCell(5);
                        tableConProductCategoryCell.setCellValue(categoryName);

                        Cell tableConProductAmountThCell = tableConRow1.createCell(6);
                        tableConProductAmountThCell.setCellValue((blokCount > 0 ? blokCount + " кор. \n" : "") +
                                (count > 0 ? String.format("%,.0f", count) + " дона" : ""));

                        Cell tableConProductSumThCell = tableConRow1.createCell(7);
                        tableConProductSumThCell.setCellValue(String.format("%,.2f", productItem.getPrice()));

                        Cell tableConProductAllSumThCell = tableConRow1.createCell(8);
                        tableConProductAllSumThCell.setCellValue(String.format("%,.2f", productItem.getTotalPrice()));

                        tableOrderThStyle.setWrapText(true);
                        tableOrderThStyle.setAlignment(HorizontalAlignment.CENTER);
                        tableOrderThStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                        tableConProductAmountThCell.setCellStyle(tableOrderThStyle);
                        allPrice += productItem.getTotalPrice();
                        rowNum2++;
                    }
//                    Row tableConRow2 = sheet.createRow(rowNum2);
//
//
//                    Cell tableConProductThCell2 = tableConRow2.createCell(2);
//                    Cell tableConProductThCellR2 = tableConRow2.createCell(3);
//                    tableConProductThCell2.setCellValue("Жами маҳсулот:");
//                    sheet.addMergedRegion(new CellRangeAddress(rowNum2, rowNum2, 2, 3));
//
//                    Cell tableConProductKeyCell2 = tableConRow2.createCell(4);
//                    tableConProductKeyCell2.setCellValue(orderItem.getProductItems().size());
//
//                    Cell tableConProductCategoryCell2 = tableConRow2.createCell(5);
//                    tableConProductCategoryCell2.setCellValue("");
//
//                    Cell tableConProductSumThCell2 = tableConRow2.createCell(6);
//                    tableConProductSumThCell2.setCellValue("Умумий нарх: " + String.format("%,.2f", allPrice));
//                    Cell tableConProductAmountThCell2 = tableConRow2.createCell(7);
//                    Cell tableConProductSumThCell22 = tableConRow2.createCell(8);
//                    sheet.addMergedRegion(new CellRangeAddress(rowNum2, rowNum2, 6, 8));
//
//
//                    CellStyle tableOrderThStyle2 = workbook.createCellStyle();
//                    tableOrderThStyle2.setWrapText(true);
//                    tableOrderThStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
//                    tableOrderThStyle2.setAlignment(HorizontalAlignment.CENTER);
//                    tableOrderThStyle2.setBorderTop(BorderStyle.THIN);
//                    tableOrderThStyle2.setBorderLeft(BorderStyle.THIN);
//                    tableOrderThStyle2.setBorderRight(BorderStyle.THIN);
//                    tableOrderThStyle2.setBorderBottom(BorderStyle.THIN);
//                    tableOrderThStyle2.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
//                    tableOrderThStyle2.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
//                    tableOrderThStyle2.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
//                    tableOrderThStyle2.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
//                    tableOrderThStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//                    tableOrderThStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
////                    tableConProductTrCell2.setCellStyle(tableOrderThStyle2);
//                    tableConProductThCell2.setCellStyle(tableOrderThStyle2);
//                    tableConProductKeyCell2.setCellStyle(tableOrderThStyle2);
//                    tableConProductSumThCell22.setCellStyle(tableOrderThStyle2);
//                    tableConProductThCellR2.setCellStyle(tableOrderThStyle2);
////                    tableConProductTrCell22.setCellStyle(tableOrderThStyle2);
//                    tableConProductAmountThCell2.setCellStyle(tableOrderThStyle2);
//                    tableConProductCategoryCell2.setCellStyle(tableOrderThStyle2);
//                    tableConProductSumThCell2.setCellStyle(tableOrderThStyle2);
//                    tableConProductAmountThCell2.setCellStyle(tableOrderThStyle2);
//                    org.apache.poi.ss.usermodel.Font fontUser = workbook.createFont();
//                    fontUser.setFontHeightInPoints((short) 8);
//                    fontUser.setFontName("sans-serif");
//                    fontUser.setBold(true);
//                    // Applying font to the style
//                    tableOrderThStyle2.setFont(font);
                    allProduct += orderItem.getProductItems().size();
                    allOrderPrice += allPrice;
                    tableConProductTrCell.setCellValue("FISH: " + orderItem.getUserName() + "\nTel: " + orderItem.getPhoneNumber() + " \n" +
                            "Vaqt: " + simpleDateFormat.format(orderItem.getDate()) + "\nУмумий нарх: " + String.format("%,.2f", allPrice)
                    );
                    tableConProductTrCell.setCellStyle(tableOrderThStyle);
                    tableConProductTrCell1.setCellStyle(tableOrderThStyle);
                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum2 - 1, 0, 1));
                }
                rowNum = rowNum2;
            }
//            rowNum++;
        }
        rowNum2++;
        //END PRODUCT LIST

        //  START ALL BOTTOM
        Row tableRBottomRow = sheet.createRow(rowNum2);

        Cell tableBotProductTrCell = tableRBottomRow.createCell(0);
        tableBotProductTrCell.setCellValue("Жами");

        Cell tableBotProductThCell = tableRBottomRow.createCell(1);
        Cell tableBotProductThCellR = tableRBottomRow.createCell(2);
        tableBotProductThCell.setCellValue("Мижоз: " + orderItems.size());
        sheet.addMergedRegion(new CellRangeAddress(rowNum2, rowNum2, 1, 2));

        Cell tableBotProductKeyCell = tableRBottomRow.createCell(3);
        tableBotProductKeyCell.setCellValue("Маҳсулот: " + allProduct);
        Cell tableBotProductCategoryCell = tableRBottomRow.createCell(4);
        sheet.addMergedRegion(new CellRangeAddress(rowNum2, rowNum2, 3, 4));

        Cell tableBotProductAmountThCell = tableRBottomRow.createCell(5);
        tableBotProductAmountThCell.setCellValue("");

        Cell tableBotProductAllSumThCell2 = tableRBottomRow.createCell(6);
        Cell tableBotProductSumThCell = tableRBottomRow.createCell(7);
        tableBotProductSumThCell.setCellValue("Умумий кирим: " + String.format("%,.2f", allOrderPrice));
        sheet.addMergedRegion(new CellRangeAddress(rowNum2, rowNum2, 7, 8));

        Cell tableBotProductAllSumThCell = tableRBottomRow.createCell(8);

        tableBotProductThCellR.setCellStyle(tableThStyle);
        tableBotProductCategoryCell.setCellStyle(tableThStyle);
        tableBotProductKeyCell.setCellStyle(tableThStyle);
        tableBotProductTrCell.setCellStyle(tableThStyle);
        tableBotProductThCell.setCellStyle(tableThStyle);
        tableBotProductAmountThCell.setCellStyle(tableThStyle);
        tableBotProductSumThCell.setCellStyle(tableThStyle);
        tableBotProductAllSumThCell2.setCellStyle(tableThStyle);
        tableBotProductAllSumThCell.setCellStyle(tableThStyle);

        File file = new File("reportByOrder.xls");
        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        return file;
    }

}
