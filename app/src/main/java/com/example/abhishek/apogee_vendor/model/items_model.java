package com.example.abhishek.apogee_vendor.model;

public class items_model {

    String itemVal , itemName;

    public items_model(String itemName,String itemVal)
    {
        this.itemName = itemName;
        this.itemVal=itemVal;
    }

    public String getItemName()
    {
        return itemName;
    }
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    /*public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }*/

    public String getItemVal() {
        return itemVal;
    }

    public void setItemVal(String itemVal) {
        this.itemVal = itemVal;
    }


}
