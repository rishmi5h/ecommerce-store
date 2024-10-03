package com.ecommerce.store.dto;

import com.ecommerce.store.model.Coupon;

import java.math.BigDecimal;
import java.util.List;

public class StoreStatistics {
    private int totalItemsPurchased;
    private BigDecimal totalPurchaseAmount;
    private List<Coupon> discountCodes;
    private BigDecimal totalDiscountAmount;

    public StoreStatistics(int totalItemsPurchased, BigDecimal totalPurchaseAmount, List<Coupon> discountCodes, BigDecimal totalDiscountAmount) {
        this.totalItemsPurchased = totalItemsPurchased;
        this.totalPurchaseAmount = totalPurchaseAmount;
        this.discountCodes = discountCodes;
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public int getTotalItemsPurchased() {
        return totalItemsPurchased;
    }

    public void setTotalItemsPurchased(int totalItemsPurchased) {
        this.totalItemsPurchased = totalItemsPurchased;
    }

    public BigDecimal getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public void setTotalPurchaseAmount(BigDecimal totalPurchaseAmount) {
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

    public List<Coupon> getDiscountCodes() {
        return discountCodes;
    }

    public void setDiscountCodes(List<Coupon> discountCodes) {
        this.discountCodes = discountCodes;
    }

    public BigDecimal getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }
}
