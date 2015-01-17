package edu.oakland.images;

/**
 * Created by brandon on 1/17/15.
 */
public class ColorInfo {
    public String color;
    public int value;
    public int size;

    public ColorInfo(int value, int size) {
        this.value = value;
        this.color = ColorUtils.whatColor(value);
        this.size = size;
    }
}
