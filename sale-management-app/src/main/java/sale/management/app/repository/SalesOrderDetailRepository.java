package sale.management.app.repository;

import org.springframework.data.repository.CrudRepository;
import sale.management.app.model.SalesOrderDetail;
import sale.management.app.model.SalesOrderDetailId;

/**
 * @author ngoc on 21/04/2018
 * @subject sale-management-app
 */

public interface SalesOrderDetailRepository extends CrudRepository<SalesOrderDetail, SalesOrderDetailId>
{
}
