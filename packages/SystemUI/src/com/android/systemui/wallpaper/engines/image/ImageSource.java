package com.android.systemui.wallpaper.engines.image;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider$$ExternalSyntheticOutline0;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.PluginWallpaper;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import java.util.ArrayList;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ImageSource {
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

    public interface ImageSupplier {
        String getFilterData();

        WallpaperImage getWallpaperImage();

        default boolean supportWallpaperScrolling() {
            return false;
        }
    }

    public class WallpaperImage {
        public final Bitmap mBitmap;
        public final ArrayList mCropRects;

        public WallpaperImage(Bitmap bitmap, ArrayList<Rect> arrayList, boolean z) {
            this.mBitmap = bitmap;
            this.mCropRects = arrayList;
        }
    }

    public ImageSource(Context context, CoverWallpaper coverWallpaper, PluginWallpaper pluginWallpaper, int i, int i2, int i3) {
        int sourceWhich = WhichChecker.getSourceWhich(i);
        this.TAG = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "ImageWallpaper_", "[ImageSource]");
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
        try {
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
            if (edmMonitor != null && edmMonitor.mIsLockscreenWallpaperConfigured && WhichChecker.isFlagEnabled(this.mWhich, 2)) {
                return this.mMdmSupplier;
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getImageSupplier: e=", e, this.TAG);
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
        if (this.isMultipack()) {
            if (((PluginWallpaperController) this.mPluginWallpaper).isPluginWallpaperRequired(this.mWhich)) {
                Log.i(this.TAG, "getImageSupplier: Get plugin wallpaper");
                return this.mPluginSupplier;
            }
        }
        return this.mWallpaperManagerSupplier;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00df  */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isFixedOrientation(boolean z, SettingsHelper settingsHelper) {
        boolean z2;
        boolean z3;
        int i;
        boolean z4;
        ?? r5;
        boolean z5;
        int i2 = this.mWhich;
        PackageManager packageManager = this.mContext.getPackageManager();
        boolean z6 = packageManager != null && packageManager.hasSystemFeature("com.samsung.feature.device_category_tablet");
        boolean z7 = Rune.SUPPORT_SUB_DISPLAY_MODE;
        boolean z8 = (!z7 || Rune.SUPPORT_COVER_DISPLAY_WATCHFACE || WhichChecker.isFlagEnabled(i2, 16)) ? false : true;
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        int i3 = this.mWhich;
        int i4 = this.mUserId;
        Bundle wallpaperExtras = wallpaperManager.getWallpaperExtras(i3, i4);
        if (wallpaperExtras != null) {
            boolean z9 = wallpaperExtras.getBoolean("isFixedOrientation");
            String str = this.TAG;
            z2 = false;
            z3 = true;
            StringBuilder sb = new StringBuilder("isFixedOrientationWallpaper: extra defines fixedOrientation. which=");
            ViewPager$$ExternalSyntheticOutline0.m(sb, this.mWhich, ", user=", i4, ", isTablet=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z6, ", isFoldMainDisplay=", z8, ", isFixedOrientation=");
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sb, z9, str);
            if (z9) {
                return (z6 || z8) ? false : true;
            }
            i = 16;
        } else {
            z2 = false;
            z3 = true;
            i = 16;
        }
        boolean zIsFlagEnabled = WhichChecker.isFlagEnabled(i2, i);
        if (z7 && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && WhichChecker.isFlagEnabled(i2, i)) {
            boolean z10 = z3;
            z4 = z10 ? 1 : 0;
            r5 = z10;
        } else {
            z4 = z2;
            r5 = z3;
        }
        boolean z11 = (!WhichChecker.isFlagEnabled(i2, r5) ? settingsHelper.getLockscreenWallpaperTransparent(zIsFlagEnabled) == 0 : settingsHelper.getHomescreenWallpaperSource(zIsFlagEnabled) == 0) ? z2 : true;
        boolean z12 = (((i2 & 60) == 4 || z4) && !((!Rune.WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER && !z4) || z6 || z11 || z)) ? true : z2;
        if (WhichChecker.isFlagEnabled(this.mWhich, 2) && this.mWallpaperType == 3) {
            int i5 = this.mWhich;
            PluginWallpaper pluginWallpaper = this.mPluginWallpaper;
            if (((PluginWallpaperController) pluginWallpaper).isPluginWallpaperRequired(i5)) {
                if (((PluginWallpaperController) pluginWallpaper).containsVideo(this.mWhich)) {
                    z5 = true;
                }
            }
        } else {
            z5 = z2;
        }
        String str2 = this.TAG;
        StringBuilder sb2 = new StringBuilder("isFixedOrientationWallpaper:  which=");
        sb2.append(this.mWhich);
        sb2.append(", feature=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, Rune.WPAPER_SUPPORT_INCONSISTENCY_WALLPAPER, ", isTablet=", z6, ", isFoldMainDisplay=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, z8, ", isFoldSubDisplay=", z4, ", isCustomWallpaper=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, z11, ", isPreview=", z, ", isCustompackContainsVideo=");
        sb2.append(z5);
        sb2.append(", isFixedOrientation=");
        sb2.append(z12);
        Log.i(str2, sb2.toString());
        if (z12) {
            return true;
        }
        if (!z5 || z8 || z6) {
            return z2;
        }
        return true;
    }

    public final boolean isMultipack() {
        int i = this.mWallpaperType;
        return i == 3 || i == 1000;
    }

    public final WallpaperImage loadWallpaper() {
        WallpaperImage wallpaperImage = getSupplier().getWallpaperImage();
        Bitmap bitmap = wallpaperImage.mBitmap;
        if (bitmap != null && bitmap.getByteCount() > RecordingCanvas.MAX_BITMAP_SIZE) {
            if (!DeviceType.isShipBuild()) {
                throw new RuntimeException("Wallpaper is too large! w=" + bitmap.getWidth() + ", h=" + bitmap.getHeight());
            }
            Log.e(this.TAG, "loadWallpaper: Wallpaper is too large! w=" + bitmap.getWidth() + ", h=" + bitmap.getHeight());
            bitmap.recycle();
            return null;
        }
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
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                sb2.append((Rect) obj);
                sb2.append(" ");
            }
        }
        sb.append(sb2.toString());
        Log.i(str, sb.toString());
        return wallpaperImage;
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
        try {
            WallpaperImage wallpaperImageLoadWallpaper = loadWallpaper();
            if (wallpaperImageLoadWallpaper == null) {
                Log.w(this.TAG, "useBitmap: bitmap not loaded");
                return;
            }
            consumer.accept(wallpaperImageLoadWallpaper);
            Bitmap bitmap = wallpaperImageLoadWallpaper.mBitmap;
            if (bitmap == null || bitmap.isRecycled()) {
                return;
            }
            wallpaperImageLoadWallpaper.mBitmap.recycle();
        } catch (Exception e) {
            WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("useBitmap: e=", e, this.TAG, e);
        }
    }
}
