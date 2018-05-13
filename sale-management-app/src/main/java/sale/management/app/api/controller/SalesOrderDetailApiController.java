package sale.management.app.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.management.app.model.SalesOrderDetail;
import sale.management.app.repository.impl.SalesOrderDetailImpl;
import sale.management.app.validator.ValidId;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author ngoc on 21/04/2018
 * @subject sale-management-app
 */

@RestController
@Validated
@RequestMapping("/sales-orders")
public class SalesOrderDetailApiController
{

    private final SalesOrderDetailImpl salesOrderDetailImpl;

    private final MessageSource messageSource;

    @Autowired
    public SalesOrderDetailApiController(SalesOrderDetailImpl salesOrderDetail, MessageSource messageSource)
    {
        this.salesOrderDetailImpl = salesOrderDetail;
        this.messageSource = messageSource;
    }

    /**
     * get all sales-orders of information.
     *
     * @param _orderNo String
     * @return ResponseEntity<List<SalesOrderDetail>>
     */
    @GetMapping(value = "/{orderNo}/info")
    public ResponseEntity<List<SalesOrderDetail>> getSalesOrderInfo(@PathVariable("orderNo") @ValidId String _orderNo)
    {
        List<SalesOrderDetail> salesOrderDetailList = salesOrderDetailImpl.getAllByOrderNo(_orderNo);
        if (salesOrderDetailList == null || salesOrderDetailList.isEmpty()) {
            String errorMessage = messageSource.getMessage(
                    "error.no.salesOrder.orderNo",
                    new Object[]{_orderNo},
                    LocaleContextHolder.getLocale());
            throw new EntityNotFoundException(errorMessage);
        }
        return new ResponseEntity<>(salesOrderDetailList, HttpStatus.OK);
    }
}