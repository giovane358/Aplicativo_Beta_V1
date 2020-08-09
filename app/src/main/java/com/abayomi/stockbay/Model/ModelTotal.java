package com.abayomi.stockbay.Model;

public class ModelTotal {

    String id;
    Double Total;

    public ModelTotal(String id, Double total) {
        this.id = id;
        Total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }
}