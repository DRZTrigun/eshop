package geek.controller;

import geek.service.ProductServiceUi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductServiceUi productServiceUi;

    @Autowired
    public ProductController(ProductServiceUi productServiceUi) {
        this.productServiceUi = productServiceUi;
    }

    @RequestMapping("/categories-sidebar")
    public String productsListPage(Model model){
        logger.info("Products list page");
        model.addAttribute("activePage", "Categories-sidebar");
        model.addAttribute("categories-sidebar", productServiceUi.findAll());
        return "categories-sidebar";
    }

    @RequestMapping("/categories-sidebar/product-details")
    public String productPage(Model model, @PathVariable("id") Long id){
        logger.info("Product page");
        model.addAttribute("activePage", "Categories-sidebar");
        model.addAttribute("categories-sidebar/product-details", productServiceUi.findById(id));
        return "categories-sidebar/product-details";
    }

}
