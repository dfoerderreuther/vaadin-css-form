package vaadin.form.sample;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MyBean {

    @Size(min = 1, max = 100)
    @NotNull
    private String name;

    @Size(min = 1, max = 100)
    @NotNull
    private String street;

    @Size(min = 1, max = 6)
    @NotNull
    private String zip;

    @Size(min = 1, max = 100)
    @NotNull
    private String city;

    @Size(min = 1, max = 100)
    @NotNull
    private String country;

    @Size(min = 1, max = 100)
    @NotNull
    private String message;

    @Size(min = 1, max = 100)
    @NotNull
    private String message2;

    @NotNull
    private Integer rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
