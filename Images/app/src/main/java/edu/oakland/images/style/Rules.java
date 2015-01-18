package edu.oakland.images.style;

import java.util.ArrayList;
import java.util.Iterator;

import edu.oakland.images.models.ArticleType;
import edu.oakland.images.models.Item;
import edu.oakland.images.models.Outfit;
import edu.oakland.images.processing.ColorInfo;
import edu.oakland.images.processing.ImageUtils;

/**
 * Created by brandon on 1/17/15.
 */
public class Rules {
    public static boolean passRuleCheck(Item item1, Item item2) {
        if(item1.articleType == item2.articleType) {
            return false;
        }

        ArrayList<ColorInfo> colorsItem1 = new ArrayList<>();
        for(ColorInfo c : item1.colors) {
            colorsItem1.add(ImageUtils.similarColor(String.format("%06X", c.value)));
        }

        ArrayList<ColorInfo> colorsItem2 = new ArrayList<>();
        for(ColorInfo c: item2.colors) {
            colorsItem2.add(ImageUtils.similarColor(String.format("%06X", c.value)));
        }

        //belt+shoe check
        if( (item1.articleType==ArticleType.BELTS || item2.articleType==ArticleType.BELTS) &&
                (item1.articleType==ArticleType.SHOES || item2.articleType==ArticleType.SHOES) ) {
            boolean shoebelt = false;
            for (ColorInfo c1 : colorsItem1) {
                for (ColorInfo c2 : colorsItem2) {
                    if (c1.value == c2.value) {
                        shoebelt = true;
                    }
                }
            }
            if (!shoebelt) {
                return false;
            }
        }

        return true;
    }
}
