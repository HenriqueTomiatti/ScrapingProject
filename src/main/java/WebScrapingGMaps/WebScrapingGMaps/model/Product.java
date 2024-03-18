package WebScrapingGMaps.WebScrapingGMaps.model;

public class Product {
    private String name;
    private String rating;
    private String street;
    private String type;
    private String number;

    public Product(String name, String rating, String street, String type, String number) {
        this.name = name;
        this.rating = rating;
        this.street = street;
        this.type = type;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
