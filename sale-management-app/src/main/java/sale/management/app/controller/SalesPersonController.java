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
import sale.management.app.model.SalesPerson;
import sale.management.app.service.SalesPersonService;

import javax.validation.Valid;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Controller
@RequestMapping(value = "/salesPerson")
public class SalesPersonController extends CommonController
{

    private final SalesPersonService salesPersonService;

    private final MessageSource messageSource;

    @Autowired
    public SalesPersonController(SalesPersonService salesPersonService, MessageSource messageSource)
    {
        this.salesPersonService = salesPersonService;
        this.messageSource = messageSource;
    }

    /**
     * load initial form
     *
     * @param salesPerson SalesPerson
     * @param pageable    Pageable
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView index(@ModelAttribute("SalesPerson") SalesPerson salesPerson, Pageable pageable)
    {
        ModelAndView modelAndView = new ModelAndView("salesPerson");
        Page<SalesPerson> salesPersonPage = salesPersonService.getAllSalesPerson(pageable);
        modelAndView.addObject("salesPersonList", salesPersonPage.getContent());
        modelAndView.addObject("lastPage", salesPersonPage.getTotalPages());
        modelAndView.addObject("currentPage", pageable.getPageNumber() + 1);
        return modelAndView;
    }

    /**
     * create a salesperson into db
     *
     * @param salesPerson SalesPerson
     * @param result      BindingResult
     * @return index
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("SalesPerson") SalesPerson salesPerson, BindingResult result,
                         SessionStatus sessionStatus, RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) return "salesPerson";
        salesPersonService.save(salesPerson);
        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("SuccessMessage",
                getMessage(messageSource, "flash.create.success", new Object[]{"SalesPerson"}));
        return "redirect:/salesPerson";
    }

//    /**
//     * delete salesperson from db
//     *
//     * @param _salesPersonId String
//     * @return index
//     */
//    @GetMapping(value = "/{salesPersonId}")
//    public String delete(@PathVariable("salesPersonId") String _salesPersonId)
//    {
//        salesPersonService.delete(_salesPersonId);
//        return "redirect:/salesPerson";
//    }

    /**
     * delete temporary salesperson
     * change status field into 'DE'
     *
     * @param _salesPersonId String
     * @return index
     */
    @GetMapping(value = "/{salesPersonId}")
    public String updateStatusToDE(@PathVariable("salesPersonId") String _salesPersonId,
                                   RedirectAttributes redirectAttributes)
    {
        salesPersonService.updateStatusToDE(_salesPersonId);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("SuccessMessage",
                getMessage(messageSource, "flash.delete.success", new Object[]{"SalesPerson", _salesPersonId}));
        return "redirect:/salesPerson";
    }

    /**
     * search sales-person by all fields.
     *
     * @param salesPerson SalesPerson
     * @param pageable    Pageable
     * @return ModelAndView
     */
    @GetMapping(value = "/search")
    public ModelAndView search(@ModelAttribute("SalesPerson") SalesPerson salesPerson, Pageable pageable)
    {
        ModelAndView modelAndView = new ModelAndView("salesPerson");
        Page<SalesPerson> salesPersonPage = salesPersonService.search(salesPerson, pageable);
        modelAndView.addObject("salesPersonList", salesPersonPage.getContent());
        modelAndView.addObject("lastPage", salesPersonPage.getTotalPages());
        modelAndView.addObject("currentPage", pageable.getPageNumber() + 1);
        return modelAndView;
    }
}
