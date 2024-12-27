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

public final class WallpaperManagerImageSupplier implements ImageSource.ImageSupplier {
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

    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.wallpaper.engines.image.ImageSource.WallpaperImage getWallpaperImage() {
        /*
            Method dump skipped, instructions count: 355
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.image.WallpaperManagerImageSupplier.getWallpaperImage():com.android.systemui.wallpaper.engines.image.ImageSource$WallpaperImage");
    }

    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    public final boolean supportWallpaperScrolling() {
        int i = this.mWhich;
        if (!((i & 1) == 1)) {
            return false;
        }
        String lastCallingPackage = WallpaperManager.getLastCallingPackage(this.mContext, i);
        if (TextUtils.isEmpty(lastCallingPackage)) {
            return false;
        }
        lastCallingPackage.getClass();
        switch (lastCallingPackage) {
            case "android":
            case "com.sec.knox.kccagent":
            case "com.samsung.android.themecenter":
            case "com.android.wallpaper.livepicker":
            case "com.android.systemui":
            case "com.samsung.android.app.dressroom":
                break;
            default:
                Log.i("ImageWallpaper[WallpaperManagerImageSupplier]", "supportWallpaperScrolling: lastCallingPkg=".concat(lastCallingPackage));
                break;
        }
        return false;
    }
}
