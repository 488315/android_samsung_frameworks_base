package com.samsung.android.core;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SizeCompatInfo implements Parcelable {
    public static final Parcelable.Creator<SizeCompatInfo> CREATOR =
            new Parcelable.Creator<
                    SizeCompatInfo>() { // from class: com.samsung.android.core.SizeCompatInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SizeCompatInfo createFromParcel(Parcel in) {
                    return new SizeCompatInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SizeCompatInfo[] newArray(int size) {
                    return new SizeCompatInfo[size];
                }
            };
    private final Bundle mBundle;

    public @interface DragMode {
        public static final int DEX_SIZE_COMPAT = 1;
        public static final int DEX_SIZE_COMPAT_ROTATABLE = 2;
        public static final int NON_RESIZABLE = 0;
    }

    private @interface Key {
        public static final String DISPLAY_HEIGHT = "DISPLAY_HEIGHT";
        public static final String DISPLAY_WIDTH = "DISPLAY_WIDTH";
        public static final String DRAG_MODE = "DRAG_MODE";
        public static final String MAX_HEIGHT = "MAX_HEIGHT";
        public static final String MAX_WIDTH = "MAX_WIDTH";
        public static final String MIN_HEIGHT = "MIN_HEIGHT";
        public static final String MIN_WIDTH = "MIN_WIDTH";
        public static final String MODE = "MODE";
    }

    public @interface Mode {
        public static final int DEX_SIZE_COMPAT_MODE = 1;
        public static final int FLIP_LARGE_COVER_SCREEN_SIZE_COMPAT_MODE = 2;
        public static final int NONE = 0;
    }

    public SizeCompatInfo() {
        this.mBundle = new Bundle();
    }

    private SizeCompatInfo(Parcel in) {
        this.mBundle = in.readBundle();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mBundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setMode(int mode) {
        this.mBundle.putInt(Key.MODE, mode);
    }

    public void setDragMode(int dragMode) {
        this.mBundle.putInt(Key.DRAG_MODE, dragMode);
    }

    public void setDisplaySize(int width, int height) {
        this.mBundle.putInt(Key.DISPLAY_WIDTH, width);
        this.mBundle.putInt(Key.DISPLAY_HEIGHT, height);
    }

    public int getDisplayWidth() {
        return this.mBundle.getInt(Key.DISPLAY_WIDTH);
    }

    public int getDisplayHeight() {
        return this.mBundle.getInt(Key.DISPLAY_HEIGHT);
    }

    public void setMinSize(int minWidth, int minHeight) {
        this.mBundle.putInt(Key.MIN_WIDTH, minWidth);
        this.mBundle.putInt(Key.MIN_HEIGHT, minHeight);
    }

    public void setMaxSize(int maxWidth, int maxHeight) {
        this.mBundle.putInt(Key.MAX_WIDTH, maxWidth);
        this.mBundle.putInt(Key.MAX_HEIGHT, maxHeight);
    }

    public int getMinWidth() {
        return this.mBundle.getInt(Key.MIN_WIDTH);
    }

    public int getMinHeight() {
        return this.mBundle.getInt(Key.MIN_HEIGHT);
    }

    public int getMaxWidth() {
        return this.mBundle.getInt(Key.MAX_WIDTH);
    }

    public int getMaxHeight() {
        return this.mBundle.getInt(Key.MAX_HEIGHT);
    }

    public void clear() {
        this.mBundle.clear();
    }

    private static int getMode(SizeCompatInfo sizeCompatInfo) {
        if (sizeCompatInfo == null) {
            return 0;
        }
        return sizeCompatInfo.mBundle.getInt(Key.MODE, 0);
    }

    private static int getDragMode(SizeCompatInfo sizeCompatInfo) {
        if (sizeCompatInfo == null) {
            return 0;
        }
        return sizeCompatInfo.mBundle.getInt(Key.DRAG_MODE, 0);
    }

    public static boolean isDragResizable(SizeCompatInfo sizeCompatInfo) {
        return getDragMode(sizeCompatInfo) != 0;
    }

    public static boolean isDragDexSizeCompat(SizeCompatInfo sizeCompatInfo) {
        int dragMode = getDragMode(sizeCompatInfo);
        return dragMode == 1 || dragMode == 2;
    }

    public static boolean isDragDexSizeCompatRotatable(SizeCompatInfo sizeCompatInfo) {
        return getDragMode(sizeCompatInfo) == 2;
    }

    public static boolean shouldRemoveCaptionInsets(SizeCompatInfo sizeCompatInfo) {
        return getMode(sizeCompatInfo) == 1;
    }

    public static String sizeCompatModeToString(int mode) {
        return Integer.toString(mode);
    }
}
