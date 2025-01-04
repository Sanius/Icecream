package sanlab.icecream.frontier.dto.extended;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import sanlab.icecream.frontier.dto.core.CategoryDto;
import sanlab.icecream.frontier.dto.core.ImageDto;
import sanlab.icecream.frontier.dto.core.ProductDto;
import sanlab.icecream.frontier.dto.core.StockDto;

import java.util.List;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductExtendedDto extends ProductDto {
    private List<CategoryDto> categories;
    private List<ImageDto> media;
    private List<StockDto> stocks;
    private ImageDto featuredBanner;
}
