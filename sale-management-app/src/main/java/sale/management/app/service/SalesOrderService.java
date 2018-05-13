package sale.management.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sale.management.app.model.Item;
import sale.management.app.model.SalesOrder;
import sale.management.app.model.SalesOrderDetail;
import sale.management.app.repository.SalesOrderDetailRepository;
import sale.management.app.repository.SalesOrderRepository;

import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@Service
public class SalesOrderService extends CommonService<SalesOrder>
{
    private final SalesOrderRepository salesOrderRepository;

    private final SalesOrderDetailRepository salesOrderDetailRepository;

    private final ItemService itemService;

    private final MessageSource messageSource;

    @Autowired
    public SalesOrderService(
            SalesOrderRepository salesOrderRepository,
            SalesOrderDetailRepository salesOrderDetailRepository,
            ItemService itemService,
            MessageSource messageSource)
    {
        this.salesOrderRepository = salesOrderRepository;
        this.salesOrderDetailRepository = salesOrderDetailRepository;
        this.itemService = itemService;
        this.messageSource = messageSource;
    }

    /**
     * generate order number.
     *
     * @return String
     */
    @Transactional(readOnly = true)
    public synchronized String generateOrderNoByDate()
    {
        LocalDate localDate = LocalDate.now();
        List<SalesOrder> salesOrderList = salesOrderRepository.findAllByOrderDate(localDate);

        if (salesOrderList != null && !salesOrderList.isEmpty()) {
            Collections.sort(salesOrderList, (o1, o2) -> o2.getOrderNo().compareTo(o1.getOrderNo()));
            String lastOrderNoByDate = salesOrderList.get(0).getOrderNo();
            int oldOrderNo = Integer.parseInt(lastOrderNoByDate.substring(8, lastOrderNoByDate.length()));
            String newOrderNo = oldOrderNo < 9 ? ("0" + (oldOrderNo + 1)) : String.valueOf(oldOrderNo + 1);
            return lastOrderNoByDate.substring(0, 8) + newOrderNo;
        } else {
            return localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "01";
        }
    }

    /**
     * save sales-order details into db.
     *
     * @param salesOrder SalesOrder
     */
    @Transactional(rollbackFor = Exception.class)
    public void createSalesOrder(SalesOrder salesOrder) throws Exception
    {
        salesOrderRepository.save(salesOrder);
        List<SalesOrderDetail> salesOrderDetailList = salesOrder.getItems();

        if (!salesOrderDetailList.isEmpty()) {
            salesOrderDetailList.get(0).getSalesOrderDetailId().setSalesOrder(salesOrder);

            for (int i = 0; i < salesOrderDetailList.size(); i++) {
                SalesOrderDetail salesOrderDetail = salesOrderDetailList.get(i);
                for (int j = i + 1; j < salesOrderDetailList.size(); j++) {
                    SalesOrderDetail nextSalesOrderDetail = salesOrderDetailList.get(j);
                    if(nextSalesOrderDetail.getSalesOrderDetailId().getSalesOrder() == null){
                       nextSalesOrderDetail.getSalesOrderDetailId().setSalesOrder(salesOrder);
                    }
                    if (nextSalesOrderDetail.getSalesOrderDetailId().equals(salesOrderDetail.getSalesOrderDetailId())) {
                        salesOrderDetail.setQty(salesOrderDetail.getQty() + nextSalesOrderDetail.getQty());
                        salesOrderDetail.setPrice(salesOrderDetail.getPrice()
                                .add(nextSalesOrderDetail.getPrice(), MathContext.DECIMAL64));
                        salesOrderDetail.setTaxAmt(salesOrderDetail.getTaxAmt()
                                .add(nextSalesOrderDetail.getTaxAmt(), MathContext.DECIMAL64));
                        salesOrderDetail.setItemDisc(salesOrderDetail.getItemDisc()
                                .add(nextSalesOrderDetail.getItemDisc(), MathContext.DECIMAL64));
                        salesOrderDetail.setAmount(salesOrderDetail.getAmount()
                                .add(nextSalesOrderDetail.getAmount(), MathContext.DECIMAL64));
                        salesOrderDetailList.remove(nextSalesOrderDetail);
                    }
                }
            }
            salesOrderDetailRepository.saveAll(salesOrderDetailList);
            for (SalesOrderDetail salesOrderDetail : salesOrderDetailList) {
                updateQtyStock(salesOrderDetail);
            }
        }
    }

    @Override
    public Page<SalesOrder> search(SalesOrder salesOrder, Pageable pageable)
    {
        return null;
    }

    /**
     * update qtyStock into Item.
     *
     * @param salesOrderDetail SalesOrderDetail
     * @throws Exception Exception
     */
    private void updateQtyStock(SalesOrderDetail salesOrderDetail) throws Exception
    {
        String itemId = salesOrderDetail.getSalesOrderDetailId().getItem().getItemId();
        Optional<Item> itemOptional = itemService.getById(itemId);

        if (itemOptional.isPresent()) {
            int qtyStock = itemOptional.get().getQtyStock();
            int qty = salesOrderDetail.getQty();
            if (qty > qtyStock) {
                String errorMessage = messageSource.getMessage("error.updateQtyStock.itemService",
                        new Object[]{itemId, qtyStock}, LocaleContextHolder.getLocale());
                throw new Exception(errorMessage);
            } else {
                itemService.updateQtyStock(qtyStock - qty, itemId);
            }
        }
    }
}
