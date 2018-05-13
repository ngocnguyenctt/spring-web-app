package sale.management.app.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;
import sale.management.app.validator.ValidId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author ngoc on 04/05/2018
 * @subject sale-management-app
 */

@Entity
@Table(name = "users")
@Getter
@Setter
public class User
{
    @Id
    @ValidId
    @GeneratedValue(generator = "user-generator")
    @GenericGenerator(name = "user-generator",
            parameters = @Parameter(name = "prefix", value = "U0000"),
            strategy = "sale.management.app.util.IdGenerator")
    private String userId;

    @ValidId
    private String username;

    @Length(max = 72)
    @NotBlank
    private String oldPassword;

    @Length(max = 72)
    private String newPassword;

    @Length(max = 72)
    private String confirmPassword;

    private Boolean active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @PrePersist
    private void prePersist()
    {
        if (active == null || "".equals(active)) {
            active = true;
        }
    }
}
