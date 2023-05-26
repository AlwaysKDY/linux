package com.example.web_backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DessertPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int purchaseNumber;
    private int dessertId;
    private String buyTime;
    private double discount;
    private double totalPrice;
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int uid) {
        this.id = uid;
    }

    public int getDessertId() {
        return dessertId;
    }

    public void setDessertId(int dessertId) {
        this.dessertId = dessertId;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "DessertPurchase{" +
                "purchaseNumber=" + purchaseNumber +
                ", uid=" + id +
                ", dessertId=" + dessertId +
                ", buyTime=" + buyTime +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
