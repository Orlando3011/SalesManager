package pl.psk.salesManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.psk.salesManager.model.Owner;
import pl.psk.salesManager.service.OwnerService;

@Controller
public class OwnerController {
    private static final Logger LOGGER = LoggerFactory.getLogger("application");
    @Autowired
    OwnerService ownerService;

    @GetMapping("/owner")
    public String displayOwnerData(Model model) {
        model.addAttribute(ownerService.displayOwnerData());
        return "owner/ownerDetails";
    }

    @GetMapping("/editOwner")
    public String showEditOwnerPage(Model model) {
        model.addAttribute(ownerService.displayOwnerData());
        return "owner/editOwner";
    }

    @PostMapping("/editOwner")
    public String editOwnerData(Model model, @ModelAttribute("owner") Owner owner) {
        ownerService.editOwnerData(owner);
        LOGGER.info("Owner data modified");
        return "redirect:/owner";
    }



}
