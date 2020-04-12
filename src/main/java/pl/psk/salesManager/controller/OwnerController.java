package pl.psk.salesManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.psk.salesManager.model.Owner;
import pl.psk.salesManager.repository.OwnerRepository;
import pl.psk.salesManager.service.OwnerService;

import javax.jws.WebParam;
import java.util.List;

@Controller
public class OwnerController {
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
        return "redirect:/owner";
    }



}
