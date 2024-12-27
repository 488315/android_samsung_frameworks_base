package com.samsung.android.sume.core.format;

import android.graphics.Rect;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface Shape extends Serializable, Parcelable, Copyable<Shape>, Comparable<Shape> {
    public static final int TYPE_NHWC = 2;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_NWHC = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {}

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

    static Shape of(int size) {
        return of(-1, 1, size, -1);
    }

    static Shape of(int rows, int cols) {
        return of(-1, rows, cols, -1);
    }

    static Shape of(int batch, int rows, int cols, int channels) {
        return new StapleShape(mutableOf(batch, rows, cols, channels));
    }

    static MutableShape mutableOf() {
        return mutableOf(-1, 0, 0, -1);
    }

    static MutableShape mutableOf(int batch, int rows, int cols, int channels) {
        return new StapleMutableShape(batch, rows, cols, channels);
    }

    static Rect rectOf(int width, int height) {
        return new Rect(0, 0, width, height);
    }

    static Rect rectOf(int left, int top, int right, int bottom) {
        return new Rect(left, top, right, bottom);
    }
}
