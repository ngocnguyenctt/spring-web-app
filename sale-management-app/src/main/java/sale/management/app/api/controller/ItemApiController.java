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
import sale.management.app.model.Item;
import sale.management.app.service.ItemService;
import sale.management.app.validator.ValidId;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * @author ngoc on 21/04/2018
 * @subject sale-management-app
 */

@RestController
@Validated
@RequestMapping("/items")
public class ItemApiController
{

    private final ItemService itemService;

    private final MessageSource messageSource;

    @Autowired
    public ItemApiController(ItemService itemService, MessageSource messageSource)
    {
        this.itemService = itemService;
        this.messageSource = messageSource;
    }

    /**
     * get all items of information.
     *
     * @param _itemId String
     * @return ResponseEntity<Item>
     */
    @GetMapping(value = "/{itemId}/info")
    public ResponseEntity<Item> getItemInfo(@PathVariable("itemId") @ValidId String _itemId)
    {
        Optional<Item> itemOptional = itemService.getById(_itemId);
        Item item;
        if (itemOptional.isPresent()) {
            itemOptional.get().setItemId(itemOptional.get().getItemId().toUpperCase());
            item = itemOptional.get();
        } else {
            String errorMessage = messageSource.getMessage(
                    "error.no.item.id", new Object[]{_itemId}, LocaleContextHolder.getLocale());
            throw new EntityNotFoundException(errorMessage);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
