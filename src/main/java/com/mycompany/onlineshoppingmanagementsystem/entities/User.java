/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Misbahul Haque
 */
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10 , name = "user_id")
    private int userId;
    
    @Column(length = 100 , name = "user_name")
    private String userName;
    
    @Column(length = 100 , name = "user_email")
    private String userEmail;
    
    @Column(name = "user_password")
    private String userPassword;
    
    @Column(length = 13 , name = "user_phone")
    private String userPhone;
    
    @Column(length = 100 , name = "user_pic")
    private String userPic;
    
    @Column(length = 1500 , name = "user_address")
    private String userAddress;
    
    @Column(name = "user_cart_count")
    private int userCartCount;
    
    @Column(name = "user_type")
    private String userType;
    
    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();
    
    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<Cart> carts = new ArrayList<>();
    
    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<Orders> orders = new ArrayList<>();

    public User(int userId, String userName, String userEmail, String userPassword, String userPhone, String userPic, String userAddress, int userCartCount, String userType, List<Address> addresses, List<Cart> carts) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userPic = userPic;
        this.userAddress = userAddress;
        this.userCartCount = userCartCount;
        this.userType = userType;
        this.addresses = addresses;
        this.carts = carts;
    }

    public User(String userName, String userEmail, String userPassword, String userPhone, String userPic, String userAddress, int userCartCount, String userType) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userPic = userPic;
        this.userAddress = userAddress;
        this.userCartCount = userCartCount;
        this.userType = userType;
    }
    
    public User(String userName, String userEmail, String userPassword, String userPhone, String userPic, String userAddress, int userCartCount, String userType, List<Address> addresses, List<Cart> carts, List<Orders> orders) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userPic = userPic;
        this.userAddress = userAddress;
        this.userCartCount = userCartCount;
        this.userType = userType;
        this.addresses = addresses;
        this.carts = carts;
        this.orders = orders;
    }
    
    

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public int getUserCartCount() {
        return userCartCount;
    }

    public void setUserCartCount(int userCartCount) {
        this.userCartCount = userCartCount;
    }
    
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", userPassword=" + userPassword + ", userPhone=" + userPhone + ", userPic=" + userPic + ", userAddress=" + userAddress + ", userCartCount=" + userCartCount + ", userType=" + userType + '}';
    }
    
}
