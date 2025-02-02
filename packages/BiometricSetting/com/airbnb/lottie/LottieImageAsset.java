package com.airbnb.lottie;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public final class LottieImageAsset {
    private Bitmap bitmap;
    private final String fileName;
    private final int height;

    /* renamed from: id */
    private final String f18id;
    private final int width;

    public LottieImageAsset(int i, int i2, String str, String str2) {
        this.width = i;
        this.height = i2;
        this.f18id = str;
        this.fileName = str2;
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final String getFileName() {
        return this.fileName;
    }

    public final int getHeight() {
        return this.height;
    }

    public final String getId() {
        return this.f18id;
    }

    public final int getWidth() {
        return this.width;
    }

    public final void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
