package com.android.systemui.wallpaper.engines.image;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.PluginWallpaper;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.engines.WallpaperSource;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ImageSource implements WallpaperSource {
    public String TAG;
    public final Context mContext;
    public CoverImageSupplier mCoverSupplier;
    public final CoverWallpaper mCoverWallpaper;
    public final int mDisplayId;
    public MdmImageSupplier mMdmSupplier;
    public PluginImageSupplier mPluginSupplier;
    public final PluginWallpaper mPluginWallpaper;
    public final int mUserId;
    public final WallpaperManager mWallpaperManager;
    public WallpaperManagerImageSupplier mWallpaperManagerSupplier;
    public int mWallpaperType;
    public int mWhich;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ImageSupplier {
        String getFilterData();

        WallpaperImage getWallpaperImage();

        default boolean supportWallpaperScrolling() {
            return false;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class WallpaperImage {
        public final Bitmap mBitmap;
        public final ArrayList mCropRects;

        public WallpaperImage(Bitmap bitmap, ArrayList<Rect> arrayList, boolean z) {
            this.mBitmap = bitmap;
            this.mCropRects = arrayList;
        }
    }

    public ImageSource(Context context, CoverWallpaper coverWallpaper, PluginWallpaper pluginWallpaper, int i, int i2, int i3) {
        int sourceWhich = WhichChecker.getSourceWhich(i);
        this.TAG = LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "ImageWallpaper_", "[ImageSource]");
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        this.mWallpaperManager = wallpaperManager;
        this.mContext = context;
        this.mCoverWallpaper = coverWallpaper;
        this.mPluginWallpaper = pluginWallpaper;
        this.mWhich = sourceWhich;
        this.mUserId = i2;
        this.mDisplayId = i3;
        this.mWallpaperType = wallpaperManager.semGetWallpaperType(sourceWhich);
        updateSupplier(sourceWhich);
    }

    public final ImageSupplier getSupplier() {
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor != null && edmMonitor.mIsLockscreenWallpaperConfigured && WhichChecker.isFlagEnabled(this.mWhich, 2)) {
            return this.mMdmSupplier;
        }
        String str = this.TAG;
        StringBuilder sb = new StringBuilder("getImageSupplier: which=");
        sb.append(this.mWhich);
        sb.append(", type=");
        RecyclerView$$ExternalSyntheticOutline0.m(this.mWallpaperType, str, sb);
        if ((WhichChecker.isWatchFace(this.mWhich) || WhichChecker.isVirtualDisplay(this.mWhich)) && this.mWallpaperType == 3) {
            Log.i(this.TAG, "getImageSupplier: Get cover wallpaper.");
            return this.mCoverSupplier;
        }
        int i = this.mWallpaperType;
        if (i == 3 || i == 1000) {
            if (((PluginWallpaperController) this.mPluginWallpaper).isPluginWallpaperRequired(this.mWhich)) {
                Log.i(this.TAG, "getImageSupplier: Get plugin wallpaper");
                return this.mPluginSupplier;
            }
        }
        return this.mWallpaperManagerSupplier;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isFixedOrientation(boolean r12, com.android.systemui.util.SettingsHelper r13) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.image.ImageSource.isFixedOrientation(boolean, com.android.systemui.util.SettingsHelper):boolean");
    }

    public final void updateSupplier(int i) {
        if (WhichChecker.isFlagEnabled(i, 2)) {
            this.mMdmSupplier = new MdmImageSupplier(this.mContext, i);
        }
        this.mWallpaperManagerSupplier = new WallpaperManagerImageSupplier(this.mContext, i, this.mUserId, this.mDisplayId);
        this.mPluginSupplier = new PluginImageSupplier(this.mContext, this.mPluginWallpaper, i);
        this.mCoverSupplier = new CoverImageSupplier(this.mContext, this.mCoverWallpaper);
    }

    public final void useBitmap(Consumer consumer) {
        WallpaperImage wallpaperImage = getSupplier().getWallpaperImage();
        Bitmap bitmap = wallpaperImage.mBitmap;
        if (bitmap == null || bitmap.getByteCount() <= RecordingCanvas.MAX_BITMAP_SIZE) {
            String str = this.TAG;
            StringBuilder sb = new StringBuilder("loadWallpaper: ");
            StringBuilder sb2 = new StringBuilder();
            Bitmap bitmap2 = wallpaperImage.mBitmap;
            if (bitmap2 != null) {
                sb2.append(BitmapUtils.getBitmapSizeString(bitmap2));
            } else {
                sb2.append("noBitmap");
            }
            sb2.append(" crops=");
            ArrayList arrayList = wallpaperImage.mCropRects;
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    sb2.append((Rect) it.next());
                    sb2.append(" ");
                }
            }
            sb.append(sb2.toString());
            Log.i(str, sb.toString());
        } else {
            if (Debug.semIsProductDev()) {
                throw new RuntimeException("Wallpaper is too large! w=" + bitmap.getWidth() + ", h=" + bitmap.getHeight());
            }
            Log.e(this.TAG, "loadWallpaper: Wallpaper is too large! w=" + bitmap.getWidth() + ", h=" + bitmap.getHeight());
            bitmap.recycle();
            wallpaperImage = null;
        }
        consumer.accept(wallpaperImage);
        Bitmap bitmap3 = wallpaperImage.mBitmap;
        if (bitmap3 == null || bitmap3.isRecycled()) {
            return;
        }
        wallpaperImage.mBitmap.recycle();
    }
}
