package sale.management.app.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import sale.management.app.validator.ValidId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Entity
@Table(name = "items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item
{

    @Id
    @ValidId
    @GeneratedValue(generator = "item-generator")
    @GenericGenerator(name = "item-generator",
            parameters = @Parameter(name = "prefix", value = "I0000"),
            strategy = "sale.management.app.util.IdGenerator")
    private String itemId;

    @NotBlank
    private String itemName;

    @NotNull
    @PositiveOrZero
    private Integer qty;

    @NotNull
    @PositiveOrZero
    private Integer qtyStock;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotBlank
    private String unit;

    @NotNull
    @PositiveOrZero
    private BigDecimal itemDisc;

    @NotNull
    @PositiveOrZero
    private BigDecimal taxAmt;

    private BigDecimal amount;

//    @PrePersist
//    private void prePersist()
//    {
//        BigDecimal salesPrice = (price.multiply(BigDecimal.valueOf(qty), MathContext.DECIMAL64))
//                .subtract(itemDisc, MathContext.DECIMAL64);
//        amount = (salesPrice.multiply(taxAmt, MathContext.DECIMAL64)).add(salesPrice, MathContext.DECIMAL64);
//    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemId, item.itemId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(itemId);
    }
}
