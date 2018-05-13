package sale.management.app.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import sale.management.app.util.IdGenerator;
import sale.management.app.validator.ValidId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Entity
@Table(name = "customers")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer
{
    @Id
    @ValidId
    @GeneratedValue(generator = "customer-generator")
    @GenericGenerator(name = "customer-generator",
            parameters = @Parameter(name = "prefix", value = "C0000"),
            strategy = "sale.management.app.util.IdGenerator")
    private String customerId;

    @NotBlank(message = "{NotBlank.firstName}")
    @Length(max = 10, message = "{Length.customer.firstName}")
    private String firstName;

    @NotBlank(message = "{NotBlank.lastName}")
    @Length(max = 50, message = "{Length.customer.lastName}")
    private String lastName;

    @NotBlank(message = "{NotBlank.customer.address}")
    private String address;

    @Pattern(regexp = "\\d{4}-\\d{7}|\\d{3}-\\d{7}", message = "{Pattern.customer.phone}")
    private String phone;

    @Pattern(regexp = "\\d{7}", message = "{Pattern.customer.fax}")
    private String fax;

    @NotBlank(message = "{NotBlank.email}")
    @Email(message = "{Email.customer.email}")
    private String email;

    private String status;

    private String description;
}
