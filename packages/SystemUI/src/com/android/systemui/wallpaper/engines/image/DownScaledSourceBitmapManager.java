package com.android.systemui.wallpaper.engines.image;

import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class DownScaledSourceBitmapManager {
    public final HashMap mSourceBitmapSet = new HashMap();

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
