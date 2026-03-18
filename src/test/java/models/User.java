package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data; // Import Lombok

@Data // This generates Getters, Setters, equals, hashCode, and toString automatically
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private Address address;
    private Company company;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private String city;
        private String zipcode;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Company {
        private String name;
    }
}