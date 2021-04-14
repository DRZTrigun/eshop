package geek.service;

import geek.controller.repr.ProductRepr;
import geek.service.model.LineItem;

import java.util.List;

public interface CartService {

    void addProductQty(ProductRepr productRepr, String color, String material, int qty);

    void removeProductQty(ProductRepr productRepr, String color, String material, int qty);

    List<LineItem> getLineItems();

    List<LineItem> removeLineItem();
}
