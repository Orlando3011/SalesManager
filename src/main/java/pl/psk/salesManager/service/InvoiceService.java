package pl.psk.salesManager.service;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.model.Order;
import pl.psk.salesManager.model.Owner;
import pl.psk.salesManager.model.SoldProduct;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class InvoiceService {
    @Autowired
    OwnerService ownerService;
    @Autowired
    OrderService orderService;
    private final String downloads = System.getProperty("user.home") + "/Downloads/";

    public void generateInvoice(int orderId) throws FileNotFoundException {
        Order order = orderService.findOrder(orderId);
        String invoicePath = downloads + "Invoice_" + order.getId() + ".pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(invoicePath));
        Document invoice = new Document(pdfDoc);

        addTitle(invoice, orderId);
        addInvoiceData(invoice, order);
        addProductsTable(invoice, order);
        addSignaturePlace(invoice, pdfDoc.getDefaultPageSize());
        invoice.close();
    }

    public void addTitle(Document invoice, int id) {
        Paragraph invoiceTitle = new Paragraph("Invoice");
        invoiceTitle.setBackgroundColor(Color.LIGHT_GRAY);
        invoiceTitle.setFontSize(28);
        invoice.add(invoiceTitle);
    }

    private void addInvoiceData(Document document, Order order) {
        Owner owner = ownerService.displayOwnerData();
        Client client = order.getClient();
        Table table = new Table(4);

        table.addCell(createBorderlessHeaderCell(owner.getCompanyName()));
        table.addCell(createBorderlessHeaderCell("Sell to:"));
        table.addCell(createBorderlessHeaderCell("Invoice no. "));
        table.addCell(createBorderlessCell(String.valueOf(order.getId())));

        table.addCell(createBorderlessCell(owner.getAddress()));
        table.addCell(createBorderlessCell(order.getClientFullName()));
        table.addCell(createBorderlessHeaderCell("Date of order:"));
        table.addCell(createBorderlessCell(formatDate(order.getCreated())));

        table.addCell(createBorderlessCell(owner.getPhone()));
        table.addCell(createBorderlessCell(client.getAddress()));
        table.addCell(createBorderlessHeaderCell("Date:"));
        table.addCell(createBorderlessCell(formatDate(new Date())));

        table.addCell(createBorderlessCell(owner.getEmail()));
        table.addCell(createBorderlessCell(" "));
        table.addCell(createBorderlessCell(" "));
        table.addCell(createBorderlessCell(" "));

        document.add(table);
    }

    private void addProductsTable(Document document, Order order) {
        Table table = new Table(4);

        table.addCell(createHeaderCell("Qty"));
        table.addCell(createHeaderCell("Description"));
        table.addCell(createHeaderCell("Unit price"));
        table.addCell(createHeaderCell("Amount"));

        addProductsToTable(table, order);

        table.addCell(createCell(" "));
        table.addCell(createCell(" "));
        table.addCell(createHeaderCell("Total: "));
        table.addCell(createHeaderCell(String.valueOf(order.getTotalPrice())));

        document.add(table);
    }

    private void addSignaturePlace(Document document, PageSize ps) {
        Table signatureTable = new Table(2);
        signatureTable.addCell(createBorderlessCell("_______________________"));
        signatureTable.addCell(createBorderlessCell("_______________________"));
        signatureTable.addCell(createBorderlessCell("Seller signature"));
        signatureTable.addCell(createBorderlessCell("Client signature"));
        signatureTable.setFixedPosition(document.getLeftMargin(), document.getBottomMargin(), ps.getWidth() - document.getLeftMargin() - document.getRightMargin());
        document.add(signatureTable);
    }

    private void addProductsToTable(Table table, Order order) {
        for (SoldProduct product: order.getProductsOrdered()) {
            table.addCell(createCell(String.valueOf(product.getQuantity())));
            table.addCell((createCell(product.getProduct().getName())));
            table.addCell(createCell(String.valueOf(product.getProduct().getPrice())));
            table.addCell(createCell(String.valueOf(product.getProduct().getPrice() * product.getQuantity())));
        }
    }

    private Cell createBorderlessHeaderCell(String content) {
        Cell cell = new Cell();
        cell.setFontColor(Color.LIGHT_GRAY);
        cell.setFontSize(14);
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
        return formatter.format(new Date());
    }

}
