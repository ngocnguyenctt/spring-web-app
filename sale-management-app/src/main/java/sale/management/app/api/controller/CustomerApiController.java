package sale.management.app.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sale.management.app.model.Customer;
import sale.management.app.service.CustomerService;
import sale.management.app.validator.ValidId;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @author ngoc on 21/04/2018
 * @subject sale-management-app
 */

@RestController
@Validated
@RequestMapping("/customers")
public class CustomerApiController
{
    private final CustomerService customerService;

    private final MessageSource messageSource;

    @Autowired
    public CustomerApiController(CustomerService customerService, MessageSource messageSource)
    {
        this.customerService = customerService;
        this.messageSource = messageSource;
    }

    /**
     * get customer name by id.
     *
     * @param _id String
     * @return ResponseEntity<Customer>
     */
    @GetMapping(value = "/{customerId}")
    public ResponseEntity<Customer> getCustomerName(@PathVariable("customerId") @ValidId String _id)
    {
        Optional<Customer> customerOptional = customerService.getById(_id);
        Customer customer;
        if (customerOptional.isPresent()) {
            customer = buildCustomer(customerOptional.get());
        } else {
            throw new EntityNotFoundException(getErrorMessage(_id));
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /**
     * get all customer of information.
     *
     * @param _id String
     * @return ResponseEntity<Customer>
     */
    @GetMapping(value = "/{customerId}/info")
    public ResponseEntity<Customer> getCustomerInfo(@PathVariable("customerId") @ValidId String _id)
    {
        Optional<Customer> customerOptional = customerService.getById(_id);
        Customer customer;
        if (customerOptional.isPresent()) {
            customer = customerOptional.get();
        } else {
            throw new EntityNotFoundException(getErrorMessage(_id));
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /**
     * create customer.
     *
     * @param customer Customer
     * @return ResponseEntity<Customer>
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@ModelAttribute @Valid Customer customer)
    {
        Customer savedCustomer = customerService.save(customer);
        return new ResponseEntity<>(buildCustomer(savedCustomer), HttpStatus.OK);
    }

    /**
     * get error message by locale.
     *
     * @param id String
     * @return error message
     */
    private String getErrorMessage(String id)
    {
        return messageSource.getMessage("error.no.customer.id", new Object[]{id}, LocaleContextHolder.getLocale());
    }

    /**
     * build customer.
     *
     * @param customer Customer
     * @return Customer
     */
    private Customer buildCustomer(Customer customer)
    {
        return Customer.builder().customerId(customer.getCustomerId().toUpperCase())
                .firstName(customer.getFirstName()).lastName(customer.getLastName()).build();
    }
}
