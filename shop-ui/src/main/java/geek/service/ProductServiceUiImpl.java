package geek.service;

import geek.controller.reprUi.ProductReprUi;
import geek.persist.repo.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceUiImpl implements  ProductServiceUi, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceUiImpl.class);

    private final ProductRepository productRepository;

    private final PictureService pictureService;

    @Autowired
    public ProductServiceUiImpl(ProductRepository productRepository, PictureService pictureService) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
    }

    @Override
    @Transactional
    public List<ProductReprUi> findAll() {
        return productRepository.findAllWithPictureFetch().stream()
                .map(ProductReprUi::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<ProductReprUi> findById(Long id) {

        return productRepository.findById(id).map(ProductReprUi::new);
    }
}
