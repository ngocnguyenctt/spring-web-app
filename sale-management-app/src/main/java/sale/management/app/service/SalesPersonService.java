package sale.management.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sale.management.app.model.SalesPerson;
import sale.management.app.repository.SalesPersonRepository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Service
public class SalesPersonService extends CommonService<SalesPerson>
{
    private final SalesPersonRepository salesPersonRepository;

    @Autowired
    public SalesPersonService(SalesPersonRepository salesPersonRepository)
    {
        this.salesPersonRepository = salesPersonRepository;
    }

    /**
     * change status into "DE"
     *
     * @param salesPersonId String
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusToDE(String salesPersonId)
    {
        salesPersonRepository.updateStatus("DE", salesPersonId);
    }

    /**
     * get all sales-people.
     *
     * @return Page<SalesPerson>
     */
    @Transactional(readOnly = true)
    public Page<SalesPerson> getAllSalesPerson(Pageable pageable)
    {
        return salesPersonRepository.findByStatus("DE", pageable);
    }

    /**
     * search sales-person by field.
     *
     * @param salesPerson SalesPerson
     * @param pageable    Pageable
     * @return Page<SalesPerson>
     */
    @Override
    public Page<SalesPerson> search(SalesPerson salesPerson, Pageable pageable)
    {
        return salesPersonRepository.findAll((Specification<SalesPerson>) (root, criteriaQuery, criteriaBuilder) ->
        {
            List<Predicate> predicateList = new ArrayList<>();
            if (salesPerson.getSalesPersonId() != null) {
                predicateList.add(criteriaBuilder.like(root.get("salesPersonId"), "%" + salesPerson.getSalesPersonId() + "%"));
            }
            if (salesPerson.getLastName() != null) {
                predicateList.add(criteriaBuilder.like(root.get("lastName"), "%" + salesPerson.getLastName() + "%"));
            }
            if (salesPerson.getFirstName() != null) {
                predicateList.add(criteriaBuilder.like(root.get("firstName"), "%" + salesPerson.getFirstName() + "%"));
            }
            if (salesPerson.getStatus() != null) {
                predicateList.add(criteriaBuilder.like(root.get("status"), "%" + salesPerson.getStatus() + "%"));
            }
            if (salesPerson.getAddress() != null) {
                predicateList.add(criteriaBuilder.like(root.get("address"), "%" + salesPerson.getAddress() + "%"));
            }
            if (salesPerson.getEmail() != null) {
                predicateList.add(criteriaBuilder.like(root.get("email"), "%" + salesPerson.getEmail() + "%"));
            }
            if (salesPerson.getPhone() != null) {
                predicateList.add(criteriaBuilder.like(root.get("phone"), "%" + salesPerson.getPhone() + "%"));
            }
            if (salesPerson.getItemLimit() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("itemLimit"), salesPerson.getItemLimit()));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }, pageable);
    }
}