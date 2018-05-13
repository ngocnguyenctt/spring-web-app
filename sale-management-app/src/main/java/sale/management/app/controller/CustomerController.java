package sale.management.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sale.management.app.model.Customer;
import sale.management.app.service.CustomerService;

import javax.validation.Valid;

/**
 * @author ngoc on 02/05/2018
 * @subject sale-management-app
 */

@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends CommonController
{
    private final CustomerService customerService;

    private final MessageSource messageSource;

    @Autowired
    public CustomerController(CustomerService customerService, MessageSource messageSource)
    {
        this.customerService = customerService;
        this.messageSource = messageSource;
    }

    /**
     * load initial form
     *
     * @param customer Customer
     * @param pageable Pageable
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView index(@ModelAttribute("Customer") Customer customer, Pageable pageable)
    {
        ModelAndView modelAndView = new ModelAndView("customer");
        Page<Customer> customerPage = customerService.getAllCustomers(pageable);
        modelAndView.addObject("customerList", customerPage.getContent());
        modelAndView.addObject("lastPage", customerPage.getTotalPages());
        modelAndView.addObject("currentPage", pageable.getPageNumber() + 1);
        return modelAndView;
    }

    /**
     * create a customer into db
     *
     * @param customer Customer
     * @param result   BindingResult
     * @return index
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("Customer") Customer customer, BindingResult result,
                         SessionStatus sessionStatus, RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) return "customer";
        customerService.save(customer);
        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("SuccessMessage",
                getMessage(messageSource, "flash.create.success", new Object[]{"customer"}));
        return "redirect:/customer";
    }

    /**
     * delete temporary customer
     * change status field into 'DE'
     *
     * @param _customerId String
     * @return index
     */
    @GetMapping(value = "/{customerId}")
    public String updateStatusToDE(@PathVariable("customerId") String _customerId, RedirectAttributes redirectAttributes)
    {
        customerService.updateStatusToDE(_customerId);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("SuccessMessage",
                getMessage(messageSource, "flash.delete.success", new Object[]{"customer", _customerId}));
        return "redirect:/customer";
    }

    /**
     * search customer by all fields.
     *
     * @param customer
     * @param pageable
     * @return
     */
    @GetMapping(value = "/search")
    public ModelAndView search(@ModelAttribute("Customer") Customer customer, Pageable pageable)
    {
        ModelAndView modelAndView = new ModelAndView("customer");
        Page<Customer> customerPage = customerService.search(customer, pageable);
        modelAndView.addObject("customerList", customerPage.getContent());
        modelAndView.addObject("lastPage", customerPage.getTotalPages());
        modelAndView.addObject("currentPage", pageable.getPageNumber() + 1);
        return modelAndView;
    }
}
