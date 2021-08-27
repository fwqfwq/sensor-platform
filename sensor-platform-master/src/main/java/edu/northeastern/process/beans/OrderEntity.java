package edu.northeastern.process.beans;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by F Wu
 */

@Table
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "seriesNumber")
    private String seriesNumber;

    @Column(name = "productName")
    private String productName;

    @Column(name = "orderId")
    private String orderId;

    @Column(name = "count")
    private int count;

    @Column(name = "buyerName")
    private String buyerName;

    @Column(name = "buyerId")
    private String buyerId;

    @Column(name = "purchaseTime")
    private Date purchaseTime;

    @Column(name = "paymentMethod")
    private String paymentMethod;


    public OrderEntity() {}

    public OrderEntity(String seriesNumber, String productName, String orderId, int count, String buyerName, String buyerId, Date purchaseTime, String paymentMethod) {
        this.seriesNumber = seriesNumber;
        this.productName = productName;
        this.orderId = orderId;
        this.count = count;
        this.buyerName = buyerName;
        this.buyerId = buyerId;
        this.purchaseTime = purchaseTime;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "Product " + this.productName + " purchased by " + this.buyerName + ". \nOrder id: " + this.orderId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public void setSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
