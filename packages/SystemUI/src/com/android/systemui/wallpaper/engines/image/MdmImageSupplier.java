package com.android.systemui.wallpaper.engines.image;

import android.app.WallpaperManager;
import android.content.Context;
import com.android.systemui.wallpaper.engines.image.ImageSource;

public final class MdmImageSupplier implements ImageSource.ImageSupplier {
    public final Context mContext;
    public final WallpaperManager mWallpaperManager;

    public MdmImageSupplier(Context context, int i) {
        this.mContext = context;
        this.mWallpaperManager = WallpaperManager.getInstance(context);
    }

    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    public final String getFilterData() {
        return "";
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.wallpaper.engines.image.ImageSource.WallpaperImage getWallpaperImage() {
        /*
            r4 = this;
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/data/system/enterprise/lso/lockscreen_wallpaper.jpg"
            r0.<init>(r1)
            boolean r2 = r0.exists()
            r3 = 0
            if (r2 == 0) goto L2c
            boolean r0 = r0.canRead()
            if (r0 == 0) goto L2c
            android.graphics.drawable.BitmapDrawable r0 = new android.graphics.drawable.BitmapDrawable     // Catch: java.lang.Exception -> L24
            android.content.Context r2 = r4.mContext     // Catch: java.lang.Exception -> L24
            android.content.res.Resources r2 = r2.getResources()     // Catch: java.lang.Exception -> L24
            r0.<init>(r2, r1)     // Catch: java.lang.Exception -> L24
            android.graphics.Bitmap r0 = r0.getBitmap()     // Catch: java.lang.Exception -> L24
            goto L2d
        L24:
            r0 = move-exception
            java.lang.String r1 = "getWallpaperImage: e="
            java.lang.String r2 = "MdmImageSupplier"
            com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m(r1, r0, r2)
        L2c:
            r0 = r3
        L2d:
            if (r0 != 0) goto L31
            r4 = 0
            goto L37
        L31:
            android.app.WallpaperManager r4 = r4.mWallpaperManager
            boolean r4 = r4.wallpaperSupportsWcg(r0)
        L37:
            com.android.systemui.wallpaper.engines.image.ImageSource$WallpaperImage r1 = new com.android.systemui.wallpaper.engines.image.ImageSource$WallpaperImage
            r1.<init>(r0, r3, r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.image.MdmImageSupplier.getWallpaperImage():com.android.systemui.wallpaper.engines.image.ImageSource$WallpaperImage");
    }
}
