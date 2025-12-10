package kz.torge.meilisearch.service;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.Searchable;
import com.meilisearch.sdk.model.TaskInfo;
import jakarta.annotation.PostConstruct;
import kz.torge.meilisearch.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private static final String INDEX_NAME = "products";
    private final ObjectMapper objectMapper;
    private final Client client;
    private Index index;

    @PostConstruct
    public void init() throws Exception {
        try {
            index = client.index(INDEX_NAME);
            index.getStats();
        } catch (Exception e) {
            log.error("Error while initializing index", e);
            TaskInfo createIndexTask = null;
            try {
                createIndexTask = client.createIndex(INDEX_NAME, "id");
            } catch (MeilisearchException ex) {
                throw new RuntimeException(ex);
            }
            client.waitForTask(createIndexTask.getTaskUid());
        }

        index = client.index(INDEX_NAME);

        index.updateSearchableAttributesSettings(new String[]{"name", "description"});

        index.updateFilterableAttributesSettings(new String[]{"active"});
    }

    public void indexProduct(ProductEntity docs) {
        if (docs == null) {
            return;
        }
        String json = objectMapper.writeValueAsString(docs);
        var task = index.addDocuments(json);
        client.waitForTask(task.getTaskUid());
    }

    public void deleteProduct(Long id) throws Exception {
        TaskInfo task = index.deleteDocument(id.toString());
        client.waitForTask(task.getTaskUid());
    }

    public Searchable searchByName(String query, int page, int size, Boolean onlyActive) throws Exception {
        int offset = (page - 1) * size;

        SearchRequest request = new SearchRequest(query)
                .setLimit(size)
                .setOffset(offset);

        if (onlyActive != null && onlyActive) {
            request.setFilter(new String[]{"active = true"});
        }

        return index.search(request);
    }
}

