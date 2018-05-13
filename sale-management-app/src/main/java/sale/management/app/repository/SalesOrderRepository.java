package sale.management.app.repository;

import org.springframework.stereotype.Repository;
import sale.management.app.model.SalesOrder;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Repository
public interface SalesOrderRepository extends CommonRepository<SalesOrder>
{
    /**
     * get all sales-orders by date
     *
     * @param date LocalDate
     * @return SalesOrder List
     */
    List<SalesOrder> findAllByOrderDate(LocalDate date);
}
