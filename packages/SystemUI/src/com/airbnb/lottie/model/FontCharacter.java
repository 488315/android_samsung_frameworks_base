package com.airbnb.lottie.model;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.airbnb.lottie.model.content.ShapeGroup;
import java.util.List;

/* loaded from: classes.dex */
public class FontCharacter {
    public final char character;
    public final String fontFamily;
    public final List shapes;
    public final String style;
    public final double width;

    public FontCharacter(List<ShapeGroup> list, char c, double d, double d2, String str, String str2) {
        this.shapes = list;
        this.character = c;
        this.width = d2;
        this.style = str;
        this.fontFamily = str2;
    }

    public static int hashFor(char c, String str, String str2) {
        return str2.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(c * 31, 31, str);
    }

    public final int hashCode() {
        return hashFor(this.character, this.fontFamily, this.style);
    }
}
