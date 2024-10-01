package com.ecommerce.store.service;

import com.ecommerce.store.model.*;
import com.ecommerce.store.repository.*;
import com.ecommerce.store.dto.StoreStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EcommerceService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Value("${coupon.generation.frequency}")
    private int couponGenerationFrequency;

    private static int orderCount = 0;
    private BigDecimal totalPurchaseAmount = BigDecimal.ZERO;
    private int totalItemsPurchased = 0;
    private BigDecimal totalDiscountAmount = BigDecimal.ZERO;

    @Transactional
    public Cart addToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cart.getItems().add(cartItem);

        return cartRepository.save(cart);
    }

    @Transactional
    public BigDecimal checkout(Long cartId, String couponCode) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        BigDecimal total = calculateTotal(cart);

        if (couponCode != null) {
            Optional<Coupon> couponOpt = couponRepository.findByCodeAndUsedFalse(couponCode);
            if (couponOpt.isPresent()) {
                Coupon coupon = couponOpt.get();
                BigDecimal discountAmount = total.multiply(BigDecimal.valueOf(coupon.getDiscountPercentage() / 100));
                total = total.subtract(discountAmount);
                totalDiscountAmount = totalDiscountAmount.add(discountAmount);
                coupon.setUsed(true);
                couponRepository.save(coupon);
            } else {
                throw new RuntimeException("Invalid or used coupon code");
            }
        }

        // Update statistics
        totalPurchaseAmount = totalPurchaseAmount.add(total);
        totalItemsPurchased += cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();

        // Clear the cart after checkout
        cart.getItems().clear();
        cartRepository.save(cart);

        // Generate coupon if necessary
        orderCount++;
        if (orderCount % couponGenerationFrequency == 0) {
            generateCoupon();
        }

        return total;
    }

    private BigDecimal calculateTotal(Cart cart) {
        return cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Coupon generateCoupon() {
        Coupon coupon = new Coupon();
        coupon.setCode(UUID.randomUUID().toString());
        coupon.setDiscountPercentage(10);
        coupon.setExpirationDate(LocalDate.now().plusDays(30));
        coupon.setUsed(false);
        return couponRepository.save(coupon);
    }

    public StoreStatistics getStoreStatistics() {
        List<Coupon> coupons = couponRepository.findAll();
        return new StoreStatistics(
                totalItemsPurchased,
                totalPurchaseAmount,
                coupons,
                totalDiscountAmount
        );
    }
}
