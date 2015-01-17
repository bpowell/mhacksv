package edu.oakland.images.models;

import java.util.ArrayList;

import edu.oakland.images.processing.ColorInfo;

/**
 * Created by brandon on 1/17/15.
 */
public class Item {
    public String name;
    public ClothingType clothingType;
    public ArticleType articleType;
    public ArrayList<ColorInfo> colors;
    public int resourceId;

    public Item(String name, ClothingType clothingType, ArticleType articleType, ArrayList<ColorInfo> colors, int resourceId) {
        this.name = name;
        this.clothingType = clothingType;
        this.articleType = articleType;
        this.colors = colors;
        this.resourceId = resourceId;
    }

    public Item(String name, ClothingType clothingType, ArticleType articleType, ColorInfo color, int resourceId) {
        ArrayList<ColorInfo> colors = new ArrayList<>();
        colors.add(color);
        this.name = name;
        this.clothingType = clothingType;
        this.articleType = articleType;
        this.colors = colors;
        this.resourceId = resourceId;
    }

    public Item() {
        this.colors = new ArrayList<>();
    }
}
