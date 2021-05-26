package geek.controller.repr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ProductRepr implements Serializable {

    private Long id;

    private String title;

    private BigDecimal price;

    private String description;

    private Long pictureId;

    private List<Long> pictureIds;

    public ProductRepr() {
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public List<Long> getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(List<Long> pictureIds) {
        this.pictureIds = pictureIds;
    }
}