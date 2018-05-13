package sale.management.app.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import sale.management.app.validator.ValidId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


/**
 * @author ngoc on 04/05/2018
 * @subject sale-management-app
 */

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role
{
    @Id
    @ValidId
    @GeneratedValue(generator = "role-generator")
    @GenericGenerator(name = "role-generator",
            parameters = @Parameter(name = "prefix", value = "R0000"),
            strategy = "sale.management.app.util.IdGenerator")
    private String roleId;

    @NotBlank
    private String roleName;
}
