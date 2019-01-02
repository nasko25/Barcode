package com.example.atanaspashov.barcode;

public class DataProvider {
    private String material, times_recycled;

    public DataProvider(String material, String times_recycled) {
        this.material = material;
        this.times_recycled = times_recycled;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTimes_recycled() {
        return times_recycled;
    }

    public void setTimes_recycled(String times_recycled) {
        this.times_recycled = times_recycled;
    }
}
