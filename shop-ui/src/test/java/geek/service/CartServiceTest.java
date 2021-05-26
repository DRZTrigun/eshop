package geek.service;

import geek.controller.repr.ProductRepr;
import geek.service.model.LineItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void testEmptyCart() {
        assertNotNull(cartService.getLineItems());
        assertEquals(0, cartService.getLineItems().size());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testAddProduct() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setTitle("Product name");
        expectedProduct.setDescription("desc");

        cartService.addProductQty(expectedProduct, "color", "material", 2);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals("color", lineItem.getColor());
        assertEquals("material", lineItem.getMaterial());
        assertEquals(2, lineItem.getQty());

        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductRepr());
        assertEquals(expectedProduct.getTitle(), lineItem.getProductRepr().getTitle());
    }
}
