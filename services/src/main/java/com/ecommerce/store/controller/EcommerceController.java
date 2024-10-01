package com.ecommerce.store.controller;

import com.ecommerce.store.dto.StoreStatistics;
import com.ecommerce.store.model.Cart;
import com.ecommerce.store.service.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/ecommerce")
public class EcommerceController {

    @Autowired
    private EcommerceService ecommerceService;

    @PostMapping("/cart/{cartId}/add")
    public ResponseEntity<Cart> addToCart(@PathVariable Long cartId,
                                          @RequestParam Long productId,
                                          @RequestParam int quantity) {
        Cart updatedCart = ecommerceService.addToCart(cartId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/cart/{cartId}/checkout")
    public ResponseEntity<BigDecimal> checkout(@PathVariable Long cartId,
                                               @RequestParam(required = false) String couponCode) {
        BigDecimal total = ecommerceService.checkout(cartId, couponCode);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/admin/statistics")
    public ResponseEntity<StoreStatistics> getStoreStatistics() {
        StoreStatistics statistics = ecommerceService.getStoreStatistics();
        return ResponseEntity.ok(statistics);
    }
}
