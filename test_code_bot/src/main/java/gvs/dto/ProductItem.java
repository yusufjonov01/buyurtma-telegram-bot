package gvs.dto;


import gvs.enums.ProductItemType;

public class ProductItem {
    private Integer id;
    private String name;
    private String keyName;
    private String categoryName;
    private double price;
    private double amount;
    private double count;
    private double allAmount;
    private double allPrice;
    private boolean isActive;
    private String weightType;
    private String photoId;
    private Long userId;
    private ProductItemType productItemType;
    private String orderProductType;
    private Double totalPrice;
    private String productSellType;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductSellType() {
        return productSellType;
    }

    public void setProductSellType(String productSellType) {
        this.productSellType = productSellType;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderProductType() {
        return orderProductType;
    }

    public void setOrderProductType(String orderProductType) {
        this.orderProductType = orderProductType;
    }

    public ProductItemType getProductItemType() {
        return productItemType;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public ProductItem(Integer id, String name, String keyName, String categoryName, double price, double amount, double count, double allAmount, double allPrice, boolean isActive, String weightType, String photoId, Long userId, ProductItemType productItemType) {
        this.id = id;
        this.name = name;
        this.keyName = keyName;
        this.categoryName = categoryName;
        this.price = price;
        this.amount = amount;
        this.count = count;
        this.allAmount = allAmount;
        this.allPrice = allPrice;
        this.isActive = isActive;
        this.weightType = weightType;
        this.photoId = photoId;
        this.userId = userId;
        this.productItemType = productItemType;
    }

    public void setProductItemType(ProductItemType productItemType) {
        this.productItemType = productItemType;
    }

    public ProductItem(Integer id, String name, String keyName, String categoryName, double price, double amount, double count, double allAmount, double allPrice, boolean isActive, String weightType, Long userId) {
        this.id = id;
        this.name = name;
        this.keyName = keyName;
        this.categoryName = categoryName;
        this.price = price;
        this.amount = amount;
        this.count = count;
        this.allAmount = allAmount;
        this.allPrice = allPrice;
        this.isActive = isActive;
        this.weightType = weightType;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductItem() {
    }

    public String getWeightType() {
        return weightType;
    }

    public void setWeightType(String weightType) {
        this.weightType = weightType;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(double allPrice) {
        this.allPrice = allPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(double allAmount) {
        this.allAmount = allAmount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
