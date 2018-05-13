package sale.management.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sale.management.app.model.Customer;

/**
 * @author ngoc on 21/04/2018
 * @subject sale-management-app
 */

@Repository
public interface CustomerRepository extends CommonRepository<Customer>
{
    /**
     * update status for customer.
     *
     * @param status     String
     * @param customerId String
     */
    @Modifying
    @Query("update Customer c set c.status = ?1 where c.customerId = ?2")
    void updateStatus(String status, String customerId);

    /**
     * get all customers.
     *
     * @param status   String
     * @param pageable Pageable
     * @return Page<Customer>
     */
    @Query("select c from Customer c where c.status <> ?1")
    Page<Customer> findByStatus(String status, Pageable pageable);
}
