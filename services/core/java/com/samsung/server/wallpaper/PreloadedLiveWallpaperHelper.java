package com.samsung.server.wallpaper;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.server.wallpaper.WallpaperData;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PreloadedLiveWallpaperHelper {
    public SemWallpaperManagerService.AnonymousClass1 mCallback;
    public Context mContext;
    public ProviderRequester mProviderRequester;

    public static boolean isStockLiveWallpaperComponent(ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        return "com.samsung.android.wallpaper.live".equals(componentName.getPackageName());
    }

    public static void recoverComponentNameIfMissed(WallpaperData wallpaperData) {
        Bundle bundle;
        String str;
        if (wallpaperData.wallpaperComponent != null) {
            return;
        }
        Log.d(
                "PreloadedLiveWallpaperHelper",
                "recoverComponentNameIfMissed: ComponentName is null. Recovering...");
        SemWallpaperData semWallpaperData = wallpaperData.mSemWallpaperData;
        if (semWallpaperData == null || (bundle = semWallpaperData.mExternalParams) == null) {
            return;
        }
        String string = bundle.getString("contentType");
        if (TextUtils.isEmpty(string)) {
            return;
        }
        string.getClass();
        switch (string) {
            case "layered":
                str = "com.samsung.android.wallpaper.live.layered.LayeredWallpaperService";
                break;
            case "infinity":
                str = "com.samsung.android.wallpaper.live.infinity.InfinityWallpaper";
                break;
            case "weather":
                str = "com.samsung.android.wallpaper.live.weather.effects.WeatherWallpaperService";
                break;
            default:
                str = null;
                break;
        }
        if (str != null) {
            wallpaperData.wallpaperComponent =
                    new ComponentName("com.samsung.android.wallpaper.live", str);
        } else {
            Log.d(
                    "PreloadedLiveWallpaperHelper",
                    "recoverComponentNameIfMissed: Failed to recover ComponentName. contentType = "
                            .concat(string));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.ParcelFileDescriptor fetchThumbnailFile(int r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.server.wallpaper.PreloadedLiveWallpaperHelper.fetchThumbnailFile(int,"
                    + " int, int):android.os.ParcelFileDescriptor");
    }

    public final boolean supportsSamsungLiveWallpaperProvider(WallpaperData wallpaperData) {
        Context context;
        int i = wallpaperData.mSemWallpaperData.mWpType;
        if (i == 0 || i == 1 || i == 4 || i == 5 || i == 8) {
            return true;
        }
        ComponentName componentName = wallpaperData.wallpaperComponent;
        if (componentName == null) {
            return false;
        }
        if (isStockLiveWallpaperComponent(componentName)) {
            return true;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        String packageName = componentName.getPackageName();
        try {
            context = this.mContext.createPackageContext(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            context = null;
        }
        if (context == null) {
            return false;
        }
        List<ProviderInfo> queryContentProviders =
                packageManager.queryContentProviders(
                        context.getApplicationInfo().processName,
                        context.getApplicationInfo().uid,
                        0);
        this.mProviderRequester.getClass();
        String str = packageName + ".provider.sepwallpaper";
        Iterator<ProviderInfo> it = queryContentProviders.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().authority)) {
                return true;
            }
        }
        return false;
    }
}
