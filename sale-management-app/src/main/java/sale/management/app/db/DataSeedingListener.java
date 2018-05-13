//package sale.management.app.db;
//
//import com.github.javafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import sale.management.app.model.Customer;
//import sale.management.app.model.Item;
//import sale.management.app.model.SalesPerson;
//import sale.management.app.repository.CustomerRepository;
//import sale.management.app.repository.ItemRepository;
//import sale.management.app.repository.SalesPersonRepository;
//
//import java.math.BigDecimal;
//import java.math.MathContext;
//import java.util.Random;
//
///**
// * @author ngoc on 14/05/2018
// * @subject spring-web-app
// */
//@Component
//public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent>
//{
//    private final CustomerRepository customerRepository;
//
//    private final SalesPersonRepository salesPersonRepository;
//
//    private final ItemRepository itemRepository;
//
//    private static final Faker faker = new Faker();
//
//    private static final Random random = new Random();
//
//    @Autowired
//    public DataSeedingListener(
//            CustomerRepository customerRepository,
//            SalesPersonRepository salesPersonRepository,
//            ItemRepository itemRepository)
//    {
//        this.customerRepository = customerRepository;
//        this.salesPersonRepository = salesPersonRepository;
//        this.itemRepository = itemRepository;
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
//    {
//
//        // Customer
//        if (customerRepository.count() == 0) {
//            for (int i = 0; i < 100; i++) {
//                Customer customer = new Customer();
//                String firstName = faker.firstName();
//                if (firstName.length() <= 10) {
//                    customer.setFirstName(firstName);
//                } else {
//                    customer.setFirstName(firstName.substring(0, 10));
//                }
//                customer.setLastName(faker.lastName());
//                customer.setAddress(faker.streetAddress(true));
//                customer.setPhone("099-" + String.valueOf(random.nextInt(9000000) + 1000000));
//                customer.setFax(String.valueOf(random.nextInt(9000000) + 1000000));
//                customer.setEmail(faker.firstName() + "@customer.com");
//                customer.setStatus("AV");
//                customer.setDescription(faker.sentence(10));
//                customerRepository.save(customer);
//            }
//        }
//
//        // SalesPerson
//        if (salesPersonRepository.count() == 0) {
//            for (int i = 0; i < 100; i++) {
//                SalesPerson salesPerson = new SalesPerson();
//                String firstName = faker.firstName();
//                if (firstName.length() <= 10) {
//                    salesPerson.setFirstName(firstName);
//                } else {
//                    salesPerson.setFirstName(firstName.substring(0, 10));
//                }
//                salesPerson.setLastName(faker.lastName());
//                salesPerson.setAddress(faker.streetAddress(true));
//                salesPerson.setEmail(faker.firstName() + "@sales-person.com");
//                salesPerson.setPhone("098-" + String.valueOf(random.nextInt(9000000) + 1000000));
//                salesPerson.setStatus("AV");
//                salesPerson.setDescription(faker.sentence(10));
//                salesPersonRepository.save(salesPerson);
//            }
//        }
//
//        // Item
//        if (itemRepository.count() == 0) {
//            for (int i = 1; i <= 100; i++) {
//                BigDecimal itemDisc = BigDecimal.valueOf(Math.random())
//                        .divide(new BigDecimal("1.00"), BigDecimal.ROUND_DOWN);
//                BigDecimal taxAmt = BigDecimal.valueOf(Math.random())
//                        .divide(new BigDecimal("2.00"), BigDecimal.ROUND_DOWN);
//                BigDecimal price = new BigDecimal("10000.00")
//                        .divide(BigDecimal.valueOf(Math.random()), BigDecimal.ROUND_DOWN);
//                BigDecimal salesPrice = (price.multiply(BigDecimal.valueOf(i), MathContext.DECIMAL64))
//                        .subtract(itemDisc, MathContext.DECIMAL64);
//                Item item = new Item();
//                item.setItemName("Item - " + i);
//                item.setQty(i);
//                item.setQtyStock(i);
//                item.setPrice(price);
//                item.setUnit("unit-" + i);
//                item.setItemDisc(itemDisc);
//                item.setTaxAmt(taxAmt);
//                item.setAmount(salesPrice.multiply(taxAmt, MathContext.DECIMAL64)
//                        .add(salesPrice, MathContext.DECIMAL64));
//                itemRepository.save(item);
//            }
//        }
//    }
//}
