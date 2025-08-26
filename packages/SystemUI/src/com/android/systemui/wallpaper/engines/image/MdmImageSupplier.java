package com.android.systemui.wallpaper.engines.image;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.samsung.android.knox.lockscreen.LSOConstants;
import java.io.File;

/* loaded from: classes3.dex */
public class MdmImageSupplier implements ImageSource.ImageSupplier {
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

    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    public final ImageSource.WallpaperImage getWallpaperImage() {
        Bitmap bitmap;
        File file = new File(LSOConstants.ADMIN_LOCKSCREEN_WALLPAPER_PORTRAIT);
        if (file.exists() && file.canRead()) {
            try {
                bitmap = new BitmapDrawable(this.mContext.getResources(), LSOConstants.ADMIN_LOCKSCREEN_WALLPAPER_PORTRAIT).getBitmap();
            } catch (Exception e) {
                EmergencyButton$$ExternalSyntheticOutline0.m("getWallpaperImage: e=", e, "MdmImageSupplier");
            }
        } else {
            bitmap = null;
        }
        return new ImageSource.WallpaperImage(bitmap, null, bitmap == null ? false : this.mWallpaperManager.wallpaperSupportsWcg(bitmap));
    }
}
