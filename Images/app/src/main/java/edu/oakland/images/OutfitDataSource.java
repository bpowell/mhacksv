package edu.oakland.images;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by brandon on 1/17/15.
 */
public class OutfitDataSource {
    private SQLiteDatabase database;
    private OutfitSQLiteHelper helper;

    public OutfitDataSource(Context context) {
        helper = new OutfitSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }
}
