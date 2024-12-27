package com.android.systemui.wallpaper.engines.image;

import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DownScaledSourceBitmapManager {
    public final HashMap mSourceBitmapSet = new HashMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Item {
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
