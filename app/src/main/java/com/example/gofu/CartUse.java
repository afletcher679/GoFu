package com.example.gofu;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

public class CartUse {

    private String documentId;
    private String Checked_in_time;
    private String Checked_out_time;
    private String Checked_out_date;
    private DocumentReference Cart_id;
    private String User;


    public void setCart_id(DocumentReference cart_id) {
        Cart_id = cart_id;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getChecked_in_time() {
        return Checked_in_time;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setChecked_in_time(String checked_in_time) {
        Checked_in_time = checked_in_time;
    }

    public String getChecked_out_time() {
        return Checked_out_time;
    }

    public void setChecked_out_time(String checked_out_time) {
        Checked_out_time = checked_out_time;
    }

    public String getChecked_out_date() {
        return Checked_out_date;
    }

    public void setChecked_out_date(String checked_out_date) {
        Checked_out_date = checked_out_date;
    }


    public CartUse() {
    }
}
