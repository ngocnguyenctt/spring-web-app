package sale.management.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderDetailId implements Serializable
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Item item;
}
