package com.android.server.am.mars.filter.filter;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;

public final class WallPaperFilter implements IFilter {
    public Context mContext;
    public final ArrayList mWallpaperPackageList = new ArrayList();

    public abstract class WallPaperFilterHolder {
        public static final WallPaperFilter INSTANCE = new WallPaperFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        this.mWallpaperPackageList.clear();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        Context context = this.mContext;
        if (context == null || i != context.getUserId()) {
            return 0;
        }
        if (i3 != 15 || MARsUtils.getScreenOnState()) {
            synchronized (this.mWallpaperPackageList) {
                try {
                    if (this.mWallpaperPackageList.contains(str)) {
                        return 10;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return 0;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        setWallPaperInfoFromWM(1);
        setWallPaperInfoFromWM(2);
    }

    public final void onWallPaperPkgBound(String str) {
        synchronized (this.mWallpaperPackageList) {
            this.mWallpaperPackageList.add(str);
            MARsUtils.addFilterDebugInfoToHistory("FILTER 10", str);
        }
    }

    public final void setWallPaperInfoFromWM(int i) {
        try {
            WallpaperInfo wallpaperInfo =
                    WallpaperManager.getInstance(this.mContext).getWallpaperInfo(i);
            if (wallpaperInfo != null) {
                synchronized (this.mWallpaperPackageList) {
                    this.mWallpaperPackageList.add(wallpaperInfo.getPackageName());
                    MARsUtils.addFilterDebugInfoToHistory(
                            "FILTER 10", wallpaperInfo.getPackageName());
                }
                return;
            }
            Slog.w("MARs:WallPaperFilter", "ignore in case of " + i + " image wallpaper");
        } catch (Exception e) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    e, "getWallpaperPackage() exception ", "MARs:WallPaperFilter");
        }
    }
}
