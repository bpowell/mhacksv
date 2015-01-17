package edu.oakland.images.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.oakland.images.models.ArticleType;
import edu.oakland.images.models.ClothingType;
import edu.oakland.images.models.Item;
import edu.oakland.images.models.Outfit;
import edu.oakland.images.processing.ColorInfo;

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
