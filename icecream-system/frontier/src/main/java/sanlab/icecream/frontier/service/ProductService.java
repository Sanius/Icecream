package sanlab.icecream.frontier.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sanlab.icecream.frontier.dto.core.FeedbackDto;
import sanlab.icecream.frontier.dto.core.ImageDto;
import sanlab.icecream.frontier.dto.extended.ProductExtendedDto;
import sanlab.icecream.fundamentum.exception.ItemNotFoundException;
import sanlab.icecream.fundamentum.exception.StoringDatabaseException;
import sanlab.icecream.frontier.mapper.IFeedbackMapper;
import sanlab.icecream.frontier.mapper.IImageMapper;
import sanlab.icecream.frontier.mapper.IProductMapper;
import sanlab.icecream.frontier.model.Category;
import sanlab.icecream.frontier.model.Image;
import sanlab.icecream.frontier.model.Product;
import sanlab.icecream.frontier.model.Stock;
import sanlab.icecream.frontier.repository.crud.ICategoryRepository;
import sanlab.icecream.frontier.repository.crud.IProductRepository;
import sanlab.icecream.frontier.dto.core.ProductDto;
import sanlab.icecream.frontier.repository.crud.IStockRepository;
import sanlab.icecream.frontier.viewmodel.response.CollectionQueryResponse;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static sanlab.icecream.fundamentum.utils.ObjectUtils.copyNotNull;
import static sanlab.icecream.fundamentum.utils.RequestUtils.calculateTotalPage;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ImageService imageService;

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final IStockRepository stockRepository;

    private final IFeedbackMapper feedbackMapper;
    private final IProductMapper productMapper;
    private final IImageMapper imageMapper;

    @Transactional(readOnly = true)
    public CollectionQueryResponse<ProductExtendedDto> getAll(Pageable pageable, boolean featuredOnly) {
        Page<Product> paginatedProducts = featuredOnly ?
            productRepository.findAllByIsFeaturedTrue(pageable) :
            productRepository.findAll(pageable);
        long total = featuredOnly ? productRepository.countByIsFeaturedTrue() : productRepository.count();
        List<ProductExtendedDto> productList = productMapper.entityToExtendedDto(paginatedProducts.stream().toList());
        return CollectionQueryResponse.<ProductExtendedDto>builder()
            .total(total)
            .page(pageable.getPageNumber())
            .totalPages(calculateTotalPage(total, pageable.getPageSize()))
            .data(productList)
            .build();
    }

    @Transactional(readOnly = true)
    public ProductExtendedDto getById(UUID id) {
        return productRepository.findById(id)
            .map(productMapper::entityToExtendedDto)
            .orElseThrow(() -> ItemNotFoundException.product(id));
    }

    @Transactional(readOnly = true)
    public CollectionQueryResponse<FeedbackDto> getAllFeedbacks(UUID productId, Pageable pageable) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> ItemNotFoundException.product(productId));
        long total = product.getFeedbacks().size();
        List<FeedbackDto> feedbackList = product.getFeedbacks().parallelStream()
            .map(feedbackMapper::entityToDto)
            .toList();
        return CollectionQueryResponse.<FeedbackDto>builder()
            .total(total)
            .page(pageable.getPageNumber())
            .totalPages(calculateTotalPage(total, pageable.getPageSize()))
            .data(feedbackList)
            .build();
    }

    @Transactional
    public ProductExtendedDto create(ProductDto request) {
        try {
            Product product = productRepository.save(productMapper.dtoToEntity(request));
            return productMapper.entityToExtendedDto(product);
        } catch (Exception ignored) {
            throw new StoringDatabaseException("Error occurs when creating product");
        }
    }

    @Transactional
    public ProductExtendedDto update(UUID id, ProductDto request) {
        Product targetProduct = productRepository.findById(id)
            .orElseThrow(() -> ItemNotFoundException.product(id));
        Product sourceProduct = productMapper.dtoToEntity(request);
        copyNotNull(sourceProduct, targetProduct);
        try {
            return productMapper.entityToExtendedDto(productRepository.save(targetProduct));
        } catch (Exception ignored) {
            throw new StoringDatabaseException("Error occurs when updating product");
        }
    }

    @Transactional
    public ProductExtendedDto setCategories(UUID productId, List<UUID> categoryIdList) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() ->ItemNotFoundException.product(productId));
        Set<Category> categories = categoryRepository.findByIdIn(categoryIdList).stream()
            .filter(category -> Optional.ofNullable(category).isPresent())
            .collect(Collectors.toSet());
        product.setCategories(categories);
        return productMapper.entityToExtendedDto(productRepository.save(product));
    }

    @Transactional
    public ProductExtendedDto setMedia(UUID productId, MultipartFile avatar, MultipartFile[] otherMedia) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> ItemNotFoundException.product(productId));
        Deque<Image> savedDQ = new ArrayDeque<>();
        Optional.ofNullable(avatar).ifPresent(img -> {
            if (!img.isEmpty()) {
                var imageDto = imageService.upsertProductAvatar(productId, avatar);
                savedDQ.add(imageMapper.dtoToEntity(imageDto));
            }
        });
        if (otherMedia != null && otherMedia.length > 0) {
            List<ImageDto> imgList = imageService.bulkUpsertProductMedia(productId, otherMedia);
            savedDQ.addAll(imageMapper.dtoToEntity(imgList));
        }
        product.setMedia(new HashSet<>(savedDQ));
        return productMapper.entityToExtendedDto(productRepository.save(product));
    }

    @Transactional
    public ProductExtendedDto setStocks(UUID productId, List<UUID> stockIdList) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> ItemNotFoundException.product(productId));
        List<Stock> stocks = stockRepository.findByIdIn(stockIdList).stream()
            .filter(stock -> Optional.ofNullable(stock).isPresent())
            .toList();
        product.setStocks(stocks);
        return productMapper.entityToExtendedDto(productRepository.save(product));
    }

    // TODO
    @Transactional
    public void delete(UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.ifPresent(product -> {
            product.setCategories(null);
            product.setMedia(null);
            productRepository.deleteById(id);
        });
    }

}
