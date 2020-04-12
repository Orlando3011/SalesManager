package pl.psk.salesManager.service;

import com.itextpdf.text.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    @Autowired
    OwnerService ownerService;
    @Autowired
    OrderService orderService;

    public void generateInvoice() {
        Document document = new Document();
        document.close();
    }

}
