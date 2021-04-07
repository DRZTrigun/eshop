package geek.service;

import geek.controller.reprUi.ProductReprUi;

import java.util.List;
import java.util.Optional;

public interface ProductServiceUi {

    List<ProductReprUi> findAll();

    Optional<ProductReprUi> findById(Long id);
}
