package com.offcasoftware.shop2;

import android.app.Application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.offcasoftware.shop2.database.Database;
import com.offcasoftware.shop2.database.DatabaseImpl;
import com.offcasoftware.shop2.database.DatabaseOrmImpl;
import com.offcasoftware.shop2.model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by RENT on 2017-03-06.
 */

public class AndroidApplication extends Application {

    private static Database mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
//        mDatabase = new DatabaseImpl(this);
        mDatabase = OpenHelperManager.getHelper(this, DatabaseOrmImpl.class);

//        ((DatabaseImpl) mDatabase).getWritableDatabase();
        try {
            ((DatabaseOrmImpl) mDatabase)
                    .getConnectionSource()
                    .getReadWriteConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static Database getDatabase() {

        return mDatabase;
    }
}
