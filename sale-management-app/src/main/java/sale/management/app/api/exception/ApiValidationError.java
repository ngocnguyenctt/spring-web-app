package sale.management.app.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ngoc on 30/04/2018
 * @subject sale-management-app
 */

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class ApiValidationError
{
    private String object;
    private String field;
    private Object rejectValue;
    private String message;

    public ApiValidationError(String object, String message)
    {
        this.object = object;
        this.message = message;
    }
}
