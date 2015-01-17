package edu.oakland.images;

/**
 * Created by brandon on 1/17/15.
 */
public class Item {
    public String name;
    public ClothingType clothingType;
    public ArticleType articleType;

    public Item(String name, ClothingType clothingType, ArticleType articleType) {
        this.name = name;
        this.clothingType = clothingType;
        this.articleType = articleType;
    }

    public Item() {}
}
