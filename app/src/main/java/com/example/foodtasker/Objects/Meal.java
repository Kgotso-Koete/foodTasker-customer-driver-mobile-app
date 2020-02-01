// ALL UPDATES COMPLETED
package com.example.foodtasker.Objects;

public class Meal {

    private String id, name, short_description, image;
    private Float price;

    public Meal(String id, String name, String short_description, String image, Float price) {
        this.id = id;
        this.name = name;
        this.short_description = short_description;
        this.image = image;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getImage() {
        return image;
    }

    public Float getPrice() {
        return price;
    }

}
