package cart.dto.response;

import cart.domain.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemResponse {
    private Long id;
    private long quantity;
    private ProductResponse product;

    private CartItemResponse(Long id, long quantity, ProductResponse product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getQuantity(),
                ProductResponse.from(cartItem.getProduct())
        );
    }
    
    public static List<CartItemResponse> from(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
    }

    public long getQuantity() {
        return quantity;
    }

    public ProductResponse getProduct() {
        return product;
    }
}
