package sale.management.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Entity
@Table(name = "sales_order_details")
@Getter
@Setter
public class SalesOrderDetail
{
    @Valid
    @EmbeddedId
    private SalesOrderDetailId salesOrderDetailId;

    @NotNull
    @PositiveOrZero
    private Integer qty;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    private BigDecimal itemDisc;

    @NotNull
    @PositiveOrZero
    private BigDecimal taxAmt;

    @NotNull
    @PositiveOrZero
    private BigDecimal amount;
}
