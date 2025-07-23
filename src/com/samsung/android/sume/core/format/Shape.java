package com.samsung.android.sume.core.format;

import android.graphics.Rect;
import android.os.Parcelable;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes6.dex */
public interface Shape extends Serializable, Parcelable, Copyable<Shape>, Comparable<Shape> {
    public static final int TYPE_NHWC = 2;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_NWHC = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    int getBatch();

    int getChannels();

    int getCols();

    int getDimension();

    int getRows();

    int getTotal();

    int[] toArray(int i);

    <V extends MutableShape> V toMutableShape();

    default boolean isEmpty() {
        return getTotal() == 0;
    }

    static Shape of(int i) {
        return of(-1, 1, i, -1);
    }

    static Shape of(int i, int i2) {
        return of(-1, i, i2, -1);
    }

    static Shape of(int i, int i2, int i3, int i4) {
        return new StapleShape(mutableOf(i, i2, i3, i4));
    }

    static MutableShape mutableOf() {
        return mutableOf(-1, 0, 0, -1);
    }

    static MutableShape mutableOf(int i, int i2, int i3, int i4) {
        return new StapleMutableShape(i, i2, i3, i4);
    }

    static Rect rectOf(int i, int i2) {
        return new Rect(0, 0, i, i2);
    }

    static Rect rectOf(int i, int i2, int i3, int i4) {
        return new Rect(i, i2, i3, i4);
    }
}
