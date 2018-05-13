package sale.management.app.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import sale.management.app.validator.ValidId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Entity
@Table(name = "sales_people")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesPerson
{
    @Id
    @ValidId
    @GeneratedValue(generator = "sales-person-generator")
    @GenericGenerator(name = "sales-person-generator",
            parameters = @Parameter(name = "prefix", value = "S0000"),
            strategy = "sale.management.app.util.IdGenerator")
    private String salesPersonId;

    @Length(max = 10, message = "{Length.salesPerson.firstName}")
    private String firstName;

    @Length(max = 50, message = "{Length.salesPerson.lastName}")
    private String lastName;

    private String address;

    @Email(message = "{Email.salesPerson.email}")
    private String email;

    @Pattern(regexp = "^$|\\d{4}-\\d{7}|\\d{3}-\\d{7}", message = "{Pattern.salesPerson.phone}")
    private String phone;

    private String imageUrl;

    private String status;

    @Range(min = 0, max = 100000, message = "{Range.salesPerson.itemLimit}")
    private BigDecimal itemLimit;

    private String description;

    @PrePersist
    public void prePersist()
    {
        if (itemLimit == null || "".equals(itemLimit.toString())) {
            itemLimit = BigDecimal.valueOf(100000.00);
            if (salesPersonId != null) {
                salesPersonId = salesPersonId.toUpperCase();
            }
        }
    }
}
