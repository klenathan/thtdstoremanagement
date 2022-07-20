package storemanagement.Model;

public class Product {
    private String productName, category;
    private long price;

    public Product(String productName, String category, long price) {
        this.productName = productName;
        this.category = category;
        setPrice(price);
    }


    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        if(price > 0) {
            this.price = price;
        } else {
            this.price = 0;
        }
    }}