package com.android.systemui.wallpaper.engines.image;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class WallpaperManagerImageSupplier implements ImageSource.ImageSupplier {
    public static final Object sLock = new Object();
    public final Context mContext;
    public CropType mCropType;
    public final String mFilterData;
    public final ArrayList mIntelligentCropRects;
    public ArrayList mLegacyCropRects;
    public final ImageSmartCropper mSmartCropper;
    public final int mUserId;
    public final WallpaperManager mWallpaperManager;
    public final int mWhich;

    enum CropType {
        NOT_DETERMINED,
        LEGACY_CROP,
        INTELLIGENT_CROP
    }

    public WallpaperManagerImageSupplier(Context context, int i, int i2, int i3) {
        this.mCropType = CropType.NOT_DETERMINED;
        this.mContext = context;
        this.mWhich = i;
        this.mUserId = i2;
        this.mSmartCropper = new ImageSmartCropper(context, i3);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        this.mWallpaperManager = wallpaperManager;
        Bundle wallpaperExtras = WallpaperManager.getInstance(context).getWallpaperExtras(i, i2);
        String string = wallpaperExtras == null ? null : wallpaperExtras.getString("cropHints");
        if (!TextUtils.isEmpty(string)) {
            this.mIntelligentCropRects = IntelligentCropHelper.parseCropHints(string);
            this.mCropType = CropType.INTELLIGENT_CROP;
        }
        Bundle wallpaperExtras2 = wallpaperManager.getWallpaperExtras(i, i2);
        if (wallpaperExtras2 != null) {
            this.mFilterData = wallpaperExtras2.getString("imageFilterParams");
        }
    }

    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    public final String getFilterData() {
        return this.mFilterData;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0054  */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.app.WallpaperManager] */
    /* JADX WARN: Type inference failed for: r3v4, types: [int] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v9, types: [android.graphics.Bitmap] */
    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ImageSource.WallpaperImage getWallpaperImage() throws Throwable {
        Bitmap bitmap;
        ?? DecodeFileDescriptor;
        ParcelFileDescriptor wallpaperFile;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        Rect rect = null;
        if (this.mCropType == CropType.INTELLIGENT_CROP) {
            try {
                ?? r2 = this.mWallpaperManager;
                DecodeFileDescriptor = this.mWhich;
                wallpaperFile = r2.getWallpaperFile(DecodeFileDescriptor, this.mUserId, false, 0);
            } catch (Exception e) {
                e = e;
                bitmap = null;
            }
            try {
                if (wallpaperFile != null) {
                    try {
                        DecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(wallpaperFile.getFileDescriptor());
                        try {
                            this.mWallpaperManager.forgetLoadedWallpaper();
                            bitmap = DecodeFileDescriptor;
                        } catch (Throwable th) {
                            th = th;
                            try {
                                wallpaperFile.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        DecodeFileDescriptor = 0;
                    }
                } else {
                    bitmap = null;
                }
                if (wallpaperFile != null) {
                    wallpaperFile.close();
                }
            } catch (Exception e2) {
                e = e2;
                EmergencyButton$$ExternalSyntheticOutline0.m("getSourceBitmap: e=", e, "ImageWallpaper[WallpaperManagerImageSupplier]");
                if (bitmap != null) {
                }
            }
            if (bitmap != null) {
                Log.e("ImageWallpaper[WallpaperManagerImageSupplier]", "getWallpaperImage: failed to get original bitmap");
                return new ImageSource.WallpaperImage(null, null, false);
            }
            boolean zWallpaperSupportsWcg = this.mWallpaperManager.wallpaperSupportsWcg(bitmap);
            Log.i("ImageWallpaper[WallpaperManagerImageSupplier]", "getWallpaperImage: intelligent crop, " + (SystemClock.elapsedRealtime() - jElapsedRealtime) + "ms");
            return new ImageSource.WallpaperImage(bitmap, this.mIntelligentCropRects, zWallpaperSupportsWcg);
        }
        synchronized (sLock) {
            try {
                this.mWallpaperManager.forgetLoadedWallpaper();
                Bitmap bitmapAsUser = this.mWallpaperManager.getBitmapAsUser(this.mUserId, false, this.mWhich, false);
                if (bitmapAsUser == null) {
                    Log.e("ImageWallpaper[WallpaperManagerImageSupplier]", "getWallpaperImage: failed to get cropped bitmap");
                    return new ImageSource.WallpaperImage(null, null, false);
                }
                this.mWallpaperManager.forgetLoadedWallpaper();
                if (this.mLegacyCropRects == null) {
                    ArrayList arrayList = new ArrayList();
                    this.mLegacyCropRects = arrayList;
                    arrayList.add(new Rect(0, 0, bitmapAsUser.getWidth(), bitmapAsUser.getHeight()));
                    ImageSmartCropper imageSmartCropper = this.mSmartCropper;
                    int i = this.mWhich;
                    if (!imageSmartCropper.needToSmartCrop(i)) {
                        Log.i("ImageWallpaper[WallpaperManagerImageSupplier]", "extractSmartCropRect: not smart crop wallpaper");
                    } else if (bitmapAsUser.getWidth() > bitmapAsUser.getHeight()) {
                        Log.i("ImageWallpaper[WallpaperManagerImageSupplier]", "extractSmartCropRect: wallpaper is landscape");
                    } else {
                        long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                        try {
                            imageSmartCropper.updateSmartCropRect(bitmapAsUser, i, this.mUserId);
                            Rect rect2 = imageSmartCropper.mCropResult;
                            if (rect2 == null) {
                                Log.i("ImageWallpaper[WallpaperManagerImageSupplier]", "extractSmartCropRect: smart crop result is null");
                            } else {
                                Log.i("ImageWallpaper[WallpaperManagerImageSupplier]", "extractSmartCropRect: wpSize=" + BitmapUtils.getBitmapSizeString(bitmapAsUser) + ", smartCropRect=" + rect2 + ", elapsed=" + (SystemClock.elapsedRealtime() - jElapsedRealtime2));
                                rect = rect2;
                            }
                        } catch (Exception e3) {
                            Log.i("ImageWallpaper[WallpaperManagerImageSupplier]", "extractSmartCropRect: e=" + e3, e3);
                        }
                    }
                    if (rect != null) {
                        this.mLegacyCropRects.add(rect);
                    }
                    this.mCropType = CropType.LEGACY_CROP;
                }
                Log.i("ImageWallpaper[WallpaperManagerImageSupplier]", "getWallpaperImage: " + (SystemClock.elapsedRealtime() - jElapsedRealtime) + "ms");
                return new ImageSource.WallpaperImage(bitmapAsUser, this.mLegacyCropRects, this.mWallpaperManager.wallpaperSupportsWcg(bitmapAsUser));
            } finally {
            }
        }
    }

    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    public final boolean supportWallpaperScrolling() {
        String lastCallingPackage;
        int i = this.mWhich;
        if (((i & 1) == 1) && ((lastCallingPackage = WallpaperManager.getLastCallingPackage(this.mContext, i, true)) == null || !lastCallingPackage.endsWith("(clear)"))) {
            String lastCallingPackage2 = WallpaperManager.getLastCallingPackage(this.mContext, i);
            if (!TextUtils.isEmpty(lastCallingPackage2)) {
                lastCallingPackage2.getClass();
                switch (lastCallingPackage2) {
                    case "com.samsung.android.app.aodservice":
                    case "android":
                    case "com.sec.knox.kccagent":
                    case "com.samsung.android.themecenter":
                    case "com.android.wallpaper.livepicker":
                    case "com.android.systemui":
                    case "com.samsung.android.app.dressroom":
                        break;
                    default:
                        Log.i("ImageWallpaper[WallpaperManagerImageSupplier]", "supportWallpaperScrolling: lastCallingPkg=".concat(lastCallingPackage2));
                        return true;
                }
            }
        }
        return false;
    }
}
