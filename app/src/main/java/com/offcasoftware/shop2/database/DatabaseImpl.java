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
    private final static int VERSION = 1;
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
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        List<Product> products = getProducts(db);
        db.execSQL(DROP_TODO_TABLE);
        onCreate(db);
        saveProducts(products, db);
    }

    private static final String DB_CREATE_TODO_TABLE =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    COLUMN_PRODUCTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PRODUCTS_NAME + " TEXT NOT NULL UNIQUE," +
                    COLUMN_PRODUCTS_PRICE + " INTEGER DEFAULT 0," +
                    COLUMN_PRODUCTS_IMAGENAME + " TEXT DEFAULT dom2);";

    private static final String DROP_TODO_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_PRODUCTS;

    private static final String ADD_COLUMN =
            "ALTER TABLE " + TABLE_PRODUCTS + " ADD test TEXT";


    @Override
    public void saveProduct(String name, int price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_PRODUCTS_ID, 12); usunięte, bo id jest unikalny
        contentValues.put(COLUMN_PRODUCTS_NAME, name);
        contentValues.put(COLUMN_PRODUCTS_PRICE, price);

        long id = db.insertOrThrow(TABLE_PRODUCTS, null, contentValues);
    }

    @Override
    public void saveProducts(final List<Product> products) {
        SQLiteDatabase db = getWritableDatabase();
        saveProducts(products, db);
    }

    private void saveProducts(List<Product> products, SQLiteDatabase db) {


        long time1 = Calendar.getInstance().getTimeInMillis();

        for (int i = 0; i < products.size(); i++) {
            ContentValues contentValues = new ContentValues();
//            contentValues.put(COLUMN_PRODUCTS_ID, products.get(i).getId()); usunięte, bo id jest unikalny
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
        SQLiteDatabase db = getReadableDatabase();
        return getProducts(db);
    }

    private List<Product> getProducts(SQLiteDatabase db) {
        List<Product> products = new ArrayList<>();


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


    public Product getProduct(int productId) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS, null, "id = ?",
                new String[]{String.valueOf(productId)}, null, null, null, null);
        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        int price = cursor.getInt(2);
        String imageName = cursor.getString(3);

        Product product = new Product(id, name, price, imageName);

        cursor.close();


        return product;
    }
}
