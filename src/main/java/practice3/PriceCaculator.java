package practice3;

import java.math.BigDecimal;

public class PriceCaculator {
    private Order order;

    public PriceCaculator(Order order) {
        this.order = order;
    }

    public BigDecimal getCalculate() {
        BigDecimal subTotal = new BigDecimal(0);
        subTotal = subTotal.add(calculateSubtotal());
        BigDecimal reducedPrice = calculateReducedPrice();
        subTotal = subTotal.subtract(reducedPrice);
        BigDecimal tax = subTotal.multiply(order.getTax());

        return subTotal.add(tax);
    }

    private BigDecimal calculateReducedPrice() {

        return order.getDiscounts().stream()
                .reduce(BigDecimal::add)
                .get();
    }

    private BigDecimal calculateSubtotal() {
        return order.getOrderLineItemList().stream()
                .map(OrderLineItem::getPrice)
                .reduce(BigDecimal::add)
                .get();
    }
}
