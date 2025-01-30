package com.airbnb.lottie.model;

import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.airbnb.lottie.model.content.ShapeGroup;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FontCharacter {
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

    public final int hashCode() {
        return this.style.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(this.fontFamily, this.character * 31, 31);
    }
}
