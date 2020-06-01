package pl.psk.salesManager.service;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DoubleBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.model.Order;
import pl.psk.salesManager.model.Owner;
import pl.psk.salesManager.model.SoldProduct;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class InvoiceService {
    @Autowired
    OwnerService ownerService;
    @Autowired
    OrderService orderService;
    private final String downloads = System.getProperty("user.home") + "/Downloads/";

    public void generateInvoice(int orderId) throws IOException {
        Order order = orderService.findOrder(orderId);
        Owner owner = ownerService.displayOwnerData();
        String invoicePath = downloads + "Faktura_" + order.getId() + ".pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(invoicePath));
        Document invoice = new Document(pdfDoc);
        FontProgram fontProgram = FontProgramFactory.createFont();
        PdfFont font = PdfFontFactory.createFont(fontProgram, "Cp1250");
        invoice.setFont(font);

        addTitle(invoice, orderId);
        addInvoiceData(invoice, order);
        invoice.add( new Paragraph("\n\n") );
        addProductsTable(invoice, order, ownerService.displayOwnerData());
        if(order.getPaymentMethod().equals("Przelew bankowy")) addTransferData(invoice, owner);
        addSignaturePlace(invoice, pdfDoc.getDefaultPageSize());
        invoice.close();
    }

    private void addTitle(Document invoice, int id) {
        Paragraph invoiceTitle = new Paragraph("Faktura VAT");
        invoiceTitle.setBackgroundColor(Color.LIGHT_GRAY);
        invoiceTitle.setFontSize(28);
        invoice.add(invoiceTitle);
    }

    private void addInvoiceData(Document document, Order order) {
        Owner owner = ownerService.displayOwnerData();
        Client client = order.getClient();
        Table table = new Table(4);

        table.addCell(createBorderlessHeaderCell("Sprzedawca: ").setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessHeaderCell("Kupujący:").setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessHeaderCell("Faktura nr. "));
        table.addCell(createBorderlessCell(String.valueOf(order.getId()+ "/" + getYearFromDate(new Date()))));

        table.addCell(createBorderlessHeaderCell(owner.getCompanyName()).setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessCell(order.getClientFullName()).setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessHeaderCell("Data transakcji:"));
        table.addCell(createBorderlessCell(formatDate(order.getCreated())));

        table.addCell(createBorderlessCell(owner.getAddress()).setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessCell(client.getAddress()).setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessHeaderCell("Data wystawienia"));
        table.addCell(createBorderlessCell(formatDate(new Date())));

        table.addCell(createBorderlessCell(owner.getPhone()).setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessCell(" ").setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessCell("Sposób płatności"));
        table.addCell(createBorderlessCell(order.getPaymentMethod()));

        table.addCell(createBorderlessCell(owner.getEmail()).setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessCell(" ").setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessCell(""));
        table.addCell(createBorderlessCell(""));

        table.addCell(createBorderlessCell("NIP: " + owner.getNip()).setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessCell("").setBorderRight(new DoubleBorder(Color.DARK_GRAY, 1)));
        table.addCell(createBorderlessCell(""));
        table.addCell(createBorderlessCell(""));

        document.add(table);
    }

    private void addProductsTable(Document document, Order order, Owner owner) {
        Table table = new Table(5);
        DecimalFormat dec = new DecimalFormat("#0.00");

        table.addCell(createHeaderCell("Ilość"));
        table.addCell(createHeaderCell("Opis"));
        table.addCell(createHeaderCell("Cena za sztukę"));
        table.addCell(createHeaderCell("Podatek"));
        table.addCell(createHeaderCell("Cena"));

        addProductsToTable(table, order, owner);

        table.addCell(createCell(" "));
        table.addCell(createCell(" "));
        table.addCell(createCell(" "));
        table.addCell(createHeaderCell("Suma: "));
        table.addCell(createHeaderCell(String.valueOf(order.getTotalPrice()) + " " + owner.getCurrencySymbol()));

        table.addCell(createCell(" "));
        table.addCell(createCell(" "));
        table.addCell(createCell(" "));
        table.addCell(createHeaderCell("Suma podatku: "));
        table.addCell(createHeaderCell(String.valueOf(dec.format(order.getTaxTotal())) + " " + owner.getCurrencySymbol()));

        table.addCell(createCell(" "));
        table.addCell(createCell(" "));
        table.addCell(createCell(" "));
        table.addCell(createHeaderCell("Kwota całościowa: "));
        table.addCell(createHeaderCell(String.valueOf(dec.format(order.getPriceAndTax())) + " " + owner.getCurrencySymbol()));

        document.add(table);
    }

    private void addSignaturePlace(Document document, PageSize ps) {
        Table signatureTable = new Table(2);
        signatureTable.addCell(createBorderlessCell("_______________________"));
        signatureTable.addCell(createBorderlessCell("_______________________"));
        signatureTable.addCell(createBorderlessCell("Podpis sprzedawcy"));
        signatureTable.addCell(createBorderlessCell("Podpis kupującego"));
        signatureTable.setFixedPosition(document.getLeftMargin(), document.getBottomMargin(), ps.getWidth() - document.getLeftMargin() - document.getRightMargin());
        document.add(signatureTable);
    }

    private void addProductsToTable(Table table, Order order, Owner owner) {
        for (SoldProduct product: order.getProductsOrdered()) {
            table.addCell(createCell(String.valueOf(product.getQuantity())));
            table.addCell((createCell(product.getProduct().getName())));
            table.addCell(createCell(String.valueOf(product.getProduct().getPrice()) + " " + owner.getCurrencySymbol()));
            table.addCell(createCell(String.valueOf(product.getProduct().getTax()) + " %"));
            table.addCell(createCell(String.valueOf((product.getProduct().getPrice() * product.getQuantity()) + " " + owner.getCurrencySymbol())));
        }
    }

    private void addTransferData(Document document, Owner owner) {
        Paragraph transferTitle = new Paragraph("Dane do przelewu:");
        transferTitle.setFontColor(Color.DARK_GRAY);

        Paragraph bankName = new Paragraph(owner.getBankName());
        Paragraph bankNumber = new Paragraph(owner.getBankNumber());

        document.add(new Paragraph(" "));
        document.add(transferTitle);
        document.add(bankName);
        document.add(bankNumber);
    }

    private Cell createBorderlessHeaderCell(String content) {
        Cell cell = new Cell();
        cell.setFontColor(Color.DARK_GRAY);
        cell.setFontSize(12);
        cell.setBorder(Border.NO_BORDER);
        cell.add(content);
        return cell;
    }

    private Cell createBorderlessCell(String content) {
        Cell cell = new Cell();
        cell.setFontSize(12);
        cell.setBorder(Border.NO_BORDER);
        cell.add(content);
        return cell;
    }

    private Cell createHeaderCell(String content) {
        Cell cell = new Cell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setBorderLeft(Border.NO_BORDER);
        cell.setBorderRight(Border.NO_BORDER);
        cell.add(content);
        return cell;
    }

    private Cell createCell(String content) {
        Cell cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.add(content);
        return cell;
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        return formatter.format(date);
    }

    private String getYearFromDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yy");
        return formatter.format(date);
    }
}
