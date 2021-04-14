package geek.persist.repo;

import geek.persist.model.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

public final class ProductSpecification {

    public static Specification<Product> byId(long id){
        return ((root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("id"), id));
    }

    public static Specification<Product> byCategory(long categoryId){
        return ((root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("category").get("id"), categoryId));
    }

    public static Specification<Product> fetchPictures(){
        return (root, criteriaQuery, criteriaBuilder) -> {
            root.fetch("pictures", JoinType.LEFT);
            criteriaQuery.distinct(true);
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }
}
