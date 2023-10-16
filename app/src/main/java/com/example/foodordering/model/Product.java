package com.example.foodordering.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    // this is construct by Parcelable
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            // here u can return the new constructor that takes Parcel
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    private String productId;
    private int imageUrlId;
    private String name;
    private float price;

    public Product(String productId, int imageUrlId, String name, float price) {
        this.productId = productId;
        this.imageUrlId = imageUrlId;
        this.name = name;
        this.price = price;
    }

    protected Product(Parcel in) {
        productId = in.readString();
        imageUrlId = in.readInt();
        name = in.readString();
        price = in.readFloat();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getImageUrlId() {
        return imageUrlId;
    }

    public void setImageUrlId(int imageUrlId) {
        this.imageUrlId = imageUrlId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // here the order of the constructor that takes parcel is same as below
        parcel.writeString(productId);
        parcel.writeInt(imageUrlId);
        parcel.writeString(name);
        parcel.writeFloat(price);
    }
}
