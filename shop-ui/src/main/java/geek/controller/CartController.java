package geek.controller;

import geek.controller.repr.CartItemRepr;
import geek.controller.repr.ProductRepr;
import geek.error.NotFoundException;
import geek.service.CartService;
import geek.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    public final CartService cartService;

    public final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("lineItems", cartService.getLineItems());
        return "shopping-cart";
    }

    @PostMapping
    public String addToCart(CartItemRepr cartItemRepr) {
        ProductRepr productRepr = productService.findById(cartItemRepr.getProductId())
                .orElseThrow(NotFoundException::new);
        cartService.addProductQty(productRepr, "", "", cartItemRepr.getQty());
        return "redirect:/cart";
    }

    @DeleteMapping
    public String delete(@RequestParam("productId") Long productId) {
        cartService.removeProduct(new ProductRepr(productId, null, null, null, null, null), "", "");
        return "redirect:/cart";
    }

    @PostMapping(path = "/update_all_qty")
    public String updateAllQty(@RequestParam Map<String, String> paramMapStr){
        Map<Long, Integer> paramMap = paramMapStr
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> Long.valueOf(e.getKey()), e -> Integer.valueOf(e.getValue())));
        logger.info("Product Qty Map: {}", paramMap);
        cartService.updateAllQty(paramMap);
        return "redirect:/cart";
    }
}
