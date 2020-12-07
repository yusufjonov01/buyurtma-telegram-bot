package gvs.dto;


import gvs.enums.OrderItemType;

import java.util.Date;
import java.util.List;

public class OrderItem {
    private Integer orderNumber;

    private Integer id;//key for search

    //user information
    private Long userId;
    private String userName;
    private String username;
    private String phoneNumber;
    private String lat;
    private String lan;
    //user information

    private double allPrice;//order price
    private List<ProductItem> productItems;//product orders
    private Date date;
    private Date acceptedDate;
    private String screenshotId;
    private OrderItemType orderItemType;

    public Date getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OrderItem() {
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(double allPrice) {
        this.allPrice = allPrice;
    }

    public List<ProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<ProductItem> productItems) {
        this.productItems = productItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getScreenshotId() {
        return screenshotId;
    }

    public void setScreenshotId(String screenshotId) {
        this.screenshotId = screenshotId;
    }

    public OrderItemType getOrderItemType() {
        return orderItemType;
    }

    public void setOrderItemType(OrderItemType orderItemType) {
        this.orderItemType = orderItemType;
    }
}
