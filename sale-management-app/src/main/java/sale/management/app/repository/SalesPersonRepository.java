package sale.management.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sale.management.app.model.SalesPerson;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Repository
public interface SalesPersonRepository extends CommonRepository<SalesPerson>
{
    /**
     * update status for salesperson.
     *
     * @param status String
     * @param salesPersonId String
     */
    @Modifying
    @Query("update SalesPerson s set s.status = ?1 where s.salesPersonId = ?2")
    void updateStatus(String status, String salesPersonId);

    /**
     * get all sales-people.
     *
     * @param status String
     * @return Page<SalesPerson>
     */
    @Query("select s from SalesPerson s where s.status <> ?1")
    Page<SalesPerson> findByStatus(String status, Pageable pageable);
}
