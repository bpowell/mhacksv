package edu.oakland.images.style;

import java.util.ArrayList;
import java.util.Iterator;

import edu.oakland.images.models.Item;
import edu.oakland.images.models.Outfit;

/**
 * Created by brandon on 1/17/15.
 */
public class Rules {
    public static boolean passRuleCheck(Outfit outfit) {
        ArrayList<Item> items = outfit.items;
        int size = items.size();

        //If there is no or one item, nothing to compare against
        if(size==0 || size==1) {
            return true;
        }

        //Check for duplicate items
        for(int i=0; i<size; i++) {
            for(int j=i+1; j<size; j++) {
                if(items.get(i).articleType == items.get(j).articleType) {
                    return false;
                }
            }
        }

        return false;
    }
}
