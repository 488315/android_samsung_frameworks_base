package com.samsung.vekit.Common.Object;

/* loaded from: classes6.dex */
public class ContentReferenceInfo {
    private int bitrate;
    private int height;
    private int width;

    public ContentReferenceInfo() {
        this.width = 0;
        this.height = 0;
        this.bitrate = 0;
    }

    public ContentReferenceInfo(int i, int i2, int i3) {
        this.width = i;
        this.height = i2;
        this.bitrate = i3;
    }

    public int getBitrate() {
        return this.bitrate;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setBitrate(int i) {
        this.bitrate = i;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public void setHeight(int i) {
        this.height = i;
    }
}
