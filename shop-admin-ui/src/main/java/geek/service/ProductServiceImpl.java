package geek.service;

import geek.aspect.TrackTime;
import geek.controller.repr.ProductRepr;
import geek.error.NotFoundException;
import geek.persist.model.Picture;
import geek.persist.model.Product;
import geek.persist.repo.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, PictureService pictureService) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
    }

    @TrackTime
    @Override
    @Transactional
    public List<ProductRepr> findAll() {
        return productRepository.findAllWithPictureFetch().stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<ProductRepr> findById(Long id) {
        return productRepository.findById(id).map(ProductRepr::new);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(ProductRepr productRepr) throws IOException {
        Product product = (productRepr.getId() != null) ? productRepository.findById(productRepr.getId())
                .orElseThrow(NotFoundException::new) : new Product();
        product.setTitle(productRepr.getTitle());
        product.setCategory(productRepr.getCategory());
        product.setBrand(productRepr.getBrand());
        product.setPrice(productRepr.getPrice());
        product.setDescription(productRepr.getDescription());

        if (productRepr.getNewPictures() != null) {
            for (MultipartFile newPicture : productRepr.getNewPictures()) {
                logger.info("Product {} file {} size {} contentType {}", productRepr.getId(),
                        newPicture.getOriginalFilename(), newPicture.getSize(), newPicture.getContentType());

                if (product.getPictures() == null) {
                    product.setPictures(new ArrayList<>());
                }

                product.getPictures().add(new Picture(
                        newPicture.getOriginalFilename(),
                        newPicture.getContentType(),
                        pictureService.createPictureData(newPicture.getBytes()),
                        product
                ));
            }
        }
        productRepository.save(product);
    }
}