package com.samsung.android.wallpaper.live.sdk.utils;

import android.app.WallpaperManager;
import android.content.Context;
import com.samsung.android.wallpaper.live.sdk.service.reflect.WallpaperManagerReflector;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SdkFoldUtils {
    public static boolean isFolded(Context context) {
        Method method = WallpaperManagerReflector.sMethodGetLidState;
        return ((Integer) SdkReflectUtils.invoke(WallpaperManager.getInstance(context), WallpaperManagerReflector.sMethodGetLidState, new Object[0])).intValue() == 0;
    }
}
