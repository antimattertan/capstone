package com.example.busaninfoapp;

public class User {
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userImageUri;

    public String getUserImageUri() { return userImageUri; }

    public String getUserEmail() { return userEmail; }

    public String getUserName() { return userName; }

    public String getUserPassword() { return userPassword; }

    public void setUserImageUri(String userImageUri) { this.userImageUri = userImageUri; }

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
}
