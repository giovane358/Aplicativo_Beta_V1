package com.abayomi.stockbay.Model;

public class ModelTotal {
    String id, Total;

    public ModelTotal(String id, String total) {
        this.id = id;
        Total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}