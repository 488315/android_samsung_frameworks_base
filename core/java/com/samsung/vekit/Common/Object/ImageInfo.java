package com.samsung.vekit.Common.Object;

public class ImageInfo {
    private final int height;
    private final int orientation;
    private final int width;

    public ImageInfo(int width, int height, int orientation) {
        this.width = width;
        this.height = height;
        this.orientation = orientation;
    }

    public ImageInfo getImageInfo() {
        return this;
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
