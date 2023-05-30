package cart.domain;

import cart.entity.OrderEntity;
import cart.exception.CartItemException.TotalPriceNotSame;

import java.util.List;

public class Order {

    private final Long id;
    private final Member member;
    private final long shippingFee;
    private final long totalPrice;
    private final List<OrderItem> orderItems;

    public Order(final Long id, final Member member, final long shippingFee, final long totalPrice, final List<OrderItem> orderItems) {
        this.id = id;
        this.member = member;
        this.shippingFee = shippingFee;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }

    public static Order of(final Member member, long shippingFee, final List<OrderItem> orderItems, final long threshold) {
        long totalProductPrice = orderItems.stream()
                .mapToLong(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
        if (totalProductPrice >= threshold) {
            shippingFee = 0;
        }
        return new Order(null, member, shippingFee, totalProductPrice, orderItems);
    }

    public OrderEntity toEntity() {
        return OrderEntity.of(member.getId(), shippingFee, totalPrice);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public long getShippingFee() {
        return shippingFee;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void checkPrice(final long price) {
        if (totalPrice + shippingFee != price) {
            throw new TotalPriceNotSame();
        }
    }
}