package com.jskno.budgetgenerator.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jskno.budgetgenerator.interfaces.BudgetService;
import com.jskno.budgetgenerator.interfaces.PdfService;
import com.jskno.budgetgenerator.model.database.Budget;
import com.jskno.budgetgenerator.model.database.BudgetItem;
import com.jskno.budgetgenerator.model.ui.converters.BigDecimalCurrencyStringConverter;
import com.jskno.budgetgenerator.model.ui.converters.BigDecimalPercentStringConverter;
import com.jskno.budgetgenerator.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PdfServiceImpl implements PdfService {

    private BudgetService budgetService;
    private BigDecimalCurrencyStringConverter bigDecimalCurrencyStringConverter;
    private BigDecimalPercentStringConverter bigDecimalPercentStringConverter;

    @Autowired
    public PdfServiceImpl(BudgetService budgetService,
              BigDecimalCurrencyStringConverter bigDecimalCurrencyStringConverter,
                          BigDecimalPercentStringConverter bigDecimalPercentStringConverter) {
        this.budgetService = budgetService;
        this.bigDecimalCurrencyStringConverter = bigDecimalCurrencyStringConverter;
        this.bigDecimalPercentStringConverter = bigDecimalPercentStringConverter;
    }

    @Override
    public void generateBudgetPdf(Long budgetId) {
        Budget budget = budgetService.findById(budgetId);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        PdfPTable titleTable = createTitleTable(budget.getName());
        PdfPTable table = createTable(budget);

        try {
            document.add(titleTable);
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();

//            Desktop.getDesktop().open(new File("iTextHelloWorld.pdf"));
        DesktopApi.open(new File("iTextHelloWorld.pdf"));
    }

    private Image getImage() {

        InputStream input = this.getClass().getResourceAsStream("/images/budgetLogo.jpg");

        Image img = null;
        try {
            img = Image.getInstance(input.readAllBytes());
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        img.scalePercent(10);
        return img;
    }

    private PdfPTable createTitleTable(String budgetName) {
        PdfPTable titleTable = new PdfPTable(2);
        titleTable.setPaddingTop(20);
        titleTable.setWidthPercentage(100);
        try {
            titleTable.setWidths(new int[] {6, 2});
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        PdfPCell titleEmptyCell = new PdfPCell();
        titleEmptyCell.setHorizontalAlignment(1);
        titleEmptyCell.setBorder(PdfPCell.NO_BORDER);

        PdfPCell imageEmptyCell = new PdfPCell();
        imageEmptyCell.setHorizontalAlignment(2);
        imageEmptyCell.setPaddingBottom(40);
        imageEmptyCell.setBorder(PdfPCell.NO_BORDER);

        Font font = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Phrase budgetTitlePhrase = new Phrase("Presupuesto: " + budgetName, font);
        PdfPCell titleCell = new PdfPCell(budgetTitlePhrase);
        titleCell.setPaddingBottom(40);
        titleCell.setHorizontalAlignment(0);
        titleCell.setBorder(PdfPCell.NO_BORDER);

        PdfPCell imageCell = new PdfPCell(getImage());
        imageCell.setHorizontalAlignment(2);
        imageCell.setBorder(PdfPCell.NO_BORDER);

        titleTable.addCell(titleEmptyCell);
        titleTable.addCell(imageCell);
        titleTable.addCell(titleCell);
        titleTable.addCell(imageEmptyCell);

        return titleTable;
    }

    private PdfPTable createTable(Budget budget) {
        PdfPTable table = new PdfPTable(11);
        table.setWidthPercentage(100);
        table.setPaddingTop(20);

        try {
            table.setWidths(new int[] {14, 5, 4, 4, 5, 3, 4, 5, 3, 4, 5});
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        addTableHeader(table);
        addRows(table, budget.getBudgetItems());
        addTotalRows(table, budget);
        return table;
    }

    private void addTableHeader(PdfPTable table) {
        addHeaderCell(table, "Concepto", 6f,1, 2, PdfPCell.BOX);
        addHeaderCell(table, "Precio", 6f,1, 2, PdfPCell.BOX);
        addHeaderCell(table, "Unidades", 5f,1, 2, PdfPCell.BOX);
        addHeaderCell(table, "Cantidad", 6f,1, 2, PdfPCell.BOX);
        addHeaderCell(table, "Total Coste", 6f,1, 2, PdfPCell.BOX);
        addHeaderCell(table, "Beneficio", 6f,2, 1, PdfPCell.TOP);
        addHeaderCell(table, "Coste + Beneficio", 6f,1, 2, PdfPCell.BOX);
        addHeaderCell(table, "IVA", 6f,2, 1, PdfPCell.TOP);
        addHeaderCell(table, "Total Concepto", 6f,1, 2, PdfPCell.BOX);
        addHeaderCell(table, " % ", 6f,1, 1, PdfPCell.BOX);
        addHeaderCell(table, " € ", 6f,1, 1, PdfPCell.BOX);
        addHeaderCell(table, " % ", 6f,1, 1, PdfPCell.BOX);
        addHeaderCell(table, " € ", 6f,1, 1, PdfPCell.BOX);
    }

    private void addHeaderCell(PdfPTable table, String columnTitle, float size,
                       int colspan, int rowspan, int border) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(0.5f);
        header.setColspan(colspan);
        header.setRowspan(rowspan);
        header.setBorder(border);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        try {
            header.setPhrase(new Phrase(columnTitle, new Font(BaseFont.createFont(), size)));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.addCell(header);
    }

    private void addRows(PdfPTable table, List<BudgetItem> budgetItems) {

        budgetItems.stream().forEach(budgetItem -> {
            addBodyCell(table, budgetItem.getConcept(), 7f);
            addBodyCell(table, bigDecimalCurrencyStringConverter.toString(budgetItem.getPrice()), 7f);
            addBodyCell(table, budgetItem.getUnits().getLabel(), 5f);
            addBodyCell(table, budgetItem.getQuantity().toString(), 7f);
            addBodyCell(table,
                    bigDecimalCurrencyStringConverter.toString(budgetItem.getTotalCost()), 7f);
            addBodyCell(table, bigDecimalPercentStringConverter.toString(
                    budgetItem.getMargin().multiply(Constants.ONE_HUNDRED)), 6f);
            addBodyCell(table,
                    bigDecimalCurrencyStringConverter.toString(budgetItem.getMarginAmount()), 7f);
            addBodyCell(table,
                    bigDecimalCurrencyStringConverter.toString(budgetItem.getTotalCostPlusMargin()), 7f);
            addBodyCell(table, bigDecimalPercentStringConverter.toString(
                    budgetItem.getVat().multiply(Constants.ONE_HUNDRED)), 6f);
            addBodyCell(table,
                    bigDecimalCurrencyStringConverter.toString(budgetItem.getVatAmount()), 7f);
            addBodyCell(table,
                    bigDecimalCurrencyStringConverter.toString(budgetItem.getTotalBudgetItem()), 7f);
        });
    }

    private void addBodyCell(PdfPTable table, String cellValue, float size) {
        PdfPCell cell = new PdfPCell();
        try {
            cell.setPhrase(new Phrase(cellValue, new Font(BaseFont.createFont(), size)));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.addCell(cell);
    }

    private void addTotalRows(PdfPTable table, Budget budget) {

        IntStream.range(0, 11).forEach(value -> table.addCell(" "));

        addBodyCell(table, "TOTALES",7f);
        IntStream.range(0, 3).forEach(value -> table.addCell(""));
        addBodyCell(table, budget.getTotalCost().toString(),7f);
        addBodyCell(table,
                budget.getMarginPercent()
                        .multiply(Constants.ONE_HUNDRED)
                        .setScale(2).toString(),7f);
        addBodyCell(table, budget.getTotalMargin().toString(),7f);
        addBodyCell(table, budget.getTotalCostPlusMargin().toString(),7f);
        table.addCell("");
        addBodyCell(table, budget.getTotalVat().toString(),7f);
        addBodyCell(table, budget.getTotal().toString(),7f);
    }

}
