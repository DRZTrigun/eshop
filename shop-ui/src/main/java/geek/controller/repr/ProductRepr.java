package geek.controller.repr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ProductRepr implements Serializable {

    private final Long id;

    private final String title;

    private final BigDecimal price;

    private final String description;

    private final Long pictureId;

    private final List<Long> pictureIds;


    public ProductRepr(Long id, String title, BigDecimal price, String description,
                       Long pictureId, List<Long> pictureIds) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.pictureId = pictureId;
        this.pictureIds = pictureIds;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public List<Long> getPictureIds() {
        return pictureIds;
    }
}