package com.example.smallbasket.model;

public class Methods
{
 String method_name;
 int image;

 public Methods(){}

    public Methods(String method_name, int image) {
        this.method_name = method_name;
        this.image = image;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String toString()
    {
        return "\n "+method_name;
    }
}
