package com.example.foodmate;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User_Reference {
    public static DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
}