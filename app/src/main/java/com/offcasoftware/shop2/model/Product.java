package com.offcasoftware.shop2.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * @author maciej.pachciarek on 2017-02-18.
 */

public class Product {

    static final String TABLE_NAME = "products";

    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;

    @DatabaseField (columnName = "name", canBeNull = false,unique = true)
    private String mName;

    @DatabaseField (columnName = "price", canBeNull = false)
    private int mPrice;

    @DatabaseField (columnName = "imageName", defaultValue = "dom2")
    private String mImage;

    public Product() {

    }

//    private final int mId;
//    private String mName;
//    private int mPrice;
//    private String mImage; //mImageResId;
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
