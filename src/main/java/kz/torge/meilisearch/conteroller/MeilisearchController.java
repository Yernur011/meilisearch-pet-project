package kz.torge.meilisearch.conteroller;

import com.meilisearch.sdk.model.Searchable;
import kz.torge.meilisearch.dto.ProductDto;
import kz.torge.meilisearch.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/meilisearch", version = "1.0")
public class MeilisearchController {
    private final ProductService service;

    @PostMapping
    public @NonNull ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) throws Exception {
        return ResponseEntity.ok(service.create(dto));
    }

    // READ one
    @GetMapping("/{id}")
    public @NonNull ResponseEntity<ProductDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // READ all
    @GetMapping
    public @NonNull ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // UPDATE
    @PutMapping("/{id}")
    public @NonNull ResponseEntity<ProductDto> update(@PathVariable Long id,
                                             @RequestBody ProductDto dto) throws Exception {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public @NonNull ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH (Meilisearch)
    @GetMapping("/search")
    public @NonNull ResponseEntity<Searchable> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "true") boolean onlyActive
    ) throws Exception {
        return ResponseEntity.ok(service.search(q, page, size, onlyActive));
    }
}

