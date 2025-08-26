package com.samsung.android.wallpaper.live.sdk.service.reflect;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.samsung.android.wallpaper.live.sdk.utils.SdkReflectUtils;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class WallpaperManagerReflector {
    public static final Method sMethodGetLidState;

    static {
        Class cls = Integer.TYPE;
        SdkReflectUtils.getMethod(WallpaperManager.class, "getWallpaperExtras", cls, cls);
        SdkReflectUtils.getMethod(WallpaperManager.class, "getWallpaperAssetFile", cls, cls, String.class);
        SdkReflectUtils.getMethod(WallpaperManager.class, "semGetWallpaperColors", cls);
        SdkReflectUtils.getMethod(WallpaperManager.class, "wallpaperSupportsWcg", Bitmap.class);
        SdkReflectUtils.getMethod(WallpaperManager.class, "semClearWallpaperThumbnailCache", cls, cls);
        SdkReflectUtils.getMethod(WallpaperManager.class, "semRequestWallpaperColorsAnalysis", cls);
        sMethodGetLidState = SdkReflectUtils.getMethod(WallpaperManager.class, "getLidState", new Class[0]);
        SdkReflectUtils.getMethod(WallpaperManager.class, "setWallpaperComponent", ComponentName.class);
        SdkReflectUtils.getMethod(WallpaperManager.class, "setWallpaperComponentWithExtras", cls, ComponentName.class, String.class, cls, Bundle.class);
        SdkReflectUtils.getMethod(WallpaperManager.class, "clearWallpaper", cls, cls);
    }
}
