package com.offcasoftware.shop2.model;

/**
 * @author maciej.pachciarek on 2017-02-18.
 */

public class Product {

    private final int mId;
    private String mName;
    private int mPrice;
    private String mImage; //mImageResId;
    public Product(final int id, final String name,
            final int price, final String imageName) {
        mId = id;
        mName = name;
        mPrice = price;
        mImage = imageName;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getPrice() {
        return mPrice;
    }

    public String getImageResId() {
        return mImage;
    }
}
