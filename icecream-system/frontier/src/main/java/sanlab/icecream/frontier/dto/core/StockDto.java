package sanlab.icecream.frontier.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {
    protected UUID id;
    protected Long quantity;
    protected Long reservedQuantity;
}