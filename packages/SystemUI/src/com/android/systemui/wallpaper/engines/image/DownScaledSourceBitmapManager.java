package com.android.systemui.wallpaper.engines.image;

import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DownScaledSourceBitmapManager {
    public final HashMap mSourceBitmapSet = new HashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Item {
        public final Bitmap mBitmap;
        public final ArrayList mCropRects;
        public final float mScale;

        public Item(int i, Bitmap bitmap, float f, ArrayList<Rect> arrayList) {
            this.mBitmap = bitmap;
            this.mScale = f;
            this.mCropRects = arrayList;
        }
    }
}
