package geek.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import geek.controller.repr.ProductRepr;
import geek.service.model.LineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private Map<LineItem, Integer> lineItems = new ConcurrentHashMap<>();

    @Override
    public void addProductQty(ProductRepr productRepr, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productRepr, color, material);
        lineItems.put(lineItem, lineItems.getOrDefault(lineItem, 0) + qty);
    }

    @Override
    public void removeProductQty(ProductRepr productRepr, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productRepr, color, material);
        int currentQty = lineItems.getOrDefault(lineItem, 0);
        if (currentQty - qty > 0) {
            lineItems.put(lineItem, currentQty - qty);
        } else {
            lineItems.remove(lineItem);
        }
    }

    @Override
    public List<LineItem> getLineItems() {
        lineItems.forEach(LineItem::setQty);
        return new ArrayList<>(lineItems.keySet());
    }

    @Override
    public void updateAllQty(Map<Long, Integer> paramMap) {
        lineItems = lineItems.entrySet()
                .stream()
                .filter(e -> {
                    Long productId = e.getKey().getProductId();
                    Integer qty = paramMap.getOrDefault(productId.longValue(), 0);
                    return qty > 0;
                })
                .collect(Collectors.toMap(e -> {
                    Integer qty = paramMap.get(e.getKey().getProductId());
                    e.getKey().setQty(qty);
                    return e.getKey();
                }, e -> paramMap.get(e.getKey().getProductId())));
    }

    @Override
    public void removeProduct(Long productId) {
        logger.info("{}", productId);
        lineItems = lineItems
                .entrySet()
                .stream()
                .filter(e -> !e.getKey().getProductId().equals(productId))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    @JsonIgnore
    @Override
    public BigDecimal getSubTotal() {
        return null;
    }

}
