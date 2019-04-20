package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal tax;

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = calculateSubtotal(products, items);
        BigDecimal reducedPrice = calculateReducedPrice(products, items);
        subTotal = subTotal.subtract(reducedPrice);
        BigDecimal taxTotal = subTotal.multiply(tax);
        BigDecimal grandTotal = subTotal.add(taxTotal);

        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private BigDecimal calculateReducedPrice(List<Product> products, List<OrderItem> items) {

        return products.stream().map(product -> {
            OrderItem curItem = findOrderItemByProduct(items, product);
            return product.getPrice()
                    .multiply(product.getDiscountRate())
                    .multiply(new BigDecimal(curItem.getCount()));
        }).reduce(BigDecimal::add).get();
    }


    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {

        return items.stream()
                .filter(item -> item.getCode() == product.getCode())
                .findFirst()
                .get();
    }

    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {

        return products.stream().map(product -> {
            OrderItem item = findOrderItemByProduct(items, product);
            return product.getPrice().multiply(new BigDecimal(item.getCount()));
        }).reduce(BigDecimal::add).get();
    }
}
