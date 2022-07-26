/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Misbahul Haque
 */
@Entity
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10, name = "address_id")
    private int addressId;
    
    @Column(length = 200, name = "locality")
    private String locality;
    
    @Column(length = 10, name = "pincode")
    private int pincode;
    
    @Column(length = 100, name = "landmark")
    private String landmark;
    
    @Column(length = 50, name = "city")
    private String city;
    
    @Column(length = 50, name = "state")
    private String state;
    
    @Column(length = 50, name = "country")
    private String country;
      
    @ManyToOne
    private User user;
    
    @OneToMany(mappedBy = "address" , fetch = FetchType.LAZY)
    private List<Orders> orders;

    public Address(int addressId, String locality, int pincode, String landmark, String city, String state, String country, User user) {
        this.addressId = addressId;
        this.locality = locality;
        this.pincode = pincode;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.country = country;
        this.user = user;
    }

    public Address(String locality, int pincode, String landmark, String city, String state, String country, User user, List<Orders> orders) {
        this.locality = locality;
        this.pincode = pincode;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.country = country;
        this.user = user;
        this.orders = orders;
    }

    public Address() {
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

}