package edu.oakland.images.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public long insertColor(ColorInfo color) {
        ContentValues values = new ContentValues();
        values.put(OutfitSQLiteHelper.COLORS_VALUE, color.value);
        return database.insert(OutfitSQLiteHelper.TABLE_COLORS, null, values);
    }

    public ArrayList<Long> insertColors(ArrayList<ColorInfo> colors) {
        ArrayList<Long> ids = new ArrayList<>();
        for(ColorInfo color : colors) {
            ids.add(insertColor(color));
        }

        return ids;
    }

    public long insertItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(OutfitSQLiteHelper.ITEMS_ARTICLE_TYPE, item.articleType.ordinal());
        values.put(OutfitSQLiteHelper.ITEMS_CLOTHING_TYPE, item.clothingType.ordinal());
        values.put(OutfitSQLiteHelper.ITEMS_NAME, item.name);
        values.put(OutfitSQLiteHelper.ITEMS_RESOURCE_PATH, item.resourcePath);
        long id = database.insert(OutfitSQLiteHelper.TABLE_ITEMS, null, values);

        ArrayList<Long> colorIds = insertColors(item.colors);
        for(Long cid : colorIds){
            values = new ContentValues();
            values.put(OutfitSQLiteHelper.COLORED_ITEMS_COLOR, cid);
            values.put(OutfitSQLiteHelper.COLORED_ITEMS_ITEM, id);

            database.insert(OutfitSQLiteHelper.TABLE_COLORED_ITEMS, null, values);
        }

        return id;
    }

    public ArrayList<Long> insertItems(ArrayList<Item> items) {
        ArrayList<Long> ids = new ArrayList<>();
        for(Item item : items) {
            ids.add(insertItem(item));
        }

        return ids;
    }

    public void insertOutfit(Outfit outfit) {
        ArrayList<Long> itemIds = insertItems(outfit.items);

        ContentValues values = new ContentValues();
        values.put(OutfitSQLiteHelper.OUTFITS_NAME, outfit.name);
        long id = database.insert(OutfitSQLiteHelper.TABLE_OUTFITS, null, values);

        for(Long itemId : itemIds) {
            values = new ContentValues();
            values.put(OutfitSQLiteHelper.OUTFITS_COLORED_ITEMS_COLORED_ITEM, itemId);
            values.put(OutfitSQLiteHelper.OUTFITS_COLORED_ITEMS_OUTFITS, id);
            database.insert(OutfitSQLiteHelper.TABLE_OUTFITS_COLORED_ITEMS, null, values);
        }
    }

    public ColorInfo getColor(int id) {
        Cursor cursor = database.rawQuery(
                "select " + OutfitSQLiteHelper.COLORS_VALUE + " from " + OutfitSQLiteHelper.TABLE_COLORS + " where id = " + id, null);
        cursor.moveToFirst();
        ColorInfo color = new ColorInfo(cursor.getInt(0), 0);
        cursor.close();

        return color;
    }

    public ArrayList<ColorInfo> getColors(ArrayList<Integer> cids) {
        ArrayList<ColorInfo> colors = new ArrayList<>();
        for(Integer id : cids) {
            colors.add(getColor(id));
        }

        return colors;
    }

    public Item getItem(int id) {
        Cursor cursor = database.rawQuery(
                "select " + OutfitSQLiteHelper.ITEMS_ARTICLE_TYPE + ", " +
                        OutfitSQLiteHelper.ITEMS_CLOTHING_TYPE + ", " +
                        OutfitSQLiteHelper.ITEMS_NAME + ", " +
                        OutfitSQLiteHelper.COL_ID + ", " +
                        OutfitSQLiteHelper.ITEMS_RESOURCE_PATH +
                        " from " + OutfitSQLiteHelper.TABLE_ITEMS +
                        " where " + OutfitSQLiteHelper.COL_ID + " = " + id
                , null
        );

        cursor.moveToFirst();
        Item item = new Item();
        item.articleType = (ArticleType.values()[cursor.getInt(0)]);
        item.clothingType = (ClothingType.values()[cursor.getInt(1)]);
        item.resourcePath = cursor.getString(4);
        cursor.close();

        cursor = database.rawQuery(
                "select " + OutfitSQLiteHelper.COLORED_ITEMS_COLOR +
                        " from " + OutfitSQLiteHelper.TABLE_COLORED_ITEMS +
                        " where " + OutfitSQLiteHelper.COLORED_ITEMS_ITEM +
                        " = " + id
                , null
        );

        ArrayList<Integer> colorIds = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            colorIds.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();

        item.colors = getColors(colorIds);

        return item;
    }

    public ArrayList<Item> getItems(ArrayList<Integer> itemIds) {
        ArrayList<Item> items = new ArrayList<>();
        for(Integer item : itemIds) {
            items.add(getItem(item));
        }

        return items;
    }

    public Outfit getOutfit(int id) {
        Cursor cursor = database.rawQuery(
                "select " + OutfitSQLiteHelper.OUTFITS_NAME +
                        " from " + OutfitSQLiteHelper.TABLE_OUTFITS +
                        " where " + OutfitSQLiteHelper.COL_ID + " = " + id
                , null
        );

        cursor.moveToFirst();
        Outfit outfit = new Outfit();
        outfit.name = cursor.getString(1);

        cursor = database.rawQuery(
                "select " + OutfitSQLiteHelper.OUTFITS_COLORED_ITEMS_COLORED_ITEM +
                        " from " + OutfitSQLiteHelper.TABLE_OUTFITS_COLORED_ITEMS +
                        " where " + OutfitSQLiteHelper.OUTFITS_COLORED_ITEMS_OUTFITS + " = " + id
                , null
        );

        ArrayList<Integer> itemIds = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            itemIds.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();

        outfit.items = getItems(itemIds);

        return outfit;
    }

    public ArrayList<Item> getItemsByClothingType(ClothingType type) {
        Cursor cursor = database.rawQuery(
                "select " + OutfitSQLiteHelper.COL_ID +
                        " from " + OutfitSQLiteHelper.TABLE_ITEMS +
                        " where " + OutfitSQLiteHelper.ITEMS_CLOTHING_TYPE + " = " + type.ordinal()
                , null
        );

        ArrayList<Integer> itemIds = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            itemIds.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();

        return getItems(itemIds);
    }

    public ArrayList<Item> getItemsByArticleType(ArticleType type) {
        Cursor cursor = database.rawQuery(
                "select " + OutfitSQLiteHelper.COL_ID +
                        " from " + OutfitSQLiteHelper.TABLE_ITEMS +
                        " where " + OutfitSQLiteHelper.ITEMS_ARTICLE_TYPE + " = " + type.ordinal()
                , null
        );

        ArrayList<Integer> itemIds = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            itemIds.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();

        return getItems(itemIds);
    }

    //WARNING
    //Right now requires an excat match!!!!
    public ArrayList<Item> getItemsByColor(ColorInfo color) {
        Cursor cursor = database.rawQuery(
                "select " + OutfitSQLiteHelper.COL_ID +
                        " from " + OutfitSQLiteHelper.TABLE_COLORED_ITEMS +
                        " where " + OutfitSQLiteHelper.COLORED_ITEMS_COLOR + " = " + color.value
                , null
        );

        ArrayList<Integer> itemIds = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            itemIds.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();

        return getItems(itemIds);
    }

    public Item getItemByName(String name) {
        Cursor cursor = database.rawQuery(
                "select " + OutfitSQLiteHelper.COL_ID +
                        " from " + OutfitSQLiteHelper.TABLE_ITEMS +
                        " where " + OutfitSQLiteHelper.ITEMS_NAME + " = " + name
                , null
        );

        cursor.moveToFirst();
        Item item = getItem(cursor.getInt(0));
        cursor.close();

        return item;
    }
}
