package com.airbnb.lottie.model;

import com.airbnb.lottie.model.content.ShapeGroup;
import java.util.List;

/* loaded from: classes.dex */
public final class FontCharacter {
    private final char character;
    private final String fontFamily;
    private final List<ShapeGroup> shapes;
    private final String style;
    private final double width;

    public FontCharacter(List list, char c, double d, String str, String str2) {
        this.shapes = list;
        this.character = c;
        this.width = d;
        this.style = str;
        this.fontFamily = str2;
    }

    public static int hashFor(char c, String str, String str2) {
        return str2.hashCode() + ((str.hashCode() + ((0 + c) * 31)) * 31);
    }

    public final List<ShapeGroup> getShapes() {
        return this.shapes;
    }

    public final double getWidth() {
        return this.width;
    }

    public final int hashCode() {
        return hashFor(this.character, this.fontFamily, this.style);
    }
}
