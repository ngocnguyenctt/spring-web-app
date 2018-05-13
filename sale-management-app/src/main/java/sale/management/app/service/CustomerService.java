package sale.management.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sale.management.app.model.Customer;
import sale.management.app.repository.CustomerRepository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ngoc on 21/04/2018
 * @subject sale-management-app
 */

@Service
public class CustomerService extends CommonService<Customer>
{

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    /**
     * change status into "DE"
     *
     * @param customerId String
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusToDE(String customerId)
    {
        customerRepository.updateStatus("DE", customerId);
    }

    /**
     * get all customers.
     *
     * @return Page<Customer>
     */
    @Transactional(readOnly = true)
    public Page<Customer> getAllCustomers(Pageable pageable)
    {
        return customerRepository.findByStatus("DE", pageable);
    }

    /**
     * search customer by all fields.
     *
     * @param customer Customer
     * @param pageable Pageable
     * @return Page<Customer>
     */
    @Override
    public Page<Customer> search(Customer customer, Pageable pageable)
    {
        return customerRepository.findAll((Specification<Customer>) (root, criteriaQuery, criteriaBuilder) ->
        {
            List<Predicate> predicateList = new ArrayList<>();
            if (customer.getCustomerId() != null) {
                predicateList.add(criteriaBuilder.like(root.get("customerId"), "%" + customer.getCustomerId() + "%"));
            }
            if (customer.getLastName() != null) {
                predicateList.add(criteriaBuilder.like(root.get("lastName"), "%" + customer.getLastName() + "%"));
            }
            if (customer.getFirstName() != null) {
                predicateList.add(criteriaBuilder.like(root.get("firstName"), "%" + customer.getFirstName() + "%"));
            }
            if (customer.getStatus() != null) {
                predicateList.add(criteriaBuilder.like(root.get("status"), "%" + customer.getStatus() + "%"));
            }
            if (customer.getAddress() != null) {
                predicateList.add(criteriaBuilder.like(root.get("address"), "%" + customer.getAddress() + "%"));
            }
            if (customer.getEmail() != null) {
                predicateList.add(criteriaBuilder.like(root.get("email"), "%" + customer.getEmail() + "%"));
            }
            if (customer.getPhone() != null) {
                predicateList.add(criteriaBuilder.like(root.get("phone"), "%" + customer.getPhone() + "%"));
            }
            if (customer.getFax() != null) {
                predicateList.add(criteriaBuilder.like(root.get("fax"), "%" + customer.getFax() + "%"));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }, pageable);
    }
}
