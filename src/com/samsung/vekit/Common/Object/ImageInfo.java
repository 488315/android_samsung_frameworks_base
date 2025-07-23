package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class ImageInfo {
    private final int height;
    private final int orientation;
    private final int width;

    public ImageInfo getImageInfo() {
        return this;
    }

    public ImageInfo(int i, int i2, int i3) {
        this.width = i;
        this.height = i2;
        this.orientation = i3;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getOrientation() {
        return this.orientation;
    }
}
