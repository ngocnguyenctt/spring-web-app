package sale.management.app.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sale.management.app.model.Item;

/**
 * @author ngoc on 21/04/2018
 * @subject sale-management-app
 */

@Repository
public interface ItemRepository extends CommonRepository<Item>
{
    /**
     * update quantity of items in stock.
     *
     * @param qty int
     * @param itemId String
     */
    @Modifying
    @Query("update Item set qtyStock = ?1 where itemId = ?2")
    void updateQtyStock(int qty, String itemId);
}