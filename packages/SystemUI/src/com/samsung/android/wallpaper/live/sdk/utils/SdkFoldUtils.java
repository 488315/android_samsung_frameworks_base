package com.samsung.android.wallpaper.live.sdk.utils;

import android.app.WallpaperManager;
import android.content.Context;
import com.samsung.android.wallpaper.live.sdk.service.reflect.WallpaperManagerReflector;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class SdkFoldUtils {
    public static boolean isFolded(Context context) {
        Method method = WallpaperManagerReflector.sMethodGetLidState;
        return ((Integer) SdkReflectUtils.invoke(WallpaperManager.getInstance(context), WallpaperManagerReflector.sMethodGetLidState, new Object[0])).intValue() == 0;
    }
}
