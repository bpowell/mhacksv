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

    public Item(String name, ClothingType clothingType, ArticleType articleType, ArrayList<ColorInfo> colors) {
        this.name = name;
        this.clothingType = clothingType;
        this.articleType = articleType;
        this.colors = colors;
    }

    public Item(String name, ClothingType clothingType, ArticleType articleType, ColorInfo color) {
        ArrayList<ColorInfo> colors = new ArrayList<>();
        colors.add(color);
        this.name = name;
        this.clothingType = clothingType;
        this.articleType = articleType;
        this.colors = colors;
    }

    public Item() {
        this.colors = new ArrayList<>();
    }
}
