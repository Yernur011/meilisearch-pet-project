package kz.torge.meilisearch.repo;

import kz.torge.meilisearch.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {
}
