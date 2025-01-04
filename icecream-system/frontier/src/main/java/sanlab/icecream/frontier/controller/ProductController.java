package sanlab.icecream.frontier.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sanlab.icecream.frontier.dto.core.FeedbackDto;
import sanlab.icecream.frontier.dto.extended.ProductExtendedDto;
import sanlab.icecream.frontier.service.ProductService;
import sanlab.icecream.frontier.viewmodel.request.CollectionQueryRequest;
import sanlab.icecream.frontier.viewmodel.response.CollectionQueryResponse;

import java.util.UUID;

import static sanlab.icecream.fundamentum.constant.PreAuthorizedAuthExp.PERMIT_ALL;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @PreAuthorize(PERMIT_ALL)
    public CollectionQueryResponse<ProductExtendedDto> getAll(@ModelAttribute CollectionQueryRequest request) {
        return productService.getAll(request.getPageRequest(), false);
    }

    @GetMapping("/featured")
    @PreAuthorize(PERMIT_ALL)
    public CollectionQueryResponse<ProductExtendedDto> getAllFeatured(@ModelAttribute CollectionQueryRequest request) {
        return productService.getAll(request.getPageRequest(), true);
    }

    @GetMapping("/{id}")
    @PreAuthorize(PERMIT_ALL)
    public ResponseEntity<ProductExtendedDto> getById(@PathVariable UUID id) {
        var result = productService.getById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/feedbacks")
    @PreAuthorize(PERMIT_ALL)
    public CollectionQueryResponse<FeedbackDto> getAllFeedbacks(@PathVariable UUID id,
                                                                @ModelAttribute CollectionQueryRequest request) {
        return productService.getAllFeedbacks(id, request.getPageRequest());
    }

}
