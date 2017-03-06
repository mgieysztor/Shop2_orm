package com.offcasoftware.shop2;

import android.app.Application;

import com.offcasoftware.shop2.database.Database;
import com.offcasoftware.shop2.database.DatabaseImpl;
import com.offcasoftware.shop2.model.Product;

import java.util.List;

/**
 * Created by RENT on 2017-03-06.
 */

public class AndroidApplication extends Application {

    private static Database mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = new DatabaseImpl(this);

        ((DatabaseImpl) mDatabase).getWritableDatabase();


    }

    public static Database getDatabase() {

        return mDatabase;
    }
}
