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
import sale.management.app.model.Item;
import sale.management.app.service.ItemService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author ngoc on 02/05/2018
 * @subject sale-management-app
 */

@Controller
@RequestMapping(value = "/item")
public class ItemController extends CommonController
{
    private final ItemService itemService;

    private final MessageSource messageSource;

    @Autowired
    public ItemController(ItemService itemService, MessageSource messageSource)
    {
        this.itemService = itemService;
        this.messageSource = messageSource;
    }

    /**
     * load initial form
     *
     * @param item     Item
     * @param pageable Pageable
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView index(@ModelAttribute("Item") Item item, Pageable pageable)
    {
        ModelAndView modelAndView = new ModelAndView("item");
        Page<Item> itemPage = itemService.getAll(pageable);
        modelAndView.addObject("itemList", itemPage.getContent());
        modelAndView.addObject("lastPage", itemPage.getTotalPages());
        modelAndView.addObject("currentPage", pageable.getPageNumber() + 1);
        return modelAndView;
    }

    /**
     * create a item into db
     *
     * @param item   item
     * @param result BindingResult
     * @return index
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("Item") Item item, BindingResult result,
                         SessionStatus sessionStatus, RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) return "item";
        BigDecimal salesPrice = (item.getPrice().multiply(BigDecimal.valueOf(item.getQty()), MathContext.DECIMAL64))
                .subtract(item.getItemDisc(), MathContext.DECIMAL64);
        item.setAmount(
                (salesPrice.multiply(item.getTaxAmt(), MathContext.DECIMAL64)).add(salesPrice, MathContext.DECIMAL64)
        );
        itemService.save(item);
        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("SuccessMessage",
                getMessage(messageSource, "flash.create.success", new Object[]{"Item"}));
        return "redirect:/item";
    }

    /**
     * delete item out of db.
     *
     * @param _itemId String
     * @return index
     */
    @GetMapping(value = "/{itemId}")
    public String deleteItem(@PathVariable("itemId") String _itemId, RedirectAttributes redirectAttributes)
    {
        itemService.delete(_itemId);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("SuccessMessage",
                getMessage(messageSource, "flash.delete.success", new Object[]{"Item", _itemId}));
        return "redirect:/item";
    }

    /**
     * search item by all fields.
     *
     * @param item     Item
     * @param pageable Pageable
     * @return ModelAndView
     */
    @GetMapping(value = "/search")
    public ModelAndView search(@ModelAttribute("Item") Item item, Pageable pageable)
    {
        ModelAndView modelAndView = new ModelAndView("item");
        Page<Item> itemPage = itemService.search(item, pageable);
        modelAndView.addObject("itemList", itemPage.getContent());
        modelAndView.addObject("lastPage", itemPage.getTotalPages());
        modelAndView.addObject("currentPage", pageable.getPageNumber() + 1);
        return modelAndView;
    }
}
