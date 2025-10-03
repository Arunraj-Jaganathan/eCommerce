package com.shoppingcartbackend.dream_shops.service.cart;

import com.shoppingcartbackend.dream_shops.model.CartItem;

public interface ICartItemService {
    void addCartItem(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
