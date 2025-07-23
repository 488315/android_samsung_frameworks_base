package com.android.internal.widget.remotecompose.core.operations.layout.measure;

/* loaded from: classes6.dex */
public class Size {
    float mHeight;
    float mWidth;

    public Size(float f, float f2) {
        this.mWidth = f;
        this.mHeight = f2;
    }

    public void setWidth(float f) {
        this.mWidth = f;
    }

    public void setHeight(float f) {
        this.mHeight = f;
    }

    public float getWidth() {
        return this.mWidth;
    }

    public float getHeight() {
        return this.mHeight;
    }
}
