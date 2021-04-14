package geek.service;

import geek.controller.repr.ProductRepr;
import geek.persist.model.Picture;
import geek.persist.model.Product;
import geek.persist.repo.ProductRepository;
import geek.persist.repo.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<ProductRepr> findById(Long id){
        return productRepository.findById(id)
                .map(ProductService::mapToRepr);
    }

    public List<ProductRepr> findByFilter(Long categoryId){
        Specification<Product> spec = ProductSpecification.fetchPictures();
        if (categoryId != null){
            spec = spec.and(ProductSpecification.byCategory(categoryId));
        }
        return productRepository.findAll(spec).stream()
                .map(ProductService::mapToRepr)
                .collect(Collectors.toList());
    }


    private static ProductRepr mapToRepr(Product product) {
        return new ProductRepr(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                product.getPictures().size() > 0 ? product.getPictures().get(0).getId() : null,
                product.getPictures().stream().map(Picture::getId).collect(Collectors.toList())
        );
    }
}
