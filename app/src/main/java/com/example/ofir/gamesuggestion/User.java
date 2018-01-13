package com.example.ofir.gamesuggestion;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ofir on 11/30/2017.
 */

public class User
{
    String username, password, firstName, lastName, street, email;
    ArrayList<String> genres = new ArrayList<>();
    boolean admin;

    public User(String username, String password, String firstName, String lastName, String street, String email, boolean admin)
    {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.email = email;
        this.admin = admin;
    }

    public User(String username, String password, boolean admin)
    {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String sayHello()
    {
        return "HELLO WORLD";
    }

    @Override
    public String toString()
    {
        if (admin)
            return "username: " + username + '\n' +
                    "password: " + password + '\n' +
                    "firstName: " + firstName + '\n' +
                    "lastName: " + lastName + '\n' +
                    "street: " + street + '\n' +
                    "email: " + email + '\n' +
                    "admin: true";
        else
            return "username: " + username + '\n' +
                    "password: " + password + '\n' +
                    "firstName: " + firstName + '\n' +
                    "lastName: " + lastName + '\n' +
                    "street: " + street + '\n' +
                    "email: " + email;
    }
}
