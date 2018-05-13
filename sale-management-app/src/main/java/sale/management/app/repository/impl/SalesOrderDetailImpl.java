package sale.management.app.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sale.management.app.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author ngoc on 22/04/2018
 * @subject sale-management-app
 */

@Repository
public class SalesOrderDetailImpl
{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public SalesOrderDetailImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
    {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * get salesOrder by orderNo.
     *
     * @param orderNo String
     * @return SalesOrder
     */
    @Transactional(readOnly = true)
    public SalesOrder getByOrderNo(String orderNo)
    {
        String sql = "SELECT customer.customer_id, " +
                        "customer.first_name as customer_first_name, " +
                        "customer.last_name as customer_last_name," +
                        "sp.sales_person_id, " +
                        "sp.first_name as sp_first_name, " +
                        "sp.last_name as sp_last_name, " +
                        "so.order_no, " +
                        "so.order_date, " +
                        "so.overdue_date, " +
                        "so.order_disc, " +
                        "so.tax_amt, " +
                        "so.total_amt, " +
                        "so.payment, " +
                        "so.description " +
                    "FROM sales_people sp " +
                    "JOIN sales_orders so ON sp.sales_person_id = so.sales_person_id " +
                    "JOIN customers customer ON so.customer_id = customer.customer_id " +
                    "WHERE so.order_no = :orderNo";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("orderNo", orderNo);
        List<SalesOrder> salesOrderList = namedParameterJdbcTemplate
                .query(sql, parameterSource, (resultSet, i) -> toSalesOrder(resultSet));
        if (salesOrderList.size() == 1) return salesOrderList.get(0);
        return null;
    }

    /**
     * get all salesOrder details by orderNo
     *
     * @param orderNo String
     * @return SalesOrderDetail List.
     */
    @Transactional(readOnly = true)
    public List<SalesOrderDetail> getAllByOrderNo(String orderNo)
    {
        String sql = "SELECT sod.order_no, " +
                        "sod.qty, sod.price, " +
                        "sod.item_disc, " +
                        "sod.tax_amt, " +
                        "sod.amount, " +
                        "item.item_id, " +
                        "item.item_name, " +
                        "item.unit " +
                    "FROM sales_order_details sod " +
                    "JOIN items item ON sod.item_id = item.item_id " +
                    "WHERE order_no = :orderNo";
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("orderNo", orderNo);
        return namedParameterJdbcTemplate.query(sql, parameter, (resultSet, i) -> toSalesOrderDetail(resultSet));
    }

    /**
     * load salesOrderDetail from db
     *
     * @param rs ResultSet
     * @return SalesOrderDetail
     * @throws SQLException SQLException
     */
    private SalesOrderDetail toSalesOrderDetail(ResultSet rs) throws SQLException
    {
        SalesOrderDetail salesOrderDetail = new SalesOrderDetail();
        SalesOrder salesOrder = getByOrderNo(rs.getString("order_no"));
        Item item = Item.builder().itemId(rs.getString("item_id"))
                .itemName(rs.getString("item_name"))
                .unit(rs.getString("unit")).build();
        salesOrderDetail.setSalesOrderDetailId(new SalesOrderDetailId(salesOrder, item));
        salesOrderDetail.setAmount(rs.getBigDecimal("amount"));
        salesOrderDetail.setItemDisc(rs.getBigDecimal("item_disc"));
        salesOrderDetail.setPrice(rs.getBigDecimal("price"));
        salesOrderDetail.setQty(rs.getInt("qty"));
        salesOrderDetail.setTaxAmt(rs.getBigDecimal("tax_amt"));
        return salesOrderDetail;
    }

    /**
     * load salesOrder from db
     *
     * @param rs ResultSet
     * @return SalesOrder
     * @throws SQLException SQLException
     */
    private SalesOrder toSalesOrder(ResultSet rs) throws SQLException
    {
        SalesOrder salesOrder = new SalesOrder();
        Customer customer = Customer.builder().customerId(rs.getString("customer_id"))
                .firstName(rs.getString("customer_first_name"))
                .lastName(rs.getString("customer_last_name")).build();
        SalesPerson salesPerson = SalesPerson.builder().salesPersonId(rs.getString("sales_person_id"))
                .firstName(rs.getString("sp_first_name"))
                .lastName(rs.getString("sp_last_name")).build();
        salesOrder.setOrderNo(rs.getString("order_no"));
        salesOrder.setOrderDate(LocalDate.parse(rs.getString("order_date")));
        salesOrder.setOverdueDate(LocalDate.parse(rs.getString("overdue_date")));
        salesOrder.setSalesPerson(salesPerson);
        salesOrder.setCustomer(customer);
        salesOrder.setOrderDisc(rs.getBigDecimal("order_disc"));
        salesOrder.setTaxAmt(rs.getBigDecimal("tax_amt"));
        salesOrder.setTotalAmt(rs.getBigDecimal("total_amt"));
        salesOrder.setPayment(rs.getBigDecimal("payment"));
        salesOrder.setDescription(rs.getString("description"));
        return salesOrder;
    }
}
