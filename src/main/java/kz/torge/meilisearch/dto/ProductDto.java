package kz.torge.meilisearch.dto;

import kz.torge.meilisearch.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean active;

    public static ProductDto fromEntity(ProductEntity e) {
        return new ProductDto(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getPrice(),
                e.isActive()
        );
    }
}
