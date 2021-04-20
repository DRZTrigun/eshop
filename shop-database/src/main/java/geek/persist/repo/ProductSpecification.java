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

    public static Specification<Product> fetchPictures() {
        return (root, query, builder) -> {
            // Don't do fetch in case of count() query for pagination
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("pictures", JoinType.LEFT);
                root.fetch("brand", JoinType.LEFT);
                query.distinct(true);
            }
            return builder.isTrue(builder.literal(true));
        };
    }
}
