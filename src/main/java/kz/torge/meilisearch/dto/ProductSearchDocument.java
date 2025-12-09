package kz.torge.meilisearch.dto;

import kz.torge.meilisearch.entity.ProductEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductSearchDocument {

    private String id;

    private String name;
    private String description;
    private BigDecimal price;
    private boolean active;

    public ProductSearchDocument(ProductEntity entity) {
        this.id = entity.getId().toString();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.active = entity.isActive();
    }
}
