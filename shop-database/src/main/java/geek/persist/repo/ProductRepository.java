package geek.persist.repo;

import geek.persist.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select distinct p " +
            "from Product p " +
            "left join fetch p.pictures " +
            "inner join fetch p.category " +
            "inner join fetch p.brand")
    List<Product> findAllWithPictureFetch();


    List<Product> findAll(Specification<Product> spec);
}
