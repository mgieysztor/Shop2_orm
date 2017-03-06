package com.offcasoftware.shop2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import com.offcasoftware.shop2.model.Product;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by RENT on 2017-03-06.
 */

public class DatabaseImpl extends SQLiteOpenHelper implements Database {

    private final static String NAME = "database.db";
    private final static int VERSION = 3;
    private final static String TABLE_PRODUCTS = "products";
    private final static String COLUMN_PRODUCTS_ID = "id";
    private final static String COLUMN_PRODUCTS_NAME = "name";
    private final static String COLUMN_PRODUCTS_PRICE = "price";
    private final static String COLUMN_PRODUCTS_IMAGENAME = "imagename";


    public DatabaseImpl(Context context) {
        super(context, NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_TODO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TODO_TABLE);
        onCreate(db);
    }

    private static final String DB_CREATE_TODO_TABLE =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    COLUMN_PRODUCTS_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_PRODUCTS_NAME + " TEXT NOT NULL," +
                    COLUMN_PRODUCTS_PRICE + " INTEGER DEFAULT 0," +
                    COLUMN_PRODUCTS_IMAGENAME + " TEXT);";

    private static final String DROP_TODO_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_PRODUCTS;

    private static final String ADD_COLUMN =
            "ALTER TABLE " + TABLE_PRODUCTS + " ADD test TEXT";


    @Override
    public void saveProducts(List<Product> products) {
        SQLiteDatabase db = getWritableDatabase();

        long time1 = Calendar.getInstance().getTimeInMillis();

        for (int i = 0; i < products.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PRODUCTS_ID, products.get(i).getId());
            contentValues.put(COLUMN_PRODUCTS_NAME, products.get(i).getName());
            contentValues.put(COLUMN_PRODUCTS_PRICE, products.get(i).getPrice());
            contentValues.put(COLUMN_PRODUCTS_IMAGENAME, products.get(i).getImageResId());
            long id = db.insertOrThrow(TABLE_PRODUCTS, null, contentValues);
            Log.i("database", "" + id);
        }

        Log.i("time1", "" + (Calendar.getInstance().getTimeInMillis() - time1));

//        long time1 = Calendar.getInstance().getTimeInMillis();
//
//        for (int i = 1000; i < 2000; i++) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(COLUMN_PRODUCTS_ID, i);
//            contentValues.put(COLUMN_PRODUCTS_NAME, "domeczek" + i);
//            contentValues.put(COLUMN_PRODUCTS_PRICE, 100*i);
//            long id = db.insert(TABLE_PRODUCTS, null, contentValues);
//        }
//
//        Log.i("time1", ""+ (Calendar.getInstance().getTimeInMillis() - time1));
//
//        long time2 = Calendar.getInstance().getTimeInMillis();
//        try {
//            db.beginTransaction();
//            for (int i = 3000; i < 4000; i++) {
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(COLUMN_PRODUCTS_ID, i);
//                contentValues.put(COLUMN_PRODUCTS_NAME, "domeczek" + i);
//                contentValues.put(COLUMN_PRODUCTS_PRICE, 100*i);
//                long id = db.insert(TABLE_PRODUCTS, null, contentValues);
//            }
//            db.setTransactionSuccessful();
//        } finally {
//            db.endTransaction();
//        }
//        Log.i("time2", ""+ (Calendar.getInstance().getTimeInMillis() - time2));
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null, null);
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_PRODUCTS_NAME);
            String name = cursor.getString(nameColumnIndex);

            int price = cursor.getInt(2);
            String imageName = cursor.getString(3);

            Product product = new Product(id, name, price, imageName);
            products.add(product);

        } while (cursor.moveToNext());
        cursor.close();

        return products;
    }
}
