package com.example.abhishek.apogee_vendor.model;

public class menu_model {
    String menuname;
    String price;
    Boolean availablity;
    String item_id;

    public menu_model(String menuname , String price , Boolean availablity,String item_id)
    {
        this.menuname = menuname;
        this.price=price;
        this.availablity=availablity;
        this.item_id =item_id;
    }
    public String getItem_id(){return  item_id;}
    public void setItem_id(String item_id){this.item_id=item_id;}

    public String getMenuname() {
        return menuname;
    }
    public Boolean getAvailablity()
    {
        return availablity;
    }
    public  void setAvailablity(Boolean availablity)
    {
        this.availablity = availablity;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
