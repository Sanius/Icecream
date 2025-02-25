package sanlab.icecream.frontier.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sanlab.icecream.frontier.dto.core.ProductDto;
import sanlab.icecream.frontier.dto.extended.CategoryExtendedDto;
import sanlab.icecream.frontier.service.CategoryService;
import sanlab.icecream.frontier.viewmodel.request.CollectionQueryRequest;
import sanlab.icecream.frontier.viewmodel.response.CollectionQueryResponse;

import java.util.List;
import java.util.UUID;

import static sanlab.icecream.fundamentum.constant.PreAuthorizedAuthExp.PERMIT_ALL;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize(PERMIT_ALL)
    public List<CategoryExtendedDto> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize(PERMIT_ALL)
    public ResponseEntity<CategoryExtendedDto> getById(@PathVariable UUID id) {
        var result = categoryService.getById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/products")
    @PreAuthorize(PERMIT_ALL)
    public CollectionQueryResponse<ProductDto> getAllProducts(@PathVariable UUID id,
                                                              @ModelAttribute CollectionQueryRequest request) {
        return categoryService.getAllProducts(id, request.getPageRequest());
    }

}
