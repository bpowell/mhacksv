package edu.oakland.images.models;

import java.util.ArrayList;

/**
 * Created by brandon on 1/17/15.
 */
public class Outfit {
    public ArrayList<Item> items;

    public Outfit() {
        items = new ArrayList<>();
    }

    public boolean addItem(Item item) {
        //loop over all current items to make sure you don't have the
        //  same type already. Nobody wants to wear two pants!
        for(Item i : items) {
            if(item.articleType == i.articleType) {
                return false;
            }
        }

        items.add(item);
        return true;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void removeItem(ArticleType item) {
       for(int i=0; i<items.size(); i++) {
           if(item == items.get(i).articleType) {
               items.remove(i);
               return;
           }
       }
    }
}
