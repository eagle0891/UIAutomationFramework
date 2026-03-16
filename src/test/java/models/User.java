package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// This annotation prevents the test from crashing if the API sends extra fields we don't need
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Nested Inner Classes (or separate files)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private String city;
        private String zipcode;
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Company {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}