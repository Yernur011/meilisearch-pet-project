package kz.torge.meilisearch.service;

import com.meilisearch.sdk.model.Searchable;
import kz.torge.meilisearch.dto.ProductDto;
import kz.torge.meilisearch.entity.ProductEntity;
import kz.torge.meilisearch.repo.ProductEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductEntityRepository productRepository;
    private final ProductSearchService productSearchService;

    @Transactional
    public ProductDto create(ProductDto dto) throws Exception {
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setActive(dto.isActive());

        ProductEntity saved = productRepository.save(entity);

        productSearchService.indexProduct(saved);

        return ProductDto.fromEntity(saved);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto dto) throws Exception {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setActive(dto.isActive());

        ProductEntity saved = productRepository.save(entity);

        productSearchService.indexProduct(saved);

        return ProductDto.fromEntity(saved);
    }

    @Transactional
    public void delete(Long id) throws Exception {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            productSearchService.deleteProduct(id);
        }
    }

    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        return ProductDto.fromEntity(entity);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Searchable search(String q, int page, int size, boolean onlyActive) throws Exception {
        return productSearchService.searchByName(q, page, size, onlyActive);
    }
}
