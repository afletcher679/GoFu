package com.example.gofu;

public class Cart {
    private int Id;
    private String Status;
    private int Size;
    private String Barcode;
    private String Location;
    private int Total_use;


    public Cart(){

    }

    public Cart(int id, String status, int size, String barcode, String location, int total_use){
        this.Id = id;
        this.Status = status;
        this.Size = size;
        this.Barcode = barcode;
        this.Location = location;
        this.Total_use = total_use;
    }

    public int getId() {
        return Id;
    }

    public String getLocation() {
        return Location;
    }

    public int getTotal_use() {
        return Total_use;
    }

    public String getStatus() {
        return Status;
    }

    public int getSize() {
        return Size;
    }

    public String getBarcode() {
        return Barcode;
    }
}