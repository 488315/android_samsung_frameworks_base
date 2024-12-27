package com.android.systemui.wallpapers;

import android.app.WallpaperColors;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Trace;
import android.util.ArraySet;
import android.util.Log;
import android.util.MathUtils;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public final class WallpaperLocalColorExtractor {
    public static final RectF LOCAL_COLOR_BOUNDS = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    static final int MINI_BITMAP_MAX_AREA = 12544;
    public final Object mLock;
    public final Executor mLongExecutor;
    public Bitmap mMiniBitmap;
    public final float mWallpaperDimAmount;
    public final WallpaperLocalColorExtractorCallback mWallpaperLocalColorExtractorCallback;
    public int mDisplayWidth = -1;
    public int mDisplayHeight = -1;
    public int mPages = -1;
    public int mBitmapWidth = -1;
    public int mBitmapHeight = -1;
    public final List mPendingRegions = new ArrayList();
    public final Set mProcessedRegions = new ArraySet();

    public interface WallpaperLocalColorExtractorCallback {
        void onActivated();

        void onColorsProcessed(List list, List list2);

        void onDeactivated();

        void onMiniBitmapUpdated();
    }

    public WallpaperLocalColorExtractor(Executor executor, Object obj, WallpaperLocalColorExtractorCallback wallpaperLocalColorExtractorCallback) {
        this.mLongExecutor = executor;
        this.mLock = obj;
        this.mWallpaperLocalColorExtractorCallback = wallpaperLocalColorExtractorCallback;
    }

    public Bitmap createMiniBitmap(Bitmap bitmap, int i, int i2) {
        return Bitmap.createScaledBitmap(bitmap, i, i2, false);
    }

    public WallpaperColors getLocalWallpaperColors(Rect rect) {
        Assert.isNotMainThread();
        return WallpaperColors.fromBitmap(Bitmap.createBitmap(this.mMiniBitmap, rect.left, rect.top, rect.width(), rect.height()));
    }

    public WallpaperColors getWallpaperColors(Bitmap bitmap, float f) {
        return WallpaperColors.fromBitmap(bitmap, f);
    }

    public final boolean isActive() {
        return ((ArraySet) this.mProcessedRegions).size() + ((ArrayList) this.mPendingRegions).size() > 0;
    }

    public final void processLocalColorsInternal() {
        RectF rectF;
        Bitmap bitmap = this.mMiniBitmap;
        if (bitmap == null || bitmap.isRecycled() || this.mDisplayWidth < 0 || this.mDisplayHeight < 0 || this.mPages < 0) {
            return;
        }
        Trace.beginSection("WallpaperLocalColorExtractor#processColorsInternal");
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < ((ArrayList) this.mPendingRegions).size(); i++) {
            RectF rectF2 = (RectF) ((ArrayList) this.mPendingRegions).get(i);
            float f = 1.0f / this.mPages;
            float f2 = (rectF2.left % f) / f;
            float f3 = (rectF2.right % f) / f;
            int floor = (int) Math.floor(rectF2.centerX() / f);
            WallpaperColors wallpaperColors = null;
            if (this.mDisplayWidth <= 0 || this.mDisplayHeight <= 0) {
                Log.e("WallpaperLocalColorExtractor", "Trying to extract colors with invalid display dimensions");
                rectF = null;
            } else {
                rectF = new RectF();
                rectF.bottom = rectF2.bottom;
                rectF.top = rectF2.top;
                float min = this.mDisplayWidth * Math.min(this.mBitmapHeight / this.mDisplayHeight, 1.0f);
                int i2 = this.mBitmapWidth;
                float min2 = Math.min(1.0f, i2 > 0 ? min / i2 : 1.0f);
                float f4 = floor * ((1.0f - min2) / (this.mPages - 1));
                rectF.left = MathUtils.constrain((f2 * min2) + f4, 0.0f, 1.0f);
                float constrain = MathUtils.constrain((f3 * min2) + f4, 0.0f, 1.0f);
                rectF.right = constrain;
                if (rectF.left > constrain) {
                    rectF.left = 0.0f;
                    rectF.right = 1.0f;
                }
            }
            if (rectF != null && LOCAL_COLOR_BOUNDS.contains(rectF)) {
                Rect rect = new Rect((int) Math.floor(rectF.left * this.mMiniBitmap.getWidth()), (int) Math.floor(rectF.top * this.mMiniBitmap.getHeight()), (int) Math.ceil(rectF.right * this.mMiniBitmap.getWidth()), (int) Math.ceil(rectF.bottom * this.mMiniBitmap.getHeight()));
                if (!rect.isEmpty()) {
                    wallpaperColors = getLocalWallpaperColors(rect);
                }
            }
            ((ArraySet) this.mProcessedRegions).add(rectF2);
            arrayList.add(wallpaperColors);
        }
        ArrayList arrayList2 = new ArrayList(this.mPendingRegions);
        ((ArrayList) this.mPendingRegions).clear();
        Trace.endSection();
        this.mWallpaperLocalColorExtractorCallback.onColorsProcessed(arrayList2, arrayList);
    }
}
