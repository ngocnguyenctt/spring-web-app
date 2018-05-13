package sale.management.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sale.management.app.model.SalesOrder;
import sale.management.app.service.SalesOrderService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Controller
@RequestMapping("/salesOrder")
public class SaleOrderController extends CommonController
{

    private final SalesOrderService salesOrderService;

    private final MessageSource messageSource;

    @Autowired
    public SaleOrderController(SalesOrderService salesOrderService, MessageSource messageSource)
    {
        this.salesOrderService = salesOrderService;
        this.messageSource = messageSource;
    }

    /**
     * initialize sales-order form.
     *
     * @param salesOrder SalesOrder
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView createSalesOrderForm(@ModelAttribute("SalesOrder") SalesOrder salesOrder)
    {
        ModelAndView modelAndView = new ModelAndView("salesOrder");
        salesOrder.setOrderNo(salesOrderService.generateOrderNoByDate());
        salesOrder.setOrderDate(LocalDate.now());
        salesOrder.setOverdueDate(LocalDate.now().plusDays(10));
        return modelAndView;
    }

    /**
     * create sales-order.
     *
     * @param salesOrder SalesOrder
     * @param result     BindingResult
     * @return index
     */
    @PostMapping
    public ModelAndView create(
            @ModelAttribute("SalesOrder") @Valid SalesOrder salesOrder,
            BindingResult result,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            return new ModelAndView("salesOrder", "items", salesOrder.getItems());
        }
        try {
            salesOrderService.createSalesOrder(salesOrder);
            sessionStatus.setComplete();
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("message",
                    getMessage(messageSource, "flash.create.success", new Object[]{"SalesOrder"}));
            return new ModelAndView("redirect:/salesOrder");
        } catch (DataIntegrityViolationException e) {
            Map<String, Object> map = new HashMap<>();
            if (e.getRootCause().getMessage().contains("sales_person_id")) {
                map.put("SalespersonMessage",
                        getMessage(messageSource, "controller.salesOrder.create.salesPersonMessage", null));
            }
            if (e.getRootCause().getMessage().contains("customer_id")) {
                map.put("CustomerMessage",
                        getMessage(messageSource, "controller.salesOrder.create.customerMessage", null));
            }
            if (e.getRootCause().getMessage().contains("item_id")) {
                map.put("css", "danger");
                map.put("message",
                        getMessage(messageSource, "controller.salesOrder.create.itemMessage", null));
            }
            return new ModelAndView("salesOrder", map).addObject("items", salesOrder.getItems());
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<>();
            map.put("css", "danger");
            map.put("message", e.getMessage());
            return new ModelAndView("salesOrder", map).addObject("items", salesOrder.getItems());
        }
    }
}