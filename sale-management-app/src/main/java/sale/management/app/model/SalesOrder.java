package sale.management.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import sale.management.app.validator.ValidId;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Entity
@Table(name = "sales_orders")
@Getter
@Setter
public class SalesOrder
{
    @Id
    @ValidId
    private String orderNo;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate orderDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate overdueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_person_id")
    private SalesPerson salesPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @PositiveOrZero
    private BigDecimal orderDisc;

    @NotNull
    @PositiveOrZero
    private BigDecimal taxAmt;

    @NotNull
    @PositiveOrZero
    private BigDecimal totalAmt;

    @PositiveOrZero
    private BigDecimal payment;

    private String description;

    @Transient
    private List<@Valid SalesOrderDetail> items;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesOrder that = (SalesOrder) o;
        return Objects.equals(orderNo, that.orderNo);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(orderNo);
    }
}
