package com.android.systemui.wallpaper.provider;

import android.app.WallpaperManager;
import android.content.Context;

/* loaded from: classes3.dex */
public class BgRegionGenerator {
    public final Context mContext;
    public final WallpaperManager mWallMgr;

    public BgRegionGenerator(Context context) {
        this.mContext = context.getApplicationContext();
        this.mWallMgr = WallpaperManager.getInstance(context);
    }
}
