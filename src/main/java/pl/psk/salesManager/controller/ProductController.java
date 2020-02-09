package pl.psk.salesManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.psk.salesManager.model.Client;
import pl.psk.salesManager.model.Product;
import pl.psk.salesManager.service.ProductService;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String ShowProductsPage(Model model) {
        model.addAttribute("productsList", productService.findAllProducts());
        return "product/productsList";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(Model model, @PathVariable(name = "id") int id){
        productService.removeProduct(id);
        model.addAttribute("clientsList", productService.findAllProducts());
        return "redirect:/products";
    }

    @GetMapping("/addProduct")
    public String ShowAddProductPage(Model model) {
        model.addAttribute(new Product());
        return "product/addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product, Model model) {
        productService.addProductToRepository(product, 1);
        model.addAttribute("productsList", productService.findAllProducts());
        return "redirect:/products";
    }

    @GetMapping("/editProduct/{id}")
    public String ShowEditProductPage(Model model, @PathVariable(name = "id") int id) {
        model.addAttribute(productService.findProductById(id));
        return "product/editProduct";
    }

    @PostMapping("/editProduct/{id}")
    public String editProduct(Model model, @ModelAttribute Product product) {
        productService.editProduct(product);
        model.addAttribute("productsList", productService.findAllProducts());
        return "redirect:/products";
    }
}
