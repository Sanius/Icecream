package sanlab.icecream.frontier.controller.watcher;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sanlab.icecream.frontier.dto.core.FeedbackDto;
import sanlab.icecream.frontier.dto.core.ProductDto;
import sanlab.icecream.frontier.dto.extended.ProductExtendedDto;
import sanlab.icecream.frontier.service.ProductService;
import sanlab.icecream.frontier.viewmodel.request.CollectionQueryRequest;
import sanlab.icecream.frontier.viewmodel.response.CollectionQueryResponse;

import java.util.List;
import java.util.UUID;

import static sanlab.icecream.fundamentum.constant.PreAuthorizedAuthExp.WATCHER;

@RestController
@RequestMapping("/watcher/product")
@PreAuthorize(WATCHER)
@RequiredArgsConstructor
public class WatchProductController {

    private final ProductService productService;

    @GetMapping
    public CollectionQueryResponse<ProductExtendedDto> getAll(@ModelAttribute CollectionQueryRequest request) {
        return productService.getAll(request.getPageRequest(), false);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductExtendedDto> getById(@PathVariable UUID id) {
        var result = productService.getById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/feedbacks")
    public CollectionQueryResponse<FeedbackDto> getAllFeedbacks(@PathVariable UUID id,
                                                                @ModelAttribute CollectionQueryRequest request) {
        return productService.getAllFeedbacks(id, request.getPageRequest());
    }

    @PostMapping
    public ResponseEntity<ProductExtendedDto> create(@Valid @RequestBody ProductDto requestBody) {
        var result = productService.create(requestBody);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductExtendedDto> update(@PathVariable UUID id,
                                                     @Valid @RequestBody ProductDto requestBody) {
        var result = productService.update(id, requestBody);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/categories")
    public ResponseEntity<ProductExtendedDto> updateCategories(@PathVariable UUID id, @RequestBody List<UUID> requestBody) {
        var result = productService.setCategories(id, requestBody);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/media")
    public ResponseEntity<ProductExtendedDto> updateMedia(@PathVariable UUID id,
                                                          @RequestPart("avatar") MultipartFile avatar,
                                                          @RequestPart("media") MultipartFile[] media) {
        var result = productService.setMedia(id, avatar, media);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/stocks")
    public ResponseEntity<ProductExtendedDto> updateStocks(@PathVariable UUID id, @RequestBody List<UUID> requestBody) {
        var result = productService.setStocks(id, requestBody);
        return ResponseEntity.ok(result);
    }

}
