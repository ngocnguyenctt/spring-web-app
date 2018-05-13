package sale.management.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import sale.management.app.model.Item;
import sale.management.app.repository.ItemRepository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ngoc on 21/04/2018
 * @subject sale-management-app
 */

@Service
@Validated
public class ItemService extends CommonService<Item>
{
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository)
    {
        this.itemRepository = itemRepository;
    }

    /**
     * update quantity of items in stock.
     *
     * @param qty    int
     * @param itemId String
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateQtyStock(int qty, String itemId)
    {
        itemRepository.updateQtyStock(qty, itemId);
    }

    /**
     * search item by all fields.
     *
     * @param item     Item
     * @param pageable Pageable
     * @return Page<item>
     */
    @Override
    public Page<Item> search(Item item, Pageable pageable)
    {
        return itemRepository.findAll((Specification<Item>) (root, criteriaQuery, criteriaBuilder) ->
        {
            List<Predicate> predicateList = new ArrayList<>();
            if (item.getItemId() != null) {
                predicateList.add(criteriaBuilder.like(root.get("itemId"), "%" + item.getItemId() + "%"));
            }
            if (item.getItemName() != null) {
                predicateList.add(criteriaBuilder.like(root.get("itemName"), "%" + item.getItemName() + "%"));
            }
            if (item.getQty() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("qty"), item.getQty()));
            }
            if (item.getQtyStock() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("qtyStock"), item.getQtyStock()));
            }
            if (item.getPrice() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("price"), item.getPrice()));
            }
            if (item.getUnit() != null) {
                predicateList.add(criteriaBuilder.like(root.get("unit"), "%" + item.getUnit() + "%"));
            }
            if (item.getItemDisc() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("itemDisc"), item.getItemDisc()));
            }
            if (item.getTaxAmt() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("taxAmt"), item.getTaxAmt()));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }, pageable);
    }
}
