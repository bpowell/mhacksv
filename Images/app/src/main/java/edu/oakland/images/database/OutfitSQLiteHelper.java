package edu.oakland.images.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by brandon on 1/17/15.
 */
public class OutfitSQLiteHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "outfits";
    public static final int DB_VERSION = 1;

    public static final String COL_ID = "id";

    public static final String TABLE_COLORS = "colors";
    public static final String COLORS_COLOR = "color";
    public static final String COLORS_VALUE = "value";
    public static final String CREATE_TABLE_COLORS = "" +
            "CREATE TABLE " + TABLE_COLORS +
            " ( " + COL_ID + " integer primary key autoincrement, " +
            COLORS_COLOR + " text not null, " +
            COLORS_VALUE + " integer not null " +
            ")";

    public static final String TABLE_ITEMS = "items";
    public static final String ITEMS_NAME = "name";
    public static final String ITEMS_ARTICLE_TYPE = "article_type";
    public static final String ITEMS_CLOTHING_TYPE = "clothing_type";
    public static final String CREATE_TABLE_ITEMS = "" +
            "CREATE TABLE " + TABLE_ITEMS +
            " ( " + COL_ID + " integer primary key autoincrement, " +
            ITEMS_NAME + " text not null, " +
            ITEMS_ARTICLE_TYPE + " integer not null, " +
            ITEMS_CLOTHING_TYPE + " integer not null " +
            ")";

    public static final String TABLE_COLORED_ITEMS = "colored_items";
    public static final String COLORED_ITEMS_COLOR = "color";
    public static final String COLORED_ITEMS_ITEM = "item";
    public static final String CREATE_TABLE_COLORED_ITEMS = "" +
            "CREATE TABLE " + TABLE_COLORED_ITEMS +
            " ( " + COL_ID + " integer primary key autoincrement, " +
            COLORED_ITEMS_COLOR + " integer, " +
            COLORED_ITEMS_ITEM + " integer, " +
            " FOREIGN KEY (" + COLORED_ITEMS_COLOR + ") REFERENCES " + TABLE_COLORS + " (" + COL_ID + "), " +
            " FOREIGN KEY (" + COLORED_ITEMS_ITEM + ") REFERENCES " + TABLE_ITEMS + " (" + COL_ID + ") " +
            ")";

    public static final String TABLE_OUTFITS = "outfits";
    public static final String OUTFITS_NAME = "name";
    public static final String CREATE_TABLE_OUTFITS = "" +
            "CREATE TABLE " + TABLE_OUTFITS +
            " ( " + COL_ID + " integer primary key autoincrement, " +
            OUTFITS_NAME + " text not null " +
            ")";

    public static final String TABLE_OUTFITS_COLORED_ITEMS = "outfits_colored_items";
    public static final String OUTFITS_COLORED_ITEMS_COLORED_ITEM = "colored_item";
    public static final String OUTFITS_COLORED_ITEMS_OUTFITS = "outfit";
    public static final String CREATE_TABLE_OUTFITS_COLORED_ITEMS = "" +
            "CREATE TABLE " + TABLE_OUTFITS_COLORED_ITEMS +
            " ( " + COL_ID + " integer primary key autoincrement, " +
            OUTFITS_COLORED_ITEMS_COLORED_ITEM + " integer, " +
            OUTFITS_COLORED_ITEMS_OUTFITS + " integer, " +
            " FOREIGN KEY (" + OUTFITS_COLORED_ITEMS_COLORED_ITEM + ") REFERENCES " + TABLE_COLORED_ITEMS + " (" + COL_ID + "), " +
            " FOREIGN KEY (" + OUTFITS_COLORED_ITEMS_OUTFITS + ") REFERENCES " + TABLE_OUTFITS + " (" + COL_ID + ") " +
            ")";

    public OutfitSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COLORS);
        db.execSQL(CREATE_TABLE_ITEMS);
        db.execSQL(CREATE_TABLE_COLORED_ITEMS);
        db.execSQL(CREATE_TABLE_OUTFITS);
        db.execSQL(CREATE_TABLE_OUTFITS_COLORED_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
