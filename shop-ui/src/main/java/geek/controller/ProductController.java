package geek.controller;

import geek.error.NotFoundException;
import geek.persist.repo.CategoryRepository;
import geek.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final CategoryRepository categoryRepository;

    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String productListPage(@RequestParam(value = "categoryId", required = false) Long categoryId,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", required = false, defaultValue = "6") Integer size,
                                  Model model) {
        logger.info("Product list page");

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("products", productService.findByFilter(categoryId, page, size));

        return "categories-sidebar";
    }

    @GetMapping("/product/{id}")
    public String productPage(@PathVariable("id") Long id, Model model) {
        logger.info("Product page {}", id);

        model.addAttribute("product", productService.findById(id).orElseThrow(NotFoundException::new));

        return "product-details";
    }

}
