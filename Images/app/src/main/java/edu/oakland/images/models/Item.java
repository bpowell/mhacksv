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
    public String resourcePath;

    public Item(String name, ClothingType clothingType, ArticleType articleType, ArrayList<ColorInfo> colors, String resourcePath) {
        this.name = name;
        this.clothingType = clothingType;
        this.articleType = articleType;
        this.colors = colors;
        this.resourcePath = resourcePath;
    }

    public Item(String name, ClothingType clothingType, ArticleType articleType, ColorInfo color, String resourcePath) {
        ArrayList<ColorInfo> colors = new ArrayList<>();
        colors.add(color);
        this.name = name;
        this.clothingType = clothingType;
        this.articleType = articleType;
        this.colors = colors;
        this.resourcePath = resourcePath;
    }

    public Item() {
        this.colors = new ArrayList<>();
    }
}
