package com.android.systemui.wallpaper.engines.image;

import android.app.WallpaperManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* JADX WARN: Removed duplicated region for block: B:50:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0054  */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.app.WallpaperManager] */
    /* JADX WARN: Type inference failed for: r3v4, types: [int] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v9, types: [android.graphics.Bitmap] */
    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.wallpaper.engines.image.ImageSource.WallpaperImage getWallpaperImage() {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.image.WallpaperManagerImageSupplier.getWallpaperImage():com.android.systemui.wallpaper.engines.image.ImageSource$WallpaperImage");
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
