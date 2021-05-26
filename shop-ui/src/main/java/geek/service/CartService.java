package geek.service;

import geek.controller.repr.ProductRepr;
import geek.service.model.LineItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CartService {

    void addProductQty(ProductRepr productRepr, String color, String material, int qty);

    void removeProductQty(ProductRepr productRepr, String color, String material, int qty);

    List<LineItem> getLineItems();

    void updateAllQty(Map<Long, Integer> paramMap);

    void removeProduct(ProductRepr productRepr, String color, String material);

    BigDecimal getSubTotal();

}
