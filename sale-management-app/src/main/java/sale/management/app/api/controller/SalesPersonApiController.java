package sale.management.app.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sale.management.app.model.SalesPerson;
import sale.management.app.service.SalesPersonService;
import sale.management.app.validator.ValidId;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@RestController
@Validated
@RequestMapping("/salesperson")
public class SalesPersonApiController
{

    private final SalesPersonService salesPersonService;

    private final MessageSource messageSource;

    @Autowired
    public SalesPersonApiController(SalesPersonService salesPersonService, MessageSource messageSource)
    {
        this.salesPersonService = salesPersonService;
        this.messageSource = messageSource;
    }

    /**
     * get salesperson name by id.
     *
     * @param _id String
     * @return ResponseEntity<SalesPerson>
     */
    @GetMapping(value = "/{salesPersonId}")
    public ResponseEntity<SalesPerson> getSalesPersonName(@PathVariable("salesPersonId") @ValidId String _id)
    {
        Optional<SalesPerson> salesPersonOptional = salesPersonService.getById(_id);
        SalesPerson salesPerson;
        if (salesPersonOptional.isPresent()) {
            salesPerson = buildSalesPerson(salesPersonOptional.get());
        } else {
            throw new EntityNotFoundException(getErrorMessage(_id));
        }
        return new ResponseEntity<>(salesPerson, HttpStatus.OK);
    }

    /**
     * get all salesperson of information.
     *
     * @param _id String
     * @return ResponseEntity<SalesPerson>
     */
    @GetMapping(value = "/{salesPersonId}/info")
    public ResponseEntity<SalesPerson> getSalesPersonInfo(@PathVariable("salesPersonId") @ValidId String _id)
    {
        Optional<SalesPerson> salesPersonOptional = salesPersonService.getById(_id);
        SalesPerson salesPerson;
        if (salesPersonOptional.isPresent()) {
            salesPerson = salesPersonOptional.get();
        } else {
            throw new EntityNotFoundException(getErrorMessage(_id));
        }
        return new ResponseEntity<>(salesPerson, HttpStatus.OK);
    }

    /**
     * create salesperson.
     *
     * @param salesPerson SalesPerson
     * @return ResponseEntity<SalesPerson>
     */
    @PostMapping
    public ResponseEntity<SalesPerson> createSalesPerson(@ModelAttribute @Valid SalesPerson salesPerson)
    {
        SalesPerson savedSalesPerson = salesPersonService.save(salesPerson);
        return new ResponseEntity<>(buildSalesPerson(savedSalesPerson), HttpStatus.OK);
    }

    /**
     * get error message by locale.
     *
     * @param id String
     * @return error message
     */
    private String getErrorMessage(String id)
    {
        return messageSource.getMessage("error.no.salesperson.id", new Object[]{id}, LocaleContextHolder.getLocale());
    }

    /**
     * build salesPerson.
     *
     * @param salesPerson SalesPerson
     * @return SalesPerson
     */
    private SalesPerson buildSalesPerson(SalesPerson salesPerson)
    {
        return SalesPerson.builder().salesPersonId(salesPerson.getSalesPersonId().toUpperCase())
                .firstName(salesPerson.getFirstName()).lastName(salesPerson.getLastName()).build();
    }
}
