package android.service.wallpaper;

import android.app.WallpaperColors;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class EngineWindowPage {
    private Bitmap mScreenShot;
    private volatile long mLastUpdateTime = 0;
    private Set<RectF> mCallbackAreas = new ArraySet();
    private Map<RectF, WallpaperColors> mRectFColors = new ArrayMap();

    public void addArea(RectF rectF) {
        this.mCallbackAreas.add(rectF);
    }

    public void addWallpaperColors(RectF rectF, WallpaperColors wallpaperColors) {
        this.mCallbackAreas.add(rectF);
        this.mRectFColors.put(rectF, wallpaperColors);
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = this.mScreenShot;
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        return this.mScreenShot;
    }

    public void removeArea(RectF rectF) {
        this.mCallbackAreas.remove(rectF);
        this.mRectFColors.remove(rectF);
    }

    public void setLastUpdateTime(long j) {
        this.mLastUpdateTime = j;
    }

    public long getLastUpdateTime() {
        return this.mLastUpdateTime;
    }

    public WallpaperColors getColors(RectF rectF) {
        return this.mRectFColors.get(rectF);
    }

    public void setBitmap(Bitmap bitmap) {
        this.mScreenShot = bitmap;
    }

    public Set<RectF> getAreas() {
        return this.mCallbackAreas;
    }

    public void removeColor(RectF rectF) {
        this.mRectFColors.remove(rectF);
    }
}
