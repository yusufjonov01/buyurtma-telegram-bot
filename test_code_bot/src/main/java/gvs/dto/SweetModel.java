package gvs.dto;

public class SweetModel {
    private String name;
    private double  price;
    private double  weight;
    private double  count;
    private double  allWeight;
    private String key;

    public SweetModel(String name, double price, double weight, double count, double allWeight, String key) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.count = count;
        this.allWeight = allWeight;
        this.key = key;
    }

    public SweetModel(String name, double price, double weight, double allWeight, String key) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.allWeight = allWeight;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getAllWeight() {
        return allWeight;
    }

    public void setAllWeight(double allWeight) {
        this.allWeight = allWeight;
    }

    public SweetModel() {
    }

    public SweetModel(String name, double price, double weight, double count, double allWeight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.count = count;
        this.allWeight = allWeight;
    }
}
